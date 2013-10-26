package pz.rg.downloadimage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.example.dreamfly.ListviewAdapter;
import com.example.dreamfly.MainActivity;
import com.example.dreamfly.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * 在4.0系统中显示网络图片
 * 
 * @author Administrator
 * 
 */
public class DownLoadImage extends AsyncTask<String, Integer, Bitmap> {
	
	private Map<String, Object> map;
	private static int id=0;
	private ListviewAdapter adapter;

	public DownLoadImage(Map<String, Object> map,ListviewAdapter adapter) {
		// TODO Auto-generated constructor stub
		this.map = map;
		this.adapter=adapter;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		// TODO Auto-generated method stub
		String httpUrl = urls[0];
		Bitmap tmpBitmap = null;
		int[] imageid=new int[20];
		if (imageid[0]==0) {
			imageid[0]=R.drawable.tb1;
			imageid[1]=R.drawable.tb2;
			imageid[2]=R.drawable.tb3;
			imageid[3]=R.drawable.tb4;
			imageid[4]=R.drawable.tb5;
			imageid[5]=R.drawable.tb6;
			imageid[6]=R.drawable.tb7;
			imageid[7]=R.drawable.tb8;
			imageid[8]=R.drawable.tb9;
			imageid[9]=R.drawable.tb10;
			imageid[10]=R.drawable.tb11;
			imageid[11]=R.drawable.tb12;
			imageid[12]=R.drawable.tb13;
			imageid[13]=R.drawable.tb14;
			imageid[14]=R.drawable.tb15;
			imageid[15]=R.drawable.tb16;
			imageid[16]=R.drawable.tb17;
			imageid[17]=R.drawable.tb18;
			imageid[18]=R.drawable.tb19;
			imageid[19]=R.drawable.tb20;
		}
		try {
			URL url = new URL(httpUrl);  
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5*1000);
            conn.setReadTimeout(5*1000);
            conn.connect();  
            if (conn.getResponseCode()==200) {
            	InputStream in=conn.getInputStream();  
                ByteArrayOutputStream bos=new ByteArrayOutputStream();  
                byte[] buffer=new byte[1024];  
                int len = 0;  
                while((len=in.read(buffer))!=-1){  
                    bos.write(buffer,0,len);  
                }  
                byte[] dataImage=bos.toByteArray();  
                bos.close();  
                in.close();  
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize = 10;
                tmpBitmap=BitmapFactory.decodeByteArray(dataImage, 0, dataImage.length);
                if (tmpBitmap==null) {
                	if (id==20) {
        				id=0;
        			}
        			
        			Resources res = MainActivity.mainActivity.getResources();
        			tmpBitmap = BitmapFactory.decodeResource(res, imageid[id]);
        			id++;
				}
                publishProgress(1);
                return tmpBitmap;
			}
            else {
            	if (id==20) {
    				id=0;
    			}
    			
    			Resources res = MainActivity.mainActivity.getResources();
    			tmpBitmap = BitmapFactory.decodeResource(res, imageid[id]);
    			id++;
                publishProgress(1);
    			return tmpBitmap;
			}
            
            
           
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		map.put("img", result);
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		System.out.println(values[0]);
		if (values[0]==1) {
			adapter.notifyDataSetChanged();
		}
		super.onProgressUpdate(values);
	}
}
