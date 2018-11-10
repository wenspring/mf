package meifeng.mobile.kevin.com.meifeng.httpservice;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * s
 * 访问网络前进行拦截
 * Created by sunhuining on 2015/11/27.
 */
public class HttpInterceptor implements Interceptor {
    String token = "";

    private HttpInterceptor(Context context) {
        this.context = context;
        resetToken();
    }

    public static HttpInterceptor getInstance(Context context) {
        if (httpInterceptor == null) {
            httpInterceptor = new HttpInterceptor(context);
        }

        return httpInterceptor;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.e("token", Fields.STRING_REQUEST_TOKEN_prefix + token);
        Request.Builder builder = chain.request().newBuilder();

        if (token == null || token.equals("")) {
            builder.removeHeader(Fields.STRING_REQUEST_Authorization);
            builder.addHeader(Fields.STRING_REQUEST_Authorization, "basic64String");
        } else {
            builder.removeHeader(Fields.STRING_REQUEST_Authorization);
            builder.addHeader(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + "" + token);
        }

        Request request = builder.build();
        Response response = chain.proceed(request);

        switch (response.code()){
            case 401:
                //MainActivity.instance.autoLogin(); 自动登录
                break;
        }
        return response;
    }

    public HttpInterceptor resetToken() {
        try {
            //token = SHAREPreferencesUtils.getToken(context);
        } catch (Exception e) {
            Log.e("HttpInterceptor", e.getMessage());
            e.printStackTrace();
        }
        return this;
    }

    private Context context;
    private String deviceInfo;
    private static HttpInterceptor httpInterceptor;

    private static final String TAG = "HttpInterceptor";


}
