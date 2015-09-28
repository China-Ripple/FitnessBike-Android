package cn.fireup.yuanyang.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.fireup.yuanyang.refresh.PullToRefreshView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

public class MyBaseAdapter extends BaseAdapter {
   protected PullToRefreshView pullToRefreshView=null;
   protected ArrayList<Object> alObjects = new ArrayList<Object>();
   protected  AbsListView absListView;
   protected  Context mContext;
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	/**add by xushiyong */
	public void addItem(Object item)
	{
		alObjects.add(item);
	}
	
	/**add by xushiyong 2015*/
	public void deleteItem(int position)
	{
		alObjects.remove(position);
		Log.i("XU", "删除:size="+alObjects.size());
		notifyDataSetChanged();
	}
	
	/**
	 * 设置集合
	 * @param alObjects
	 * @param boo
	 */
	public void setList(ArrayList<Object> alObjects,boolean boo)
	{
		if(null!=alObjects&&alObjects.size()>0){
			//this.alObjects.addAll(alObjects);
			this.alObjects=alObjects;
			if(boo)
			{
				notifyDataSetChanged();
			}
		}
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return null;
	}
	public PullToRefreshView getPullToRefreshView() {
		return pullToRefreshView;
	}
	public void setPullToRefreshView(PullToRefreshView pullToRefreshView) {
		this.pullToRefreshView = pullToRefreshView;
	}
	public ArrayList<Object> getAlObjects() {
		return alObjects;
	}	

	public void setAlObjects(ArrayList<Object> alObjects) {
		this.alObjects = alObjects;
	}
	public AbsListView getAbsListView() {
	return absListView;
}

	public void setAbsListView(AbsListView absListView) {
		this.absListView = absListView;
	}
}
