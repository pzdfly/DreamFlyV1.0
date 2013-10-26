package pz.rg.http;

import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class HttpTools {
	
	private static String PHPSESSID = null;
	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	   
	public HttpTools(){
		
	}

	public static String getJsonString(String urlPath){
		try {
			HttpGet requestGet = new HttpGet(urlPath);
			if (PHPSESSID != null) {
				requestGet.setHeader("Cookie", "PHPSESSID"+PHPSESSID);
			}
			BasicHttpParams httpParams = new BasicHttpParams();  
		    HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
		    HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpResponse response = httpClient.execute(requestGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.print("Success!");
				List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					if ("PHPSESSID".equals(cookies.get(i).getName())) {
						PHPSESSID = cookies.get(i).getValue();
						break;
					}
				}
				String jsonString = EntityUtils.toString(response.getEntity());
				PHPSESSID=null;
				return jsonString;
			}
		} catch (Exception e) {
			System.out.println("网络不给力!");
		}
		return null;
	}
}
