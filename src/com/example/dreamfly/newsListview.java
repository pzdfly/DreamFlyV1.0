package com.example.dreamfly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pz.rg.domain.NewsHead;
import pz.rg.downloadimage.DownLoadImage;

public class newsListview {
	
	 //ListView上需要显示的数据
    public static List<Map<String, Object>> getData(List<NewsHead> newhead,ListviewAdapter adapter) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        
        //设置绑定是数据是图片
        for (int i = 0; i < newhead.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("newsid", newhead.get(i).getNewsId());
			map.put("databaseid", newhead.get(i).getDatabaseId());
			DownLoadImage downLoadImage=new DownLoadImage(map,adapter);
        	downLoadImage.execute(newhead.get(i).getImgUrl());
            map.put("title", newhead.get(i).getTitle());
            map.put("info", newhead.get(i).getIntroduce());
            list.add(map);
		}
        
        

        return list;
    }

}
