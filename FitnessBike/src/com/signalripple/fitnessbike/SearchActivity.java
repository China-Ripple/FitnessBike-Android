package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import com.signalripple.fitnessbike.adapter.GeneralListViewAdapter;
import com.signalripple.fitnessbike.adapter.GridViewAdapter;
import com.signalripple.fitnessbike.adapter.SearchResultAdapter;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.bean.SuperBiker;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SearchActivity extends BaseActivity implements OnClickListener {

	private GridView gridview;
	private RelativeLayout btnNearByPeople;
	private RelativeLayout btnSuperBiker;
	private EditText searchBar;
	private Button btnAction;
	private TextView txtReturn;
	private TextView txtTitle;
	private TextView txtSearch;
	private ImageButton ibSearch;
	private RelativeLayout layoutTitleBar;
	private LinearLayout layoutSearchBar;
	private boolean isShowSearchBarView = false;
	private ListView listview;
	private MRequset mRequset;
	private SearchResultAdapter adapter;
	private List<SuperBiker> listSearch = new ArrayList<SuperBiker>();
	/**非搜索模式下的界面布局*/
	private LinearLayout normal_state_views_layout;
	private List<SuperBiker> list = new ArrayList<SuperBiker>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initViews();
		initEvent();
		initValues();
	}

	private void initValues() {
		// TODO Auto-generated method stub
		mRequset = MRequset.getInstance(this);
		adapter = new SearchResultAdapter(this,listSearch);
		listview.setAdapter(adapter);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btnNearByPeople.setOnClickListener(this);
		btnSuperBiker.setOnClickListener(this);
		searchBar.setOnClickListener(this);
		ibSearch.setOnClickListener(this);
		searchBar.addTextChangedListener(watcher);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		
		normal_state_views_layout = (LinearLayout)this.findViewById(R.id.normal_state_views_layout);
		
		listview = (ListView)this.findViewById(R.id.listview);
		ibSearch = (ImageButton)this.findViewById(R.id.ibSearchIcon);
		
		gridview = (GridView)this.findViewById(R.id.gridview);
		list.add(new SuperBiker());
		list.add(new SuperBiker());
		list.add(new SuperBiker());
		list.add(new SuperBiker());
		
		layoutSearchBar = (LinearLayout)this.findViewById(R.id.layout_search_bar);
		layoutTitleBar = (RelativeLayout)this.findViewById(R.id.layout_titlebar);
				
		txtReturn = (TextView)this.findViewById(R.id.include_view_btnLeft);
		txtTitle = (TextView)this.findViewById(R.id.include_view_titlebar_text);
		txtSearch = (TextView)this.findViewById(R.id.include_view_btnRight);
		
		txtSearch.setText(getString(R.string.search));
		txtTitle.setText(getString(R.string.others));
		
		txtReturn.setOnClickListener(this);
		txtSearch.setOnClickListener(this);
		
		btnAction = (Button)this.findViewById(R.id.btnAction);
		btnAction.setOnClickListener(this);
		
		GridViewAdapter adapter = new GridViewAdapter(this, list);
		gridview.setAdapter(adapter);
		
		searchBar    = (EditText)this.findViewById(R.id.ivSearchBar);
		btnNearByPeople = (RelativeLayout)this.findViewById(R.id.ibDetailsNearByPeople);
		btnSuperBiker = (RelativeLayout)this.findViewById(R.id.ibDetailsSuperBiker);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibDetailsNearByPeople:
			 Intent intent = new Intent(this, GeneralListViewActivity.class);
			 intent.putExtra("title", "附近的人");
			 intent.putExtra("type", "1");
			 startActivity(intent);
			break;
		case R.id.ibDetailsSuperBiker:
			 Intent intent2 = new Intent(this, GeneralListViewActivity.class);
			 intent2.putExtra("title", "骑行达人");
			 intent2.putExtra("type", "2");
			 startActivity(intent2);
			
			break;
		case R.id.ivSearchBar:
//			 Intent intent3 = new Intent(this, GeneralListViewActivity.class);
//			 startActivity(intent3);
			
			break;
		case R.id.include_view_btnLeft:
			finish();
			break;
			//显示搜索模式
		case R.id.include_view_btnRight:
			layoutTitleBar.setVisibility(View.GONE);
			layoutSearchBar.setVisibility(View.VISIBLE);
			listview.setVisibility(View.VISIBLE);
			normal_state_views_layout.setVisibility(View.GONE);
			break;
			// 显示普通模式
		case R.id.btnAction:
			layoutTitleBar.setVisibility(View.VISIBLE);
			layoutSearchBar.setVisibility(View.GONE);
			listview.setVisibility(View.GONE);
			normal_state_views_layout.setVisibility(View.VISIBLE);
			break;
		case R.id.ibSearchIcon:
			// todo 搜索的相关事宜
			searchAction();
			break;
		default:
			break;
		}
	}
	
	private void searchAction() {
		// TODO 执行搜索的相关操作，根据未来的接口来获取数据，并籍由ListView展示
//		mRequset.requestForJsonObject(url, jsonRequest, listener, errorListener)
		
		// 模拟数据
		listSearch.add(new SuperBiker());
		listSearch.add(new SuperBiker());
		listSearch.add(new SuperBiker());

		adapter.notifyDataSetChanged();
	}

	private TextWatcher watcher = new TextWatcher() {
		   
	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	        // TODO Auto-generated method stub
	       if(s.length() > 0)
	    	   ibSearch.setVisibility(View.VISIBLE);
	       else
	    	   ibSearch.setVisibility(View.GONE);
	    }
	   
	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
	            int after) {
	        // TODO Auto-generated method stub
	       
	    }
	   
	    @Override
	    public void afterTextChanged(Editable s) {
	        // TODO Auto-generated method stub
	       
	    }
	};
}
