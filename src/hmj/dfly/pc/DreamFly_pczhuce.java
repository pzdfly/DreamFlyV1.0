package hmj.dfly.pc;


import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DreamFly_pczhuce extends Activity{
	
	private Button btn_tijiao;
	
	private EditText name=null;
	private EditText realname=null;
	private EditText mima=null;
	private EditText sex=null;
	private EditText roomnumber=null;
	private EditText area=null;
	private EditText email=null;
	private EditText phone=null;

		
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pc_zhuce);
								
		btn_tijiao=(Button)findViewById(R.id.btn_zhucetijiao);
		btn_tijiao.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				toRegist toregist=new toRegist();
				toregist.execute();
			}
		});
				
	}
	
	
	public class toRegist extends AsyncTask<Void, Void, String>{
		
		private String jsonString;

		
		public toRegist() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected String doInBackground(Void... parm) {
			
			name=(EditText)findViewById(R.id.et_zhuceyonghu);
			mima=(EditText)findViewById(R.id.et_zhucemima);
			realname=(EditText)findViewById(R.id.et_zhucerealname);
			sex=(EditText)findViewById(R.id.et_zhucesex);
			roomnumber=(EditText)findViewById(R.id.et_zhucesushe);
			area=(EditText)findViewById(R.id.et_zhucearea);
			email=(EditText)findViewById(R.id.et_zhuceemail);
			phone=(EditText)findViewById(R.id.et_zhucedianhua);
						
			String unameString=name.getText().toString();
			String mimaString=mima.getText().toString();
			//String mima2String=mima2.getText().toString();
			String realnameString=realname.getText().toString();
			String sexString=sex.getText().toString();
			String roomnumberString=roomnumber.getText().toString();
			String areaString=area.getText().toString();
			String emailString=email.getText().toString();
			String phoneString=phone.getText().toString();
			
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0201&uname="+
					unameString+"&upwd="+mimaString+"&realname="+realnameString+"&sex="+sexString+"&roomnumber="+roomnumberString+"&area="+areaString+"&email="+emailString+"&phone="+phoneString+
					"&cname=dfly&cpwd=123456");
			
			jsonString=HttpTools.getJsonString(url);
			String status=JsonTools.getStatus(jsonString);

			
			return status;
		}
		
		@Override
		protected void onPostExecute(String status) {
			if(status.equals("1")){
				Intent intent =new Intent();
				intent.setClass(DreamFly_pczhuce.this,DreamFly_pcActivity.class);
				startActivity(intent);
				Toast.makeText(DreamFly_pczhuce.this,"注册成功", Toast.LENGTH_SHORT).show();	
				finish();
			} else {
				Toast.makeText(DreamFly_pczhuce.this, "注册失败，可能用户名已存在", Toast.LENGTH_SHORT).show();
			}
			
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

		

