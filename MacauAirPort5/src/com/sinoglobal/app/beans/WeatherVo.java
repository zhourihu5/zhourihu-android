package com.sinoglobal.app.beans;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;


/**
 * 
 * @author zhourihu
 * @createdate 2014年5月15日 下午2:35:58
 * @Description: TODO(用一句话描述该类做什么) 天气信息
 * 
   <WeatherForecast>
	<ValidFor>2014-06-20</ValidFor>
	<station code="45005">
		<stationname>香港</stationname>
		<Temperature>
			<MeasureUnit>°C</MeasureUnit>
			<Type>2</Type>
			<Value>27</Value>
		</Temperature>
		<Temperature>
			<MeasureUnit>°C</MeasureUnit>
			<Type>1</Type>
			<Value>31</Value>
		</Temperature>
		<Icon>
			<IconName>ww-c18.gif</IconName>
			<IconURL>http://xml.smg.gov.mo/icons/weatherIcon/ww-c18.gif</IconURL>
		</Icon>
		<WeatherDescription>雷雨</WeatherDescription>
	</station></WeatherForecast>
	<WeatherForecast>
}
 */
public class WeatherVo implements Serializable {
	@Id(column="stationname")
	private String stationname;
	@Property(column="measureunit")
	private String measureUnit;
	@Property(column="valuefrom")
	private String valueFrom;
	@Property(column="valueto")
	private String valueTo;
	@Property(column="iconurl")
	private String iconURL;
	@Property(column="validFor")
	private String validFor;
	@Property(column="language")
	private String language;
	
	public String getStationname() {
		return stationname;
	}
	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getValueFrom() {
		return valueFrom;
	}
	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}
	public String getValueTo() {
		return valueTo;
	}
	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}
	public String getIconURL() {
		return iconURL;
	}
	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
	public String getValidFor() {
		return validFor;
	}
	public void setValidFor(String validFor) {
		this.validFor = validFor;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public String toString() {
		return "WeatherVo [stationname=" + stationname + ", measureUnit="
				+ measureUnit + ", valueFrom=" + valueFrom + ", valueTo="
				+ valueTo + ", iconURL=" + iconURL + ", validFor=" + validFor
				+ ", language=" + language + "]";
	}
	
}

