package com.signalripple.fitnessbike.adapter;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.fireup.yuanyang.adapter.MyBaseAdapter;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.bean.PKBean;
import com.signalripple.fitnessbike.utils.ShareDB;
import com.signalripple.fitnessbike.utils.ToastUtil;

public class PKAdapter extends MyBaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private MRequset mRequset;
	
	public PKAdapter(Context context)
	{
		this.context = context;
		mRequset = MRequset.getInstance(context);
		inflater = LayoutInflater.from(context);
	}
	
	public void clear() {
		this.alObjects.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alObjects.size();//.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return alObjects.get(position);
	}

	int position;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.pkinfo_list_item, null);
			holder.ivTypeIcon = (ImageView)convertView.findViewById(R.id.ivIcon);
			holder.txtAccept = (TextView)convertView.findViewById(R.id.txtAccept);
			holder.txtRefuse = (TextView)convertView.findViewById(R.id.txtRefuse);
			holder.txtName = (TextView)convertView.findViewById(R.id.txtName);
			holder.txtType = (TextView)convertView.findViewById(R.id.txtType);
//			holder.btnPK = (Button)convertView.findViewById(R.id.btnPK);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtAccept.setOnClickListener(new ClickListener(API.ACCEPT,position));
		holder.txtRefuse.setOnClickListener(new ClickListener(API.REFUSE,position));
//		holder.btnPK.setOnClickListener(new PKButtonClickListener(position));
		return convertView;
	}
	

//	class PKButtonClickListener implements OnClickListener 
//	{
//		int position;
//		
//		public PKButtonClickListener(int position)
//		{
//			this.position = position;
//		}
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Map<String, Object> map = new HashMap<String, Object>();
//			PKBean pkBean = (PKBean) getItem(position);
//			map.put("defierid", ShareDB.getStringFromDB(context, "account"));
//			map.put("defenderid", pkBean.getDefierid());
//			mRequset.requestForJsonObject(URLFactory.getURL(API.apiCompitition, map), null, new Listener<JSONObject>() {
//
//				@Override
//				public void onResponse(JSONObject response) {
//					// TODO Auto-generated method stub
//					
//				}
//			}, new ErrorListener() {
//
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					// TODO Auto-generated method stub
//					Log.i("XU", "请求ak时错误"+error.toString());
//				}
//			});
//		}
//	}

	class ClickListener implements OnClickListener 
	{
		int position;
		int type;
		
		public ClickListener(int type, int position)
		{
			this.type = type;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i("XU", "position="+position);
			PKBean pkBean = (PKBean) getItem(position);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", ShareDB.getStringFromDB(context, "account"));
			map.put("msgid", pkBean.getId());
			map.put("code", type);
			deleteItem(position);
//			getAlObjects().remove(position);
//			notifyDataSetChanged();
			mRequset.requestForJsonObject(URLFactory.getURL(API.apiMsgresponse, map), null, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					// TODO Auto-generated method stub
					Log.i("XU","respone"+response.toString());
					try {
						if("success".equals(response.getString("respone")))
						{
							if(type == 1)
							{
								Log.i("XU", "你已接受对方邀请");
								ToastUtil.show(context, "你已接受对方请求", ToastUtil.INFO);
							}
							else 
							{
								Log.i("XU", "你已拒接对方邀请");
								ToastUtil.show(context, "你已接拒接方请求", ToastUtil.INFO);
								// todo : 拒绝之后只需把本条记录取消（删除隐藏）
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
					Log.i("XU", "发送接受PK请求时错误:"+error.toString());
				}
			});
		}
	}

	private static final class  ViewHolder 
	{
		private TextView txtRefuse;
		private TextView txtAccept;
		private TextView txtName;
		private TextView txtType;
		private ImageView ivTypeIcon;
//		private Button btnPK;
	}

}
