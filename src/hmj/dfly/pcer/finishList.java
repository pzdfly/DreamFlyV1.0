package hmj.dfly.pcer;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import hmj.dfly.pc.user_tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class finishList extends Activity{
	
	private Button btn;
	private EditText et;
	private Bundle bundle;
	private Context context=finishList.this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listinfo_finish);
		
		bundle=getIntent().getExtras();
		
		et=(EditText)findViewById(R.id.et_log);
		
		btn=(Button)findViewById(R.id.btn_log);
		btn.setOnClickListener(l);
		
		
	}
	
	private OnClickListener l=new OnClickListener() {
		
		public void onClick(View v) {
			toFinish to=new toFinish();
			to.execute();				
		}
	};
	
	public class toFinish extends AsyncTask<Void, Void, String>{
		private String jsonString;
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0210&oid="+bundle.getString("oid")+
					"&uname="+user_info.getString("uname", "none")+
					"&log="+et.getText().toString()+"&cname=dfly&cpwd=123456");
			jsonString=HttpTools.getJsonString(url);
			String status=JsonTools.getStatus(jsonString);
			return status;
		}
		
		@Override
		protected void onPostExecute(String status) {
				Intent intent =new Intent();
				intent.setClass(finishList.this,user_tab.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				Toast.makeText(finishList.this,"“—ÕÍ≥…Œ¨–ﬁ", Toast.LENGTH_SHORT).show();	
				finish();
				finishFromChild(getParent());
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


