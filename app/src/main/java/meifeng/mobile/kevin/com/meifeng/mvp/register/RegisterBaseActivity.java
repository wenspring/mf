package meifeng.mobile.kevin.com.meifeng.mvp.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackForUploadImageToSevenNiuListener;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.register.model.RegisterModelNet;
import meifeng.mobile.kevin.com.meifeng.utils.bitmap.EffectiveBitmapUtils;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.file.FileUtils;
import meifeng.mobile.kevin.com.meifeng.utils.sevenniu.UploadSevenNiuImage;
import okhttp3.Call;

public class RegisterBaseActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.rl_register)
    private RelativeLayout rl_register;

    @BindView(id = R.id.et_username)
    private EditText etUseName;

    @BindView(id = R.id.et_pwd)
    private EditText etPwd;

    @BindView(id = R.id.et_company_mobile)
    private EditText etCompanyMoobile;

    @BindView(id = R.id.et_company_name)
    private EditText etCompanyName;

    @BindView(id = R.id.switch_isbuness)
    private Switch switchIsBuness;

    @BindView(id = R.id.btn_reg_submit, click = true)
    private Button btnRegSubmit;

    @BindView(id = R.id.rl_back, click = true)
    private RelativeLayout rlBack;

    @BindView(id = R.id.iv_choose_head, click = true)
    private ImageView ivChooseHead;

    @BindView(id = R.id.ll_business_view, click = true)
    private LinearLayout llBusinessView;

    @BindView(id = R.id.iv_card_img_face, click = true)
    private ImageView ivCardImgFace;

    @BindView(id = R.id.iv_card_img_back, click = true)
    private ImageView ivCardImgBack;

    @BindView(id = R.id.iv_zhizhao, click = true)
    private ImageView ivZhizhao;

    private String filePathCamera;

    private String mobile;
    //private boolean isBusiness;

    private String userHeadPath;
    private String userCardPathFace;
    private String userCardPathBack;
    private String userZhizhaoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_base);

        Intent intent = getIntent();
        if (intent != null) {
            mobile = intent.getStringExtra("k_mobile").toString();
        }
        setupUI();
    }

    void setupUI() {
        switchIsBuness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llBusinessView.setVisibility(View.VISIBLE);
                } else {
                    llBusinessView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg_submit: // 提交注册
                registerStart();
                break;
            case R.id.rl_back: // 返回
                finish();
                break;
            case R.id.iv_choose_head: // 上传头像
                showImageSelector(Fields.INT_REQUEST_IMAGE_HEAD, 1);
                break;
            case R.id.iv_card_img_face: // 正面
                showImageSelector(Fields.INT_REQUEST_IMAGE_CARD_FACE, 1);
                break;
            case R.id.iv_card_img_back: // 反面
                showImageSelector(Fields.INT_REQUEST_IMAGE_CARD_BACK, 1);
                break;
            case R.id.iv_zhizhao: // 执照
                showImageSelector(Fields.INT_REQUEST_IMAGE_ZHIZHAO, 1);
                break;

        }
    }

    // 开始注册
    void registerStart() {
        String userName = etUseName.getText().toString();
        String pwd = etPwd.getText().toString();

        String companyName = "";
        String companyMobile = "";

        if (switchIsBuness.isChecked()) {
            companyName = etCompanyName.getText().toString();
            companyMobile = etCompanyMoobile.getText().toString();
        }

        String errorMsg = "";
        if (TextUtils.isEmpty(userName)) {
            errorMsg = "请输入用户名";
        } else if (TextUtils.isEmpty(pwd)) {
            errorMsg = "请输入密码";
        } else if (TextUtils.isEmpty(userCardPathFace) || TextUtils.isEmpty(userCardPathBack)) {
            errorMsg = "请输入上传身份证照片";
        } else if (switchIsBuness.isChecked()) {
            if (TextUtils.isEmpty(userZhizhaoPath)) {
                errorMsg = "请输入上营业执照";
            } else if (TextUtils.isEmpty(companyName)) {
                errorMsg = "请输入公司名称";
            } else if (TextUtils.isEmpty(companyMobile)) {
                errorMsg = "请输入公司联系电话";
            }
        }

        if (!TextUtils.isEmpty(errorMsg)) {
            showToastMsg(errorMsg);
        } else {
            showLoading("注册中...");
            String city = ConfigUtils.getChooseCity();
            OkHttpApi.registerUser(userName, mobile, pwd, companyName, "" + userCardPathFace, userCardPathBack, userZhizhaoPath, "", userHeadPath, companyMobile, city, switchIsBuness.isChecked(), new CallBackForOk<RegisterModelNet>() {
                @Override
                public void onFailure(Call call, Exception e) {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
                }

                @Override
                public void onResponse(RegisterModelNet response) {
                    if (response.isSucceed()) {
                        handler.sendMessageDelayed(handler.obtainMessage(1, "注册成功"), 0);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(-1, response.getMessage()), 0);
                    }
                }
            });
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case -1:// failed
                    dismissLoading();
                    showToastMsg(msg.obj + "");
                    break;
                case 1: // success
                    ConfigUtils.saveUserInfo(mobile + "", "");
                    dismissLoading();
                    showToastMsg("" + msg.obj);
                    finish();
                    break;
                case Fields.INT_REQUEST_IMAGE_HEAD:
                    if (msg.obj != null) {
                        String userHeadPathTemp = (String) msg.obj;
                        UploadSevenNiuImage.uploadImageToSevenNiu(userHeadPathTemp, new CallBackForUploadImageToSevenNiuListener() {
                            @Override
                            public void completed(boolean isSuccess, String imgKey) {
                                super.completed(isSuccess, imgKey);
                                dismissLoading();
                                if (isSuccess) {
                                    userHeadPath = imgKey;
                                    Bitmap pBitmap = FileUtils.decodeSampledBitmapFromFile(userHeadPathTemp);
                                    ivChooseHead.setImageBitmap(pBitmap);
                                } else {
                                    showToastMsg("上传失败");
                                }
                            }
                        });
                    }
                    break;
                case Fields.INT_REQUEST_IMAGE_CARD_FACE:
                    if (msg.obj != null) {
                        String userCardPathFaceTemp = (String) msg.obj;
                        UploadSevenNiuImage.uploadImageToSevenNiu(userCardPathFaceTemp, new CallBackForUploadImageToSevenNiuListener() {
                            @Override
                            public void completed(boolean isSuccess, String imgKey) {
                                super.completed(isSuccess, imgKey);
                                dismissLoading();
                                if (isSuccess) {
                                    userCardPathFace = imgKey;
                                    Bitmap pBitmap = FileUtils.decodeSampledBitmapFromFile(userCardPathFaceTemp);
                                    ivCardImgFace.setImageBitmap(pBitmap);
                                } else {
                                    showToastMsg("上传失败");
                                }
                            }
                        });
                    }
                    break;
                case Fields.INT_REQUEST_IMAGE_CARD_BACK:
                    if (msg.obj != null) {
                        String userCardPathBackTemp = (String) msg.obj;
                        UploadSevenNiuImage.uploadImageToSevenNiu(userCardPathBackTemp, new CallBackForUploadImageToSevenNiuListener() {
                            @Override
                            public void completed(boolean isSuccess, String imgKey) {
                                super.completed(isSuccess, imgKey);
                                dismissLoading();
                                if (isSuccess) {
                                    userCardPathBack = imgKey;
                                    Bitmap pBitmap = FileUtils.decodeSampledBitmapFromFile(userCardPathBackTemp);
                                    ivCardImgBack.setImageBitmap(pBitmap);
                                } else {
                                    showToastMsg("上传失败");
                                }
                            }
                        });
                    }
                    break;
                case Fields.INT_REQUEST_IMAGE_ZHIZHAO:
                    if (msg.obj != null) {
                        String userZhizhaoPathTemp = (String) msg.obj;
                        UploadSevenNiuImage.uploadImageToSevenNiu(userZhizhaoPathTemp, new CallBackForUploadImageToSevenNiuListener() {
                            @Override
                            public void completed(boolean isSuccess, String imgKey) {
                                super.completed(isSuccess, imgKey);
                                dismissLoading();
                                if (isSuccess) {
                                    userZhizhaoPath = imgKey;
                                    Bitmap pBitmap = FileUtils.decodeSampledBitmapFromFile(userZhizhaoPathTemp);
                                    ivZhizhao.setImageBitmap(pBitmap);
                                } else {
                                    showToastMsg("上传失败");
                                }
                            }
                        });
                    }
                    break;
            }
        }
    };

    public static int taskCount = 0;
    public static int nowProcess = 0;

    private void showImageSelector(int request_code, int image_max_count) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true); // whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, image_max_count); // max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI); // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, new ArrayList<String>());// default select images (support array list)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, request_code);
    }

    @Override
    protected void onActivityResult(final int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == Fields.INT_REQUEST_IMAGE_HEAD ||
                request == Fields.INT_REQUEST_IMAGE_CARD_FACE ||
                request == Fields.INT_REQUEST_IMAGE_CARD_BACK ||
                request == Fields.INT_REQUEST_IMAGE_ZHIZHAO) { // 头像
            if (result == RESULT_OK) {
                // Get the result list of select image paths
                final List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                taskCount = path.size();
                nowProcess = 0;
                showLoading("上传中...");
                for (int i = 0; i < path.size(); i++) {
                    final int finalI = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap pBitmap = FileUtils.decodeSampledBitmapFromFileSmall(path.get(finalI));
                            String filePath = FileUtils.saveBitmapToLocal(pBitmap);
                            handler.sendMessageDelayed(handler.obtainMessage(request, filePath), 0);
                            pBitmap.recycle();
                            pBitmap = null;
                        }
                    }).start();
                }
            }
        }
    }
}
