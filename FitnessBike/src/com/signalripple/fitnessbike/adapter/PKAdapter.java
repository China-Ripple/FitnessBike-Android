package com.signalripple.fitnessbike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.fireup.yuanyang.adapter.MyBaseAdapter;

import com.signalripple.fitnessbike.R;

public class PKAdapter extends MyBaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	
	public PKAdapter(Context context)
	{
		this.context = context;
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


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.pkinfo_list_item, null);
//			holder.circleImageViewForHead = (CircleImageView)convertView.findViewById(R.id.ivHead);
//			holder.ivNumberOfImage = (ImageView)convertView.findViewById(R.id.ivNumberOfImage);
//			holder.txtNumberOfText = (TextView)convertView.findViewById(R.id.ivNumberOfText);
//			holder.ivZan = (ImageView)convertView.findViewById(R.id.ivZan);
//			holder.txtDistance = (TextView)convertView.findViewById(R.id.txtDistance);
//			holder.txtName = (TextView)convertView.findViewById(R.id.txtName);
//			holder.txtZanNumber = (TextView)convertView.findViewById(R.id.txtZanNumber);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	private static final class  ViewHolder 
	{
//		private ImageView ivNumberOfImage;
//		private ImageView ivZan;
//		private CircleImageView circleImageViewForHead;
//		private TextView  txtNumberOfText;
//		private TextView txtName;
//		private TextView txtDistance;
//		private TextView txtZanNumber;
	}

}
