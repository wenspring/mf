package meifeng.mobile.kevin.com.meifeng.httpservice.requesthttp;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.ByteString;

/**
 * Created by kevin.w on 2018/4/18.
 */
public class FileParamsRequest {

    public static Request getPicFileRequest(String url, File file, Map<String, String> maps) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (maps == null) {
            builder.addPart(
                    Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
                    RequestBody.create(MediaType.parse("image/png"), file))
                    .build();
        } else {
            for (String key : maps.keySet()) {
                builder.addFormDataPart(key, maps.get(key));
            }
            builder.addPart(
                    Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
                    RequestBody.create(MediaType.parse("image/png"), file)
            );
        }

        RequestBody body = builder.build();
        //return new Request.Builder().url(url).post(body).header(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + SelfApplication.userToken.getAccess_token()).build();
        return new Request.Builder().url(url).post(body).build();
    }

//    public static Request getFileRequest(String url, File file, Map<String, String> maps) {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if (maps == null) {
//            builder.addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
//                    RequestBody.create(MediaType.parse("image/png"), file))
//                    .build();
//        } else {
//            for (String key : maps.keySet()) {
//                builder.addFormDataPart(key, maps.get(key));
//            }
//            builder.addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
//                    RequestBody.create(MediaType.parse("image/png"), file)
//            );
//        }
//
//        RequestBody body = builder.build();
//        return new Request.Builder().url(url).post(body).header(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + SelfApplication.userToken.getAccess_token()).build();
//    }

//    public static Request getLogFileRequest(String url, File file) {
//        RequestBody fileBody = RequestBody.create(MediaType.parse("File/*"), file);
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//
//        Log.e("getLogFileRequest", file.length() + "");
//        builder
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.log\""),
//                        RequestBody.create(MediaType.parse("multipart/form-data;charset=utf-8"), file)
//                )
//                .addFormDataPart("file", "file.log", fileBody)
//        .addFormDataPart("app_name", "barcode").addFormDataPart("username", SHAREPreferencesUtils.getUserNOrPwd(Fields.FLAG_SP_LOCAL_USER_INFO_USERNAME,SelfApplication.getContext()));
//
//        RequestBody body = builder.build();
//        return new Request.Builder().url(url).post(body).build();
//    }

//    public static Request getAudioFileRequest(String url, File file, Map<String, String> maps) {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if (maps == null) {
//            builder.addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.amr\""),
//                    RequestBody.create(MediaType.parse("audio/amr"), file))
//                    .build();
//        } else {
//            for (String key : maps.keySet()) {
//                builder.addFormDataPart(key, maps.get(key));
//            }
//            builder.addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.amr\""),
//                    RequestBody.create(MediaType.parse("audio/amr"), file)
//            );
//        }
//
//        RequestBody body = builder.build();
//        return new Request.Builder().url(url).post(body).header(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + SelfApplication.userToken.getAccess_token()).build();
//    }

//    // 带参数(String)
//    public static Request getFileRequestWithParamsBytes(String url, ArrayList<String> poNumbers, String jsonString, byte[] bytes) {
//        RequestBody body = null;
//        if (!TextUtils.isEmpty(jsonString) && bytes != null) {
//            MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式
//            HashMap<String, Object> m = new HashMap<>();
//            m.put("purchaseOrderNumbers", poNumbers);
//            m.put("signatureBinary", ByteString.of(bytes).base64());
//            body = RequestBody.create(mediaTypeJson, new Gson().toJson(m));
//        }
//        return new Request.Builder().url(url).post(body).header(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + SelfApplication.userToken.getAccess_token()).build();
//    }

}
