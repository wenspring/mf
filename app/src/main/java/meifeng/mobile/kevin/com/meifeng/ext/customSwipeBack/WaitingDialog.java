package meifeng.mobile.kevin.com.meifeng.ext.customSwipeBack;

import android.app.Dialog;
import android.content.Context;


/**
 * Created by kevin.w on 2018/4/1.
 */

public class WaitingDialog extends Dialog {

	public WaitingDialog(Context context) {
		super(context);
	}

	public WaitingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public WaitingDialog(Context context, int theme) {
		super(context, theme);
	}
}
