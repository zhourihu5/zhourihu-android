package com.sinoglobal.app.util.constants;

/**
 * 
 * @author ty
 * @createdate 2012-6-27 下午8:33:04
 * @Description: 请求常量定义
 */
public class RequestConstant {
	
	/**
	 * 测试环境
	 */
	/*public static final String URL = "http://jk3.itkt.com:7070/stsf/mvc/mobile/";
//	public static final String HTTPS_URL = "https://jk3.itkt.com:8444/stsf/mvc/mobile/";
	public static final String URL_TRAIN = "http://124.207.105.28:5500/itkt_train/mvc/train/";
	public static final String URL_AUTH = "http://jk3.itkt.com:7070/stsf/mvc/mobile/";*/
	
	/**
	 * 试运行环境
	 */
	public static final String URL = "http://jk.itkt.com.cn:9393/stsf/mvc/mobile/";
//	public static final String HTTPS_URL = "https://jk.itkt.com.cn:9443/stsf/mvc/mobile/";
	public static final String URL_TRAIN = "http://124.207.105.28:5500/itkt_train/mvc/train/";
	public static final String URL_AUTH = "http://jk.itkt.com.cn:9393/stsf/mvc/mobile/"; 
//	public static final String URL_AUTH = "http://jk3.itkt.com:7070/stsf/mvc/mobile/"; 
	
	/**
	 * 生产环境
	 */
	/*public static final String URL = "http://phone.itkt.com:30088/stsf/mvc/mobile/";
//	public static final String HTTPS_URL = "https://phone.itkt.com:39443/stsf/mvc/mobile/";
	public static final String URL_TRAIN = "http://train.itkt.com:9090/itkt_train/mvc/train/";  //火车票
	public static final String URL_AUTH = "http://auth.itkt.com:8080/stsf/mvc/mobile/";*/        //获取统一配置、注册、推送接口
	
	/**
	 * @author ty
	 * @createdate 2012-9-19 上午11:38:28
	 * @Description: 获取请求的url
	 * @param methodName
	 * @return
	 */
	public static String getMethod(String methodName){
		return URL+methodName;
	}
	
	public static String getHttpsMethod(String methodName){
//		return HTTPS_URL+methodName;
		return URL+methodName;
	}
	
	/**
	 * @author ty
	 * @createdate 2013-1-8 下午1:57:44
	 * @Description: 火车URL方法
	 * @param methodName
	 * @return
	 */
	public static String getTrainMethod(String methodName){
		return URL_TRAIN+methodName;
	}
	
	/**
	 * @author ty
	 * @createdate 2013-1-17 下午3:04:26
	 * @Description: 获取统一配置、注册、推送接口
	 * @param methodName
	 * @return
	 */
	public static String getAuthMethod(String methodName){
		return URL_AUTH+methodName;
	}
	
	public class MethodName{
		public static final String GET_CONFIGURATION="getConfiguration";
		public static final String GET_STATION_CITY_VERSION="version";
		public static final String MEMBER_LOGIN="memberLogin"; //会员登录
		public static final String MEMBER_REGISTER="memberRegister"; //会员注册
		public static final String GET_VERIFICATION_CODE="getVerificationCode"; //获取验证码 
		public static final String FORGET_PASSWORD="forgetPassword"; //忘记密码
		public static final String UPDATE_PASSWORD="updatePassword"; //修改密码  
		public static final String MY_LCD_CONIN="myLCDCoin"; //我的畅达币
		public static final String GET_PERSONAL_INFORMATION="getPersonalInformation"; //获取个人信息
		public static final String EDIT_PERSONAL_INFORMATION="editPersonalInformation"; //编辑个人信息
		public static final String UPDATE_AIRPORT_CITYLIST="updateAirportCityList";
		public static final String UPDATE_AIRPORT_LIST="updateAirportList";
		public static final String STATION_LIST="stationList";
		public static final String GET_MY_ATTENTION_LIST="getMyAttentionList";//关注列表
		public static final String GET_TICKET_RESERVATION_LIST="queryReservationList";
		public static final String QUERY_FLIGHT_TICKET="queryFlightTicket";
		public static final String RECOMMEND_FLIGHT="recommendFlight";//机票推荐
		public static final String QUERY_AUTO_MATICALLY_DROPPED_LIST = "queryAutomaticallyDroppedList";	//自动降价列表
		public static final String ADD_FLIGHT_TICKET="addReservation";
		public static final String QUERY_FLIGHT_TICKET_DETAIL="queryFlightTicketDetail";
		public static final String SEARCH_RULE="searchRule";
		public static final String QUERY_AUTOMATICALLY_DROOOED_DETAIL = "queryAutomaticallyDroppedDetail";	//自动降价推荐内容
		public static final String GET_PASSENGER_LIST = "getPassengerList";	//乘机人列表
		public static final String GET_FLIGHT_DYNAMIC_BY_FLIGHT_NO = "getFlightDynamicByFlightNo";	//按航班号查询
		public static final String GET_FLIGHT_DYNAMIC_BY_STARTARRIVAL = "getFlightDynamicByStartArrival";	//按航班号查询
		public static final String SET_ATTENTION_STATE = "setAttentionState";	//添加关注
		public static final String UPDATE_PASSENGERS_NAME = "updatePassengersName";	//修改乘机人姓名
		public static final String CANCEL_ATTENTION_STATE = "cancelAttentionState";	//取消关注
		public static final String ADD_PASSENGERS = "addPassengers";//添加乘机人
		public static final String DELETE_PASSENGERS = "deletePassengers";//删除乘机人
		public static final String UPDATE_PASSENGERS = "updatePassengers";//更新乘机人
		public static final String GET_USERADDRESS = "getUserAddress";//得到邮寄地址列表
		public static final String UPDATE_USERADDRESS = "updateUserAddress";//更新邮寄地址信息
		public static final String DELETE_USERADDRESS = "deleteUserAddress";//删除邮寄地址信息
		public static final String ADD_USERADDRESS = "addUserAddress";//新增邮寄地址信息
		public static final String QUERY_WEATHERINFOVO = "queryWeatherInfo";	//机场天气
		public static final String QUERY_RESERVATIONDETAIL = "queryReservationDetail";	//机票预约详情
		public static final String DELETE_RESERVATION = "deleteReservation";	//删除机票预约
		public static final String SUBMIT_ORDER = "submitOrder";	//提交订单
		public static final String QUERY_AIRPORT_TRAFFIC = "queryAirportTraffic";	//机场交通查询
		public static final String QUERY_TICKET_NUMBER_INFORMATION= "queryTicketNumberInformation";	//获取退票界面中票的信息
		
		public static final String GET_MESSAGE_STATE = "ssl/getMessageState";	//获取报文支付
		public static final String GET_ORDERS_STATE = "ssl/getOrdersState";	    //通知支付结果
		public static final String FLIGHTS_ORDERS_LIST = "flightsOrdersList";	//获取订单列表
		public static final String CANCEL_ORDER = "cancelOrder";	//取消订单
		
		public static final String QUERY_CAR_STORE= "car/queryCarStore";	//租车门店查询 
		public static final String QUERY_CAR_MODEL= "car/queryCarModel";	//车型查询
		public static final String QUERY_CAR_RECOMMEND= "car/recommendCar";	//推荐车查询
		public static final String QUERY_CAR_SERVICE= "car/queryCarService"; //服务查询
		public static final String SUBMIT_ORDER_CAR= "car/submitOrderCar"; //查询提交前订单信息
		public static final String SUBMIT_ORDER_CAR_INFO= "car/submitOrderCarInfo"; //提交订单
		
		public static final String ORDER_CAR_LIST= "car/orderCarList"; //租车订单列表查询
		public static final String ORDER_CAR_INFORMATION= "car/orderCarInformation"; //租车订单详情 
		public static final String DELETE_ORDER_CAR= "car/deleteOrderCar"; //取消租车订单
		
		public static final String GET_TERMINAL_CITY_LIST = "getTerminalCityList";	//查询终端机城市列表
		public static final String GET_TERMINALINFO_BY_CITY = "getTerminalInfoByCity";	//查询城市的终端机列表
		public static final String QUERY_FACILITY_SERVICE = "queryFacilityService";	//设施服务 
		public static final String UPDATE_RENT_CITYLIST = "car/updateRentCityList";	//更新租车城市表
		public static final String FLIGHTS_ORDER_DETAILS = "flightsOrderDetails";	//获取订单详情
		public static final String GELAYS = "gelays";	//延误情况
		public static final String CREDIT_CARDLIST = "ehotel/creditCardList";	//常用信用卡列表
		public static final String DELETE_CREDIT_CARD= "ehotel/ssl/deleteCreditCard";	//删除常用信用卡
		public static final String ADD_CREDIT_CARD = "ehotel/ssl/addCreditCard";	//新增常用信用卡
		public static final String UPDATE_CREDIT_CARD = "ehotel/ssl/updateCreditCard";	//修改常用信用卡
		public static final String QUERY_CREDIT_CARD = "ehotel/queryCreditCard";	//查询酒店担保
		public static final String GET_BANKLIST = "getBankList";
		public static final String QUERYHOTEL = "ehotel/queryhotel";	    //酒店查询
		public static final String RECOMMEND_HOTEL = "ehotel/recommendHotel";	    //酒店推荐信息
		public static final String FILTERHOTEL = "ehotel/filterHotel";	    //酒店筛选查询
		public static final String HOTEL_INFORMATION = "ehotel/hotelInformation";	    //酒店详细
		public static final String ORDER_HOTEL_LIST = "ehotel/orderHotelList";	//酒店订单
		public static final String SUBMIT_ORDDR_HOTEL= "ehotel/ssl/submitOrderHotel";	//酒店订单提交
		public static final String UPDAT_EHOTEL_CITYLIST = "ehotel/updateHotelCityList";	 
		public static final String REFUND = "Refund";	//退票 
		public static final String CHECK_PERSONNEL_LIST = "ehotel/checkPersonnelList";	//查询常用入住人
		public static final String ADD_CHECK_PERSONNEL = "ehotel/addCheckPersonnel";	//添加常用入住人
		public static final String FEED_BACK = "feedback";	//问题反馈
		public static final String DELETE_CHECK_PERSONNEL = "ehotel/deleteCheckPersonnel";	//删除常用入住人
		public static final String QUERY_COMMENT = "ehotel/queryComment";	//查询酒店评论
		public static final String UPDATE_ORDER = "updateOrder";	//修改订单
		public static final String PAY_LCD_COIN = "ssl/payLcdCoin";	//畅达币支付(全额)
		public static final String CREDIT_CARARD_PAY = "ssl/creditCarardPay";	//信用卡支付
		public static final String ACTIVITY = "activity";	//活动的url路径
		public static final String SEARCHTRAIN = "searchTrain";	//火车票查询 
		public static final String SEARCHTRAINDETAIL = "searchTrainDetail";	//火车票查询
		
		public static final String ITKT_ANDROID_PUSH = "ItktAndroidpush";	//推送信息
	}
}
