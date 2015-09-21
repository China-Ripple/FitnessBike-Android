package com.signalripple.fitnessbike;

import android.app.Application;

import com.signalripple.fitnessbike.api.API;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

	public class MyApplication extends Application{

		public static IWXAPI wxapi;
//		public static String mAPPid= "wx9972e13ac66616bf";
		
		private static MyApplication instance;
		
		
		public static MyApplication getContext(){
			return instance;
		}


		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			if(wxapi == null)
			{
				wxapi = WXAPIFactory.createWXAPI(this, API.AppID,  true); 
				wxapi.registerApp(API.AppID);
			}
			instance=this;
		}
	}
