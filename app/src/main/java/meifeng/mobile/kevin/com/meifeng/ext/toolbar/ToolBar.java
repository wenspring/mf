package meifeng.mobile.kevin.com.meifeng.ext.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import meifeng.mobile.kevin.com.meifeng.R;


/**
 * Created by kevin.w on 2018/4/1.
 */

public class ToolBar extends LinearLayout implements OnClickListener {
    private TextView titleView;
    private RelativeLayout backButton;
    private RelativeLayout functionButton;
    private RelativeLayout leftTitleButton;
    private onBackListener backListener;
    private onFunctionListener functionListener;
    private float textSize = 36;
    private ImageView right_img;

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        titleView.setTextSize(textSize);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.toolbar, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ToolBar);
        init(typedArray);
        typedArray.recycle();
    }

    private void init(TypedArray typedArray) {
        String title = typedArray.getString(R.styleable.ToolBar_title_name);
        titleView = (TextView) this.findViewById(R.id.title);
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }
        backButton = (RelativeLayout) this.findViewById(R.id.back);
        backButton.setOnClickListener(this);
        boolean funcitonEnable = typedArray.getBoolean(
                R.styleable.ToolBar_function_enable, true);
        functionButton = (RelativeLayout) this.findViewById(R.id.function);
        functionButton.setOnClickListener(this);

        right_img = (ImageView) findViewById(R.id.right_img);

        if (!funcitonEnable) {
            right_img.setVisibility(View.GONE);
        }
        setFunctionImage(typedArray.getResourceId(
                R.styleable.ToolBar_function_image, R.drawable.icon_arrowleft));
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public void setFunctionDisplayable(boolean displayable) {
        if (displayable) {
            right_img.setVisibility(View.VISIBLE);
        } else {
            right_img.setVisibility(View.GONE);
        }
    }

    public void setFunctionImage(int resourceId) {
        if (resourceId != 0) {
            right_img.setBackgroundResource(resourceId);
        }
    }

    public void setBackListener(onBackListener backListener) {
        this.backListener = backListener;
    }

    public void setFunctionListener(onFunctionListener functionListener) {
        this.functionListener = functionListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                if (backListener != null) {
                    backListener.onToolbarBackClick();
                }
                break;
            case R.id.function:
                if (functionListener != null) {
                    functionListener.onToolbarFunctionClick();
                }
                break;
        }
    }

}
