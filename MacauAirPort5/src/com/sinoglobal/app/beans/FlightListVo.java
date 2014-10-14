package com.sinoglobal.app.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月11日 上午9:31:18
 * @Description: TODO(用一句话描述该类做什么) 航班查询列表信息
 *{
    "city": "北京",
    "air": [
        {
            "airline": "NX",
            "dateBegin": "2014-03-30",
            "dateOver": "2014-10-25",
            "flightNumber": "NX008",
            "planeType": "A319",
            "arrtime": "19:15",
            "devtime": "22:30",
            "weekDay": "1,2,3,4,5,6,7"
        },
        {
            "airline": "NX",
            "dateBegin": "2014-03-30",
            "dateOver": "2014-06-14",
            "flightNumber": "NX006",
            "planeType": "A319",
            "arrtime": "07:30",
            "devtime": "10:45",
            "weekDay": "1,2,3,4,5,6,7"
        },
        {
            "airline": "NX",
            "dateBegin": "2014-03-30",
            "dateOver": "2014-10-25",
            "flightNumber": "NX002",
            "planeType": "A321",
            "arrtime": "11:45",
            "devtime": "15:10",
            "weekDay": "1,2,3,4,5,6,7"
        }
    ]
}
 */
public class FlightListVo implements Serializable{
	
	/**
	 * @author zhourihu
	 * @adddate 2014年6月11日 上午10:52:35
	 *
	 */
	private static final long serialVersionUID = 1919015005524710995L;
	private List<FlightVo>air;
	private String city;
	public List<FlightVo> getAir() {
		return air;
	}
	public void setAir(List<FlightVo> air) {
		this.air = air;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
