package pz.rg.menu;

import pz.rg.mydialog.MyDialog;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.dreamfly.AboutContent;
import com.example.dreamfly.MainActivity;
import com.example.dreamfly.NewsContent;
import com.example.dreamfly.NewsPage;
import com.example.dreamfly.R;

public class BottomMenu{

	private static int screenWidth;
	private static int screenHeight;

	public static void initPopuptWindow(final PopupWindow popupWindow,View popupWindow_view,final Activity activity) {
		// TODO Auto-generated method stub

		screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();   
        screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
		popupWindow_view.setFocusable(true); // 这个很重要
		popupWindow_view.setFocusableInTouchMode(true);				
		popupWindow.setFocusable(true);
		
	    // 重写onKeyListener
		popupWindow_view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		
        popupWindow_view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				return true;
			}
		});
		// pop.xml	
		Button homepage = (Button) popupWindow_view.findViewById(R.id.homepage);
		homepage.setWidth(screenWidth/4);
		homepage.setHeight(screenHeight/9);
		Button update = (Button) popupWindow_view.findViewById(R.id.update);
		update.setWidth(screenWidth/4);
		update.setHeight(screenHeight/9);
		Button about = (Button) popupWindow_view.findViewById(R.id.about);
		about.setWidth(screenWidth/4);
		about.setHeight(screenHeight/9);
		Button exit = (Button) popupWindow_view.findViewById(R.id.exit);
		exit.setWidth(screenWidth/4);
		exit.setHeight(screenHeight/9);
		
		
		homepage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity!=MainActivity.mainActivity) {
					Intent intent=new Intent(activity,MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					activity.startActivity(intent);
					
				}
				popupWindow.dismiss();
			}
		});
		
		// 淇濆瓨
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "您的软件版本已是最新版本", Toast.LENGTH_SHORT).show();
				popupWindow.dismiss();
			}
		});
		// 鍏抽棴
		about.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(activity,AboutContent.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				activity.startActivity(intent);
					
				
				popupWindow.dismiss();
			}
		});
		
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 popupWindow.dismiss();   
		    	 View dialog_view = activity.getLayoutInflater().inflate(R.layout.mydialog, null,false);
		    	 PopupWindow dialogPopupWindow= new PopupWindow(dialog_view, screenWidth, screenHeight/2, true);
				 MyDialog.initPopuptWindow(dialogPopupWindow, dialog_view,activity);
				 if (activity==MainActivity.mainActivity) {
					 dialogPopupWindow.showAtLocation(activity.findViewById(R.id.mainactivity), Gravity.CENTER, 0, screenHeight/20);
				}
				if (activity==NewsPage.newsPageactivity) {
					dialogPopupWindow.showAtLocation(activity.findViewById(R.id.newspage_activity), Gravity.CENTER, 0, screenHeight/20);
				}
				if (activity==NewsContent.newsContentactivity) {
					dialogPopupWindow.showAtLocation(activity.findViewById(R.id.newcontent_activity), Gravity.CENTER, 0, screenHeight/20);
				}
	             
			}
		});

	}

	
	
}
