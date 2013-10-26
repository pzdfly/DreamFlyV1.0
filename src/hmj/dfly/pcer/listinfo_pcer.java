package hmj.dfly.pcer;


import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.listInfo;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class listinfo_pcer extends Activity{
	
	private Bundle bundle;
	private Button btnfinish;
	private Button btngiveup;

	
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
		setContentView(R.layout.listinfo_pcer);
		btnfinish=(Button)findViewById(R.id.btn_fixfinish);
		btngiveup=(Button)findViewById(R.id.btn_fixgiveup);
		
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
        
		btnfinish.setOnClickListener(l1);
		btngiveup.setOnClickListener(l2);
		
	}
	
	private OnClickListener l1=new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.putExtra("oid", bundle.getString("oid"));
			intent.setClass(listinfo_pcer.this, finishList.class);
			startActivity(intent);
		}
	};
	private OnClickListener l2=new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.putExtra("oid", bundle.getString("oid"));
			intent.setClass(listinfo_pcer.this, giveupList.class);
			startActivity(intent);			
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
