package hmj.dfly.pcer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.dreamfly.R;
import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.fixList;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class myList extends Activity{
	
	private String workidString;
	private ListView listView;
	private Context context=myList.this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		SharedPreferences user_info=context.getSharedPreferences("USER_INFO",MODE_PRIVATE);
		super.onCreate(savedInstanceState); 
        setContentView(R.layout.pc_baoxiudan);
        listView=(ListView)findViewById(R.id.baoxiudan_list);
        
        workidString=user_info.getString("uid", "none");
        
        getMylist getmylist=new getMylist(workidString);
        getmylist.execute();
        
	}
	
	 public class getMylist extends AsyncTask<String, Void, List<fixList>>{

		private String jsonString;
	    private String workid;
	    
	    public getMylist(String workidString){
	    	workid=workidString;
	    	
	    }
	    
		@Override
		protected List<fixList> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=new String("http://www.peizheng.cn/mobile/index.php?interfaceid=0211&page=1&limit=100&type=mine&workid="+workid+"&cname=dfly&cpwd=123456");
			jsonString=HttpTools.getJsonString(url);
			List<fixList> mylist=JsonTools.getfixLists(jsonString);
			return mylist;
		}
		
		@Override
		protected void onPostExecute(List<fixList> result) {
		
			final ArrayList<HashMap<String, String>> listItem=new ArrayList<HashMap<String,String>>();
			for(int i=0;i<result.size();i++){
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("listid",result.get(i).getListid());
				map.put("computertype", "电脑类型："+result.get(i).getComputertype());
				map.put("booktime", "预约时间："+result.get(i).getBooktime());
				map.put("submittime", result.get(i).getSubmittime());
				map.put("state", result.get(i).getState());
				listItem.add(map);
			}
			SimpleAdapter listItemAdapter=new SimpleAdapter(myList.this, listItem, R.layout.baoxiudan_list_item,
					new String[]{"listid","computertype","booktime","submittime","state"},
					new int[]{R.id.tv_pc_danhao,R.id.tv_pc_computertype,R.id.tv_pc_booktime,R.id.tv_pc_submittime,R.id.tv_pc_zhuangtai});
			listView.setAdapter(listItemAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent=new Intent();
					intent.putExtra("oid", listItem.get(arg2).get("listid"));
					intent.setClass(myList.this, listinfo_pcer.class);
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