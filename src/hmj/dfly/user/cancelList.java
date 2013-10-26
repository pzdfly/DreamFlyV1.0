package hmj.dfly.user;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import hmj.dfly.pc.user_tab;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
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

public class cancelList extends Activity{
	
	private Bundle bundle;
	private Button btn;
	private EditText reason;
	private Context context=cancelList.this;
	private String unameString;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {  	
		SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
		super.onCreate(savedInstanceState); 
        setContentView(R.layout.cancellist);
        
        unameString=user_info.getString("uname", "none");
        bundle=getIntent().getExtras();
        reason=(EditText)findViewById(R.id.et_cancelreason);
        btn=(Button)findViewById(R.id.btn_cancelreason);
        btn.setOnClickListener(new OnClickListener(){		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toCancelList to=new toCancelList();
				to.execute();
			}
		});
	}
	
	public class toCancelList extends AsyncTask<Void, Void, String>{
		 private String jsonString;
		@Override
		protected String doInBackground(Void... params) {
		     
		     String oidString=bundle.getString("oid");
		     String reasonString=reason.getText().toString();
		    	     
		     String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0207&oid="+
		     oidString+"&uname="+unameString+"&reason="+reasonString+"&cname=dfly&cpwd=123456");
		     jsonString=HttpTools.getJsonString(url);
		     String status=JsonTools.getStatus(jsonString);
			return status;
		}
		@Override
		protected void onPostExecute(String status) {
			Toast.makeText(cancelList.this, status+"，取消成功", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(cancelList.this,user_tab.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//刷新
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
