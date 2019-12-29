package ml.huytools.lib.API;

import java.net.HttpURLConnection;

import ml.huytools.lib.API.ApiAuthenticate;

public class JWTAuthenticate extends ApiAuthenticate {
    public final String BEARER = "bearer ";
    private String token;

    public JWTAuthenticate(){
        setToken("");
    }

    public JWTAuthenticate(String token){
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.setHeader("Authorization", BEARER + token);
    }
}
