package ml.huytools.lib.API;

import java.net.HttpURLConnection;
import java.util.LinkedList;

/**
 * Đánh chặn và tiêm các đoạn mã theo nhu cầu
 * Cận thận hoạt động nào có thể xãy ra lỗi trên toàn ApiProvider
 *
 */
public abstract class ApiIntercept {
    /**
     * Không inject khi gặp uri này
     * Except uri chỉ có hiệu lực khi onlyUri không chứa phần tử nào
     */
    protected LinkedList<String> exceptUri;

    /**
     * Chỉ inject khi gặp uri này
     */
    protected LinkedList<String> onlyUri;

    public ApiIntercept(){
        exceptUri = new LinkedList<>();
        onlyUri = new LinkedList<>();
    }

    private boolean status = true;

    /**
     * Inject header
     * @param connection
     */
    public abstract void OnHeaderInject(HttpURLConnection connection);

    /**
     * Inject output
     * @param apiOutput
     */
    public abstract void OnResult(ApiOutput apiOutput);


    /**
     * Với uri cung cấp so sánh xem có nên gọi OnInject hay không
     * @param uri
     * @param exceptParams
     * @return
     */
    public boolean canInject(String uri, boolean exceptParams){
        if(!status){
            return false;
        }

        if(exceptParams){
            uri = uri.replaceAll("\\?(.*)$", "");
        }

        if(onlyUri.size() > 0) {
            for (String u:onlyUri) {
                if (u.equals(uri)) {
                    return true;
                }
            }
            return false;
        } else {
            for (String u:exceptUri) {
                if (u.equals(uri)) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean canInject(String uri){
        return canInject(uri, true);
    }

    public final LinkedList<String> getExceptUri() {
        return exceptUri;
    }

    public void addExceptUri(String uri) {
        this.exceptUri.add(uri);
    }

    public void removeExceptUri(String uri) {
        this.exceptUri.remove(uri);
    }

    public final LinkedList<String> getOnlyUri() {
        return onlyUri;
    }

    public void addOnlyUri(String uri) {
        this.onlyUri.add(uri);
    }

    public void removeOnlyUri(String uri) {
        this.onlyUri.remove(uri);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
