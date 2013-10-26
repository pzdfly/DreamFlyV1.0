package com.example.dreamfly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.dreamfly.PullToRefreshListView.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.NewsHead;
import pz.rg.exit.ExitApplication;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import pz.rg.menu.BottomMenu;
import pz.rg.menu.LeftMenu;
import pz.rg.sliding.BuileGestureExt;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NewsPage extends Activity {

	private Button page_refreshButton;
	private Button page_set_netButton;
	private ProgressBar page_loading_progressBar;
	private ProgressBar loadmoreprogressBar;
	private TextView loadmoretextView;
	private ListviewAdapter adapter;
	private static int page=2;
	private Button loadmoreButton;
	private static List<Map<String, Object>> list;
	private View loadMoreView;
	private PullToRefreshListView listView;
	private int screenWidth;
	private int screenHeight;
	private PopupWindow left_menu;//菜单控件
	private PopupWindow bottom_menu;
	private GestureDetector gestureDetector;//滑动控件
	private ImageView topimageview_page;
	public static NewsPage newsPageactivity=null;
	
	public NewsPage() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newspage);
		newsPageactivity=this;
		//返回和退出程序
		ExitApplication.getInstance().addActivity(this);
		
		
		
		Intent intent=getIntent();
		final String catid=intent.getStringExtra("catid");
		
		topimageview_page=(ImageView)findViewById(R.id.toptitle_page);
		if (catid.equals("77")) {
			topimageview_page.setBackgroundResource(R.drawable.toptitle_jrpz);
		}
		else if (catid.equals("45")) {
			topimageview_page.setBackgroundResource(R.drawable.toptitle_xytb);
		}
		else if (catid.equals("101")) {
			topimageview_page.setBackgroundResource(R.drawable.toptitle_xszz);
		}
		else if (catid.equals("102")) {
			topimageview_page.setBackgroundResource(R.drawable.toptitle_stfc);
		}
		else {
			topimageview_page.setBackgroundResource(R.drawable.toptitle_pcbx);
		}
		
		page_loading_progressBar=(ProgressBar)findViewById(R.id.page_loading_progress);
		page_refreshButton=(Button)findViewById(R.id.page_refresh);
		page_set_netButton=(Button)findViewById(R.id.page_set_net);
		
		page_refreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListviewRefresh pageRefresh=new ListviewRefresh(catid);
				pageRefresh.execute();
			}
		});
		
		page_set_netButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pageintent=null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本 
                if(android.os.Build.VERSION.SDK_INT>10){
                	pageintent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                	pageintent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    pageintent.setComponent(component);
                    pageintent.setAction("android.intent.action.VIEW");
                }
                startActivity(pageintent);
			}
		});
		
		//列表
		listView=(PullToRefreshListView)findViewById(R.id.newslist_newspage);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setBackgroundResource(R.drawable.listviewbackground);
		list=new ArrayList<Map<String,Object>>();
		ListviewRefresh listviewdefault=new ListviewRefresh(catid);
		listviewdefault.execute();
		adapter = new ListviewAdapter(this, list);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				String newsid=list.get(position-1).get("newsid").toString();
				String databaseid=list.get(position-1).get("databaseid").toString();
				Intent intent=new Intent(NewsPage.this,NewsContent.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("newsid", newsid);
				intent.putExtra("databaseid", databaseid);
				startActivity(intent);
			}
		});

       listView.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				
				list.clear();
                page=2;
				ListviewRefresh listviewRefresh=new ListviewRefresh(catid);
				listviewRefresh.execute();
				
			}
		});
		
        loadMoreView = getLayoutInflater().inflate(R.layout.loadmoreview, null); 
		listView.addFooterView(loadMoreView);
		loadmoreprogressBar=(ProgressBar)findViewById(R.id.loadmoreprogress);
		loadmoretextView=(TextView)findViewById(R.id.loadmoretextview);
		loadmoreButton=(Button)findViewById(R.id.loadmorebutton);
		loadmoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadmoreButton.setVisibility(View.INVISIBLE);
				loadmoreprogressBar.setVisibility(View.VISIBLE);
				loadmoretextView.setVisibility(View.VISIBLE);
				LoadMoreData loadMoreData=new LoadMoreData(catid);
				loadMoreData.execute(Integer.toString(page));
				page++;
			}
		});
		
		
		//向右滑动弹出菜单栏
				gestureDetector = new BuileGestureExt(this,new BuileGestureExt.OnGestureResult() {
		            @Override
		            public void onGestureResult(int direction) {
		            	if (direction==3) {
		            		screenWidth = NewsPage.this.getWindowManager().getDefaultDisplay().getWidth();
		            	    screenHeight = NewsPage.this.getWindowManager().getDefaultDisplay().getHeight(); 
		   		    	    View left_menu_view = getLayoutInflater().inflate(R.layout.leftmenu, null,false);
		   		    	    left_menu = new PopupWindow(left_menu_view, screenWidth, screenHeight, true);
		   		    	    LeftMenu.initPopuptWindow(left_menu, left_menu_view,NewsPage.this);
		   				    left_menu.showAsDropDown(findViewById(R.id.toptitle_page), 0, screenHeight/6);
						}
		            	
		            }
		        }
		        ).Buile();
	}
	
public class ListviewRefresh extends AsyncTask<Void, Void, List<NewsHead>> {
		
		private String jsonString;
		private String catid;


		public ListviewRefresh(String catid) {
			// TODO Auto-generated constructor stub
			this.catid=catid;
		}
		@Override
		protected void onPreExecute() {
		// TODO Auto-generated method stub
			page_loading_progressBar.setVisibility(View.VISIBLE);
			page_refreshButton.setVisibility(View.INVISIBLE);
			page_set_netButton.setVisibility(View.INVISIBLE);
			listView.setVisibility(View.INVISIBLE);
		super.onPreExecute();
		}

		@Override
		protected List<NewsHead> doInBackground(Void... parm) {
			// TODO Auto-generated method stub
			StringBuffer path = new StringBuffer("http://www.peizheng.cn/mobile/index.php?interfaceid=0102&page=1&limit=10&catid="+catid+"&cname=dfly&cpwd=123456");
			jsonString = HttpTools.getJsonString(path.toString());
			List<NewsHead> newsHeads=new ArrayList<NewsHead>();
			newsHeads=JsonTools.getNewsHeads(jsonString);
			return newsHeads;
		}

		@Override
		protected void onPostExecute(List<NewsHead> result) {
			// TODO Auto-generated method stub
			page_loading_progressBar.setVisibility(View.INVISIBLE);
			if (result.toString().equals("[]")) {
				
				page_refreshButton.setVisibility(View.VISIBLE);
				page_set_netButton.setVisibility(View.VISIBLE);
			}
			else {
				
				listView.setVisibility(View.VISIBLE);
			}
			list.addAll(newsListview.getData(result,adapter));
			adapter.notifyDataSetChanged();
			listView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

public class LoadMoreData extends AsyncTask<String, Void, List<NewsHead>> {
	
	private String jsonString;
	private String catid;


	public LoadMoreData(String catid) {
		// TODO Auto-generated constructor stub
		this.catid=catid;
	}
	

	@Override
	protected List<NewsHead> doInBackground(String... parm) {
		// TODO Auto-generated method stub
		String page = parm[0];
		StringBuffer path = new StringBuffer("http://www.peizheng.cn/mobile/index.php?interfaceid=0102&page="+page+"&limit=10&catid="+catid+"&cname=dfly&cpwd=123456");
		jsonString = HttpTools.getJsonString(path.toString());
		List<NewsHead> newsHeads=new ArrayList<NewsHead>();
		newsHeads=JsonTools.getNewsHeads(jsonString);
		return newsHeads;
	}

	@Override
	protected void onPostExecute(List<NewsHead> result) {
		// TODO Auto-generated method stub
		if (result.toString().equals("[]")) {
			
			Toast.makeText(NewsPage.this, "网络不给力，请重试！",Toast.LENGTH_SHORT).show();
		}
		list.addAll(newsListview.getData(result,adapter));
		adapter.notifyDataSetChanged();
	    loadmoreButton.setVisibility(View.VISIBLE);
		loadmoreprogressBar.setVisibility(View.INVISIBLE);
		loadmoretextView.setVisibility(View.INVISIBLE);

		
		super.onPostExecute(result);
	}
}

//滑动时用的触摸事件	
		@Override
	    public boolean onTouchEvent(MotionEvent event) {
	        return gestureDetector.onTouchEvent(event);
	    }	
			
					
				
		
		//防止滑动事件被listview的item事件覆盖
		@Override
		 public boolean dispatchTouchEvent(MotionEvent ev) {
		  // TODO Auto-generated method stub
		  this.gestureDetector.onTouchEvent(ev);
		  
		  return super.dispatchTouchEvent(ev);
		 }	
		
	//菜单

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU) {
	        //监控/拦截菜单键
	    	//获取屏幕高宽   
	         screenWidth = NewsPage.this.getWindowManager().getDefaultDisplay().getWidth();   
	         screenHeight = NewsPage.this.getWindowManager().getDefaultDisplay().getHeight();   
	    	 View bottom_menu_view = getLayoutInflater().inflate(R.layout.bottommenu, null,false);
	    	 bottom_menu = new PopupWindow(bottom_menu_view, screenWidth, screenHeight, true);
			 BottomMenu.initPopuptWindow(bottom_menu, bottom_menu_view,NewsPage.this);
			 bottom_menu.showAtLocation(findViewById(R.id.newspage_activity), Gravity.BOTTOM, 0, 0);
	    } 
	    return super.onKeyDown(keyCode, event);
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
