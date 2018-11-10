package meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.view.producOrdertDetailView;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.base.BaseBindActivity;
import meifeng.mobile.kevin.com.meifeng.ext.bindview.BindView;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.ToolBar;
import meifeng.mobile.kevin.com.meifeng.ext.toolbar.onBackListener;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyOrderModelNet;

public class FlowInfoActivity extends BaseBindActivity {

    private ToolBar toolBar;

    @BindView(id = R.id.tv_order_no)
    private TextView tvOrderNo;

    @BindView(id = R.id.tv_flow_company)
    private TextView tvFlowCompany;

    @BindView(id = R.id.tv_flow_no)
    private TextView tvFlowNumber;

    @BindView(id = R.id.tv_flow_info)
    private TextView tvFlowInfo;

    private MyOrderModelNet myOrderModelNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_info);

        intiData();
        setupUI();
    }

    void intiData() {
        if (getIntent() != null) {
            myOrderModelNet = (MyOrderModelNet) getIntent().getSerializableExtra("k_my_order");
        }
    }

    void setupUI() {
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        toolBar.setBackListener(new onBackListener() {
            @Override
            public void onToolbarBackClick() {
                finish();
            }
        });

        if (myOrderModelNet != null) {
            tvOrderNo.setText("订单编号: " + myOrderModelNet.getID() + "");
            tvFlowCompany.setText("物流公司：" + myOrderModelNet.getFlowCompany());
            tvFlowNumber.setText("物流公司：" + myOrderModelNet.getFlowNumber());
            tvFlowInfo.setText("已出库...");
        }

    }
}
