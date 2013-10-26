package hmj.dfly.user;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import hmj.dfly.pc.DreamFly_pcActivity;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editInfo extends Activity{
	
	private Button btn;
	
	private EditText realname=null;
	private EditText sex=null;
	private EditText roomnumber=null;
	private EditText area=null;
	private EditText email=null;
	private EditText phone=null;
	
	private Context context=editInfo.this;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editinfo);
								
		btn=(Button)findViewById(R.id.btn_editinfo);
		btn.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				toEditinfo toeditinfo=new toEditinfo();
				toeditinfo.execute();
			}
		});
				
	}
	
	public class toEditinfo extends AsyncTask<Void, Void, String>{
		private String jsonString;
		@Override
		protected String doInBackground(Void... params) {
			SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);

			realname=(EditText)findViewById(R.id.et_realname);
			sex=(EditText)findViewById(R.id.et_sex);
			roomnumber=(EditText)findViewById(R.id.et_sushe);
			area=(EditText)findViewById(R.id.et_area);
			email=(EditText)findViewById(R.id.et_email);
			phone=(EditText)findViewById(R.id.et_dianhua);
			
			String uidString=user_info.getString("uid","none");
			System.out.println(uidString);
			String realnameString=realname.getText().toString();
			String sexString=sex.getText().toString();
			String roomnumberString=roomnumber.getText().toString();
			String areaString=area.getText().toString();
			String emailString=email.getText().toString();
			String phoneString=phone.getText().toString();
			
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0203&uid="+
			uidString+"&realname="+realnameString+"&sex="+sexString+"&dorm="+roomnumberString+
			"&area="+areaString+"&email="+emailString+"&phone="+phoneString+"&cname=dfly&cpwd=123456");
			System.out.println(url);
			jsonString=HttpTools.getJsonString(url);
			String status=JsonTools.getInfo(jsonString);
			
			return status;
		}
		@Override
		protected void onPostExecute(String status){
			Toast.makeText(editInfo.this,"资料修改成功,为了您的账号安全，请重新登录", Toast.LENGTH_SHORT).show();
			SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
			Editor editor=user_info.edit();
			editor.clear();
			editor.commit();
			Intent intent=new Intent();
			intent.setClass(editInfo.this, DreamFly_pcActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();

			super.onPostExecute(status);
		}
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
