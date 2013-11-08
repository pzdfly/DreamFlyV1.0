package hmj.dfly.user;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.Baoxiudan;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class pc_baoxiudan extends Activity {
    /** Called when the activity is first created. */
	private String uidString;
	private ListView listView;
	private Context context=pc_baoxiudan.this;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.pc_baoxiudan);
        listView=(ListView)findViewById(R.id.baoxiudan_list);
        
        uidString=user_info.getString("uid","none");
        

        getBaoxiudan getbaoxiudan=new getBaoxiudan(uidString);
        getbaoxiudan.execute();
        
        
    }   
    
    public class getBaoxiudan extends AsyncTask<String, Void, List<Baoxiudan>>{

    	private String jsonString;
    	private String uid;
    	
		public getBaoxiudan(String uidString) {
			uid=uidString;
			
		}

		@Override
		protected List<Baoxiudan> doInBackground(String... parm) {
			// TODO Auto-generated method stub
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0206&uid="+uid+"&cname=dfly&cpwd=123456");
			jsonString=HttpTools.getJsonString(url);
			List<Baoxiudan> baoxiudanList=JsonTools.getBaoxiudan(jsonString);
			
			return baoxiudanList;
		}
		
		@Override
		protected void onPostExecute(List<Baoxiudan> result) {
			
			final ArrayList<HashMap<String, String>> listItem=new ArrayList<HashMap<String,String>>();
			
			for(int i=0;i<(result.size());i++){
				HashMap<String, String> map=new HashMap<String,String>();
				map.put("listid",result.get(i).getListid());
				map.put("computertype", "电脑类型："+result.get(i).getComputertype());
				map.put("booktime", "预约时间："+result.get(i).getBooktime());
				map.put("submittime", result.get(i).getSubmittime());
				map.put("state", result.get(i).getState());
				listItem.add(map);
			}
			SimpleAdapter listItemAdapter=new SimpleAdapter(pc_baoxiudan.this, listItem, R.layout.baoxiudan_list_item,
					new String[]{"listid","computertype","booktime","submittime","state"},
					new int[]{R.id.tv_pc_danhao, R.id.tv_pc_computertype,R.id.tv_pc_booktime,R.id.tv_pc_submittime,R.id.tv_pc_zhuangtai});
			listView.setAdapter(listItemAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent=new Intent();
					intent.putExtra("oid", listItem.get(arg2).get("listid"));
					intent.setClass(pc_baoxiudan.this, listinfo_user.class);
					startActivity(intent);
				}
			});
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