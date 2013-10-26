package com.example.dreamfly;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListviewAdapter extends BaseAdapter {  
    private List<Map<String, Object>> list;  
    Context context;  
      
    public ListviewAdapter(Context context,List<Map<String, Object>> list){  
        this.list = list;  
        this.context = context;  
    }  
  
    @Override  
    public int getCount() {  
        return (list==null)?0:list.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return list.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
      
      
    public class ViewHolder{  
        TextView title;  
        TextView info; 
        ImageView img;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {    
        ViewHolder viewHolder = null;  
        if(convertView==null){  
            convertView = LayoutInflater.from(context).inflate(  
                    R.layout.vlist, null);  
              
            viewHolder = new ViewHolder();  
            viewHolder.title = (TextView)convertView.findViewById(  
                    R.id.title);  
            viewHolder.info = (TextView)convertView.findViewById(  
                    R.id.info);   
            viewHolder.img = (ImageView)convertView.findViewById(R.id.img);            
            convertView.setTag(viewHolder);  
        }else{  
            viewHolder = (ViewHolder)convertView.getTag();   
        }  
          
        viewHolder.title.setText(list.get(position).get("title").toString());  
        viewHolder.info.setText(list.get(position).get("info").toString()); 
        viewHolder.img.setImageBitmap((Bitmap)list.get(position).get("img")); 
        
          
          
        return convertView;  
    }

	
  
}  