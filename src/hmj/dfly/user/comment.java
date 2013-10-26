package hmj.dfly.user;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import hmj.dfly.pc.user_tab;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class comment extends Activity{
	private Bundle bundle;
	private Button btn;
	private EditText comment;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
        setContentView(R.layout.user_comment);
        bundle=getIntent().getExtras();
        comment=(EditText)findViewById(R.id.et_comment);
        btn=(Button)findViewById(R.id.btn_comment);
        btn.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toComment to=new toComment();
				to.execute();
			}
		});   
	}
	public class toComment extends AsyncTask<Void, Void, String>{
		private String jsonString;
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String oidString=bundle.getString("oid");
			String commentString=comment.getText().toString();
			
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0205&oid="+oidString+"&level=很好&remark="+commentString+"&cname=dfly&cpwd=123456");
			jsonString=HttpTools.getJsonString(url);
			String status=JsonTools.getStatus(jsonString);
			return status;
		}
		@Override
		protected void onPostExecute(String status) {
			Toast.makeText(comment.this, status+"，评价成功，谢谢您的合作", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(comment.this,user_tab.class);
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
