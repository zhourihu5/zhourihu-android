package com.sinoglobal.app.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import org.xmlpull.v1.XmlPullParser;

import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.beans.WeatherVo;
import com.sinoglobal.app.util.http.ConnectionUtil;

import android.util.Log;
import android.util.Xml;

/**
 * 
 * @author zhourihu
 * @createdate 2014年6月20日 下午5:13:46
 * @Description: TODO(用一句话描述该类做什么) 解析xml 操作的工具类
 */
public class XmlUtil {
	 /** 
     * 解析XML转换成对象 
     *  
     * @param is 
     *            输入流 
     * @param clazz 
     *            对象Class 
     * @param fields 
     *            字段集合一一对应节点集合 
     * @param elements 
     *            节点集合一一对应字段集合 
     * @param itemElement 
     *            每一项的节点标签 
     * @return 
     */  
    public static List<Object> parse(InputStream is, Class<?> clazz,  
            List<String> fields, List<String> elements, String itemElement) {  
        Log.v("rss", "开始解析XML.");  
        List<Object> list = new ArrayList<Object>();  
        try {  
            XmlPullParser xmlPullParser = Xml.newPullParser();  
            xmlPullParser.setInput(is, "UTF-8");  
            int event = xmlPullParser.getEventType();  
  
            Object obj = null;  
  
            while (event != XmlPullParser.END_DOCUMENT) {  
                switch (event) {  
                case XmlPullParser.START_TAG:  
                    if (itemElement.equals(xmlPullParser.getName())) {  
                        obj = clazz.newInstance();  
                    }  
                    if (obj != null  
                            && elements.contains(xmlPullParser.getName())) {  
                        setFieldValue(obj, fields.get(elements  
                                .indexOf(xmlPullParser.getName())),  
                                xmlPullParser.nextText());  
                    }  
                    break;  
                case XmlPullParser.END_TAG:  
                    if (itemElement.equals(xmlPullParser.getName())) {  
                        list.add(obj);  
                        obj = null;  
                    }  
                    break;  
                }  
                event = xmlPullParser.next();  
            }  
        } catch (Exception e) {  
            Log.e("rss", "解析XML异常：" + e.getMessage());  
            throw new RuntimeException("解析XML异常：" + e.getMessage());  
        }  
        return list;  
    }  
      
    /** 
     * 设置字段值 
     *  
     * @param propertyName 
     *            字段名 
     * @param obj 
     *            实例对象 
     * @param value 
     *            新的字段值 
     * @return 
     */  
    public static void setFieldValue(Object obj, String propertyName,  
            Object value) {  
        try {  
            Field field = obj.getClass().getDeclaredField(propertyName);  
            field.setAccessible(true);  
            field.set(obj, value);  
        } catch (Exception ex) {  
            throw new RuntimeException();  
        }  
    }  
    public static List<WeatherVo> parseWeather(FinalDb db, String url,String sqlWhere) {
    	WeatherVo weatherVo=null;
		try {
			XmlPullParser parser = Xml.newPullParser();
			//设置解析数据源
			parser.setInput(ConnectionUtil.get(url), "utf-8");
			//取得事件的类型
			int eventType = parser.getEventType();
			String eleName = null ;
			boolean isFrom=false;
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
					//文档开始
					case XmlPullParser.START_DOCUMENT:
						break ;
					//元素开始
					case XmlPullParser.START_TAG:
						eleName = parser.getName();
						if("WeatherForecast".equals(eleName)){
							weatherVo = new WeatherVo();
							weatherVo.setLanguage(FlyApplication.language);
						}
						else if("ValidFor".equals(eleName)){
							weatherVo.setValidFor(parser.nextText()); 
						}
						else if("stationname".equals(eleName)){
							weatherVo.setStationname(parser.nextText());
						}
						else if("MeasureUnit".equals(eleName)){
							weatherVo.setMeasureUnit(parser.nextText());
						}
						else if("IconURL".equals(eleName)){
							weatherVo.setIconURL(parser.nextText());
						}
						else if("Type".equals(eleName)){
							isFrom="2".equals(parser.nextText());
						}
						else if("Value".equals(eleName)){
							if(isFrom){
								weatherVo.setValueFrom(parser.nextText());
							}else{
								weatherVo.setValueTo(parser.nextText());
							}
						}
						break ;
					//标记结束
					case XmlPullParser.END_TAG:
						eleName = parser.getName();
						if("WeatherForecast".equals(eleName)){
							db.delete(weatherVo);
							db.save(weatherVo);;
							LogUtil.e("weatherVo", weatherVo.toString());
						}
						break ;
				}
				//手动激活下个事件的触发
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db.findAllByWhere(WeatherVo.class,sqlWhere);
	}
}
