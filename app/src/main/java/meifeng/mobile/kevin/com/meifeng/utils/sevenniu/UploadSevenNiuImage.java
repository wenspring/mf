package meifeng.mobile.kevin.com.meifeng.utils.sevenniu;

import android.text.TextUtils;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import meifeng.mobile.kevin.com.meifeng.callback.CallBackForUploadImageToSevenNiuListener;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;

public class UploadSevenNiuImage {

    public static void uploadImageToSevenNiu(String imgPath, CallBackForUploadImageToSevenNiuListener listener) {

        if (!TextUtils.isEmpty(imgPath)) {
            UploadManager uploadManager = new UploadManager();
            String token = Auth.create(Fields.SevenNiu_App_Access_Key, Fields.SevenNiu_App_Secret_Key).uploadToken("decration");
            uploadManager.put(imgPath, null, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");

                                if (res != null) {
                                    String imgKey = res.optString("key");
                                    L.print("IMG KEY", imgKey);

                                    listener.completed(true, imgKey);
                                }
                            } else {
                                Log.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因

                                listener.completed(false, "");
                            }
                            //Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }
                    }, null);
        }

    }
}
