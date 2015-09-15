package com.example.loadmore;


import java.util.ArrayList;
import java.util.List;


import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import cn.fireup.loadmore.R;
import cn.fireup.yuanyang.adapter.NewsIndexAdapter;
import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import cn.fireup.yuanyang.swipelistview.BaseSwipeListViewListener;


public class MainActivity extends Activity  implements ICommViewListener 
{
	
	

	
	View mView=null;
	PullToRefreshList loadDataView=null; 
	NewsIndexAdapter newsIndexAdapter=null;
	LoadMoreListView loadMoreListView=null;
	String defaultTitle="";
     @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	 setContentView(  R.layout.content_layout);
    	 loadDataView=(PullToRefreshList)this.findViewById(R.id.loaddataview);
 		loadDataView.setCommViewListener(this);
 		loadMoreListView=loadDataView.getLoadMoreListView();
 		init();
 		loadDataView.setListViewDriver(0);
 		newsIndexAdapter=new NewsIndexAdapter(this);
 		
 		loadMoreListView.setAdapter(newsIndexAdapter);
 		loadDataView.initData();
    }
     public static ContentActivity newInstance(String  title) {
    	 ContentActivity fragment = new ContentActivity(title);
         return fragment;
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
                	newsIndexAdapter.getAlObjects().remove(position);
                }
                newsIndexAdapter.notifyDataSetChanged();
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
		newsIndexAdapter.setList((ArrayList)list, true);
	}

	@Override
	public void onHeadRefresh() {
		newsIndexAdapter.clear();
	}
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
}
