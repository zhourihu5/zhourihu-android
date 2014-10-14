package com.sinoglobal.app.util.constants;


/**
 * 
 * @author ty
 * @createdate 2012-6-5 下午3:59:11
 * @Description: 常量
 */
public class Constants {
	public static final float TARGET_HEAP_UTILIZATION = 0.75f;
	public static final boolean LOGVV = true;                    //拼接查询的Sql语句log
	
	public static final String UTF_8 = "UTF-8";
	public static final String DB_DIR = "data/data/cn.itkt.travelsky/databases/";//DB
    public static final String DATE_SUB="-";
    public static final String BLANK=" ";//空格 
    public static final String BLANK_ES="";//空
	public static final String MONTH="月";
	public static final String SHANG_HAI="浦东(上海)";
//	public static final String SHANG_HAI="阿勒泰";
	public static final String SHANG_HAI_CODE="PVG";
//	public static final String SHANG_HAI_CODE="AAT";
	public static final String BEI_JING="北京";
	public static final String BEI_JING_SOUNTH="北京南站";
	public static final String SHANGHAI_HONGQIAO="上海虹桥站";
	public static final String BEI_JING_CODE="PEK";  //北京三字码
	public static final String DEFAULT_AIRPORT_CODE="PEK"; //机场代码(默认首都国际机场)
	public static final String DEFAULT_CAR_CITY="北京"; //默认租车城市
	public static final String HOT="热"; 
	public static final String IS_UMENG="isUmeng";      //友盟统计控制开关，true允许使用，false则反
	public static final String UUID="uuid";      //36位UUID
	
	public static final int CACHE_DIR_SYSTEM=20;                 //缓存在SYSTEM;卡的文件
	public static final int CACHE_DIR_SD=10;                     //缓存在SD卡的文件
	public static final int FLIGHT_DEPARTURE=1;                  //起飞日期
	public static final int FLIGHT_ARRIVAL=2;                  	 //返程日期
	public static final int NOFLG = 1;							 //表示是首页航班号界面
	public static final int PLACEFLG = 2;//表示是首页起降低界面
	public static final int ATTENTION = 3;//表示是首页我的关注界面
	public static final int ONE_WAY_TYPE=1;//"单程 "; 
	public static final int BACK_AND_FORTH_TYPE_START=2;//往返-----------去 ; 
	public static final int BACK_AND_FORTH_TYPE_RETURN=4;//往返 -----------返; 
	public static final int CONNECTING_TYPE_START=3;//联程 -----------行程一
	public static final int CONNECTING_TYPE_RETURN=5;//联程  -----------行程二
	public static final int TITCTK_STATE_ONE=0; 
	public static final int TICKET_STATE_TWO=6; 
	public static final int MULTIPLE=2;//儿童是成人的倍数
	public static final int ADMINISTRATIVEAREA=0;//行政区
	public static final int TRAFFIC=1;//交通枢纽
	public static final int BUSINESSAREA=2;//热门商圈
	public static final int METOR=3;//地铁
	public static final int HOTELPRICE=4;//房价
	public static final int STARLEVEL=5;//星级
	public static final int BRAND=6;//品牌
	public static final int AIRLINE7=7;//品牌
	public static final int AIRLINE8=8;//品牌
	public static final int LENGTH_ZNAME=11;//输入位数

	public static final String PLACESEARCH = "0";//按起降低查询
	public static final String FLIGHTSEARCH = "1";//航班查询
	public static final String TRAINSEARCH = "2";//火车票站站查询
    public static final int CHANGE_TYPE_INCOME = 0; //收入
    public static final int CHANGE_TYPE_EXPENSE = 1; //支出
	public static final String PREFERENCE_AUTO_LOGIN = "auto_login";
	public static final String PREFERENCE_LOGIN_PHONE = "login_phone"; //记录登录的手机号
    public static final String AT = "&";
    public static final String F = "F";  //F舱位
    
    public static final String TICKET_NUMBER=">9";  //显示充足
    public static final String ADEQUATE="充足";  //显示充足
    public static final String ZHANG="张"; 
    public static final String PLANETYPE="机型："; 
    public static final String LCDFEE="返 "; 
    public static final String TICKET_INFO="机票信息 "; 
    public static final String ONE_WAY="单程 "; 
    public static final String BACK_AND_FORTH="往返 "; 
    public static final String CONNECTING="联程 "; 
    public static final String BACK_AND_FORTH_GO="去程 "; 
    public static final String BACK_AND_FORTH_RETURN="返程 "; 
    public static final String CONNECTING_ONE="行程一"; 
    public static final String CONNECTING_TWO="行程二 ";
	public static final String TICKET_EXECUTORY_COST ="待支付";
    public static final String TICKET_PAYING="支付中";
    public static final String TICKET_REISSUED="已出票";
    public static final String TICKET_REFUND="有退票";
    public static final String TICKET_OPERATIONAL="已使用";
    public static final String TICKET_REFUNDED="已退票";
    public static final String TICKET_CANCELED="已取消";
    public static final String TICKET_PRICE="全价";
    public static final String TICKET_FOLD="折";
    public static final String TICKET_FOLDSS="%";
    public static final String NUMBER="0";  //没有票
    public static final String ISSEARCH="isSearch";  //是否查询过
    public static final String CLIENT_VERSION="clientVersion";  //客户端版本号
    public static final String TEL="tel:"; 
    public static final String Setting_vo="settingVo"; //统一配置接口
    public static final long SETTING_VO_TIMES=1000L*60L*60L*2L;   //2小时有限时间
    public static final String colon="：";
    public static final String DELETE = "delete";
    public static final String EDITE = "edite";
    public static final String PASSENGER = "passenger";
    public static final String ADDRESS = "address";
    
    public static final int TYPE_VALID_CODE_REG = 0; //注册
    public static final int TYPE_VALID_CODE_FORGET_PWD = 1; //忘记密码
    public static final int ItineraryType_NO = 0;   //获取行程单方式(默认为0无需行程单,1为免费邮寄,2民航自助售票打印)
    public static final int ItineraryType_Free = 1; //获取行程单方式(默认为0无需行程单,1为免费邮寄,2民航自助售票打印)
    public static final int ItineraryType_PRINT = 2; //获取行程单方式(默认为0无需行程单,1为免费邮寄,2民航自助售票打印)
    
    public static final String AIRLINE_ALL="全部航空";
    public static final String NOT_LIMITED_TO="不限";
//    public static final String[] MENU_NAME={"登录/注册","关于我们","使用帮助","意见反馈","退出软件" };
    public static final String[] MENU_NAME={"关于我们","使用帮助","意见反馈","退出软件" };
    
    public static final String SAVE_VERSION_STATION_TIME="saveVersionStationTime";   //保存火车票版本的时间
    public static final String VERSION_AIRPORT_CITY="cityVersion";   //机场城市表版本号
//    public static final String VERSION_AIRPORT="versionAirport";   //机场表版本号
    public static final String VERSION_PAY_TYPE="paVersion";   //支付方式列表的更新版本号
    public static final String VERSION_FLIGHT_MODE="moVersion";   //机型列表的更新版本号
//    public static final String VERSION_STATION="versionStation";   //火车城市表版本号
    public static final String DB_VERSION="dbVersion";   //数据库版本号
    public static final String HOTEL_CITY_CODE = "0101";//酒店城市码
    public static final String bookTicketNumbers = "400-818-9008";	//立即订票电话
    public static final String bookTicketNumber = "4008189008";	//立即订票电话
    
    public static final String PUSH_TIME="pushTime";
    public static final String SUCCESS_PUSH_TIME="successPushTime";
	
    
    //日历类型(0为机票,1为租车,2为酒店)
    public static final int CALENDAR_TYPE_AIR = 0;
    public static final int CALENDAR_TYPE_CAR = 1;
    public static final int CALENDAR_TYPE_HOTEL = 2;
    
    public static final int IDENTITY_CARD = 0;//身份证
    public static final int PASSPORT = 1;//护照
    public static final int OTHER = 9;//其他
    public static final int ADULT = 0;//成年人
    public static final int CHILD = 1;//儿童
    
    //租车服务类型(1必须  3可选)
    public static final int CAR_SERVICE_TYPE_REQUIRED = 1;
    public static final int CAR_SERVICE_TYPE_OPTIONAL = 3;
    
    //租车门店(1取车门店 2还车门店)
    public static final int CAR_STORE_TYPE_GET = 1;
    public static final int CAR_STORE_TYPE_RETURN = 2;
    
    //租车日期
    public static final int CAR_GET_DATE=1;       //取车日期
	public static final int CAR_RETURN_DATE=2;    //还车日期
	//租车时段
	public static final int CAR_TIME_HOUR=1;     //小时 
	public static final int CAR_TIME_MINUTE=2;   //分钟
	
	//租车订单状态(已预订、租赁中、已完成、已取消)
	public static final String CAR_ORDER_STATUS_BOOK="已预订";
	public static final String CAR_ORDER_STATUS_LEASE="租赁中";
	public static final String CAR_ORDER_STATUS_COMPLETE="已完成";
	public static final String CAR_ORDER_STATUS_CANCEL="已取消";
    
    //性别(0男,1女)
    public static final String SEX_MAN = "0";
    public static final String SEX_WOMAN = "1";
    
    //会员信息文件
    public static final String FILE_NAME_MEMBERINFO = "memberInfo";
    
    //证件类型(0身份证)
    public static final String CARD_TYPE_IDENTITY = "0";
    
    //邮寄地址条数
    public static final int ADDRESS_NUM = 3;
    //乘机人最大选择人数
    public static final int PASSENGER_NUM = 9;
    
    public static final int PAY_TYPE_UPPAY=1; 			//银联
    public static final int PAY_TYPE_ALIPAY=0; 			//支付宝
    
    public static final int IMAGE_HOTEL_SMALL=101; 		//酒店显示的小图
    public static final int IMAGE_HOTEL_BIG=102;		//酒店显示的大图
    public static final int IMAGE_CAR_SMALL=103;		//酒店租车的小图
//    public static final int IMAGE_CAR_BIG=104;			//酒店租车的大图
    
//    public static final int OTHER_CITY_HOTEL=0;  	//酒店城市
    public static final int OTHER_FLIGHT_MODE=1;		//机型
    public static final int OTHER_PAY_TYPE=2;		//支付方式
    public static final int OTHER_CITY_AIRPORTCITY=0;		//机票城市
//    public static final int OTHER_CITY_STATION=4;		//火车城市
	public static final int HOTEL_NAME = 88;//就弹名
	
	public static final int INIT_COMPLATE=900;   //初始化完成
	public static final int INIT_ERROR=800;		 //初始化错误
	public static final int INIT_NETWORK_ERROR=700;		 //没有网络 
	public static final int INIT_UPDATE_VALUE=600;		 //更新数据 
	
	public static final long EXPIRATION_TIME=30L*60L*1000L;     //用户过期时长   ,30分钟
//	public static final long EXPIRATION_TIME=20L*1000L;     //用户过期时长   ,30分钟
	public static final String[] LIST = new String[]{"6:00-9:00","7:00-10:00","8:00-11:00","9:00-12:00","10:00-13:00",
		"11:00-14:00","12:00-15:00","13:00-16:00","14:00-17:00","15:00-18:00","16:00-19:00",
		"17:00-20:00","18:00-21:00","19:00-22:00","20:00-23:00","21:00-23:59","22:00-次日1:00",
		"23:00-次日2:00","次日0:00-次日3:00","次日1:00-次日4:00","次日2:00-次日5:00","次日3:00-次日6:00"};;
		
		
	public static final String SHARED_PREF_NAME = "userInfor";			//用户信息
	public static final String TENCENT_STATE = "tencent_state";			//腾讯微博绑定状态
	public static final String SINA_STATE = "sina_state";			//新浪微博绑定状态
	public static final String SHARED_PREF_LOGIN_ACCOUNT_ID = "uId";	//用户id
	public static final String SHARED_PREF_AUTO_LOGIN = "auto_login";	//用户信息
	public static String flight = "flight";
	public static int ADDPASSENGER = 2;
	public static String target = "object";						//传递的对象名称
	public static String FROM_ACTIVITY = "from_activity";						//传递来自的activity
	
	//从哪个界面进入登录界面的判断
	public static String FROM_HOME = "FROM_HOME";//从首页进入登录
	public static String FROM_MY_INFOR = "FROM_MY_INFOR";//从首页进入登录
	public static String FROM_SELECT_TICKET = "fromSelectTicket";//从机票选择界面进入登录
	public static String FROM_SINGEL_INFOR = "FROM_SINGEL_INFOR";//从会员信息中点击注销进入
	public static String ADD_ADDRESS = "ADD_ADDRESS";//要去添加邮递地址
	public static String EDIT_ADDRESS = "EDIT_ADDRESS";//编辑邮寄地址
	public static String FROM_TICKET = "FROM_TICKET";//从订单进入
	public static String FROM_WRITE_TICKET = "FROM_WRITE_TICKET";//从订单进入
	
	
	
}
