package com.example.dreamfly;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class WellcomeActivity extends Activity{

	private final int SPLASH_DISPLAY_LENGHT=5000;
	private ImageView wellcome_img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wellcome);
		wellcome_img=(ImageView)this.findViewById(R.id.wellcome_img);
		AnimationSet  animationset=new AnimationSet(true);  
	    AlphaAnimation alphaAnimation=new AlphaAnimation(1, (float) 0.1);  
	    alphaAnimation.setDuration(5000);  
	    animationset.addAnimation(alphaAnimation);  
	    wellcome_img.startAnimation(animationset);  
	    new Handler().postDelayed(new Runnable(){   
            
	        @Override   
	        public void run() {   
	        	
    			Intent intent=new Intent(WellcomeActivity.this,MainActivity.class);
    			startActivity(intent);
    			WellcomeActivity.this.finish();
	        	
	        }   
	             
	       }, SPLASH_DISPLAY_LENGHT);   
		
	}
	
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {   
        //ÔÚ»¶Ó­½çÃæÆÁ±ÎBACK¼ü   
        if(keyCode==KeyEvent.KEYCODE_BACK) {   
            return false;   
        }   
        return false;   
    }   
    
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
}
