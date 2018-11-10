package meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.httpservice.L;
import meifeng.mobile.kevin.com.meifeng.utils.UtilsMethod;

public class SearchProductActivity extends BaseBindActivity implements View.OnClickListener {

    @BindView(id = R.id.et_search)
    private EditText etSearch;

    @BindView(id = R.id.btn_cancel, click = true)
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        setupUI();
    }

    void setupUI() {

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //Toast.makeText(AddMemberActivity.this, "呵呵", Toast.LENGTH_SHORT).show();
                    // search pressed and perform your functionality.

                    L.print("search key = ", v.getText().toString());
                }
                return false;
            }
        });

        // 默认弹出键盘
        showSoftInputFromWindow(etSearch);

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel: {
                finish();
            }
            break;
        }
    }
}
