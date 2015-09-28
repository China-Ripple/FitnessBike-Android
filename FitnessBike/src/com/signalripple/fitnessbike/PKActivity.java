package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import cn.fireup.yuanyang.swipelistview.BaseSwipeListViewListener;
import cn.fireup.yuanyang.swipelistview.SwipeListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.signalripple.fitnessbike.adapter.PKAdapter;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.bean.PKBean;

public class PKActivity extends BaseActivity implements OnClickListener, ICommViewListener {
	
	private PullToRefreshList pullToRefreshList;
	private TextView btnReturn;
	private LoadMoreListView loadMoreListView=null;
	private PKAdapter adapter;
	private MRequset mRequset;
	private List<Object> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pk);
		
		mRequset = MRequset.getInstance(this);
		getViews();
		setEvent();
		setDatas();
		
		requestData();
	}

	
	private List<Object> requestData() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiCompmsg, map), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				log("结果="+response.toString());
				
				try {
					if("success".equals(response.getString("respone")))
					{
						String data = response.getString("message");
						Gson gson = new Gson();
//											
						list = gson.fromJson(data, new TypeToken<List<PKBean>>() {}.getType());
						for (int i = 0; i < list.size(); i++) {
							adapter.addItem(list.get(i));
						}
						adapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				log("PKActivity-->获取数据时错误已转用模拟数据带入:"+error.toString());
				list = objects(1);
			}
		});
		return list;
	}  

	private void setDatas() {
		// TODO Auto-generated method stub
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
		 
		// 设置模式，可指定左右滑动，上下滑等
		loadMoreListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
		//设置左右滑的动作 ，默认为左右滑展示backView，此处设置hi了左右滑不作为
		loadMoreListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_NONE);
//		loadMoreListView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_CHOICE);//.SWIPE_ACTION_NONE);
		
		init();
		btnReturn = (TextView)this.findViewById(R.id.btnReturn);
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
		for (int i = 0; i <10; i++) {
			PKBean bean = new PKBean();
			bean.setName("哈哈哈");
			bean.setType(1);
			arrayList.add(bean);
		}
		return arrayList;
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
                	Log.i("XU", "点击了动作按钮xxxxx");
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
            	Log.i("XU", "----opend");
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            	Log.i("XU", "----onClosed");
            }

            @Override
            public void onListChanged() {
            	Log.i("XU", "----onListChanged");
            }

            @Override
            public void onMove(int position, float x) {
            	Log.i("XU", "----onMove");
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
                Log.i("XU", String.format("onClickBackView %d", position));
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
}
