package auth;

import com.google.gson.JsonObject;
import okhttp3.*;

public final class AuthService {
    private static final String API_KEY = "\n" +
            "AIzaSyC8R01Tkm6QfRKGQyR9gqJ5IzsVmY3MCe0";
    private static final OkHttpClient HTTP = new OkHttpClient();

    private static String call(String url, String mail, String pw) throws Exception {
        JsonObject j = new JsonObject();
        j.addProperty("email", mail);
        j.addProperty("password", pw);
        j.addProperty("returnSecureToken", true);

        Request r = new Request.Builder()
                .url(url)
                .post(RequestBody.create(j.toString(), MediaType.get("application/json")))
                .build();
        try (Response rsp = HTTP.newCall(r).execute()) {
            if (!rsp.isSuccessful()) throw new IllegalStateException(rsp.body().string());
            return rsp.body().string();
        }
    }
    public static String signUp(String m,String p)throws Exception{
        return call("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key="+API_KEY,m,p);}
    public static String signIn(String m,String p)throws Exception{
        return call("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="+API_KEY,m,p);}
}

