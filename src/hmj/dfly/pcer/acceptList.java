package hmj.dfly.pcer;


import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.listInfo;
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
import android.widget.TextView;
import android.widget.Toast;


public class acceptList extends Activity{
	
	private Bundle bundle;
	private Button btn;
	
	private Context context=acceptList.this;
	
	private TextView listid;
	private TextView type;
	private TextView uname;
	private TextView area;
	private TextView dorm;
	private TextView phone;
	private TextView comtype;
	private TextView booktime;
	private TextView time;
	private TextView pcnumber;
	private TextView state;
	private TextView reviewlevel;
	private TextView remark;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listinfo_accept);
		btn=(Button)findViewById(R.id.btn_fixfinish);
		
		listid=(TextView)findViewById(R.id.tv_listid);
		type=(TextView)findViewById(R.id.tv_type);
		uname=(TextView)findViewById(R.id.tv_user);
		area=(TextView)findViewById(R.id.tv_area);
		dorm=(TextView)findViewById(R.id.tv_dorm);
		phone=(TextView)findViewById(R.id.tv_phone);
		comtype=(TextView)findViewById(R.id.tv_comtype);
		booktime=(TextView)findViewById(R.id.tv_booktime);
		time=(TextView)findViewById(R.id.tv_time);
		pcnumber=(TextView)findViewById(R.id.tv_pcnumber);
		state=(TextView)findViewById(R.id.tv_state);
		reviewlevel=(TextView)findViewById(R.id.tv_reviewlevel);
		remark=(TextView)findViewById(R.id.tv_remark);
		
        bundle=getIntent().getExtras();
        System.out.println(bundle.getString("oid")+"huangmingjiu");
        
        getListinfo getlistinfo=new getListinfo();
        getlistinfo.execute();
        
		btn.setOnClickListener(l);
		
	}
	
	private OnClickListener l=new OnClickListener() {
		
		public void onClick(View v) {
			toAcceptlist acceptlist=new toAcceptlist();
			acceptlist.execute();				
		}
	};
	
	
	public class getListinfo extends AsyncTask<String, Void, listInfo>{
		private String jsonString;
		@Override
		protected listInfo doInBackground(String... parm) {
			// TODO Auto-generated method stub
			String oidString=bundle.getString("oid");
			System.out.println(bundle.getString("oid")+"huangmingjiu2");
			
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0212&oid="+oidString+"&cname=dfly&cpwd=123456");
			System.out.println(url);
			jsonString=HttpTools.getJsonString(url);
			System.out.println(jsonString+"ffffffuckyouououou");
			listInfo listinfo=new listInfo();
			listinfo=JsonTools.getInfos(jsonString);
			System.out.println(listinfo.toString());
			return listinfo;
		}
		
		@Override
		protected void onPostExecute(listInfo result){
			listid.setText(result.getListid());
			type.setText(result.getType());
			uname.setText(result.getUname());
			area.setText(result.getArea());
			dorm.setText(result.getDorm());
			phone.setText(result.getPhone());
			comtype.setText(result.getComputertype());
			booktime.setText(result.getBooktime());
			time.setText(result.getTime());
			pcnumber.setText(result.getPcnumber());
			state.setText(result.getState());
			reviewlevel.setText(result.getReviewlevel());
			remark.setText(result.getRemark());

			super.onPostExecute(result);
		}	
	}
	
	public class toAcceptlist extends AsyncTask<Void, Void, String>{
		private String jsonString;
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0209&oid="+bundle.getString("oid")+"&workid="+user_info.getString("uid", "none")+"&cname=dfly&cpwd=123456");
			jsonString=HttpTools.getJsonString(url);
			String status=JsonTools.getStatus(jsonString);
			return status;
		}
		
		@Override
		protected void onPostExecute(String status) {
				Intent intent =new Intent();
				intent.setClass(acceptList.this,user_tab.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				Toast.makeText(acceptList.this,"受理成功", Toast.LENGTH_SHORT).show();	
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
