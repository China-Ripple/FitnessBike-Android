package com.signalripple.fitnessbike.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.signalripple.fitnessbike.PKSelectActivity;
import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.bean.ActionBean;
import com.signalripple.fitnessbike.bean.FriendBean;
import com.signalripple.fitnessbike.bean.PKBean;
import com.signalripple.fitnessbike.utils.ShareDB;
import com.signalripple.fitnessbike.view.CircleImageView;


public class FriendListViewAdapter extends MyBaseAdapter {

//	private List<ActionBean> list;
	private Context context;
	private LayoutInflater inflater;
	private MRequset mRequset;

	public FriendListViewAdapter(Context context)
	{
		mRequset = MRequset.getInstance(context);
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	
	public FriendListViewAdapter(Context context,List<ActionBean> list)
	{
		inflater = LayoutInflater.from(context);
		this.context = context;
//		this.list = list;
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

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void clear() {
		this.alObjects.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		FriendBean bean = (FriendBean) getItem(position);
		if(bean == null)
			return null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.friend_listview_item, null);
			holder.circleImageViewForHead = (CircleImageView)convertView.findViewById(R.id.ivHead);
			holder.ivNumberOfImage = (ImageView)convertView.findViewById(R.id.ivNumberOfImage);
			holder.txtNumberOfText = (TextView)convertView.findViewById(R.id.ivNumberOfText);
			holder.ivZan = (ImageView)convertView.findViewById(R.id.ivZan);
			holder.txtDistance = (TextView)convertView.findViewById(R.id.txtDistance);
			holder.txtName = (TextView)convertView.findViewById(R.id.txtName);
			holder.txtZanNumber = (TextView)convertView.findViewById(R.id.txtZanNumber);
			holder.btnPK = (Button)convertView.findViewById(R.id.btnPK);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(bean.getPosition() == 1)
		{
			holder.ivNumberOfImage.setVisibility(View.VISIBLE);
			holder.txtNumberOfText.setVisibility(View.GONE);
			holder.ivNumberOfImage.setBackgroundResource(R.drawable.first);
		}
		else if(bean.getPosition() == 2)
		{
			holder.ivNumberOfImage.setVisibility(View.VISIBLE);
			holder.txtNumberOfText.setVisibility(View.GONE);
			holder.ivNumberOfImage.setBackgroundResource(R.drawable.second);
		}
		else if(bean.getPosition() == 3)
		{
			holder.ivNumberOfImage.setVisibility(View.VISIBLE);
			holder.txtNumberOfText.setVisibility(View.GONE);
			holder.ivNumberOfImage.setBackgroundResource(R.drawable.third);
		}
		else
		{
			holder.ivNumberOfImage.setVisibility(View.GONE);
			holder.txtNumberOfText.setVisibility(View.VISIBLE);
			holder.txtNumberOfText.setText(""+bean.getPosition());
		}
		
		holder.btnPK.setOnClickListener(new PKButtonClickListener(position));
		holder.txtName.setText(bean.getName());
		holder.txtDistance.setText(bean.getKilometre()+" km");

		return convertView;
	}
	
	class PKButtonClickListener implements OnClickListener 
	{
		int position;
		
		public PKButtonClickListener(int position)
		{
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FriendBean friendBean = (FriendBean) getItem(position);
			
			Intent intent = new Intent(context, PKSelectActivity.class);
			// 发起者id
			intent.putExtra("defierid", ShareDB.getStringFromDB(context, "account"));
			// 被发起者id
			intent.putExtra("defenderid", friendBean.getId());
			((Activity)context).overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
			context.startActivity(intent);
		}
	}
	
	private static final class  ViewHolder 
	{
		private ImageView ivNumberOfImage;
		private ImageView ivZan;
		private CircleImageView circleImageViewForHead;
		private TextView  txtNumberOfText;
		private TextView txtName;
		private TextView txtDistance;
		private TextView txtZanNumber;
		private Button btnPK;
	}
}
