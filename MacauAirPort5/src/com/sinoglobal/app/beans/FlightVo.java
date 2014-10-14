package com.sinoglobal.app.beans;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月11日 上午9:31:18
 * @Description: TODO(用一句话描述该类做什么) 航班信息
 * {
        "time": "09:00",
        "airline": "BL",
         "origin": "上海浦東",//实时抵达澳门航班信息
        "destination": "峴港",//实时离开澳门航班信息
        "dateBegin": "2014-03-30",//查询出发地 为某地的航班
        "dateOver": "2014-10-25",//查询出发地 为某地的航班
        "flightNumber": "NX007",
        "planeType": "A319",//查询出发地 为某地的航班
        "arrtime": "08:25",//查询出发地 为某地的航班
        "devtime": "12:25",//查询出发地 为某地的航班
        "weekDay": "1,2,3,4,5,6,7"//查询出发地 为某地的航班
        "gate": "2A",
        "statue": "停止登機",
        "date": "11/Jun/2014",
        "weather": "--"
    }
 */
public class FlightVo implements Serializable{
//	public static final long serialVersionUID = -1221175291407945777L;
	
	private String time;
	private String airline;
	private String origin;//实时抵达澳门航班信息
	private String destination;//实时离开澳门航班信息
	
	private String dateBegin;//查询出发地 为某地的航班
	private String dateOver;//查询出发地 为某地的航班
	private String planeType;//查询出发地 为某地的航班
	private String arrtime;//查询出发地 为某地的航班
	private String devtime;//查询出发地 为某地的航班
	private String weekDay;//查询出发地 为某地的航班
	@Id(column="flightNumber")	
	private String flightNumber;
	private String gate="";
	private String statue;
	private String date;
	private String weather;
	private String weatheren;//天气英文
	private boolean leave;//離開澳門
	private boolean realTime;//實時航班
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getDestination() {
		return destination;
	}
	
	public boolean getLeave() {
		return leave;
	}
	public void setLeave(boolean leave) {
		this.leave = leave;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getStatue() {
		return statue;
	}
	public void setStatue(String statue) {
		this.statue = statue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public String getDateOver() {
		return dateOver;
	}
	public void setDateOver(String dateOver) {
		this.dateOver = dateOver;
	}
	public String getPlaneType() {
		return planeType;
	}
	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}
	public String getArrtime() {
		return arrtime;
	}
	public void setArrtime(String arrtime) {
		this.arrtime = arrtime;
	}
	public String getDevtime() {
		return devtime;
	}
	public void setDevtime(String devtime) {
		this.devtime = devtime;
	}
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public String getWeatheren() {
		return weatheren;
	}
	public void setWeatheren(String weatheren) {
		this.weatheren = weatheren;
	}
	public boolean getRealTime() {
		return realTime;
	}
	public void setRealTime(boolean isRealTime) {
		this.realTime = isRealTime;
	}
	
	
}
