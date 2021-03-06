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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class baoxiu extends Activity{
	
	private TextView uname;
	private TextView area;
	private TextView dorm;
	private TextView phone;
	private TextView email;
	
	private Spinner  comtype;
	private EditText faultdesc;
	private EditText booktime;
	
	private String uidString;
	private String unameString;
	private String comtypeString;
	private String faultdescString;
	private String booktimeString;
	private String phoneString = "笔记本";
	private String dormString;
	private String emailString;
	private String areaString;
	
	private ArrayAdapter<CharSequence> comtypeAdapter;
	
	private Button btn;
	
	private Context context=baoxiu.this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
		
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.uploadlist);
          
        
        uname=(TextView)findViewById(R.id.tv_baoxiu_user);
        area=(TextView)findViewById(R.id.tv_baoxiu_area);
        dorm=(TextView)findViewById(R.id.tv_baoxiu_dorm);
        email=(TextView)findViewById(R.id.tv_baoxiu_email);
        phone=(TextView)findViewById(R.id.tv_baoxiu_phone);
        
        faultdesc	= (EditText)findViewById(R.id.et_baoxiu_faultdesc);
        booktime	= (EditText)findViewById(R.id.et_baoxiu_booktime);
       
        comtype 		= (Spinner)findViewById(R.id.sp_baoxiu_comtype);
        comtypeAdapter	= ArrayAdapter.createFromResource(baoxiu.this, R.array.compure_kind, R.layout.list_item);
        comtypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        comtype.setAdapter(comtypeAdapter);
        comtype.setSelection(0, true);
  
        comtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(0 != arg2){
					comtypeString = comtypeAdapter.getItem(arg2).toString();
				}else {
					Toast.makeText(baoxiu.this, "没有选择任何项", Toast.LENGTH_SHORT).show();
				}
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(baoxiu.this, "没有选择任何项", Toast.LENGTH_SHORT).show();
			}
		});
        
          
        uidString		= user_info.getString("uid", "none");
        unameString		= user_info.getString("uname", "none");
        comtypeString	= user_info.getString("comtype", "none");
        faultdescString	= user_info.getString("faultdesc", "none");
        booktimeString	= user_info.getString("booktime", "none");
        phoneString		= user_info.getString("phone", "none");
        dormString		= user_info.getString("dorm", "none");
        emailString		= user_info.getString("email", "none");
        areaString		= user_info.getString("area", "none");

        uname.setText(unameString);
        area.setText(areaString);
        dorm.setText(dormString);
        phone.setText(phoneString);
        email.setText(emailString);
        
        btn=(Button)findViewById(R.id.btn_tijiaobaoxiu);
        btn.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
				faultdescString=faultdesc.getText().toString();
				booktimeString=booktime.getText().toString();
				
				toBaoxiu tobaoxiu =new toBaoxiu();
				tobaoxiu.execute();	
			}
		});   
	}
	public class toBaoxiu extends AsyncTask<Void, Void, String> {
		private String jsonString;
		
		public toBaoxiu(){	
		}

		@Override
		protected String doInBackground(Void... parm) {			
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0204&uid="+
			uidString+"&uname="+unameString+"&comtype="+comtypeString+"&faultdesc="+faultdescString+
			"&booktime="+booktimeString+"&phone="+phoneString+"&dorm="+dormString+"&email="+emailString+
			"&area="+areaString+"&cname=dfly&cpwd=123456");
			
			jsonString=HttpTools.getJsonString(url);
			String status=JsonTools.getStatus(jsonString);
			
			return status;
		}
		
		@Override
		protected void onPostExecute(String status){
			if(status.equals("1")){
				Toast.makeText(baoxiu.this, "提交报修单成功", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(baoxiu.this,user_tab.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				}else{
					Toast.makeText(baoxiu.this, "提交保修单失败", Toast.LENGTH_SHORT).show();
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
        

 
