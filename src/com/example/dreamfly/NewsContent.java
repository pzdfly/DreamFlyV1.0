package com.example.dreamfly;

import com.umeng.analytics.MobclickAgent;

import pz.rg.domain.NewsContentDefine;
import pz.rg.exit.ExitApplication;
import pz.rg.http.HttpTools;
import pz.rg.json.tool.JsonTools;
import pz.rg.menu.BottomMenu;
import pz.rg.menu.LeftMenu;
import pz.rg.sliding.BuileGestureExt;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class NewsContent extends Activity {

	
	private ScrollView content_scrollview;
	private Button content_refreshButton;
	private Button content_set_netButton;
	private ProgressBar content_loading_progressBar;
	final String mimetype = "text/html";  
    final String encoding = "utf-8"; 
	private TextView title;
    private TextView author;
    private TextView copyfrom;
    private TextView time;
    private WebView content;
	private int screenWidth;
	private int screenHeight;
	private PopupWindow left_menu;//�˵��ؼ�
	private PopupWindow bottom_menu;
	private GestureDetector gestureDetector;//�����ؼ�
	public static NewsContent newsContentactivity;

	public NewsContent() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newscontent);
		newsContentactivity=this;
		ExitApplication.getInstance().addActivity(this);
		
		
		
		
		final Intent intent=getIntent();
		title=(TextView)this.findViewById(R.id.title);
		author=(TextView)this.findViewById(R.id.author);
		copyfrom=(TextView)this.findViewById(R.id.copyfrom);
		time=(TextView)this.findViewById(R.id.time);
		content=(WebView)this.findViewById(R.id.newscontent);
		
		
		content_loading_progressBar=(ProgressBar)findViewById(R.id.content_loading_progress);
		content_refreshButton=(Button)findViewById(R.id.content_refresh);
		content_set_netButton=(Button)findViewById(R.id.content_set_net);
		content_scrollview=(ScrollView)findViewById(R.id.content_scrollview);
		content_scrollview.setVisibility(View.INVISIBLE);
		
		
		content_refreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetNewsContent getNewsContent=new GetNewsContent(intent.getStringExtra("newsid"));
				getNewsContent.execute(intent.getStringExtra("databaseid"));
			}
		});
		
		content_set_netButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent contentintent=null;
                //�ж��ֻ�ϵͳ�İ汾  ��API����10 ����3.0�����ϰ汾 
                if(android.os.Build.VERSION.SDK_INT>10){
                	contentintent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                	contentintent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    contentintent.setComponent(component);
                    contentintent.setAction("android.intent.action.VIEW");
                }
                startActivity(contentintent);
			}
		});
		
		
		GetNewsContent getNewsContent=new GetNewsContent(intent.getStringExtra("newsid"));
		getNewsContent.execute(intent.getStringExtra("databaseid"));
		
		
		//���һ��������˵���
		gestureDetector = new BuileGestureExt(this,new BuileGestureExt.OnGestureResult() {
            @Override
            public void onGestureResult(int direction) {
            	if (direction==3) {
            		screenWidth = NewsContent.this.getWindowManager().getDefaultDisplay().getWidth();
            	    screenHeight = NewsContent.this.getWindowManager().getDefaultDisplay().getHeight(); 
   		    	    View left_menu_view = getLayoutInflater().inflate(R.layout.leftmenu, null,false);
   		    	    left_menu = new PopupWindow(left_menu_view, screenWidth, screenHeight, true);
   		    	    LeftMenu.initPopuptWindow(left_menu, left_menu_view,NewsContent.this);
   				    left_menu.showAsDropDown(findViewById(R.id.toptitle_content), 0, screenHeight/6);
				}
            	
            }
        }
        ).Buile();
	}
	
public class GetNewsContent extends AsyncTask<String, Void, NewsContentDefine> {
		
		private String jsonString;
		private String newsid;


		public GetNewsContent(String newsid) {
			// TODO Auto-generated constructor stub
			this.newsid=newsid;
		}
		
		@Override
		protected void onPreExecute() {
		// TODO Auto-generated method stub
			content_scrollview.setVisibility(View.INVISIBLE);
			content_loading_progressBar.setVisibility(View.VISIBLE);
			content_refreshButton.setVisibility(View.INVISIBLE);
			content_set_netButton.setVisibility(View.INVISIBLE);
			
		super.onPreExecute();
		}
		

		@Override
		protected NewsContentDefine doInBackground(String... parm) {
			// TODO Auto-generated method stub
			String databaseid = parm[0];
			StringBuffer path = new StringBuffer("http://www.peizheng.cn/mobile/index.php?interfaceid=0104&databaseid="+databaseid+"&newsid="+newsid+"&cname=dfly&cpwd=123456");
			jsonString = HttpTools.getJsonString(path.toString());
			NewsContentDefine newsContent=JsonTools.getNewsContent(jsonString);
			return newsContent;
		}


		@Override
		protected void onPostExecute(NewsContentDefine result) {
			// TODO Auto-generated method stub
			content_loading_progressBar.setVisibility(View.INVISIBLE);
			if (result.getTitle()==null) {
				
				content_refreshButton.setVisibility(View.VISIBLE);
				content_set_netButton.setVisibility(View.VISIBLE);
			}
			else {
				if (result.getAuthor().equals("")) {
					result.setAuthor("��");
				}
				if (result.getCopyfrom().equals("")) {
					result.setCopyfrom("��");
				}
				
				content_scrollview.setVisibility(View.VISIBLE);
			}
			
			
			title.setText(result.getTitle());
			author.setText("����:"+result.getAuthor()+"   ");
			copyfrom.setText("��Դ:"+result.getCopyfrom());
			time.setText(result.getTime());
			content.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			content.setHorizontalScrollBarEnabled(false);
			content.getSettings().setJavaScriptEnabled(true);
			content.getSettings().setUseWideViewPort(false);
			content.getSettings().setLoadWithOverviewMode(true);
			content.loadDataWithBaseURL(null, result.getContent(), mimetype, encoding, null);
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
			         screenWidth = NewsContent.this.getWindowManager().getDefaultDisplay().getWidth();   
			         screenHeight = NewsContent.this.getWindowManager().getDefaultDisplay().getHeight();   
			    	 View bottom_menu_view = getLayoutInflater().inflate(R.layout.bottommenu, null,false);
			    	 bottom_menu = new PopupWindow(bottom_menu_view, screenWidth, screenHeight, true);
					 BottomMenu.initPopuptWindow(bottom_menu, bottom_menu_view,NewsContent.this);
					 bottom_menu.showAtLocation(findViewById(R.id.newcontent_activity), Gravity.BOTTOM, 0, 0);
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
