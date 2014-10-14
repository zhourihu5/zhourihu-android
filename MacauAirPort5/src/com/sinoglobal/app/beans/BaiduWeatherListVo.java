package com.sinoglobal.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhourihu
 * @createdate 2014年7月8日 上午11:27:02
 * @Description: TODO(用一句话描述该类做什么)
 * {
    "error": 0,
    "status": "success",
    "date": "2014-07-08",
    "results": [
        {
            "currentCity": "澳门",
            "pm25": "32",
            "index": "",
            "weather_data": [
                {
                    "date": "周二 07月08日 (实时：26℃)",
                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/leizhenyu.png",
                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/leizhenyu.png",
                    "weather": "雷阵雨",
                    "wind": "微风",
                    "temperature": "33 ~ 28℃"
                },
                {
                    "date": "周三",
                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/zhenyu.png",
                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/duoyun.png",
                    "weather": "阵雨转多云",
                    "wind": "西南风3-4级",
                    "temperature": "33 ~ 28℃"
                },
                {
                    "date": "周四",
                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/duoyun.png",
                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/duoyun.png",
                    "weather": "多云",
                    "wind": "西南风3-4级",
                    "temperature": "33 ~ 28℃"
                },
                {
                    "date": "周五",
                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/duoyun.png",
                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/duoyun.png",
                    "weather": "多云",
                    "wind": "西南风3-4级",
                    "temperature": "33 ~ 28℃"
                }
            ]
        }
    ]
}
 */
public class BaiduWeatherListVo implements Serializable {
    private String date;
    private List<BaiduWeatherResults>results;
    
    public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<BaiduWeatherResults> getResults() {
		return results;
	}
	public void setResults(List<BaiduWeatherResults> results) {
		this.results = results;
	}
	
	
	public class BaiduWeatherResults implements Serializable{
    	private List<BaiduWeatherData> weather_data;
    	
    	 public List<BaiduWeatherData> getWeather_data() {
			return weather_data;
		}

		public void setWeather_data(List<BaiduWeatherData> weather_data) {
			this.weather_data = weather_data;
		}

		public class BaiduWeatherData implements Serializable{
    	    	private String temperature;
    	    	private String dayPictureUrl;
    	    	private String nightPictureUrl;
    			public String getTemperature() {
    				return temperature;
    			}
    			public void setTemperature(String temperature) {
    				this.temperature = temperature;
    			}
    			public String getDayPictureUrl() {
    				return dayPictureUrl;
    			}
    			public void setDayPictureUrl(String dayPictureUrl) {
    				this.dayPictureUrl = dayPictureUrl;
    			}
    			public String getNightPictureUrl() {
    				return nightPictureUrl;
    			}
    			public void setNightPictureUrl(String nightPictureUrl) {
    				this.nightPictureUrl = nightPictureUrl;
    			}
    	    	
    	    }
    }
   
}
