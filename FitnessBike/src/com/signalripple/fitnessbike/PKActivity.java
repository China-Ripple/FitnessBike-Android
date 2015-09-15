package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import com.signalripple.fitnessbike.adapter.FriendListViewAdapter;
import com.signalripple.fitnessbike.adapter.PKAdapter;
import com.signalripple.fitnessbike.bean.PKBean;

import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class PKActivity extends Activity implements OnClickListener, ICommViewListener {
	
	private PullToRefreshList pullToRefreshList;
	private ImageButton btnReturn;
	private LoadMoreListView loadMoreListView=null;
	private PKAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pk);
		
		getViews();
		setEvent();
		setDatas();
	}

	private void setDatas() {
		// TODO Auto-generated method stub
//		pullToRefreshList
		adapter = new PKAdapter(this);
		loadMoreListView.setAdapter(adapter);
		pullToRefreshList.initData();
	}

	private void setEvent() {
		// TODO Auto-generated method stub
		btnReturn.setOnClickListener(this);
	}

	private void getViews() {
		// TODO Auto-generated method stub
		pullToRefreshList = (PullToRefreshList)this.findViewById(R.id.loaddataview);
		pullToRefreshList.setCommViewListener(this);
		loadMoreListView=pullToRefreshList.getLoadMoreListView();
		btnReturn = (ImageButton)this.findViewById(R.id.btnReturn);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnReturn:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public List<Object> doInBackGround(int CurrentPage) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return objects(CurrentPage);
	}

	@Override
	public void callBackListData(List<Object> list) {
		adapter.setList((ArrayList)list, true);
	}

	@Override
	public void onHeadRefresh() {
		adapter.clear();
	}
	
	String defaultTitle="";
	static int index=0;
	public List<Object> objects(int currentpage){
		ArrayList<Object> arrayList=new ArrayList<Object>();
		for (int i = 0; i <60; i++) {
			PKBean bean = new PKBean();
			bean.setName("哈哈哈");
			bean.setType(1);
			arrayList.add(bean);
		}
		return arrayList;
	}
}
