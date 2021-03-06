package pz.rg.notice;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

import com.example.dreamfly.R;

import pz.rg.domain.Notice;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MC_Timer extends Handler {
	private static 	int 			TIMERID = 0;			//静态变量，保证ID唯一。当ID超过整形最大值时，应该把它恢复为0 
	private final 	int 			m_Interval;
	private   	List<Notice>	m_List = null;
	private final   TextView		m_Title;
	private final	TextView		m_Content;
	private final	TextView		m_Time;
	private int		m_Count			= 0;

	@SuppressWarnings("unchecked")
	public MC_Timer(int vInterval, Activity vActivity){
		m_Interval 	= vInterval;
		m_Title		= (TextView)vActivity.findViewById(R.id.tv_notice_title);
		m_Content	= (TextView)vActivity.findViewById(R.id.tv_notice_content);
		m_Time		= (TextView)vActivity.findViewById(R.id.tv_notice_time);
		
		try {
			InputStream 		tFileStream 	= vActivity.openFileInput("Notices");
			ObjectInputStream	tObjectStream	= new ObjectInputStream(tFileStream);
			m_List								= (List<Notice>) tObjectStream.readObject();
			tFileStream.close();
			tObjectStream.close();
		} catch (Exception e) {
			System.out.println("MC_Timer Conusrtor Error!");
		}	
		
		TIMERID++;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if(msg.what == TIMERID){
			Message tMessage = obtainMessage(TIMERID);
			this.sendMessageDelayed(tMessage, m_Interval);
		}

		m_Title.setText("通知："+m_List.get(m_Count).getTitle());
		m_Content.setText(m_List.get(m_Count).getNotice());
		m_Time.setText(m_List.get(m_Count).getTime());
		
		m_Count++;
		m_Count = m_Count % m_List.size(); 
	}
	
	public void startTimer(){
		Message msg = this.obtainMessage(TIMERID);
		this.sendMessage(msg);
	}
	
	public void stopTimer(){
		this.removeMessages(TIMERID);
	}
}
