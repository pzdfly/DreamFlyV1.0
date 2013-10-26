package hmj.dfly.user;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class pc_ziliao extends Activity{
    /** Called when the activity is first created. */
	private TextView user;
	private TextView realname;
	private TextView sex;
	private TextView area;
	private TextView dorm;
	private TextView email;
	private TextView phone;
	private TextView pcnumber;
	
	private Button btn;
	private Context context=pc_ziliao.this;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_ziliao);
        
        
        user=(TextView)findViewById(R.id.tv_user);
        realname=(TextView)findViewById(R.id.tv_realname);
        sex=(TextView)findViewById(R.id.tv_sex);
        area=(TextView)findViewById(R.id.tv_area);
        dorm=(TextView)findViewById(R.id.tv_dorm);
        email=(TextView)findViewById(R.id.tv_email);
        phone=(TextView)findViewById(R.id.tv_phone);
        pcnumber=(TextView)findViewById(R.id.tv_pcnumber);
        
        btn=(Button)findViewById(R.id.btn_xiugaiziliao);
        
        user.setText(user_info.getString("uname","none"));
        realname.setText(user_info.getString("realname","none"));
        sex.setText(user_info.getString("sex","none"));
        area.setText(user_info.getString("area","none"));
        dorm.setText(user_info.getString("dorm","none"));
        email.setText(user_info.getString("email","none"));
        phone.setText(user_info.getString("phone","none"));
        pcnumber.setText(user_info.getString("pcnumber","none"));

       
       final Intent intent_editinfo=new Intent();
       intent_editinfo.setClass(this, editInfo.class);
       btn.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				startActivity(intent_editinfo);				
			}});
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