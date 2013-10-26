package com.example.dreamfly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.dreamfly.PullToRefreshListView.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.NewsHead;
import pz.rg.domain.NewsPictures;
import pz.rg.downloadimage.DownLoadImage_top;
import pz.rg.exit.ExitApplication;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import pz.rg.menu.BottomMenu;
import pz.rg.menu.LeftMenu;
import pz.rg.mydialog.MyDialog;
import pz.rg.sliding.BuileGestureExt;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;


@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	private static boolean setstate=false;
	private PopupWindow dialogPopupWindow;
	private Button homepage_refreshButton;
	private Button homepage_set_netButton;
	private ProgressBar homepage_loading_progressBar;
	private ProgressBar loadmoreprogressBar;
	private TextView loadmoretextView;
	private ListviewAdapter adapter;
	private static int page=2;
	private Button loadmoreButton;
	private static List<Map<String, Object>> list;
	private View loadMoreView;
	private View listview_headview;
	private PullToRefreshListView listView;
	private ViewPager viewPager; // android-support-v4�еĻ������
	private static List<ImageView> imageViews; // ������ͼƬ����

	private static String[] titles; // ͼƬ����
	private List<View> dots; // ͼƬ�������ĵ���Щ��

	private TextView tv_title;
	private int currentItem = 0; // ��ǰͼƬ��������

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// �л���ǰ��ʾ��ͼƬ
	private Handler handler = new Handler() {
		
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
		};
	};
	
	@Override
	protected void onStop() {
		// ��Activity���ɼ���ʱ��ֹͣ�л�
		if(setstate==false)
		    scheduledExecutorService.shutdown();
		setstate=false;
		
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		// ��Activity���¿ɼ�ʱ��ʱ��ֹͣ�л�
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// ÿ�������л�һ��ͼƬ��ʾ
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
		setstate=false;
		super.onRestart();
	}

	public static MainActivity mainActivity=null;
	private PopupWindow left_menu;//�˵��ؼ�
	private PopupWindow bottom_menu;
	private GestureDetector gestureDetector;//�����ؼ�
	private int screenWidth;   
    private int screenHeight;  
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity=this;
		ExitApplication.getInstance().addActivity(this);
 	    
		homepage_loading_progressBar=(ProgressBar)findViewById(R.id.homepage_loading_progress);
		homepage_refreshButton=(Button)findViewById(R.id.homepage_refresh);
		homepage_set_netButton=(Button)findViewById(R.id.homepage_set_net);
		
		homepage_refreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HomeListviewRefresh pageRefresh=new HomeListviewRefresh();
				pageRefresh.execute();
				
				PicturesList picturesRefresh=new PicturesList();
				picturesRefresh.execute();
			}
		});
		
		homepage_set_netButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setstate=true;
				Intent homepageintent=null;
                //�ж��ֻ�ϵͳ�İ汾  ��API����10 ����3.0�����ϰ汾 
                if(android.os.Build.VERSION.SDK_INT>10){
                	homepageintent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                	homepageintent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    homepageintent.setComponent(component);
                    homepageintent.setAction("android.intent.action.VIEW");
                }
                startActivity(homepageintent);
			}
		});		
		//�б�
		
		
		PicturesList pictures=new PicturesList();
		pictures.execute();
			
		
		
		listView=(PullToRefreshListView)findViewById(R.id.newslist);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setBackgroundResource(R.drawable.listviewbackground);
		listview_headview = getLayoutInflater().inflate(R.layout.listview_headview, null); 
		listView.addHeaderView(listview_headview);
		list=new ArrayList<Map<String,Object>>();
		HomeListviewRefresh listviewdefault=new HomeListviewRefresh();
		listviewdefault.execute();
		adapter=new ListviewAdapter(MainActivity.this, list);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				String newsid=list.get(position-2).get("newsid").toString();
				String databaseid=list.get(position-2).get("databaseid").toString();
				Intent intent=new Intent(MainActivity.this,NewsContent.class);
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
				HomeListviewRefresh homelistviewRefresh=new HomeListviewRefresh();
				homelistviewRefresh.execute();
				
				
				
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
				HomeLoadMoreData homeLoadMoreData=new HomeLoadMoreData();
				homeLoadMoreData.execute(Integer.toString(page));
				page++;
			}
		});
		
		//���һ��������˵���
				gestureDetector = new BuileGestureExt(this,new BuileGestureExt.OnGestureResult() {
		            @Override
		            public void onGestureResult(int direction) {
		            	if (direction==3) {
		            		screenWidth = MainActivity.this.getWindowManager().getDefaultDisplay().getWidth();
		            	    screenHeight = MainActivity.this.getWindowManager().getDefaultDisplay().getHeight(); 
		   		    	    View left_menu_view = getLayoutInflater().inflate(R.layout.leftmenu, null,false);
		   		    	    left_menu = new PopupWindow(left_menu_view, screenWidth, screenHeight, true);
		   		    	    LeftMenu.initPopuptWindow(left_menu, left_menu_view,MainActivity.this);
		   				    left_menu.showAsDropDown(findViewById(R.id.toptitle), 0, screenHeight/6);
						}
		            	
		            }
		        }
		        ).Buile();
		
		
		
	}	
		
		
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
			}
		}

	}

	/**
	 * ��ViewPager��ҳ���״̬�����ı�ʱ����
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

			
		}
	}

	/**
	 * ���ViewPagerҳ���������
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
	
	//listviewˢ��
	
	
public class HomeListviewRefresh extends AsyncTask<Void, Void, List<NewsHead>> {
		
		private String jsonString;


		public HomeListviewRefresh() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onPreExecute() {
		// TODO Auto-generated method stub
			homepage_loading_progressBar.setVisibility(View.VISIBLE);
			homepage_refreshButton.setVisibility(View.INVISIBLE);
			homepage_set_netButton.setVisibility(View.INVISIBLE);
			listView.setVisibility(View.INVISIBLE);
		super.onPreExecute();
		}

		@Override
		protected List<NewsHead> doInBackground(Void... parm) {
			// TODO Auto-generated method stub
		    StringBuffer pathBuffer=new StringBuffer("http://www.peizheng.cn/mobile/index.php?interfaceid=0101&page=1&limit=10"+"&cname=dfly&cpwd=123456");
			jsonString = HttpTools.getJsonString(pathBuffer.toString());
			List<NewsHead> newsHeads=new ArrayList<NewsHead>();
			newsHeads=JsonTools.getNewsHeads(jsonString);
			return newsHeads;
		}
		
		

		@Override
		protected void onPostExecute(List<NewsHead> result) {
			// TODO Auto-generated method stub
			homepage_loading_progressBar.setVisibility(View.INVISIBLE);
			if (result.toString().equals("[]")) {
				
				homepage_refreshButton.setVisibility(View.VISIBLE);
				homepage_set_netButton.setVisibility(View.VISIBLE);
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

public class HomeLoadMoreData extends AsyncTask<String, Void, List<NewsHead>> {
	
	private String jsonString;


	public HomeLoadMoreData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<NewsHead> doInBackground(String... parm) {
		// TODO Auto-generated method stub
		String page = parm[0];
	    StringBuffer pathBuffer=new StringBuffer("http://www.peizheng.cn/mobile/index.php?interfaceid=0101&page="+page+"&limit=10"+"&cname=dfly&cpwd=123456");
		jsonString = HttpTools.getJsonString(pathBuffer.toString());
		List<NewsHead> newsHeads=new ArrayList<NewsHead>();
		newsHeads=JsonTools.getNewsHeads(jsonString);
		
		return newsHeads;
	}
	
	
	@Override
	protected void onPostExecute(List<NewsHead> result) {
		// TODO Auto-generated method stub
		if (result.toString().equals("[]")) {
			
			Toast.makeText(MainActivity.this, "���粻�����������ԣ�",Toast.LENGTH_SHORT).show();
		}		
		list.addAll(newsListview.getData(result,adapter));
		adapter.notifyDataSetChanged();
		loadmoreButton.setVisibility(View.VISIBLE);
		loadmoreprogressBar.setVisibility(View.INVISIBLE);
		loadmoretextView.setVisibility(View.INVISIBLE);
		
		super.onPostExecute(result);
	}
}
	
public class PicturesList extends AsyncTask<Void, Void, List<NewsPictures>> {
	
	private String jsonString;

	public PicturesList() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected List<NewsPictures> doInBackground(Void... parm) {
		// TODO Auto-generated method stub
	    StringBuffer pathBuffer=new StringBuffer("http://www.peizheng.cn/mobile/index.php?interfaceid=0102&page=1&limit=10&catid=252&cname=dfly&cpwd=123456");
		jsonString = HttpTools.getJsonString(pathBuffer.toString());
		List<NewsPictures> newsPictures=new ArrayList<NewsPictures>();
		newsPictures=JsonTools.getPictures(jsonString);
		
		return newsPictures;
	}

	@Override
	public void onPostExecute(List<NewsPictures> result) {
		// TODO Auto-generated method stub
		try {
			
			titles = new String[5];
			for (int i = 0; i < 5; i++) {
				titles[i] = result.get(i).getTitle();
			}	

			imageViews = new ArrayList<ImageView>();

			// ��ʼ��ͼƬ��Դ
			for (int i = 0; i < 5; i++) {
				ImageView imageView = new ImageView(MainActivity.this);
				DownLoadImage_top downLoadImage_top=new DownLoadImage_top(imageView);
				downLoadImage_top.execute(result.get(i).getImgUrl());
				imageView.setScaleType(ScaleType.CENTER_INSIDE);
				imageViews.add(imageView);
			}
			dots = new ArrayList<View>();
			dots.add(findViewById(R.id.v_dot0));
			dots.add(findViewById(R.id.v_dot1));
			dots.add(findViewById(R.id.v_dot2));
			dots.add(findViewById(R.id.v_dot3));
			dots.add(findViewById(R.id.v_dot4));

			tv_title = (TextView)findViewById(R.id.tv_title);
			tv_title.setText(titles[0]);

			viewPager = (ViewPager)findViewById(R.id.vp);
			viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
			// ����һ������������ViewPager�е�ҳ��ı�ʱ����
			viewPager.setOnPageChangeListener(new MyPageChangeListener());
			
			
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			// ÿ�������л�һ��ͼƬ��ʾ
			scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ͼƬ��ȡ����");
		}
		super.onPostExecute(result);
	}
}
	
	
    
	
	//����ʱ�õĴ����¼�	
		@Override
	    public boolean onTouchEvent(MotionEvent event) {
			
	       return gestureDetector.onTouchEvent(event);
	    }	
			
					
				
		
		//��ֹ�����¼���listview��item�¼�����
		@Override
		 public boolean dispatchTouchEvent(MotionEvent ev) {
		  // TODO Auto-generated method stub
		  this.gestureDetector.onTouchEvent(ev);
		  
		  return super.dispatchTouchEvent(ev);
		 }	
		

	
	//�˵�

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		     if(keyCode == KeyEvent.KEYCODE_MENU) {
		        //���/���ز˵���
		    	//��ȡ��Ļ�߿�   
		         screenWidth = MainActivity.this.getWindowManager().getDefaultDisplay().getWidth();   
		         screenHeight = MainActivity.this.getWindowManager().getDefaultDisplay().getHeight();   
		    	 View bottom_menu_view = getLayoutInflater().inflate(R.layout.bottommenu, null,false);
		    	 bottom_menu = new PopupWindow(bottom_menu_view, screenWidth, screenHeight, true);
				 BottomMenu.initPopuptWindow(bottom_menu, bottom_menu_view,MainActivity.this);
				 bottom_menu.showAtLocation(findViewById(R.id.mainactivity), Gravity.BOTTOM, 0, 0);
		    } 
		     if (keyCode==KeyEvent.KEYCODE_BACK ) {
		    	 
		    	 screenWidth = MainActivity.this.getWindowManager().getDefaultDisplay().getWidth();   
		         screenHeight = MainActivity.this.getWindowManager().getDefaultDisplay().getHeight();   
		    	 View dialog_view = getLayoutInflater().inflate(R.layout.mydialog, null,false);
		    	 dialogPopupWindow= new PopupWindow(dialog_view, screenWidth, screenHeight/2, true);
				 MyDialog.initPopuptWindow(dialogPopupWindow, dialog_view,MainActivity.this);
	             dialogPopupWindow.showAtLocation(findViewById(R.id.mainactivity), Gravity.CENTER, 0, screenHeight/20);
				
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

