package com.sinoglobal.app.service.api;

import java.util.List;

import com.sinoglobal.app.beans.BaiduWeatherListVo;
import com.sinoglobal.app.beans.BannerVo;
import com.sinoglobal.app.beans.FlightListVo;
import com.sinoglobal.app.beans.FlightVo;
import com.sinoglobal.app.beans.MapDataListVo;
import com.sinoglobal.app.beans.MapDataVo;
import com.sinoglobal.app.beans.MessageVo;
import com.sinoglobal.app.beans.PushMessageVo;
import com.sinoglobal.app.beans.QuestionNaireVo;
import com.sinoglobal.app.beans.VersionVo;
import com.sinoglobal.app.beans.WeatherVo;


/**
 * 
 * @author ty
 * @createdate 2013-3-21 上午10:30:36
 * @Description: 接口
 */
public interface IRemote {
	/**
	 * @author ty
	 * @createdate 2013-3-23 上午11:13:59
	 * @Description: 获取统一配置接口
	 * @param version
	 *            当前版本号
	 * @return
	 * @throws Exception
	 * 
	 */
	public List<BannerVo> getBannerList(boolean isCache)throws Exception;
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月11日 上午11:53:08
	 * @Description: (用一句话描述该方法做什么) 获取实时航班信息列表
	 * @param type   leave：离澳实时航班    arrive：抵澳实时航班
	 * @param language Zh-cn：中文，返回中文字用了编码  En：英文
	 * @return
	 * @throws Exception
	 *
	 */
	public List<FlightVo> getFlightListRealTimeVo(boolean isCache,String type)throws Exception;
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月12日 上午9:21:01
	 * @Description: (用一句话描述该方法做什么) 获取城市列表信息
	 * @param type alist：出发地列表  dlist：目的地列表  
	 * @return
	 * @throws Exception
	 *
	 */
	public List<String> getCityList(boolean isCache,String type)throws Exception;
	public PushMessageVo getPushMessage(boolean isCache)throws Exception;
	public VersionVo geVersionVo(boolean isCache)throws Exception;
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月17日 上午8:54:24
	 * @Description: (用一句话描述该方法做什么) 查询航班
	 * @param type asearch：出发地搜索 dsearch：目的地搜索 flightNumber:航班号搜索

	 * @param search 查询的关键字
	 * @return
	 * @throws Exception
	 *
	 */
	public FlightListVo searchFlight(boolean isCache,String type,String search)throws Exception;
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月20日 下午7:10:16
	 * @Description: (用一句话描述该方法做什么) 查询天气
	 * @param city 查询的城市
	 * @return 天气信息
	 * @throws Exception
	 *
	 */
	public List<WeatherVo> getWeatherList(boolean isCache,String city)throws Exception;
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年7月2日 下午1:43:27 
	 * @Description: (用一句话描述该方法做什么) 获取新闻
	 * @return
	 * @throws Exception
	 *
	 */
	public List<PushMessageVo> getMessageVos(boolean isCache)throws Exception;
	public QuestionNaireVo getQuestionNaire(boolean isCache)throws Exception;
	public MapDataListVo getMapData(boolean isCache)throws Exception;
	public BaiduWeatherListVo getBaiduWeatherListVo(boolean isCache)throws Exception;
	public MapDataVo getServeDetail(boolean isCache,String serveName)throws Exception;
	public PushMessageVo getNewsDetail(boolean b, String nid)throws Exception;
	public List<MapDataVo> getFacilityList()throws Exception;
	public MessageVo getFlightMessageVo(boolean b)throws Exception;
    
}


