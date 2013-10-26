package pz.rg.menu;

import hmj.dfly.pc.DreamFly_pcActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.dreamfly.MainActivity;
import com.example.dreamfly.NewsPage;
import com.example.dreamfly.R;

public class LeftMenu {

	
	
	public static void initPopuptWindow(final PopupWindow popupWindow,View popupWindow_view,final Activity activity) {
		// TODO Auto-generated method stub

		popupWindow_view.setFocusable(true); // 这个很重要
		popupWindow_view.setFocusableInTouchMode(true);
		
		
		popupWindow.setAnimationStyle(R.style.AnimationFade);
				
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
		//ImageView left_menu_bg=(ImageView)popupWindow_view.findViewById(R.id.left_menu_bg);
		//left_menu_bg.getBackground().setAlpha(200);
		Button jrpz = (Button) popupWindow_view.findViewById(R.id.jrpz);
		//jrpz.getBackground().setAlpha(200);
		Button xytb = (Button) popupWindow_view.findViewById(R.id.xytb);
		//xytb.getBackground().setAlpha(200);
		Button stfc = (Button) popupWindow_view.findViewById(R.id.stfc);
		//stfc.getBackground().setAlpha(200);
		Button xszz = (Button) popupWindow_view.findViewById(R.id.xszz);
		//xszz.getBackground().setAlpha(200);
	    Button pcbx = (Button) popupWindow_view.findViewById(R.id.pcbx);
	    //pcbx.getBackground().setAlpha(200);
		
		
		jrpz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity!=MainActivity.mainActivity) {
					
					activity.finish();
				}
				Intent intent=new Intent(activity,NewsPage.class);
				intent.putExtra("catid", "77");
				activity.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		
		// 淇濆瓨
		xytb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity!=MainActivity.mainActivity) {
					
					activity.finish();
				}
				Intent intent=new Intent(activity,NewsPage.class);
				intent.putExtra("catid", "45");
				activity.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		// 鍏抽棴
		stfc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity!=MainActivity.mainActivity) {
					
					activity.finish();
				}
				Intent intent=new Intent(activity,NewsPage.class);
				intent.putExtra("catid", "102");
				activity.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		
		xszz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity!=MainActivity.mainActivity) {
					
					activity.finish();
				}
				Intent intent=new Intent(activity,NewsPage.class);
				intent.putExtra("catid", "101");
				activity.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		
		pcbx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity!=MainActivity.mainActivity) {
					
					activity.finish();
				}
				Intent intent=new Intent(activity,DreamFly_pcActivity.class);
				activity.startActivity(intent);
				popupWindow.dismiss();
			}
		});

		
	}
}
