package cn.fireup.yuanyang.refresh;


import java.util.List;

import cn.fireup.loadmore.R;
import cn.fireup.yuanyang.refresh.LoadMoreListView.IClickloadMoreBtnCallBack;
import cn.fireup.yuanyang.refresh.PullToRefreshView.OnFooterRefreshListener;
import cn.fireup.yuanyang.refresh.PullToRefreshView.OnHeaderRefreshListener;
import cn.fireup.yuanyang.refresh.RequestDataAsyncTask.ICallBackAsyncTaskLister;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


public class PullToRefreshList extends LinearLayout implements ICallBackAsyncTaskLister , OnHeaderRefreshListener , IClickloadMoreBtnCallBack , OnFooterRefreshListener
{
	
	public PullToRefreshList(
			Context context )
	{
		super( context );
	}
	
	Context mContext = null;
	LoadMorePullToreshView loadMorePullToreshView = null;
	public PullToRefreshView pullToRefreshView = null;
	
	public PullToRefreshView getPullToRefreshView()
	{
		return pullToRefreshView;
	}
	
	public LoadMoreListView loadMoreListView = null;
	
	public LoadMoreListView getLoadMoreListView()
	{
		return loadMoreListView;
	}
	
	private RequestDataAsyncTask requestDataAsyncTask = null;
	
	public RequestDataAsyncTask getRequestDataAsyncTask()
	{
		return requestDataAsyncTask;
	}
	
	public void setRequestDataAsyncTask(
			RequestDataAsyncTask requestDataAsyncTask )
	{
		this.requestDataAsyncTask = requestDataAsyncTask;
	}
	
	BaseAdapter baseAdapter = null;
	private ICommViewListener commViewListener = null;
	
	public ICommViewListener getCommViewListener()
	{
		return commViewListener;
	}
	
	public void setCommViewListener(
			ICommViewListener commViewListener )
	{
		this.commViewListener = commViewListener;
	}
	
	
	public PullToRefreshList(
			Context context ,
			AttributeSet attributeSet )
	{
		super( context , attributeSet );
		mContext = context;
		initWidget( context );
	}
	
	/**
	 * 初始化布局信息
	 * @param context
	 */
	public void initWidget(
			Context context )
	{
		LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View layout = inflater.inflate( R.layout.loadataview_layout , null );
		this.addView( layout );
		loadMorePullToreshView = (LoadMorePullToreshView)findViewById( R.id.lmpView );
		pullToRefreshView = loadMorePullToreshView.getPullToRefreshView();
		pullToRefreshView.setOnHeaderRefreshListener( this );
		pullToRefreshView.setOnFooterRefreshListener( this );
		loadMoreListView = loadMorePullToreshView.getLoadMoreListView();
		loadMoreListView.setClickloadMoreBtnCallBack( this );
		loadMoreListView.hideLoadMore();
		requestDataAsyncTask = new RequestDataAsyncTask( context , pullToRefreshView , loadMoreListView );
		requestDataAsyncTask.setCallBackAsyncTaskLister( this );
	}
	
	/**
	 * 执行数据
	 */
	public void initData()
	{
		pullToRefreshView.headerRefreshing();
	}
	
	@Override
	public void onPreExecute(
			int currentPage )
	{
	}
	
	@Override
	public List<Object> doInBackground(
			int currentPage )
	{
		return commViewListener.doInBackGround( currentPage );
	}
	
	@Override
	public void onPostExecute(
			List<Object> listDatas ,
			int currentPage )
	{
		commViewListener.callBackListData( listDatas );
	}
	
	@Override
	public void onHeaderRefresh(
			PullToRefreshView view )
	{
		commViewListener.onHeadRefresh();
		requestDataAsyncTask.executeLoadData();
	}
	
	/**
	* @类名:ICommViewListener 
	* @描述:TODO(异步请求接口返回回调) 
	* @作者:zhaohao 
	* @时间 2013-5-21 上午10:11:28
	 */
	public interface ICommViewListener
	{
		
		public List<Object> doInBackGround(
				int CurrentPage );
		
		public void callBackListData(
				List<Object> list );
		
		public void onHeadRefresh();
	}
	
	/**
	 * 设置listView分割线
	 * @param ResId
	 */
	public void setListViewDriver(
			int ResId )
	{
		loadMorePullToreshView.setListViewDriverLine( ResId );
	}
	
	@Override
	public void loadMore()
	{
		requestDataAsyncTask.executeLoadMore();
	}
	
	/**
	 * 设置没有数据时候加载没有数据的布局
	 */
	public void setNoDataShowLayout(
			boolean hasdatalayoutstatus )
	{
		requestDataAsyncTask.setShowNoDataLayout( hasdatalayoutstatus );
	}
	
	/**
	 * 设置没有数据的默认图片
	 * @param id
	 */
	public void setNodataShowNoIcon(
			int id )
	{
		requestDataAsyncTask.setNotDataImgResId( id );
	}
	
	/**
	 * 设置没有数据的提示语
	 * @param msg
	 */
	public void setNodateMsg(
			String msg )
	{
		requestDataAsyncTask.setNotDataMsg( msg );
	}
	
	/**
	 * 设置是否开启加载更多功能
	 * @param isloadmorestatus true开启加载更多功能 false禁止加载更多功能
	 *  默认开启
	 */
	public void setIsLoadMoreData(
			boolean isloadmorestatus )
	{
		requestDataAsyncTask.setLoadMoreStatus( isloadmorestatus );
		setPullTpFootViewLoadMoreDataEnbale( isloadmorestatus );
	}
	
	/**
	 * 是否开启下拉刷新功能 默认开启
	 * @param enablePullTorefresh true为开启 false 禁用下拉刷新功能
	 */
	public void setPullToreshareEnable(
			boolean enablePullTorefresh )
	{
		pullToRefreshView.setEnablePullTorefresh( enablePullTorefresh );
	}
	
	/**
	 * 是否开启上拉加载更多功能
	 * @param enableloadmore true为开启 false 禁用下拉刷新功能
	 */
	public void setPullTpFootViewLoadMoreDataEnbale(
			boolean enableloadmore )
	{
		pullToRefreshView.setEnablePullLoadMoreDataStatus( enableloadmore );
		setIsLoadMoreData( enableloadmore );
	}
	
	@Override
	public void onFooterRefresh(
			PullToRefreshView view )
	{
		requestDataAsyncTask.executeLoadMore();
	}
}
