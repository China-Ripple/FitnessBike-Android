package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.signalripple.fitnessbike.adapter.FriendListViewAdapter;
import com.signalripple.fitnessbike.adapter.TestAdapter;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.bean.FriendBean;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.fireup.yuanyang.adapter.NewsIndexAdapter;
import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import cn.fireup.yuanyang.swipelistview.BaseSwipeListViewListener;
import cn.fireup.yuanyang.swipelistview.SwipeListView;

public class RankingFragment extends Fragment implements OnCheckedChangeListener, ICommViewListener, OnClickListener{

	private ViewPager viewPager;
	private FrameLayout btnMessage;
	private List<View> viewList = new ArrayList<View>();
	private RadioGroup radioGroup;
	private RadioButton rbFriendList;
	private RadioButton rbAllPerson;
	private FriendListViewAdapter adapter;
	private FriendListViewAdapter adapterForAll;
	private PullToRefreshList loadDataViewForFriend=null;
	private LoadMoreListView loadMoreListViewForFriend=null;
	
	private PullToRefreshList loadDataViewForAll=null;
	private LoadMoreListView loadMoreListViewForAll=null;
	private ImageView btnAddFriend;
	private MRequset mRequset;
	private RelativeLayout titlebar;
	int page = 0;
	
	List<Object> listForFriend = new ArrayList<Object>();
	List<Object> listForAll = new ArrayList<Object>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.fragment_3, null);		

		viewList.add(inflater.inflate(R.layout.friend_list, null));
		viewList.add(inflater.inflate(R.layout.allperson_list, null));
		titlebar = (RelativeLayout)view.findViewById(R.id.titlebar_layout);
		
		// 初始化ListView还有其他
		initOthers();

		// 初始化本视图内的控件
		initViews(view);
		
		// 初始化设置控件的事件
		initEvent();
		
//		requestDatasForFriend();
//		requestDatasForAll();
		
		return view;
	}	
	

    private  List<Object> requestDatasForFriend() {
		// TODO Auto-generated method stub
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("maxId", ""+(9 * (page-1))); // 9 x 0   9 x 1
    	map.put("num", "10");  // 9 x 1   9 x 2
    	Log.i("XU", "好友榜=="+URLFactory.getURL(API.apiFriendRanking, map));
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiFriendRanking, map),null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = response.getString("response");
					if("success".equals(result))
					{
						Log.i("XU","结果解饿---->");
						String people = response.getString("people");
						Gson gson = new Gson();
						listForFriend = gson.fromJson(people, new TypeToken<List<FriendBean>>() {}.getType());
						
						for (int i = 0; i < listForFriend.size(); i++) {
							adapter.addItem(listForFriend.get(i));
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
				Toast.makeText(getActivity(), "出错的结果="+error.toString(), Toast.LENGTH_LONG).show();
				Log.i("XU", "cuowu 请求结果--->"+error.toString());	
			}
		});
			return listForFriend;
	}

    private  List<Object> requestDatasForAll() {
		// TODO Auto-generated method stub
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("maxId", "0");
    	map.put("num", "9");
    	Log.i("XU", "总榜单==="+URLFactory.getURL(API.apiFriendRanking, map));
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiFriendRanking, map),null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = response.getString("response");
					if("success".equals(result))
					{
						String people = response.getString("people");
						Gson gson = new Gson();
						listForAll = gson.fromJson(people, new TypeToken<List<FriendBean>>() {}.getType());
						for (int i = 0; i < listForAll.size(); i++) {
							adapterForAll.addItem(listForAll.get(i));
						}
						adapterForAll.notifyDataSetChanged();
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
				Toast.makeText(getActivity(), "出错的结果="+error.toString(), Toast.LENGTH_LONG).show();
				Log.i("XU", "cuowu 请求结果--->"+error.toString());	
			}
		});
		
		return listForAll;
	}
    
	private void initOthers() {
		// TODO Auto-generated method stub
		
		// 生产请求队列对象
		mRequset = MRequset.getInstance(getActivity());
		
		// 为String 设置不同分段颜色
//		SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.string_today_friend_list));  
//		ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.sea_blue));  
//		TextView textView = (TextView) viewList.get(0).findViewById(R.id.txtTitle);
//		builder.setSpan(blueSpan, 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
//		textView.setText(builder);
		
		// 好友排行榜相关
		loadDataViewForFriend=(PullToRefreshList)viewList.get(0).findViewById(R.id.loaddataview);
		loadDataViewForFriend.setCommViewListener(this);
		loadMoreListViewForFriend=loadDataViewForFriend.getLoadMoreListView();
		loadMoreListViewForFriend.setDivider(null);
//		loadMoreListViewForFriend.setOnScrollListener(new ScrollListener());
		
		// 设置只能往左滑
		loadMoreListViewForFriend.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		
		init();
		adapter = new FriendListViewAdapter(getActivity());
		loadMoreListViewForFriend.setAdapter(adapter);
		loadDataViewForFriend.initData();
		
		//总排行榜
		loadDataViewForAll = (PullToRefreshList)viewList.get(1).findViewById(R.id.loaddataviewforall);
		loadDataViewForAll.setCommViewListener(new CommViewListener());
		loadMoreListViewForAll=loadDataViewForAll.getLoadMoreListView();
		loadMoreListViewForAll.setDivider(null);
		
		// 设置只能往左滑
		loadMoreListViewForAll.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		
		init();
		adapterForAll = new FriendListViewAdapter(getActivity());
		loadMoreListViewForAll.setAdapter(adapterForAll);
		loadDataViewForAll.initData();
	}
	
	class ScrollListener implements OnScrollListener
	{
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			 switch(scrollState){  
		        case OnScrollListener.SCROLL_STATE_IDLE://空闲状态  
		        	Log.i("XU", "空闲停止滑动状态");
		        	titlebar.setAlpha(1);             
		        	break;  
		        case OnScrollListener.SCROLL_STATE_FLING://滚动状态  
		        	Log.i("XU", "滑动状态");  
		        	titlebar.setAlpha(0.1f);
		            break;  
		        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动  
		        	Log.i("XU", "触摸滑动状态");
		            break;  
		        }  
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			Log.i("XU", "一直滑动状态");
		}
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btnMessage.setOnClickListener(this);
		btnAddFriend.setOnClickListener(this);
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		viewPager    = (ViewPager)view.findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyAdapter());
		
		radioGroup   = (RadioGroup)view.findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		rbFriendList = (RadioButton)view.findViewById(R.id.rbFriend);
		rbAllPerson  = (RadioButton)view.findViewById(R.id.rbAllPerson);
		
		btnMessage   = (FrameLayout)view.findViewById(R.id.btnMessage);
		btnAddFriend = (ImageView)view.findViewById(R.id.btnAddFriend); 
		
		rbFriendList.setChecked(true);
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
	
	
	class MyAdapter extends PagerAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));

		}

		@Override
		public int getItemPosition(Object object) {

			return super.getItemPosition(object);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));
			return viewList.get(position);
		}
		
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if(checkedId == R.id.rbFriend)
		{
//			rbFriendList.setBackgroundResource(R.drawable.bg_buttons);
			rbFriendList.setBackgroundColor(getResources().getColor(R.color.sea_blue));
			rbFriendList.setTextColor(getResources().getColor(R.color.white));
			
//			rbAllPerson.setBackgroundResource(android.R.color.transparent);
			rbAllPerson.setBackgroundColor(getResources().getColor(R.color.white));
			rbAllPerson.setTextColor(getResources().getColor(R.color.sea_blue));
			
			viewPager.setCurrentItem(0, true);
		}
		else
		{
//			rbAllPerson.setBackgroundResource(R.drawable.bg_buttons);
			rbAllPerson.setBackgroundColor(getResources().getColor(R.color.sea_blue));
			rbAllPerson.setTextColor(getResources().getColor(R.color.white));
			
//			rbFriendList.setBackgroundResource(android.R.color.transparent);
			rbFriendList.setBackgroundColor(getResources().getColor(R.color.white));
			rbFriendList.setTextColor(getResources().getColor(R.color.sea_blue));
			
			viewPager.setCurrentItem(1, true);
		}
	}


	
	@Override
	public List<Object> doInBackGround(int CurrentPage) {
		Log.i("XU", "功能=>doInBackGround");
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		page ++;
		return requestDatasForFriend();
	}

	@Override
	public void callBackListData(List<Object> list) {
		Log.i("XU", "功能=>callBackListData");
//		ArrayList<Object> arrayList = adapter.getAlObjects();
//		for (int i = 0; i < list.size(); i++) {
//			arrayList.add(list.get(i));
//		}
//		adapter.notifyDataSetChanged();
		//adapter.setList((ArrayList)list, true);
	}

	@Override
	public void onHeadRefresh() {
		Log.i("XU", "功能=>onHeadRefresh");
//		adapter.clear();
		
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
		case R.id.btnMessage:
			Intent intent = new Intent(getActivity(), PKActivity.class);
			startActivity(intent);
			break;
		case R.id.btnAddFriend:
			Intent intentAddFriend = new Intent(getActivity(), SearchActivity.class);
			startActivity(intentAddFriend);
			break;

		default:
			break;
		}
	}
	
	class CommViewListener implements ICommViewListener
	{

		@Override
		public List<Object> doInBackGround(int CurrentPage) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return requestDatasForAll();
		}

		@Override
		public void callBackListData(List<Object> list) {
			// TODO Auto-generated method stub
//			adapterForAll.setList((ArrayList)list, true);
		}

		@Override
		public void onHeadRefresh() {
			// TODO Auto-generated method stub
			adapterForAll.clear();
		}
		
		
		
	}
}