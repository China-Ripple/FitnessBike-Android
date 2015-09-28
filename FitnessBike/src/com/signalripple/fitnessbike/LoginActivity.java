package com.signalripple.fitnessbike;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.interfaces.IActivity;
import com.signalripple.fitnessbike.utils.Activity2Activity;
import com.signalripple.fitnessbike.utils.ShareDB;
import com.signalripple.fitnessbike.utils.ToastUtil;

public class LoginActivity extends BaseActivity implements IActivity, OnClickListener{

	private EditText etUserName;
	private EditText etPassWord;
	private Button btnLogin;
	private TextView txtReturn;
	private TextView txtRegister;
	private MRequset mRequset;
	private ProgressDialog dialog;
	String account;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		auto();
		
		setContentView(R.layout.activity_login_layout);
		
		initView();
		initValue();
		initEvent();
	}


	private void auto() {
		// TODO Auto-generated method stub
		String token = ShareDB.getStringFromDB(this, "token");
		if(token != null)
		{
			Intent intent = new Intent(this, MainTabActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		etUserName = (EditText)this.findViewById(R.id.etUserName);
		etPassWord = (EditText)this.findViewById(R.id.etPassWord);
		btnLogin   = (Button)this.findViewById(R.id.btnLogin);
		txtReturn  = (TextView)this.findViewById(R.id.include_view_btnLeft);
		txtRegister= (TextView)this.findViewById(R.id.include_view_btnRight);
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		mRequset = MRequset.getInstance(this);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(this);
		txtRegister.setOnClickListener(this);
		txtReturn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			// 登陆
		case R.id.btnLogin:
			login();
			break;
			// 返回
		case R.id.include_view_btnLeft:
			finish();
			break;
			// 注册
		case R.id.include_view_btnRight:
			Activity2Activity.gotoNewActivity(this, RegisterActivity.class);
			break;

		default:
			break;
		}
	}


	private void login() {
		account = getTextValue(etUserName);
		password = getTextValue(etPassWord);
		
		if(account == null || password == null)
			return;
		
		dialog = ProgressDialog.show(this, "温馨提示", "正在验证,请稍等");
		
		if( account!= null && password != null)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("account", account);
			map.put("password", password);
			mRequset.requestForJsonObject(URLFactory.getURL(API.apiLogin, map),null, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					// TODO Auto-generated method stub
					try {
						if(response != null)
						{
							Log.i("XU", "登陆请求 ---》result="+response.toString());
							dialog.dismiss();
							String result = response.getString("response");
							// 服务器验证成功 ，可以登录
							if("success".equals(result))
							{
								ShareDB.save2DB(LoginActivity.this, "token", response.getString("token"));
								ShareDB.save2DB(LoginActivity.this, "account", account);
								ShareDB.save2DB(LoginActivity.this, "password", password);

								Activity2Activity.gotoNewActivity(LoginActivity.this, MainTabActivity.class);
							}
							else
							{
								ToastUtil.show(LoginActivity.this, "登陆失败", ToastUtil.ERROR);
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	private String getTextValue(EditText editText) {
		// TODO Auto-generated method stub
		String temp = editText.getText().toString();
		if(temp == null || temp.trim().equals(""))
		{
			editText.setError("不能为空");
			return null;
		}
		else 
			return temp;
			
	}
}
