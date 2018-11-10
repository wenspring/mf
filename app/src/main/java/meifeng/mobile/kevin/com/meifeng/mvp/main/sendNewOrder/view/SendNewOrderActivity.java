package meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackForUploadImageToSevenNiuListener;
import meifeng.mobile.kevin.com.meifeng.callback.CallBackRemovePhotoListener;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onFunctionListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.httpservice.requesthttp.OkhttpUtil;
import meifeng.mobile.kevin.com.meifeng.mvp.main.city.CityActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.NewOderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.CancelDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.PayForDecorateModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.PlaceModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.SendOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.SendOrderModelNewNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view.adapter.ChooseMaterilListViewAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.view.adapter.GridPlaceImgListAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.CreateOrderActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.view.PayPopwindow;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.utils.file.FileUtils;
import meifeng.mobile.kevin.com.meifeng.utils.sevenniu.UploadSevenNiuImage;
import okhttp3.Call;
import okhttp3.Response;

public class SendNewOrderActivity extends BaseBindActivity implements View.OnClickListener {

    ToolBar toolBar;
    @BindView(id = R.id.et_title)
    private EditText etTitle;

    @BindView(id = R.id.et_desc)
    private EditText etDesc;

    @BindView(id = R.id.et_amount)
    private EditText etAmount;

    @BindView(id = R.id.switch_is_other_amount)
    private Switch switchIsOtherAmount;

    @BindView(id = R.id.ll_other_amount)
    private LinearLayout llOtherAmount;

    @BindView(id = R.id.et_other_amount)
    private EditText etOtherAmount;

    @BindView(id = R.id.iv_take_photo_place)
    private ImageView ivTakePhotoPlace;

    @BindView(id = R.id.switch_is_choose_materiel)
    private Switch switchIsChooseMateriel;

    @BindView(id = R.id.ll_product)
    private LinearLayout llProduct;

    @BindView(id = R.id.iv_choose_product, click = true)
    private ImageView ivChooseProduct;

    @BindView(id = R.id.et_end_date)
    private Button btnEndDate;

    @BindView(id = R.id.gv_place)
    private GridView gvPlace;

    @BindView(id = R.id.lv_choose_materil)
    private ListView lvChooseMateril;

    @BindView(id = R.id.tv_work_city, click = true)
    private TextView tvWorkCity;

    @BindView(id = R.id.et_work_address)
    private EditText etWorkAddress;

    GridPlaceImgListAdapter adapterPlace;
    ArrayList<PlaceModel> listPlaces = new ArrayList<>();

    ChooseMaterilListViewAdapter adapterMateril;
    ArrayList<MallModel.productModel> listMateril = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_order_avtivity);

        initData();
        setupUI();
    }

    void initData() {
        PlaceModel model = new PlaceModel();
        model.setAdd(true);
        listPlaces.add(model);
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        toolBar.setFunctionDisplayable(true);
        toolBar.setFunctionImage(R.mipmap.icon_complete);
        toolBar.setFunctionListener(new onFunctionListener() {  //保存发布信息
            @Override
            public void onToolbarFunctionClick() {
                submitOrder();
            }
        });

        // 感谢费
        switchIsOtherAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    llOtherAmount.setVisibility(View.VISIBLE);
                } else {
                    llOtherAmount.setVisibility(View.GONE);
                    etOtherAmount.setText("");
                }
            }
        });

        // 材料选择
        switchIsChooseMateriel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llProduct.setVisibility(View.VISIBLE);
                } else {
                    llProduct.setVisibility(View.GONE);
                }
            }
        });

        adapterPlace = new GridPlaceImgListAdapter(this, listPlaces, new CallBackRemovePhotoListener() {
            @Override
            public void removeImageForUpload(int position) {
                super.removeImageForUpload(position);
                // 删除上传的图片
                if (listPlaces != null && listPlaces.size() > 0) {
                    listPlaces.remove(position);
                    adapterPlace.notifyDataSetChanged();
                }
            }
        });
        gvPlace.setAdapter(adapterPlace);
        gvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlaceModel model = listPlaces.get(position);
                if (model.isAdd()) {
                    if (listPlaces.size() < 5) {
                        showImageSelector(Fields.INT_REQUEST_IMAGE_PLACEIMG, 5 - listPlaces.size()); // 最多可以选择4张图
                    } else {
                        showToastMsg("限制3张图片");
                    }
                }
            }
        });

        // 日期选择
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();

                DatePickerDialog dpDialog = new DatePickerDialog(SendNewOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        btnEndDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                dpDialog.getDatePicker().setMinDate(new Date().getTime() + 60 * 60 * 24);
                dpDialog.show();
            }
        });
    }

    void submitOrder() {
        String title = etTitle.getText().toString();
        String desc = etDesc.getText().toString();
        String amount = etAmount.getText().toString();
        String endDate = btnEndDate.getText().toString();
        String otherAmounts = etOtherAmount.getText().toString();
        float otherAmount = 0.0f;

        String[] citys = tvWorkCity.getText().toString().split("-");
        String address = etWorkAddress.getText().toString();

        if (switchIsOtherAmount.isChecked() && !TextUtils.isEmpty(otherAmounts)) {
            otherAmount = Float.parseFloat(otherAmounts);
        }

        ArrayList<String> placeImages = new ArrayList<>();
        if (TextUtils.isEmpty(title)) {
            showToastMsg("请输入标题");
        } else if (TextUtils.isEmpty(desc)) {
            showToastMsg("请输入描述信息");
        } else if (TextUtils.isEmpty(amount)) {
            showToastMsg("请输入佣金");
        } else if (Float.parseFloat(amount) <= 0.0f) {
            showToastMsg("请输入佣金");
        } else if (switchIsOtherAmount.isChecked() && otherAmount <= 0.0f) {
            showToastMsg("请输入感谢费");
        } else if (listPlaces == null || listPlaces.size() == 1) {
            showToastMsg("请上传场景照片");
        } else if (citys.length != 2 || TextUtils.isEmpty(address)) {
            showToastMsg("请填写施工地址");
        } else if (TextUtils.isEmpty(endDate)) {
            showToastMsg("请选择完成日期");
        } else {

            if (listPlaces != null && listPlaces.size() > 0) {
                for (int i = 0; i < listPlaces.size(); i++) {
                    PlaceModel placeModel = listPlaces.get(i);
                    if (!placeModel.isAdd() && !TextUtils.isEmpty(placeModel.getImgKey())) {
                        placeImages.add(placeModel.getImgKey());
                    }
                }
            }
            showLoading("提交中...");
            OkHttpApi.submitNewOrder(title, desc, Float.parseFloat(amount), switchIsOtherAmount.isChecked(), otherAmount, placeImages, switchIsChooseMateriel.isChecked(), null,
                    endDate, citys[0], citys[1], address, new CallBackForOk<SendOrderModelNewNet>() {
                        @Override
                        public void onFailure(Call call, Exception e) {
                            handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
                        }

                        @Override
                        public void onResponse(SendOrderModelNewNet response) {
                            if (response != null) {
                                if (response.isItem1() == true) {
                                    handler.sendMessageDelayed(handler.obtainMessage(1, response.getItem3().getID()), 0);
                                } else {
                                    handler.sendMessageDelayed(handler.obtainMessage(-1, response.getItem2()), 0);
                                }
                            } else {
                                handler.sendMessageDelayed(handler.obtainMessage(-1, "发布失败"), 0);
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
                case -1:
                    dismissLoading();
                    showToastMsg("" + msg.obj);
                    break;
                case 1:
                    dismissLoading();
                    showToastMsg("订单提交成功，请完成支付");
                    if (msg.obj != null) {
                        payNow(msg.obj + "");
                    }
                    break;
                case 2:
                    dismissLoading();
                    showToastMsg("支付完成");
                    setResult(1);
                    finish();
                    break;
                case 3:
                    dismissLoading();
                    adapterPlace.notifyDataSetChanged();//刷新
                    break;
                case Fields.INT_REQUEST_IMAGE_PLACEIMG:
                    if (msg.obj != null) {
                        String imgPath = msg.obj + "";
                        UploadSevenNiuImage.uploadImageToSevenNiu(imgPath, new CallBackForUploadImageToSevenNiuListener() {
                            @Override
                            public void completed(boolean isSuccess, String imgKey) {
                                super.completed(isSuccess, imgKey);
                                nowProcess++;
                                if (isSuccess) {
                                    PlaceModel model = new PlaceModel();
                                    model.setImgPath(imgPath);
                                    model.setImgKey(imgKey);
                                    listPlaces.add(model);
                                }
                                if (nowProcess == taskCount) {
                                    handler.sendMessageDelayed(handler.obtainMessage(3, ""), 0);
                                }
                            }
                        });
                    }
                    break;
            }
        }
    };

    @BindView(id = R.id.rl_full_view)
    private RelativeLayout rlFullView;

    void payNow(final String ordreId) {
        rlFullView.setVisibility(View.VISIBLE);
        float total = Float.parseFloat(etAmount.getText().toString());
        PopupDismissListener popupDismissListener = new PopupDismissListener();

        new PayPopwindow(this, rlFullView, new CreateOrderActivity.CallBackForPayClickListener() {
            @Override
            public void forPayChoose() {

            }

            @Override
            public void forPayChooseNew(String payType) {
                startPay(ordreId, payType, total);
            }

            @Override
            public void close() {
                rlFullView.setVisibility(View.GONE);

            }
        }, popupDismissListener, total).init();
    }

    void startPay(String orderId, String payType, float total) {
        showLoading("正在付款...");
        OkHttpApi.payForDecorateByOrderId(orderId, total, payType, new CallBackForOk<PayForDecorateModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(PayForDecorateModelNet response) {
                if (response.isSuccessed()) {
                    handler.sendMessageDelayed(handler.obtainMessage(2, ""), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, response.getErrorMsg()), 0);
                }

            }
        });
    }

    public class PopupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //backgroundAlpha(1f);
            rlFullView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_take_photo_place: // 场景拍照
                showImageSelector(Fields.INT_REQUEST_IMAGE_PLACEIMG, 3);
                break;
            case R.id.iv_choose_product: // 选择已购买的商品
                Intent intent = new Intent();
                intent.setClass(this, MyBuyMaterilActivity.class);
                startActivityForResult(intent, Fields.INT_REQUEST_RESULT_CHOOSE_MATERIL);
                break;
            case Fields.INT_REQUEST_IMAGE_PLACEIMG:
                break;
            case R.id.tv_work_city:// 施工省份城市
                Intent intent1 = new Intent();
                intent1.setClass(this, CityActivity.class);
                startActivityForResult(intent1, Fields.INT_REQUEST_RESULT_CITY);
                break;
        }
    }

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
        if (request == Fields.INT_REQUEST_IMAGE_PLACEIMG) { // 上传场景图片
            if (result == RESULT_OK) {
                // Get the result list of select image paths
                final List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                taskCount = paths.size();
                nowProcess = 0;
                showLoading("上传中...");
                for (int i = 0; i < paths.size(); i++) {
                    final int finalI = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            L.print("gogogofinalI====", finalI + "");
                            Bitmap pBitmap = FileUtils.decodeSampledBitmapFromFileSmall(paths.get(finalI));
                            String filePath = FileUtils.saveBitmapToLocal(pBitmap);
                            handler.sendMessageDelayed(handler.obtainMessage(request, filePath), 0);
                            pBitmap.recycle();
                            pBitmap = null;
                        }
                    }).start();
                }
            }
        } else if (request == Fields.INT_REQUEST_RESULT_CITY) {
            if (result == RESULT_OK) {
                String resultCity = data.getStringExtra("k_city").toString();
                tvWorkCity.setText(resultCity);
            }
        }
    }
}
