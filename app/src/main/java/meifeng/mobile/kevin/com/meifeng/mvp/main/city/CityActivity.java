package meifeng.mobile.kevin.com.meifeng.mvp.main.city;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.httpservice.OkHttpApi;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.mvp.main.city.adapter.CityExpandableListAdapter;
import meifeng.mobile.kevin.com.meifeng.mvp.main.city.model.CityModelNet;
import meifeng.mobile.kevin.com.meifeng.utils.config.ConfigUtils;
import meifeng.mobile.kevin.com.meifeng.utils.view.ViewUtil;
import okhttp3.Call;

public class CityActivity extends BaseBindActivity implements View.OnClickListener {

    ToolBar toolBar;
    @BindView(id = R.id.elv_citys)
    private ExpandableListView elvCitys;

    @BindView(id = R.id.et_search_city)
    private EditText etSearchCity;

    @BindView(id = R.id.tv_current_city, click = true)
    private TextView tvCurrentCity;

    @BindView(id = R.id.ll_click_current_city, click = true)
    private LinearLayout llCurrentCity;

    @BindView(id = R.id.elv_citys)
    private ExpandableListView elvCity;

    private CityExpandableListAdapter cityAdapter;
    ArrayList<CityModelNet.ProvinceModel> listProvinces;
    ArrayList<CityModelNet.ProvinceModel> listProvinces_temp = new ArrayList<>();
    private boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        setupUI();
        initData();

        ViewUtil.hideSoftKeyboard(this);
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        elvCity.setGroupIndicator(null); //去除箭頭

        try {
            String[] citys = ConfigUtils.getChooseCity().split("-");
            if (citys.length == 2) {
                tvCurrentCity.setText("" + citys[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        llCurrentCity.setOnClickListener(this);

        elvCitys.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CityModelNet.ProvinceModel provinceModel = null;
                CityModelNet.ProvinceModel.CityModel cityModel = null;

                if (isSearch && listProvinces_temp != null) {
                    provinceModel = listProvinces_temp.get(groupPosition);
                    cityModel = provinceModel.getCities().get(childPosition);
                } else {
                    provinceModel = listProvinces.get(groupPosition);
                    cityModel = provinceModel.getCities().get(childPosition);
                }

                if (cityModel != null) {
                    String newCity = provinceModel.getName() + "-" + cityModel.getName() + "";
                    tvCurrentCity.setText(newCity);
                    Intent data = new Intent();
                    data.putExtra("k_city", newCity);
                    setResult(-1, data);
                    finish();
                } else {
                    tvCurrentCity.setText(cityModel.getName() + "");
                }

                return false;
            }

        });

        etSearchCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ViewUtil.hideSoftKeyboard(CityActivity.this);
                    String key = v.getText().toString();
                    if (!TextUtils.isEmpty(key)) {
                        searchText(key);
                    } else {
                        showToastMsg("请输入搜索关键字");
                    }
                    return true;
                }
                return false;
            }
        });

        etSearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String res = s.toString();
                if (TextUtils.isEmpty(res)) {
                    ViewUtil.hideSoftKeyboard(CityActivity.this);
                    updateExpandableList(listProvinces);
                    setExpanStatus(listProvinces, false);
                    isSearch = false;
                }
            }
        });

        showSoftInputFromWindow(etSearchCity);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    void initData() {
        getAllCitys();
    }

    void searchText(String key) {
        listProvinces_temp.clear();
        if (listProvinces != null && listProvinces.size() > 0) {
            for (int i = 0; i < listProvinces.size(); i++) {

                CityModelNet.ProvinceModel tempP = new CityModelNet().getPModel();
                ArrayList<CityModelNet.ProvinceModel.CityModel> tempC = new ArrayList<>();

                CityModelNet.ProvinceModel v = listProvinces.get(i);
                for (int j = 0; j < v.getCities().size(); j++) {
                    CityModelNet.ProvinceModel.CityModel c = v.getCities().get(j);
                    if (c.getName().toUpperCase().contains(key.toUpperCase())) {
                        tempC.add(c);
                    }
                }

                if (tempC != null && tempC.size() > 0) {
                    tempP.setName(v.getName());
                    tempP.setCities(tempC);
                }

                if (tempP.getCities() != null && tempP.getCities().size() > 0) {
                    listProvinces_temp.add(tempP);
                }

            }
            isSearch = true;
            handler.sendMessageDelayed(handler.obtainMessage(2), 0);
        }

    }

    // 获取所有城市
    void getAllCitys() {

        showLoading("获取城市列表...");
        OkHttpApi.getAllCitys(new CallBackForOk<CityModelNet>() {
            @Override
            public void onFailure(Call call, Exception e) {
                handler.sendMessageDelayed(handler.obtainMessage(-1, e.getMessage()), 0);
            }

            @Override
            public void onResponse(CityModelNet response) {
                if (response.getProvinces() != null && response.getProvinces().size() > 0) {
                    handler.sendMessageDelayed(handler.obtainMessage(1, response.getProvinces()), 0);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(-1, "获取数据失败"), 0);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    showToastMsg("" + msg.obj);
                    dismissLoading();
                    break;
                case 1:
                    dismissLoading();
                    listProvinces = (ArrayList<CityModelNet.ProvinceModel>) msg.obj;

                    if (listProvinces != null) {

                        updateExpandableList(listProvinces);
                        setExpanStatus(listProvinces, false);

                        //cityAdapter = new CityExpandableListAdapter(CityActivity.this, listProvinces);
                        //elvCity.setAdapter(cityAdapter);

                        // OPEN CHILD ITEM
//                        for (int i = 0; i < listProvinces.size(); i++) {
//                            elvCitys.expandGroup(i);
//                        }
                    }
                    break;
                case 2:
                    if (listProvinces_temp != null) {

                        updateExpandableList(listProvinces_temp);
                        setExpanStatus(listProvinces_temp, true);
                        //cityAdapter = new CityExpandableListAdapter(CityActivity.this, listProvinces_temp);
                        //elvCity.setAdapter(cityAdapter);
                    }
                    break;
            }
        }
    };

    void updateExpandableList(ArrayList<CityModelNet.ProvinceModel> list) {
        if (list != null) {
            cityAdapter = new CityExpandableListAdapter(CityActivity.this, list);
            elvCity.setAdapter(cityAdapter);
        }
    }

    // OPEN CHILD ITEM
    void setExpanStatus(ArrayList<CityModelNet.ProvinceModel> listTag, boolean isOpen) {
        for (int i = 0; i < listTag.size(); i++) {
            if (isOpen) {
                elvCitys.expandGroup(i);
            } else {
                elvCitys.collapseGroup(i);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_click_current_city:
                if (!TextUtils.isEmpty(tvCurrentCity.getText().toString())) {
                    Intent data = new Intent();
                    data.putExtra("k_city", ConfigUtils.getChooseCity());
                    setResult(-1, data);
                    finish();
                }
                break;
        }
    }
}
