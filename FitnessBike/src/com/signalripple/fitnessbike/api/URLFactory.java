package com.signalripple.fitnessbike.api;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.util.Log;

public class URLFactory {

	/**
	 * URL生产拼接
	 * @param api
	 * @param map
	 * @return
	 */
	public static String getURL(String api,Map<String,Object> map)
	{
		String temp = API.Host + api + "?";
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		
		while (iterator.hasNext()) 
		{
			Object object = iterator.next();
			String key = (String) object;
			if(key != null)
			temp += (key+"="+map.get(key)+"&");
		}
		// 去除最后一个‘&’
		temp = temp.substring(0,temp.length()-1);
		return temp;
	}
}
