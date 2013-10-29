package hmj.dfly.pc;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class DreamFly_pczhuce extends Activity{
	
	private Button btn_tijiao;
	private ArrayAdapter<CharSequence> buildingAdapter;
	private ArrayAdapter<CharSequence> areaAdapter;
	
	private String buildingNumber = "1";
	private String userName;
	private String pwd;
	private String realName;
	private String sex;
	private String roomNumber;
	private String dromNumber;
	private String area = "北区";
	private String eMail;
	private String phone;
		
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pc_zhuce);
								
		btn_tijiao=(Button)findViewById(R.id.btn_zhucetijiao);
		btn_tijiao.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				if (false == getViewInfo()) return ;
				
				toRegist toregist=new toRegist();
				toregist.execute();
			}
		});
		
		//填充buildingSpinner
		Spinner buildingSpinner	= (Spinner)findViewById(R.id.sp_zhucearea_number);
		buildingAdapter = ArrayAdapter.createFromResource(DreamFly_pczhuce.this, R.array.building_number, R.layout.list_item);
		buildingAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		buildingSpinner.setAdapter(buildingAdapter);
		buildingSpinner.setSelection(0, true);
		
		buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(0 != arg2){
					buildingNumber = buildingAdapter.getItem(arg2).toString();
				}else{
					Toast.makeText(DreamFly_pczhuce.this, "没有选择任何项", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(DreamFly_pczhuce.this, "没有选择任何项", Toast.LENGTH_SHORT).show();
			}
		});
		
		//填充areaSpinner
		Spinner areaSpinner = (Spinner)findViewById(R.id.sp_zhucearea);
		areaAdapter			= ArrayAdapter.createFromResource(DreamFly_pczhuce.this, R.array.area_name, R.layout.list_item);
		areaAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		areaSpinner.setAdapter(areaAdapter);
		areaSpinner.setSelection(0, true);
		
		areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(0 != arg2){
					area = areaAdapter.getItem(arg2).toString();
				}else {
					Toast.makeText(DreamFly_pczhuce.this, "没有选择任何项", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(DreamFly_pczhuce.this, "没有选择任何项", Toast.LENGTH_SHORT).show();
				
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
			String url 	 = new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0201&uname="+
							userName+"&upwd="+pwd+"&realname="+realName+"&sex="+sex+"&roomnumber="+dromNumber+"&area="+area+"&email="+eMail+"&phone="+phone+
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
	
	private Boolean getViewInfo() {
		//用户名 密码 真实姓名
		userName = ((EditText)findViewById(R.id.et_zhuceyonghu)).getText().toString();
		pwd		 = ((EditText)findViewById(R.id.et_zhucemima)).getText().toString();
		realName = ((EditText)findViewById(R.id.et_zhucerealname)).getText().toString();
		
		if("".equals(userName)){	Toast.makeText(DreamFly_pczhuce.this,"用户名不能为空！", Toast.LENGTH_SHORT).show(); 		return false;	}
		if("".equals(pwd))	   {	Toast.makeText(DreamFly_pczhuce.this,"密码不能为空！", Toast.LENGTH_SHORT).show(); 		return false;	}
		if("".equals(realName)){	Toast.makeText(DreamFly_pczhuce.this,"真实姓名不能为空！", Toast.LENGTH_SHORT).show(); 	return false;	}
		
		//性别
		int CheckID	= ((RadioGroup)findViewById(R.id.zhucesex)).getCheckedRadioButtonId();
		sex 	= ((RadioButton)findViewById(CheckID)).getText().toString();
		
		//宿舍号
		roomNumber = ((EditText)findViewById(R.id.et_zhucedrom)).getText().toString();
		if("".equals(roomNumber)){	Toast.makeText(DreamFly_pczhuce.this,"宿舍号不能为空！", Toast.LENGTH_SHORT).show(); 		return false;	}
		dromNumber = buildingNumber + "-" + roomNumber;
		
		//E-mail
		eMail = ((EditText)findViewById(R.id.et_zhuceemail)).getText().toString();
		
		String eMailRule = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		Pattern pattern  = Pattern.compile(eMailRule);
		Matcher matcher = pattern.matcher(eMail);
		if(!matcher.matches()){	Toast.makeText(DreamFly_pczhuce.this,"邮箱格式不合法！", Toast.LENGTH_SHORT).show(); 	return false;	}
		if("".equals(eMail)){	Toast.makeText(DreamFly_pczhuce.this,"邮箱不能为空！", Toast.LENGTH_SHORT).show(); 	return false;	}
		
		//Phone
		phone = ((EditText)findViewById(R.id.et_zhucedianhua)).getText().toString();
		if("".equals(phone)){	Toast.makeText(DreamFly_pczhuce.this,"联系电话不能为空！", Toast.LENGTH_SHORT).show(); 	return false;	}
		
		return true;
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

		

