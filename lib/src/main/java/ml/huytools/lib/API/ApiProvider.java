package ml.huytools.lib.API;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


/***
 * ApiProvider.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 16/11/2019
 * Update: 24/11/2019
 *
 */
public class ApiProvider {


    /**
     * Async
     */
    public static class Async implements Runnable {
        Async.AnyRun runnable;
        Callback callback;
        Thread thread;
        ApiParameters params;
        Handler handler;
        int requestCode;

        private Async(Async.AnyRun run){
            thread = new Thread(this);
            params = new ApiParameters();
            runnable = run;
            handler = new Handler(Looper.myLooper());
        }

        private static Async ANY(AnyRun anyRun){
            Async async = new Async(anyRun);
            return async;
        }

        public static Async GET(final String uri){
            return ANY(new Async.AnyRun() {
                @Override
                public ApiOutput run(Async async) {
                    return ApiProvider.GET(uri);
                }
            });
        }

        public static Async POST(final String uri){
            return ANY(new Async.AnyRun() {
                @Override
                public ApiOutput run(Async async) {
                    return ApiProvider.POST(uri, async.params);
                }
            });
        }

        public Async AddParam(String key, String value){
            params.add(key, value);
            return this;
        }

        public Async AddParam(String key, File value){
            params.add(key, value);
            return this;
        }

        public Async AddParam(String key, String filename, byte[] bytes){
            params.add(key, filename, bytes);
            return this;
        }

        public Async AddParam(String key, String filename, Bitmap bitmap){
            params.add(key, filename, bitmap);
            return this;
        }

        public Async SetParams(ApiParameters params){
            this.params = params;
            return this;
        }

        public Async SetRequestCode(int code){
            requestCode = code;
            return this;
        }

        public Async Then(Callback callback){
            this.callback = callback;
            this.thread.start();
            return this;
        }

        @Override
        public void run() {
            // run
            final ApiOutput output = runnable.run(Async.this);

            // run on thread created
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Async.this.callback.OnAPIResult(output, Async.this.requestCode);
                }
            });
        }

        public interface Callback {
            void OnAPIResult(ApiOutput output, int requestCode);
        }

        private interface AnyRun {
            ApiOutput run(Async async);
        }
    }


    /**
     * Create connection
     * @param uri
     * @return
     * @throws IOException
     */
    private static HttpURLConnection CreateConnection(String uri) throws IOException {
        URL url = new URL(ApiConfig.buildUrl(uri));
        HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
        return myConnection;
    }


    /**
     * Read string
     * @param httpURLConnection
     */
    private static String ReadString(HttpURLConnection httpURLConnection){
        try {
            InputStream ip = httpURLConnection.getInputStream();
            InputStreamReader ipr = new InputStreamReader(ip);
            BufferedReader bufferedReader = new BufferedReader(ipr);

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{}";
    }


    /**
     * ALL Method
     * @param uri
     * @param runnable
     * @return
     */
    private static ApiOutput ANY(String uri, AnyRun runnable){
        ApiOutput output = new ApiOutput();
        ApiAuthenticate auth = ApiConfig.getAuthenticate();

        try {
            HttpURLConnection httpURLConnection = CreateConnection(uri);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");

            /// Inject
            ApiConfig.applyInterceptHeader(httpURLConnection,uri);

            /// Auth
            if(auth != null && auth.canInject(uri)){
                auth.OnHeaderInject(httpURLConnection);
            }

            /// Custom
            runnable.run(httpURLConnection);

            /// code
            int responseCode = httpURLConnection.getResponseCode();
            output.Uri = ApiConfig.buildUrl(uri);
            output.ResponseCode = responseCode;

            if(responseCode == HttpURLConnection.HTTP_OK){
                String strJson = ReadString(httpURLConnection);
                output.DataString = strJson;
                output.set(strJson);
                Log(strJson);
            } else {
                Log("Response code: "+ responseCode);
            }

            /// disconnection
            httpURLConnection.disconnect();

        } catch (IOException e) {
            Log(e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log("Error format json");
            e.printStackTrace();
        }

        /// inject
        ApiConfig.applyInterceptOutput(output,uri);

        return output;
    }

    private static ApiOutput ANY_NO_DATA(String uri, final String method){
        return ANY(uri, new AnyRun() {
            @Override
            public void run(HttpURLConnection connection) throws ProtocolException {
                connection.setRequestMethod(method);
            }
        });
    }

    private static ApiOutput ANY_HAS_DATA(String uri, final String method , final ApiParameters params){
        return ANY(uri, new AnyRun() {
            @Override
            public void run(HttpURLConnection connection) throws ProtocolException {
                connection.setRequestMethod(method);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + ApiParameters.Parameter.BOUNDARY);

                //out
                try {
                    OutputStream out = connection.getOutputStream();
                    DataOutputStream dataOutputStream =  new DataOutputStream(out);
                    params.writeToDataOutputStream(dataOutputStream);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log("Error post data");
                }

            }
        });
    }


    /// ---------------------
    public static ApiOutput GET(String uri){
        return ANY_NO_DATA(uri, "GET");
    }

    public static ApiOutput POST(String uri, final ApiParameters params){
        return ANY_HAS_DATA(uri, "POST", params);
    }



    private static void Log(String str){
        Log.v("Log", str);
    }


    interface AnyRun {
        void run(HttpURLConnection connection) throws ProtocolException;
    }

}
