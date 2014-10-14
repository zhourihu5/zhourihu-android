package com.sinoglobal.app.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalDb;

import com.alibaba.fastjson.JSON;
import com.sinoglobal.app.activity.FlyApplication;
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
import com.sinoglobal.app.service.api.IRemote;
import com.sinoglobal.app.service.parse.TestJson;
import com.sinoglobal.app.util.FileLocalCache;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.TimeUtil;
import com.sinoglobal.app.util.XmlUtil;
import com.sinoglobal.app.util.http.ConnectionUtil;

public class RemoteImpl implements IRemote {
	private static IRemote remote;
	String url="http://202.175.83.22:8095/RequestService";//  肖勇写的后台接口
	static String language="zh-cn".equals(FlyApplication.language)?"chinese":"english";
	//getMessage&language=english  或者 chinese

	private RemoteImpl() {
	}

	static {
		remote = new RemoteImpl();
	}

	public static IRemote getInstance() {
		language="zh-cn".equals(FlyApplication.language)?"chinese":"english";
		return remote;
	}

	@Override
	public List<BannerVo> getBannerList(boolean isCache) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actionType", "getBulletInfo");
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}

		 return JSON.parseArray(json, BannerVo.class);
//		return JSON.parseArray(TestJson.getBannerList(), BannerVo.class);

	}

	@Override
	public List<FlightVo> getFlightListRealTimeVo(boolean isCache,String type) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("lan", FlyApplication.language);
		String url = ConnectionUtil.URL+"api.php";
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		if(TextUtil.stringIsNotNull(json)){
			int i=json.indexOf("[");
			LogUtil.e("json前的特殊字符","=="+json.substring(0, i));
			json=json.substring(i);
			
		}
		return JSON.parseArray(json, FlightVo.class);
	}

	@Override
	public List<String> getCityList(boolean isCache,String type) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("lan", FlyApplication.language);
		
		String url = ConnectionUtil.URL+"search.php";
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		int i=json.indexOf("[");
		LogUtil.e("json前的特殊字符","=="+json.substring(0, i));
		json=json.substring(i);
		List<String>list=null;
		list=JSON.parseArray(json, String.class);//fastJson 解析存在bug,解析速度慢
		
		return list;
	}

	@Override
	public PushMessageVo getPushMessage(boolean isCache) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VersionVo geVersionVo(boolean isCache) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actionType", "getVersionInfo");
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		return JSON.parseObject(json, VersionVo.class);
//		return JSON.parseObject(TestJson.getNewVersion(), VersionVo.class);
	}

	@Override
	public FlightListVo searchFlight(boolean isCache,String type,String search) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("search", search);
		params.put("lan", FlyApplication.language);
		String url=ConnectionUtil.URL+"search.php";
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		int i=json.indexOf("{");
		LogUtil.e("json前的特殊字符","=="+json.substring(0, i));
		json=json.substring(i);
		return JSON.parseObject(json, FlightListVo.class);
		
	}

	@Override
	public List<WeatherVo> getWeatherList(boolean isCache,String city) throws Exception {
		FinalDb db=FinalDb.create(FlyApplication.context);
		String date=TimeUtil.parseDateToString(TimeUtil.sdf1,new Date());
		String sqlWhere=" validFor<"+"'"+date+"'";
		db.deleteByWhere(WeatherVo.class,sqlWhere );
		List<WeatherVo> weatherVoList = null ;
		sqlWhere=" stationname="+"'"+city+"'"+" and language="+"'"+FlyApplication.language+"'";
		weatherVoList=db.findAllByWhere(WeatherVo.class,sqlWhere  );
		if(weatherVoList!=null&&!weatherVoList.isEmpty()){
			return weatherVoList;
		}
		String url=null;
		if(FlyApplication.isEnglishDefault()){// 英语接口
			url="http://xml.smg.gov.mo/e_china.xml";//再从中国查询
		}else{
			url="http://xml.smg.gov.mo/c_china.xml";//再从中国查询
		}
		weatherVoList=XmlUtil.parseWeather(db, url,sqlWhere);
		if(weatherVoList!=null&&!weatherVoList.isEmpty()){
			return weatherVoList;
		}
		if(FlyApplication.isEnglishDefault()){// 英语接口
			url="http://xml.smg.gov.mo/e_asia.xml";//先从亚洲查
		}else{
			url="http://xml.smg.gov.mo/c_asia.xml";//先从亚洲查
		}
		weatherVoList=XmlUtil.parseWeather(db, url,sqlWhere);
		
		return weatherVoList;
	}

	@Override
	public List<PushMessageVo> getMessageVos(boolean isCache) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		String url="http://202.175.83.22:8096/newsapi.asmx/GetNewsList";
		params.put("language", FlyApplication.language);
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		 return JSON.parseArray(json, PushMessageVo.class);
//		return JSON.parseArray(TestJson.getPushMessageList(), PushMessageVo.class);
	}

	@Override
	public QuestionNaireVo getQuestionNaire(boolean isCache) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("language", language);
		params.put("actionType", "getQuestionNaire");
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		return JSON.parseObject(json, QuestionNaireVo.class);
//		return JSON.parseObject(TestJson.getQuestionNaire(), QuestionNaireVo.class);
	}
	@Override
	public MessageVo getFlightMessageVo(boolean isCache) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("language", language);
		params.put("actionType", "getMessage");
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		return JSON.parseObject(json, MessageVo.class);
	}
	
	@Override
	public MapDataListVo getMapData(boolean isCache) throws Exception {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("language", language);
		params.put("actionType", "getMapDetail");
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		return JSON.parseObject(json, MapDataListVo.class);
//		return JSON.parseObject(TestJson.getMapData(), MapDataListVo.class);
	}

	@Override
	public BaiduWeatherListVo getBaiduWeatherListVo(boolean isCache)
			throws Exception {
		//百度 澳门天气接口
		String url="http://api.map.baidu.com/telematics/v3/weather?location=%20%E6%BE%B3%E9%97%A8&output=json&ak=GuZriL3rkm1MUnyTyfsNGvTC";
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, null));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,null,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		return JSON.parseObject(json, BaiduWeatherListVo.class);
	}

	@Override
	public MapDataVo getServeDetail(boolean isCache, String serveName)
			throws Exception {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("language", language);
		params.put("actionType", "getServiceInfo");
		params.put("type",serveName);
		String json =null;
		if (isCache) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
		return JSON.parseObject(json, MapDataVo.class);
	}

	@Override
	public PushMessageVo getNewsDetail(boolean b, String nid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String url="http://202.175.83.22:8096/newsapi.asmx/GetNewsContentByNid";// 端口可能改成8096 或8095
		params.put("nid", nid);
		String json =null;
		if (b) {
			json = FileLocalCache.loadFileCache(FileLocalCache.getFileName(
					url, params));
			LogUtil.e("缓存返回====>" , json==null?"null":json);
		} else {
			json = ConnectionUtil.get(url,params,true);
			LogUtil.e("网络返回====>:  ", json);
		}
//		LogUtil.i("新闻详情返回 json=="+json);
//		json=json.substring(json.indexOf("{"), json.lastIndexOf("}"));
//		if(!json.contains("}")){
//			json=json+"}";
//		}
//		LogUtil.i("新闻详情截取后的json=="+json);
		 return JSON.parseObject(json, PushMessageVo.class);
	}

	@Override
	public List<MapDataVo> getFacilityList() throws Exception {
		if("en".equals(FlyApplication.language)){
			return JSON.parseArray(TestJson.getFacilityListEn(), MapDataVo.class);
		}
		return JSON.parseArray(TestJson.getFacilityListCn(), MapDataVo.class);
	}

	
}
