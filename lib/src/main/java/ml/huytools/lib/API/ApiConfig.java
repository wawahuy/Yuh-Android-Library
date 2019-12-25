package ml.huytools.lib.API;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.regex.Pattern;

import ml.huytools.lib.API.ApiAuthenticate;

/**
 * Cấu hình hoạt động cho toàn bộ phiên làm việc của ApiProvider
 *
 */
public class ApiConfig {
    private static String hostname = null;
    private static LinkedList<ApiIntercept> Intercepts = new LinkedList<>();
    private static ApiOutput.Handling customOutput = null;
    private static boolean canIntercept = true;
    private static ApiAuthenticate authenticate;

    /**
     * @return host
     */
    public static String getHostname() {
        return hostname;
    }

    /**
     * Không cần phải là hostname
     * Có thể chứa uri
     * Kết hợp để tạo ra url = hostname + uri
     * @param hostname
     */
    public static void setHostname(String hostname) {
        if(!isUrl(hostname)){
            hostname = "http://" + hostname;
        }

        if(hostname.charAt(hostname.length() - 1) == '/'){
            hostname = hostname.substring(0, hostname.length()-1);
        }

        ApiConfig.hostname = hostname;
    }

    /**
     * Kiểm tra có phải url chứa https:// hoặc http://
     * @param url
     * @return
     */
    public static boolean isUrl(String url){
        return Pattern.matches("([hH][tT][tT][pP][sS]?):\\/\\/(.*)", url);
    }

    /**
     * Kiểm tra phải http
     * @param url
     * @return
     */
    public static boolean isHttp(String url){
        return Pattern.matches("([hH][tT][tT][pP]):\\/\\/(.*)", url);
    }

    /**
     * Kiểm tra phải https
     * @param url
     * @return
     */
    public static boolean isHttps(String url){
        return Pattern.matches("([hH][tT][tT][pP][s].):\\/\\/(.*)", url);
    }

    /**
     * Lấy đối tượng xử lý JSON thành ApiOutput
     * @return
     */
    public static ApiOutput.Handling getCustomOutput() {
        return customOutput;
    }

    /**
     * Chuyển đối JSON thành ApiOutput
     * Handling sẽ được gọi mỗi khi có 1 ApiOutput được set JSON
     * @param customOutput
     */
    public static void setCustomOutput(ApiOutput.Handling customOutput) {
        ApiConfig.customOutput = customOutput;
    }

    /***
     * URL = hostname + uri
     * Ưu tiên uri, nếu uri chứa ^http([s]{0,1}):\\\\ => URL = uri
     *
     * @param uri
     * @return
     */
    public static String buildUrl(String uri){
        if(isUrl(uri) || hostname == null){
            return uri;
        }

        if(uri.charAt(0) != '/'){
            return hostname + '/' + uri;
        }

        return hostname + uri;
    }

    /**
     * Thêm mã trước khi thực hiện một connect
     * @param injectHeader
     */
    public static void addIntercept(ApiIntercept injectHeader){
        Intercepts.add(injectHeader);
    }

    /**
     * @param inject
     */
    public static void removeIntercept(ApiIntercept inject){
        Intercepts.remove(inject);
    }

    /**
     * Thực hiện gọi các Intercept ở sự kiện OnHeaderInject
     * @param connection
     * @param uri
     *          uri được so khớp với except hoặc only uri
     */
    public static void applyInterceptHeader(HttpURLConnection connection, String uri){
        if(!canIntercept){
            return;
        }

        for(ApiIntercept c: Intercepts){
            if(c.canInject(uri)){
                c.OnHeaderInject(connection);
            }
        }
    }

    /**
     * Thực hiện gọi các Intercept ở sự kiện OnResult
     * @param output
     * @param uri
     *          uri được so khớp với except hoặc only uri
     */
    public static void applyInterceptOutput(ApiOutput output, String uri){
        if(!canIntercept){
            return;
        }

        for(ApiIntercept c: Intercepts){
            if(c.canInject(uri)){
                c.OnResult(output);
            }
        }
    }

    /**
     * trạng thái tiêm intercept
     * @return
     */
    public static boolean isCanIntercept() {
        return canIntercept;
    }

    /**
     * Bật/tắt intercept trên toàn provider
     * @param canInject
     */
    public static void setCanIntercept(boolean canInject) {
        ApiConfig.canIntercept = canInject;
    }

    public static ApiAuthenticate getAuthenticate() {
        return authenticate;
    }

    public static void setAuthenticate(ApiAuthenticate authenticate) {
        ApiConfig.authenticate = authenticate;
    }
}
