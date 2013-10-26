package pz.rg.mydialog;

import pz.rg.exit.ExitApplication;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.PopupWindow;
import com.example.dreamfly.R;

public class MyDialog {

	private static boolean dialogisShow=false;


	public static void initPopuptWindow(final PopupWindow popupWindow,View popupWindow_view,final Activity activity) {
		// TODO Auto-generated method stub

		popupWindow_view.setFocusable(true); // �������Ҫ
		popupWindow_view.setFocusableInTouchMode(true);				
		popupWindow.setFocusable(true);
		
	    // ��дonKeyListener
		popupWindow_view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && dialogisShow) {
					popupWindow.dismiss();
					dialogisShow=false;
					return true;
				}
				dialogisShow=true;
				return false;
			}
		});
		// pop.xml	
		Button dialog_button_ok = (Button) popupWindow_view.findViewById(R.id.dialog_button_ok);
		
		dialog_button_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				ExitApplication.getInstance().exit();
			}
		});
		
		Button dialog_button_cancel=(Button)popupWindow_view.findViewById(R.id.dialog_button_cancel);
		dialog_button_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				dialogisShow=false;
			}
		});
		// 保存
		

	}

	
}
