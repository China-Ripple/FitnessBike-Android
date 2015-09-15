package com.signalripple.fitnessbike.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.bean.SuperBiker;

public class GridViewAdapter extends BaseAdapter{

	private List<SuperBiker> list;
	private Context context;
	private LayoutInflater inflater;
	
	public GridViewAdapter(Context context,List<SuperBiker> list)
	{
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.superbike_gridview_item, null);
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
		ImageView ivHead;
		TextView txtName;
	}
}
