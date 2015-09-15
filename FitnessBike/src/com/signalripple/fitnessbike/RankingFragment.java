package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import com.signalripple.fitnessbike.adapter.FriendListViewAdapter;
import com.signalripple.fitnessbike.adapter.TestAdapter;

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
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.fireup.yuanyang.adapter.NewsIndexAdapter;
import cn.fireup.yuanyang.adapter.NewsListEntity;
import cn.fireup.yuanyang.refresh.LoadMoreListView;
import cn.fireup.yuanyang.refresh.PullToRefreshList;
import cn.fireup.yuanyang.refresh.PullToRefreshList.ICommViewListener;
import cn.fireup.yuanyang.swipelistview.BaseSwipeListViewListener;

public class RankingFragment extends Fragment implements OnCheckedChangeListener, ICommViewListener, OnClickListener{

	private ViewPager viewPager;
	private FrameLayout btnMessage;
	private List<View> viewList = new ArrayList<View>();
	private RadioGroup radioGroup;
	private RadioButton rbFriendList;
	private RadioButton rbAllPerson;
//	NewsIndexAdapter newsIndexAdapter;
	private FriendListViewAdapter adapter;
	private PullToRefreshList loadDataView=null;
	private LoadMoreListView loadMoreListView=null;
	private ImageView btnAddFriend;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.fragment_3, null);		

		viewList.add(inflater.inflate(R.layout.friend_list, null));
		viewList.add(inflater.inflate(R.layout.allperson_list, null));
		
		SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.string_today_friend_list));  
		ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.sea_blue));  
		TextView textView = (TextView) viewList.get(0).findViewById(R.id.txtTitle);
		builder.setSpan(blueSpan, 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		textView.setText(builder);
		
   	 	loadDataView=(PullToRefreshList)viewList.get(0).findViewById(R.id.loaddataview);
		loadDataView.setCommViewListener(this);
		loadMoreListView=loadDataView.getLoadMoreListView();
		init();
//		loadDataView.setListViewDriver(0);
		adapter = new FriendListViewAdapter(getActivity());
//		adapter = new TestAdapter(getActivity());
//		adapter=new NewsIndexAdapter(getActivity());
		loadMoreListView.setAdapter(adapter);
		loadDataView.initData();

		// 初始化本视图内的控件
		initViews(view);
		// 初始化设置控件的事件
		initEvent();
		
		return view;
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
			rbFriendList.setBackgroundResource(R.drawable.bg_buttons);
			rbFriendList.setTextColor(getResources().getColor(R.color.store_blue));
			
			rbAllPerson.setBackgroundResource(android.R.color.transparent);
			rbAllPerson.setTextColor(Color.WHITE);
			
			viewPager.setCurrentItem(0, true);
		}
		else
		{
			rbAllPerson.setBackgroundResource(R.drawable.bg_buttons);
			rbAllPerson.setTextColor(getResources().getColor(R.color.store_blue));
			
			rbFriendList.setBackgroundResource(android.R.color.transparent);
			rbFriendList.setTextColor(Color.WHITE);
			
			viewPager.setCurrentItem(1, true);
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
}