package hmj.dfly.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ShowToast")
public class pc_ziliao extends Activity{
	private TextView user;
	private TextView realname;
	private TextView sex;
	private TextView area;
	private TextView dorm;
	private TextView email;
	private TextView phone;
	private TextView pcnumber;
	
	private Button m_ModifyInfo;
	private Button m_GoBack;
	private Button m_Submit;
	
	private Context 			context = pc_ziliao.this;
	private SharedPreferences	user_info;
	
	ArrayAdapter<CharSequence> m_buildingAdapter;
	ArrayAdapter<CharSequence> m_areaAdapter;
	
	private EditText 	m_RealName;
	private RadioButton	m_Male;
	private RadioButton m_Female;
	private Spinner 	m_Area;
	private Spinner		m_DromBuilding;
	private EditText    m_DromNumber;
	private EditText	m_Email;
	private EditText	m_Phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
        	
        this.findInfoView();
    }
    
    //获取View和显示信息
    private void findInfoView(){
    	setContentView(R.layout.pc_ziliao);
    	
    	user=(TextView)findViewById(R.id.tv_user);
        realname=(TextView)findViewById(R.id.tv_realname);
        sex=(TextView)findViewById(R.id.tv_sex);
        area=(TextView)findViewById(R.id.tv_area);
        dorm=(TextView)findViewById(R.id.tv_dorm);
        email=(TextView)findViewById(R.id.tv_email);
        phone=(TextView)findViewById(R.id.tv_phone);
        pcnumber=(TextView)findViewById(R.id.tv_pcnumber);
        m_ModifyInfo=(Button)findViewById(R.id.btn_xiugaiziliao);
        
        showUserInfo();
    }
    
    private void findEditInfoView() {
    	setContentView(R.layout.editinfo);
    	
		m_RealName 	= (EditText)this.findViewById(R.id.et_realname);
		m_RealName.setText(user_info.getString("realname", "none"));
		
		m_Male 		= (RadioButton)this.findViewById(R.id.rb_male);
		m_Female	= (RadioButton)this.findViewById(R.id.rb_female);
		if("男".equals(user_info.getString("sex", "男")))
			m_Male.setChecked(true);
		else 
			m_Female.setChecked(true);
		
		//m_DromBuilding = (Spinner)this.findViewById(R.id.sp_area_number);
		String dromString = user_info.getString("dorm", "0-000");
		
		Pattern pattern  = Pattern.compile("-");
		String[] dromArray = pattern.split(dromString);
		System.out.println(dromArray.toString());
		
		m_DromBuilding		= (Spinner)findViewById(R.id.sp_area_number);
		m_buildingAdapter	= ArrayAdapter.createFromResource(context, R.array.building_number,R.layout.list_item);
		m_buildingAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		m_DromBuilding.setAdapter(m_buildingAdapter);
		m_DromBuilding.setSelection(m_buildingAdapter.getPosition(dromArray[0]), true);
		
		m_DromNumber = (EditText)this.findViewById(R.id.et_drom);
		m_DromNumber.setText(dromArray[1]);
		
		m_Area 			= (Spinner)this.findViewById(R.id.sp_area);
		m_areaAdapter	= ArrayAdapter.createFromResource(context, R.array.area_name, R.layout.list_item);
		m_areaAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		m_Area.setAdapter(m_areaAdapter);
		m_Area.setSelection(m_areaAdapter.getPosition(user_info.getString("area", "北区")), true);
		
		m_Phone = (EditText)this.findViewById(R.id.et_phone);
		m_Phone.setText(user_info.getString("phone", "none"));
		
		
		m_Email = (EditText)this.findViewById(R.id.et_email);
		m_Email.setText(user_info.getString("email", "none"));
		
		//设置添加信息和返回个人资料的界面点解事件
		submitUserInfo();
		goBack();
	}
    
    //显示用户信息
    private void showUserInfo(){
    	user.setText(user_info.getString("uname","none"));
        realname.setText(user_info.getString("realname","none"));
        sex.setText(user_info.getString("sex","none"));
        area.setText(user_info.getString("area","none"));
        dorm.setText(user_info.getString("dorm","none"));
        email.setText(user_info.getString("email","none"));
        phone.setText(user_info.getString("phone","none"));
        pcnumber.setText(user_info.getString("pcnumber","none"));
        
        
        TableRow tTableRow = (TableRow)this.findViewById(R.id.tr_pcnumber);
        if(!"0".equals(user_info.getString("pcnumber", "none")))
        	tTableRow.setVisibility(View.VISIBLE);
        
        m_ModifyInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				findEditInfoView();
			}
		});
    }
    
    //提交修改的用户信息
    @SuppressLint("ShowToast")
	private void submitUserInfo() {
    	m_Submit = (Button)this.findViewById(R.id.btn_submit_info);
    	
    	m_Submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getSubmitInfo();
			}
		});
	}
    
    //返回到个人信息界面
    private void goBack() {
		m_GoBack = (Button)this.findViewById(R.id.btn_goback);
		
		m_GoBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				findInfoView();
			}
		});
	}
    
    private boolean getSubmitInfo() {
    	String tRealName = m_RealName.getText().toString();
		if("".equals(tRealName)){
			Toast.makeText(context, "真实姓名不能为空！", Toast.LENGTH_LONG);
			return false;
		}
    	
    	String tSex = "男";
		if(m_Female.isChecked())
			tSex = "女";
		else if(m_Male.isChecked())
			tSex = "男";
		
		String tArea = m_areaAdapter.getItem(m_Area.getSelectedItemPosition()).toString();
		
		String tDrom = m_buildingAdapter.getItem(m_DromBuilding.getSelectedItemPosition()).toString()
						+ "-" + m_DromNumber.getText().toString();
		if("".equals(tDrom)){
			Toast.makeText(context, "宿舍号不能为空！", Toast.LENGTH_SHORT);
			return false;
		}
		
		String tPhone = m_Phone.getText().toString();
		if("".equals(tPhone)){
			Toast.makeText(context, "联系电话不能为空！", Toast.LENGTH_SHORT);
			return false;
		}
		
		String tEmail = m_Email.getText().toString();
		String eMailRule = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		Pattern pattern  = Pattern.compile(eMailRule);
		Matcher matcher = pattern.matcher(tEmail);
		if(!matcher.matches()){	Toast.makeText(context,"邮箱格式不合法！", Toast.LENGTH_SHORT).show(); 	return false;}
		if("".equals(tEmail)){	Toast.makeText(context,"邮箱不能为空！", Toast.LENGTH_SHORT).show();		return false;}
		
		String uidString=user_info.getString("uid","none");
		
		String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0203&uid="+
				uidString+"&realname="+tRealName+"&sex="+tSex+"&dorm="+tDrom+
				"&area="+tArea+"&email="+tEmail+"&phone="+tPhone+"&cname=dfly&cpwd=123456");
		
		SubmitUserInfo tSubmitUserInfo = new SubmitUserInfo();
		tSubmitUserInfo.execute(url, tRealName, tSex, tArea, tDrom, tPhone, tEmail);
		
		return true;
	}

    protected class SubmitUserInfo extends AsyncTask<String, Void, String>{

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if("0".equals(result)){
				Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT);
				return;
			}else if ("1".equals(result)) {
				Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT);
				findInfoView();	
			}
			
		}

		@SuppressLint("ShowToast")
		@Override
		protected String doInBackground(String... arg0) {
			System.out.println(arg0[0]);
			String jsonString=HttpTools.getJsonString(arg0[0]);
			String status = JsonTools.getStatus(jsonString);
			
			if("1".equals(status)){
				Editor  tEditor = user_info.edit();
				tEditor.putString("realname", arg0[1]);
				tEditor.putString("sex", arg0[2]);
				tEditor.putString("area", arg0[3]);
				tEditor.putString("dorm", arg0[4]);
				tEditor.putString("phone", arg0[5]);
				tEditor.putString("email", arg0[6]);
				tEditor.commit();
			}
			return status;
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