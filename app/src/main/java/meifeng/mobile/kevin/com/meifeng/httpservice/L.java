package meifeng.mobile.kevin.com.meifeng.httpservice;

import android.util.Log;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

/**
 * Created by kevin.w on 2018/3/30.
 */
public class L {

    private static boolean debug = true;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

    public static void print(String TAG, String MSG)
    {
        if (debug)
        {
            Log.i(TAG, MSG);
        }
    }

    public static void printOKHttpRes(String MSG)
    {
        if (debug)
        {
            Log.i("ok_http_result", MSG);
        }
    }

    public static void pintOkHttpLog(String url, Object params, String jsonString, Object headers)
    {
        if (debug)
        {
            Log.i("okhttp", "url:"+url);
            if (params!=null && !params.equals("null")){
                Log.i("ok_http", "params:"+params.toString());
            }else {
                Log.i("ok_http", "params:null");
            }

            if (jsonString!=null && !UtilsMethod.isEmptyString(jsonString) && !jsonString.equals("null")){
                Log.i("ok_http", "paramsJ:"+jsonString.toString());
            }else {
                Log.i("ok_http", "paramsJ:null");
            }

            if (headers!=null && !headers.equals("null")){
                Log.i("ok_http", "headers:"+headers.toString());
            }else {
                Log.i("ok_http", "headers:null");
            }


        }
    }

}
