package com.signalripple.fitnessbike.adapter;

import java.util.List;

import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.bean.ActionBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ActionListViewAdapter extends BaseAdapter {

	private List<ActionBean> list;
	private Context context;
	private LayoutInflater inflater;
	
	public ActionListViewAdapter(Context context,List<ActionBean> list)
	{
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
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
