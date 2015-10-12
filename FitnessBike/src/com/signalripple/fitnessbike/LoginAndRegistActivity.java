package com.signalripple.fitnessbike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.ParseByteData;
import com.signalripple.fitnessbike.bluetooth.BlueTooth;
import com.signalripple.fitnessbike.utils.Activity2Activity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginAndRegistActivity extends BaseActivity implements OnClickListener {

	private TextView txtLogin;
	private TextView txtRegister;
	private Tencent tencent;
	private ImageButton btnQQLogin;
	private ImageButton btnWXLogin;
	BaseUiListener baseUiListener = new BaseUiListener();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_and_register);
		
		initViews();
		initEvent();
		//testBlueThooth();
	}
	
	
	private void testBlueThooth() {
		// TODO Auto-generated method stub
		BlueTooth blueTooth = new BlueTooth(this);
		blueTooth.openBlueTooth();
		blueTooth.searchDevices();
		blueTooth.getAleadyPairDevices();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btnWXLogin.setOnClickListener(this);
		btnQQLogin.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtRegister.setOnClickListener(this);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		btnWXLogin = (ImageButton)this.findViewById(R.id.ibWeiXinLogin);
		btnQQLogin = (ImageButton)this.findViewById(R.id.ibQQLogin);

		tencent = Tencent.createInstance(API.QQAPPID, this.getApplicationContext());
		txtLogin = (TextView)this.findViewById(R.id.txtLogin);
		txtRegister = (TextView)this.findViewById(R.id.txtRegister);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibWeiXinLogin:
		    SendAuth.Req req = new SendAuth.Req();
		    req.openId= API.AppID;
		    req.scope = "snsapi_userinfo";
		    req.state = "wechat_sdk_demo_test";
		    MyApplication.wxapi.sendReq(req);
			
			break;
		case R.id.ibQQLogin:
			if(tencent != null)
			{
				if (!tencent.isSessionValid())
				{
					tencent.login(this, API.SCOPE, baseUiListener);
					//Activity2Activity.gotoNewActivity(this, MainTabActivity.class);
				}
			}
			break;
		case R.id.txtLogin:
			Activity2Activity.gotoNewActivity(this, LoginActivity.class);
			break;
		case R.id.txtRegister:
			Activity2Activity.gotoNewActivity(this, LoginActivity.class);
			break;
		default:
			break;
		}
	}
	
	@Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	                Tencent.handleResultData(data, baseUiListener);  
	            super.onActivityResult(requestCode, resultCode, data);  
	        } 

	
    @Override  
    protected void onDestroy() {  
        if (tencent != null) {  
            //注销登录  
            tencent.logout(this); 
        }  
        super.onDestroy();  
    }  
	
	private class BaseUiListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Toast.makeText(LoginAndRegistActivity.this, "用户取消登陆授权", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			Log.i("XU", "结果="+arg0.toString());
			Toast.makeText(LoginAndRegistActivity.this, arg0.toString(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(LoginAndRegistActivity.this, "登陆授权出错", Toast.LENGTH_SHORT).show();
		}
	}
	
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	
    }

}
