package meifeng.mobile.kevin.com.meifeng.mvp.main.setting;

import android.os.Bundle;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;

public class SettingActivity extends BaseBindActivity {

    private ToolBar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setupUI();
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });
    }
}
