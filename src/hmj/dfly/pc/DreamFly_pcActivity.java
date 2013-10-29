package hmj.dfly.pc;

import java.util.ArrayList;
import java.util.List;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;


import pz.rg.domain.Login;
import pz.rg.domain.Notice;

import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import pz.rg.notice.MC_Timer;
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

public class DreamFly_pcActivity extends Activity {
    /** Called when the activity is first created. */
	private Button btn_pczhuce=null;
	private Button btn_pcdenglu=null;
	
	private EditText nameEditText=null;
	private EditText passwordEditText=null;
	
	private String nameString;
	private String passwordString;
	
	private Context context=DreamFly_pcActivity.this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
    	super.onCreate(savedInstanceState);       
		setContentView(R.layout.pc_denglu);   
		String k=user_info.getString("uname", "none");

    	if(k.equals("none")){
    		 
    		btn_pczhuce=(Button)findViewById(R.id.btn_pczhuce);
    		btn_pczhuce.setOnClickListener(l_pczhuce);
        
    		btn_pcdenglu=(Button)findViewById(R.id.btn_pcdenglu);
    		btn_pcdenglu.setOnClickListener(l_pcdenglu);  
        
    		getNotice getnotice=new getNotice();
    		getnotice.execute();
    	}
    	else {	
			Intent intent=new Intent();
			intent.setClass(DreamFly_pcActivity.this, user_tab.class);
			startActivity(intent);	
			finish();
		}
    }
    
	private OnClickListener l_pczhuce=new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.setClass(DreamFly_pcActivity.this,DreamFly_pczhuce.class);
			DreamFly_pcActivity.this.startActivity(intent);		
			
		}
	};
	
	private OnClickListener l_pcdenglu=new OnClickListener() {
		
		public void onClick(View v) {
			
			toLogin tologin=new toLogin();
			tologin.execute();			
		}
	};
	

	public class toLogin extends AsyncTask<Void, Void, List<Login>>{
		private String jsonString;
		
		public toLogin(){
			
		}
		
		@Override
		protected List<Login> doInBackground(Void... parm) {
			
			nameEditText=(EditText)findViewById(R.id.et_pcyonghu);
			passwordEditText=(EditText)findViewById(R.id.et_pcmima);		
			nameString=nameEditText.getText().toString();
			passwordString=passwordEditText.getText().toString();
			
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0202&uname="
					+nameString+"&upwd="+passwordString+"&cname=dfly&cpwd=123456");
			jsonString=HttpTools.getJsonString(url);	
			System.out.println(jsonString);
			if(JsonTools.getStatus(jsonString).equals("0")){
				return null;
			}else {
				List<Login> login=new ArrayList<Login>();			
				login=JsonTools.getLogin(jsonString);
				System.out.println(login.toString());
				return login;
			}		
		}
		
		@Override
		protected void onPostExecute(List<Login> result) {
			if(result==null){
				Toast.makeText(DreamFly_pcActivity.this, "µÇÂ½Ê§°Ü£¬ÓÃ»§»òÃÜÂë´íÎó", Toast.LENGTH_SHORT).show();
			}else {
				
				Intent intent=new Intent();
				
				SharedPreferences user_info=context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
				Editor editor=user_info.edit();	
				editor.putString("uid", result.get(0).getUid());
				editor.putString("uname", result.get(0).getUname());
				editor.putString("realname", result.get(0).getRealname());
				editor.putString("sex", result.get(0).getSex());
				editor.putString("area", result.get(0).getArea());
				editor.putString("dorm", result.get(0).getDorm());
				editor.putString("email", result.get(0).getEmail());
				editor.putString("phone", result.get(0).getPhone());
				editor.putString("identity", result.get(0).getIdentity());
				editor.putString("pcnumber",result.get(0).getPcnumber());
				editor.commit();
				System.out.println(user_info.getAll());
				
				
				if(result.get(0).getIdentity().equals("0")){
					intent.setClass(DreamFly_pcActivity.this,user_tab.class);
					startActivity(intent);
					Toast.makeText(DreamFly_pcActivity.this, result.get(0).getRealname()+"µÇÂ½³É¹¦£¬»¶Ó­Äú»ØÀ´", Toast.LENGTH_SHORT).show();
					finish();
				}else {
					intent.setClass(DreamFly_pcActivity.this, user_tab.class);
					startActivity(intent);
					Toast.makeText(DreamFly_pcActivity.this, result.get(0).getRealname()+",µÇÂ½³É¹¦£¬»¶Ó­Äú»ØÀ´", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		}
			

			
	}

	public class getNotice extends AsyncTask<Void, Void, List<Notice>>{
		private String 	jsonString;
		
		@Override
		protected List<Notice> doInBackground(Void... params) {
			
			String url				= new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0213&topnum=3&cname=dfly&cpwd=123456");
			jsonString				= HttpTools.getJsonString(url);
			List<Notice> noticeList	= JsonTools.getNotice(jsonString);
			
			return noticeList;
		}

		@Override
		protected void onPostExecute(List<Notice> list) {
			MC_Timer tTimer = new MC_Timer(4000, list, DreamFly_pcActivity.this);
			tTimer.startTimer();
			
			super.onPostExecute(list);
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
	
	
	
