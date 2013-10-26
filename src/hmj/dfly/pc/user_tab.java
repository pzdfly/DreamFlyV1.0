package hmj.dfly.pc;

import com.example.dreamfly.R;

import hmj.dfly.pcer.myList;
import hmj.dfly.pcer.teleList;
import hmj.dfly.pcer.webtList;
import hmj.dfly.user.baoxiu;
import hmj.dfly.user.pc_baoxiudan;
import hmj.dfly.user.pc_ziliao;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class user_tab extends TabActivity{
	
	private TextView tv=null;
	private Context context=user_tab.this;
	private TextView logout=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {  
		SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.user_tab);
        
        logout=(TextView)findViewById(R.id.btn_logout);
        logout.setOnClickListener(l);
        
        tv=(TextView)findViewById(R.id.tv_zhanghu);
        tv.setText(user_info.getString("realname","none"));
        
        String identity=user_info.getString("identity", "none");
        
        if(identity.equals("0")){
        	user();
        }else {
        	pcer();
        }  
	}
	private OnClickListener l=new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
			Editor editor=user_info.edit();
			editor.clear();
			editor.commit();
			Intent intent=new Intent();
			intent.setClass(user_tab.this, DreamFly_pcActivity.class);
			startActivity(intent);
			Toast.makeText(user_tab.this, "注销成功", Toast.LENGTH_SHORT).show();
			finish();
		}
	};
	
	private void user(){
		TabHost tabHost=this.getTabHost();
        Intent intent1=new Intent();
        intent1.setClass(user_tab.this,baoxiu.class);
        Intent intent2=new Intent();
        intent2.setClass(user_tab.this, pc_ziliao.class);
        Intent intent3=new Intent();
        intent3.setClass(user_tab.this, pc_baoxiudan.class);
        
        View tab1=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text1=(TextView)tab1.findViewById(R.id.tab_label);
        text1.setText("提交报修");
        View tab2=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text2=(TextView)tab2.findViewById(R.id.tab_label);
        text2.setText("个人资料");
        View tab3=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text3=(TextView)tab3.findViewById(R.id.tab_label);
        text3.setText("报修单");
             
        tabHost.addTab(tabHost.newTabSpec("报修单").setIndicator(tab3).setContent(intent3));
        tabHost.addTab(tabHost.newTabSpec("提交报修").setIndicator(tab1).setContent(intent1));
        tabHost.addTab(tabHost.newTabSpec("个人资料").setIndicator(tab2).setContent(intent2));
        
	}
	
	private void pcer(){
		TabHost tabHost=this.getTabHost();
		
		Intent intent1=new Intent();
        intent1.setClass(user_tab.this,pc_ziliao.class);
        View tab1=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text1=(TextView)tab1.findViewById(R.id.tab_label);
        text1.setText("个人资料");
        
        Intent intent2=new Intent();
        intent2.setClass(user_tab.this,webtList.class);
        View tab2=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text2=(TextView)tab2.findViewById(R.id.tab_label);
        text2.setText("网单");
        
        Intent intent3=new Intent();
        intent3.setClass(user_tab.this, myList.class);
        View tab3=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text3=(TextView)tab3.findViewById(R.id.tab_label);
        text3.setText("我的维修单");
        
        Intent intent4=new Intent();
        intent4.setClass(user_tab.this, teleList.class);
        View tab4=LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView text4=(TextView)tab4.findViewById(R.id.tab_label);
        text4.setText("电话单");
        
        
        
        
        
        tabHost.addTab(tabHost.newTabSpec("个人资料").setIndicator(tab1).setContent(intent1));
        tabHost.addTab(tabHost.newTabSpec("网单").setIndicator(tab2).setContent(intent2));
        tabHost.addTab(tabHost.newTabSpec("我的维修单").setIndicator(tab3).setContent(intent3));
        tabHost.addTab(tabHost.newTabSpec("电话单").setIndicator(tab4).setContent(intent4));
	}

}
