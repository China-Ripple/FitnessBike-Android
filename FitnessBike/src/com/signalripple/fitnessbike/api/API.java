package com.signalripple.fitnessbike.api;

public interface API {

	/** 耐力之王*/
	int nlzw = 1;
	
	/** 竞速冠军*/
	int jsgj = 2;
	
	/** 接受PK */
	int ACCEPT = 1;
	
	/** 拒绝 PK*/
	int REFUSE = 0;
	
	/** HOST 主机**/
	String Host = "http://wx.rongtai-china.com/fitnessbike";
	
	// 微信
	String AppID = "wx9972e13ac66616bf";
	String AppSecret = "5cc0dace9fb82c488dd2006dc442f6d7";
	
	// QQ
	String QQAPPID = "1104799437";
	String QQAPPKEY = "zQKefzrKZ3BvDGeC";
	String SCOPE = "all";///"get_user_info,get_simple_userinfo,get_user_profile,get_app_friends";
	
	/** 蓝牙适配 UUID	**/
	String BLUE_TOOTH_API_UUID = "11223344-5566-7788-99AA-BBCCDDEEFF00";
	
	/** 登陆API	**/  
	String apiLogin = "/signin";
	
	/** 注册 API  **/
	String apiRegister = "/signup";
	
	/** 好友排行榜   **/
	String apiFriendRanking = "/friendsranking";

	/** 总排行榜 */
	String apiAllRanking = "/allranking";
	
	/** 历史记录 */
	String  apiExercise = "/exercise";
	
	/** 一周里程记录**/
	String apiWeeklymile = "/weeklymile";
	
	/** PK的站内消息 */
	String apiCompmsg = "/compmsg";
	
	/** 接受或拒绝 PK请求*/
	String apiMsgresponse = "/msgresponse";
	
	/** 发起PK请求*/
	String apiCompitition = "/compitition";
}
