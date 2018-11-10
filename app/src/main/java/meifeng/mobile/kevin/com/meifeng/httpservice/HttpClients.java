package meifeng.mobile.kevin.com.meifeng.httpservice;

import android.content.Context;
import android.util.Log;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClients {
    private Context context; //访问SharedPreference
    private OkHttpClient client;
    private static HttpClients httpClients;

    private HttpClients(Context context) {
        this.context = context;
    }

    public static HttpClients getInstance(Context context) {
        if (httpClients == null) {
            httpClients = new HttpClients(context);
        }

        return httpClients;
    }

    public OkHttpClient getDefaultOkClient() { //系统Cookie
        if (client == null) {
            SSLContext sslContext = null;
            TrustManagerFactory trustManagerFactory = null;
            HostnameVerifier hostnameVerifier = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
                hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
            } catch (Exception e) {
                Log.e("OkHttpClient",e.getMessage());
                sslContext = null;
            }

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(HttpInterceptor.getInstance(context));
            builder.addInterceptor(httpLoggingInterceptor);

           // builder.cookieJar(new OkhttpCookiesManager(context));
            if (null != sslContext) {
                builder.sslSocketFactory(sslContext.getSocketFactory());
                builder.hostnameVerifier(hostnameVerifier);
            }
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            client = builder.build();
        }
        return client;
    }
}
