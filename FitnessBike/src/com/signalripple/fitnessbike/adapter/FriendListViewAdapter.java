package com.signalripple.fitnessbike.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.bean.ActionBean;
import com.signalripple.fitnessbike.view.CircleImageView;

import cn.fireup.yuanyang.adapter.MyBaseAdapter;


public class FriendListViewAdapter extends MyBaseAdapter {

//	private List<ActionBean> list;
	private Context context;
	private LayoutInflater inflater;

	public FriendListViewAdapter(Context context)
	{
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
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(position == 0)
		{
			holder.ivNumberOfImage.setBackgroundResource(R.drawable.first);
		}
		else if(position == 1)
		{
			holder.ivNumberOfImage.setBackgroundResource(R.drawable.second);
		}
		else if(position == 2)
		{
			holder.ivNumberOfImage.setBackgroundResource(R.drawable.third);
		}
		else
		{
			holder.ivNumberOfImage.setVisibility(View.GONE);
			holder.txtNumberOfText.setVisibility(View.VISIBLE);
			holder.txtNumberOfText.setText(""+(position + 1));
		}
		
		
		return convertView;
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
	}
}
