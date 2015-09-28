package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import cn.fireup.yuanyang.swipelistview.BaseSwipeListViewListener;
import cn.fireup.yuanyang.swipelistview.SwipeListView;

import com.signalripple.fitnessbike.adapter.GeneralListViewAdapter;

/**
 * 通用ListView
 * @author xushiyong
 *
 */
public class GeneralListViewActivity extends BaseActivity implements ICommViewListener, OnClickListener {

	private PullToRefreshList pullToRefreshList = null;
	private LoadMoreListView loadMoreListView=null;
	private GeneralListViewAdapter adapter;
	
	private TextView txtReturn;
	private TextView txtTitle;
	private TextView txtRightAction;
	
	private String activityTitle;
	private String type ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general_listview);
		
		Intent intent = getIntent();
		activityTitle = intent.getStringExtra("title");
		type = intent.getStringExtra("type");
		
		initViews();
		initEvent();
		initValues();
	}


	private void initEvent() {
		// TODO Auto-generated method stub
		txtReturn.setOnClickListener(this);
		txtRightAction.setOnClickListener(this);
	}


	private void initValues() {
		// TODO Auto-generated method stub
		txtTitle.setText(activityTitle);
	}


	private void initViews() {
		// TODO Auto-generated method stub
		pullToRefreshList = (PullToRefreshList)this.findViewById(R.id.loaddataview);
		pullToRefreshList.setCommViewListener(this);
		loadMoreListView=pullToRefreshList.getLoadMoreListView();
		
		// 设置不能左右滑动
		loadMoreListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
		
		init();
		adapter = new GeneralListViewAdapter(this);
		loadMoreListView.setAdapter(adapter);
		pullToRefreshList.initData();
		
		txtReturn = (TextView)this.findViewById(R.id.include_view_btnLeft);
		txtTitle = (TextView)this.findViewById(R.id.include_view_titlebar_text);
		txtRightAction = (TextView)this.findViewById(R.id.include_view_btnRight);
		txtRightAction.setVisibility(View.GONE);
	}

	public void init(){
    	loadMoreListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      	  loadMoreListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + loadMoreListView.getCountSelected() + ")");
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
              	  loadMoreListView.unselectedChoiceStates();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        loadMoreListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
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
			index++;
			NewsListEntity newsListEntity=new NewsListEntity();
			newsListEntity.setNEWS_COMMENT_COUNT(index+"");
			newsListEntity.setNEWS_TITLE(defaultTitle+i);
			//newsListEntity.setSERVER_DOMAIN(Constants.IMAGES[i]);
			newsListEntity.setNEWS_MEMO(defaultTitle);
			arrayList.add(newsListEntity);
		}
		return arrayList;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.include_view_btnLeft:
			finish();
			break;
		case R.id.include_view_btnRight:
			
			break;
		default:
			break;
		}
	}
}
