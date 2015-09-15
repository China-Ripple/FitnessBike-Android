package com.example.loadmore;

import java.util.ArrayList;
import java.util.List;

import cn.fireup.loadmore.R;
import cn.fireup.yuanyang.adapter.NewsIndexAdapter;
import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
* @类名:ContentActivity 
* @描述:TODO(新闻内容页面，异步加载数据，下拉刷新，上拉加载更多) 
* @作者:zhaohao 
* @时间 2013-7-1 下午3:43:52
 */
@SuppressLint( "ValidFragment" )
public class ContentActivity extends Fragment implements ICommViewListener {
	View mView=null;
	PullToRefreshList loadDataView=null; 
	NewsIndexAdapter newsIndexAdapter=null;
	LoadMoreListView loadMoreListView=null;
	String defaultTitle="";
     @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
     public static ContentActivity newInstance(String  title) {
    	 ContentActivity fragment = new ContentActivity(title);
         return fragment;
     }
     public ContentActivity(String defaultTitle) {
    	 this.defaultTitle=defaultTitle;
	}
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	 mView=inflater.inflate(R.layout.content_layout, null);
    	 return mView;
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadDataView=(PullToRefreshList) mView.findViewById(R.id.loaddataview);
		loadDataView.setCommViewListener(this);
		loadMoreListView=loadDataView.getLoadMoreListView();
		loadDataView.setListViewDriver(0);
		newsIndexAdapter=new NewsIndexAdapter(getActivity());
		loadMoreListView.setAdapter(newsIndexAdapter);
		loadDataView.initData();
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
		newsIndexAdapter.setList((ArrayList)list, true);
	}

	@Override
	public void onHeadRefresh() {
		newsIndexAdapter.clear();
	}
	public List<Object> objects(int currentpage){
		ArrayList<Object> arrayList=new ArrayList<Object>();
		for (int i = 0; i <20; i++) {
			NewsListEntity newsListEntity=new NewsListEntity();
			newsListEntity.setNEWS_COMMENT_COUNT((i+1)+"");
			newsListEntity.setNEWS_TITLE(defaultTitle+i);
			//newsListEntity.setSERVER_DOMAIN(Constants.IMAGES[i]);
			newsListEntity.setNEWS_MEMO(defaultTitle);
			arrayList.add(newsListEntity);
		}
		return arrayList;
	}
}
