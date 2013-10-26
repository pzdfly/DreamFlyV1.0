package pz.rg.json.tool;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pz.rg.domain.Baoxiudan;
import pz.rg.domain.Login;
import pz.rg.domain.NewsContentDefine;
import pz.rg.domain.NewsHead;
import pz.rg.domain.NewsPictures;
import pz.rg.domain.Notice;
import pz.rg.domain.fixList;
import pz.rg.domain.listInfo;


public class JsonTools {
	
	public static String getStatus(String jsonString){
		String status = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			status = jsonObject.getString("status");
		} catch (Exception e) {
			System.out.println("JsonObject is error");
			//e.printStackTrace();
		}
		return status;
	}
	
	public static String getHasData(String jsonString){
		String hasData = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			hasData = jsonObject.getString("hasdata");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return hasData;
	}
	
	public static String getInfo(String jsonString){
		String info = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			info = jsonObject.getString("info");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}
	
	public static List<NewsHead> getNewsHeads(String jsonString){
		List<NewsHead> list = null;
		try {
			list = new ArrayList<NewsHead>();
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("newsHead");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				NewsHead newsHead = new NewsHead();
				newsHead.setNewsId(jsonObject2.getString("newsid"));
				newsHead.setDatabaseId(jsonObject2.getString("databaseid"));
				newsHead.setTitle(jsonObject2.getString("title"));
				newsHead.setIntroduce(jsonObject2.getString("introduce"));
				newsHead.setTime(jsonObject2.getString("time"));
				newsHead.setImgUrl("http://www.peizheng.cn/mobile/"+jsonObject2.getString("imgurl"));
				//System.out.println(newsHead.toString());
				list.add(newsHead);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	
	public static NewsContentDefine getNewsContent(String jsonString){
		NewsContentDefine newsContent = new NewsContentDefine();
		try {
			JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("content");			
			newsContent.setTitle(jsonObject.getString("title"));
			newsContent.setAuthor(jsonObject.getString("author"));
			newsContent.setCopyfrom(jsonObject.getString("copyfrom"));
			newsContent.setTime(jsonObject.getString("time"));
			newsContent.setContent(jsonObject.getString("content"));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return newsContent;
	}
	public static List<NewsPictures> getPictures(String jsonString){
		List<NewsPictures> list = null;
		try {
			int j=0;
			list = new ArrayList<NewsPictures>();
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("newsHead");
			for(int i=0;i<jsonArray.length();i++){
				if (j==5) {
					break;
				}
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				String imgurl=jsonObject2.getString("imgurl");
				NewsPictures newsPictures = new NewsPictures();
				if (imgurl==null) {
					continue;
				}
				newsPictures.setTitle(jsonObject2.getString("title"));
				newsPictures.setImgUrl("http://www.peizheng.cn/"+jsonObject2.getString("imgurl"));
				//System.out.println(newsHead.toString());
				list.add(newsPictures);
				j++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public static List<Login> getLogin(String jsonString){
		List<Login> list=null;
		try {
			list=new ArrayList<Login>();
			JSONObject jsonObject=new JSONObject(jsonString);
			JSONArray jsonArray=jsonObject.getJSONArray("userinfo");
			
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				Login login=new Login();
				login.setUid(jsonObject2.getString("uid"));
				login.setUname(jsonObject2.getString("uname"));
				login.setRealname(jsonObject2.getString("realname"));
				login.setSex(jsonObject2.getString("sex"));
				login.setArea(jsonObject2.getString("area"));
				login.setDorm(jsonObject2.getString("dorm"));
				login.setEmail(jsonObject2.getString("email"));
				login.setPhone(jsonObject2.getString("phone"));
				login.setPcnumber(jsonObject2.getString("pcnumber"));
				login.setIdentity(jsonObject2.getString("identity"));	
				list.add(login);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return list;		
	}
	
	
	public static List<Baoxiudan> getBaoxiudan(String jsonString){
		List<Baoxiudan> list=null;
		try{
			list=new ArrayList<Baoxiudan>();
			JSONObject jsonObject=new JSONObject(jsonString);
			JSONArray jsonArray=jsonObject.getJSONArray("listHead");
			
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				Baoxiudan baoxiudan=new Baoxiudan();
				baoxiudan.setListid(jsonObject2.getString("listid"));
				baoxiudan.setComputertype(jsonObject2.getString("computertype"));
				baoxiudan.setBooktime(jsonObject2.getString("booktime"));
				baoxiudan.setSubmittime(jsonObject2.getString("submittime"));
				baoxiudan.setPcnumber(jsonObject2.getString("pcnumber"));
				baoxiudan.setState(jsonObject2.getString("state"));
				baoxiudan.setIsreview(jsonObject2.getString("isreview"));
				System.out.println(baoxiudan.toString());
				list.add(baoxiudan);			
			}		
		} catch (Exception e){	}
		return list;
	}
	
	public static List<Notice> getNotice(String jsonString){
		List<Notice> list=null;
		try{
			list=new ArrayList<Notice>();
			JSONObject jsonObject=new JSONObject(jsonString);
			JSONArray jsonArray=jsonObject.getJSONArray("notice");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				Notice notice=new Notice();
				notice.setNid(jsonObject2.getString("nid"));
				notice.setTitle(jsonObject2.getString("title"));
				notice.setNotice(jsonObject2.getString("notice"));
				notice.setTime(jsonObject2.getString("time"));
				list.add(notice);	
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public static List<fixList> getfixLists(String jsonString){
		List<fixList> list=null;
		try{
			list=new ArrayList<fixList>();
			JSONObject jsonObject=new JSONObject(jsonString);
			JSONArray jsonArray=jsonObject.getJSONArray("listHead");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				fixList fixlist=new fixList();
				fixlist.setListid(jsonObject2.getString("listid"));
				fixlist.setUname(jsonObject2.getString("uname"));
				fixlist.setComputertype(jsonObject2.getString("computertype"));
				fixlist.setBooktime(jsonObject2.getString("booktime"));
				fixlist.setSubmittime(jsonObject2.getString("submittime"));
				fixlist.setState(jsonObject2.getString("state"));
				list.add(fixlist);
			}
		}catch(Exception e){			}
	return list;
	}
	
	public static listInfo getInfos(String jsonString){
		listInfo listinfo=new listInfo();
		try{
			JSONObject jsonObject=new JSONObject(jsonString).getJSONObject("listInfo");
			listinfo.setListid(jsonObject.getString("listid"));
			listinfo.setType(jsonObject.getString("type"));
			listinfo.setUname(jsonObject.getString("uname"));
			listinfo.setArea(jsonObject.getString("area"));
			listinfo.setDorm(jsonObject.getString("dorm"));
			listinfo.setPhone(jsonObject.getString("phone"));
			listinfo.setComputertype(jsonObject.getString("computertype"));
			listinfo.setBooktime(jsonObject.getString("booktime"));
			listinfo.setTime(jsonObject.getString("time"));
			listinfo.setPcnumber(jsonObject.getString("pcnumber"));
			listinfo.setState(jsonObject.getString("state"));
			listinfo.setReviewlevel(jsonObject.getString("reviewlevel"));
			listinfo.setRemark(jsonObject.getString("remark"));
			//System.out.println(listinfo.getArea()+"111111111111111");
		}catch(Exception e){
			System.out.println("fjdkjfkdsojdkslmdklsjvlkdsdsf");
		}
		
		return listinfo;
	}
}
