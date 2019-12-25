package ml.huytools.lib.API;

import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiParameters {

    /**
     * Danh sách các parameters
     */
    private LinkedHashMap<String, Parameter> params;

    public ApiParameters(){
        params = new LinkedHashMap<>();
    }

    /**
     * Thêm parameter key=value&....
     * @param key
     * @param value
     */
    public void add(String key, String value){
        params.put(key, new Parameter(key, value));
    }

    /**
     * Thêm file vào parameters
     * @param key
     * @param file
     */
    public void add(String key, File file){
        params.put(key, new Parameter(key, file));
    }

    public void add(String key, String filename, byte[] bytes){
        params.put(key, new Parameter(key, filename, bytes));
    }

    public void add(String key, String filename, Bitmap bitmap){
        params.put(key, new Parameter(key, filename, bitmap));
    }

    /**
     * Xóa parameter qua key
     * @param key
     */
    public void remove(String key){
        params.remove(key);
    }

    /**
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/POST
     * Ghi dữ liệu của các parameter vào 'dataOutputStream'
     *
     * @param dataOutputStream
     * @return
     */
    public void writeToDataOutputStream(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<String, Parameter> parameterEntry : params.entrySet()) {
            parameterEntry.getValue().writeToDataOutputStream(dataOutputStream);
        }

        /**
         * Header:
         *
         *      --****--
         */
        dataOutputStream.writeBytes(Parameter.CRLF);
        dataOutputStream.writeBytes(Parameter.TWO_HYPHENS + Parameter.BOUNDARY + Parameter.TWO_HYPHENS + Parameter.CRLF);
    }



    public static class Parameter {
        /**
         * Các kí hiệu trong "multipart/form-data" POST
         *
         */
        public static final String BOUNDARY =  "*****";
        public static final String CRLF = "\r\n";
        public static final String TWO_HYPHENS = "--";

        enum Type { TEXT, FILE, BITMAP, BYTES };
        String key;
        String value;
        String filename;
        File file;
        byte[] bBytes;
        Bitmap bitmap;
        Type type;

        public Parameter(String key, String value) {
            this.key = key;
            this.value = value;
            type = Type.TEXT;
        }

        public Parameter(String key, File file) {
            this.key = key;
            this.file = file;
            type = Type.FILE;
        }

        public Parameter(String key, String filename, Bitmap bitmap) {
            this.key = key;
            this.filename = filename;
            this.bitmap = bitmap;
            type = Type.BITMAP;
        }

        public Parameter(String key, String filename, byte[] bBytes) {
            this.key = key;
            this.filename = filename;
            this.bBytes = bBytes;
            type = Type.BYTES;
        }

        public void writeToDataOutputStream(DataOutputStream dataOutputStream) throws IOException {
            switch (type){
                case FILE:
                    writeDataFile(dataOutputStream);
                    break;

                case TEXT:
                    writeDataText(dataOutputStream);
                    break;

                case BYTES:
                    writeDataBinary(dataOutputStream, filename, bBytes);
                    break;

                case BITMAP:
                    writeDataBitmap(dataOutputStream);
                    break;
            }
        }

        private void writeDataText(DataOutputStream dataOutputStream) throws IOException {
            /***
             * Header:
             *      --*****
             *      Content-Disposition: form-data; name="..."
             *      Content-type: text/plain; charset=UTF-8
             *
             *      [value]
             *
             */
            dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + CRLF);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\""+ CRLF);
            dataOutputStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + CRLF);
            dataOutputStream.writeBytes(CRLF);
            dataOutputStream.writeBytes(value + CRLF);
            dataOutputStream.flush();
        }

        private void writeDataFile(DataOutputStream dataOutputStream) throws IOException {
            // read bytes file
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(bytes, 0, bytes.length);
            bufferedInputStream.close();
            // write to data output stream
            writeDataBinary(dataOutputStream, file.getName(), bytes);
        }

        private void writeDataBitmap(DataOutputStream dataOutputStream) throws IOException {
            /**
             * https://stackoverflow.com/a/7698445/9946233
             */
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
            // write to data output stream
            writeDataBinary(dataOutputStream, filename, byteArrayOutputStream.toByteArray());
        }

        private void writeDataBinary(DataOutputStream dataOutputStream, String filename, byte[] bytes) throws IOException {
            /***
             * Header:
             *      --*****
             *      Content-Disposition: form-data; name="..."; filename="..."
             *
             *      [data]
             */
            StringBuilder disposition = new StringBuilder();
            disposition.append("Content-Disposition: form-data; ");
            disposition.append("name=\"" + key + "\";");
            disposition.append("filename=\"" + filename + "\"" + CRLF);

            dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + CRLF);
            dataOutputStream.writeBytes(disposition.toString());
            dataOutputStream.writeBytes(CRLF);
            dataOutputStream.write(bytes);
        }


    }
}
