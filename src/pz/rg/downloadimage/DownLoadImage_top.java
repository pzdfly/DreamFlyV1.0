package pz.rg.downloadimage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.dreamfly.MainActivity;
import com.example.dreamfly.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * 在4.0系统中显示网络图片
 * 
 * @author Administrator
 * 
 */
public class DownLoadImage_top extends AsyncTask<String, Void, Bitmap> {
	
	private ImageView imageView;

	public DownLoadImage_top(ImageView imageView) {
		// TODO Auto-generated constructor stub
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		// TODO Auto-generated method stub
		String httpUrl = urls[0];
		Bitmap tmpBitmap = null;
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
        			Resources res = MainActivity.mainActivity.getResources();
        			tmpBitmap = BitmapFactory.decodeResource(res, R.drawable.default_image_top);
        			return tmpBitmap;
                }
			}
            else {
            	Resources res = MainActivity.mainActivity.getResources();
    			tmpBitmap = BitmapFactory.decodeResource(res, R.drawable.default_image_top);
    			return tmpBitmap;
			}
            
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("test", e.getMessage());
		}
		return tmpBitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		imageView.setImageBitmap(result);
	}
}
