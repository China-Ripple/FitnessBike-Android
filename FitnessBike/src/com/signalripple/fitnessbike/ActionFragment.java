package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import cn.fireup.yuanyang.swipelistview.BaseSwipeListViewListener;
import cn.fireup.yuanyang.swipelistview.SwipeListView;

import com.signalripple.fitnessbike.adapter.ActionListViewAdapter;
import com.signalripple.fitnessbike.adapter.FriendListViewAdapter;
import com.signalripple.fitnessbike.bean.ActionBean;

public class ActionFragment extends Fragment implements ICommViewListener{

	private ActionListViewAdapter adapter;
	private PullToRefreshList loadDataViewForFriend=null;
	private LoadMoreListView loadMoreListViewForFriend=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.fragment_4, null);
		
		initListView(view);
		
		return view;
	}

	private void initListView(View view) {
		// TODO Auto-generated method stub
		// 好友排行榜相关
		loadDataViewForFriend=(PullToRefreshList)view.findViewById(R.id.listview);
		loadDataViewForFriend.setCommViewListener(this);
		loadMoreListViewForFriend=loadDataViewForFriend.getLoadMoreListView();
		
		// 设置只能往左滑
		loadMoreListViewForFriend.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
		
		init();
		adapter = new ActionListViewAdapter(getActivity());
		loadMoreListViewForFriend.setAdapter(adapter);
		loadDataViewForFriend.initData();
	}
	
	public void init(){
    	loadMoreListViewForFriend.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      	  loadMoreListViewForFriend.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + loadMoreListViewForFriend.getCountSelected() + ")");
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.menu_delete:
//                      	  loadMoreListView.dismissSelected();
//                            mode.finish();
//                            return true;
//                        default:
//                            return false;
//                    }
                	return false;
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                    MenuInflater inflater = mode.getMenuInflater();
//                    inflater.inflate(R.menu.menu_choice_items, menu);
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
              	  loadMoreListViewForFriend.unselectedChoiceStates();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        loadMoreListViewForFriend.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                	adapter.getAlObjects().remove(position);
                }
                adapter.notifyDataSetChanged();
            }

        });
    }

	@Override
	public List<Object> doInBackGround(int CurrentPage) {
		Log.i("XU", "功能=>doInBackGround");
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		page ++;
		return objects(CurrentPage);
	}

	@Override
	public void callBackListData(List<Object> list) {
		Log.i("XU", "功能=>callBackListData");
//		ArrayList<Object> arrayList = adapter.getAlObjects();
//		for (int i = 0; i < list.size(); i++) {
//			arrayList.add(list.get(i));
//		}
//		adapter.notifyDataSetChanged();
		adapter.setList((ArrayList)list, true);
	}

	@Override
	public void onHeadRefresh() {
		Log.i("XU", "功能=>onHeadRefresh");
		adapter.clear();
	}
	
	String defaultTitle="";
	static int index=0;
	public List<Object> objects(int currentpage){
		ArrayList<Object> arrayList=new ArrayList<Object>();
		for (int i = 0; i <60; i++) {
			index++;
			NewsListEntity newsListEntity=new NewsListEntity();
			newsListEntity.setNEWS_COMMENT_COUNT(index+"");
			newsListEntity.setNEWS_TITLE(defaultTitle+i);
			newsListEntity.setNEWS_MEMO(defaultTitle);
			arrayList.add(newsListEntity);
		}
		return arrayList;
	}
}