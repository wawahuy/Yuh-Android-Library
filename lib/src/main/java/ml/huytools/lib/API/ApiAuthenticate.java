package ml.huytools.lib.API;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ApiAuthenticate extends ApiIntercept {

    private LinkedHashMap<String, String> headersInject;

    ApiAuthenticate(){
        headersInject = new LinkedHashMap<>();
    }

    public void setHeader(String key, String value){
        headersInject.put(key, value);
    }

    public void removeHeader(String key){
        headersInject.remove(key);
    }

    @Override
    public void OnHeaderInject(HttpURLConnection connection) {
        for (Map.Entry<String, String> entry : headersInject.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void OnResult(ApiOutput apiOutput) {
        /// no get data
    }
}
