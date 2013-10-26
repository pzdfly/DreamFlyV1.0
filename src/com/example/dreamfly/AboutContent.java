package com.example.dreamfly;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutContent extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_content);

		TextView tDeveloperName = (TextView)this.findViewById(R.id.developer_name);
		tDeveloperName.setText("马海宏  黄明就  林彬弟  蔡长希  罗子聪");
		
//		RelativeLayout tAboutContent = (RelativeLayout)this.findViewById(R.id.about_content_layout);
//		tAboutContent.set
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
