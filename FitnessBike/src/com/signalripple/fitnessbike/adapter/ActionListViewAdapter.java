package com.signalripple.fitnessbike.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.fireup.yuanyang.adapter.MyBaseAdapter;

import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.bean.ActionBean;

public class ActionListViewAdapter extends MyBaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	
	public ActionListViewAdapter(Context context)
	{
		inflater = LayoutInflater.from(context);
		this.context = context;
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
			convertView = inflater.inflate(R.layout.action_list_item_layout, null);
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
		
	}

}
