package com.sinoglobal.app.beans;

import java.io.Serializable;
import java.util.List;

import net.tsz.afinal.annotation.sqlite.Id;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月11日 上午9:31:18
 * @Description: TODO(用一句话描述该类做什么) 地图信息
 * {
    "resumeMapUrl": "/resources/map/main.jpg",
    "width": "300.0",
    "height": "400.0",
    "location": [
        {
            "locx": "23.0",
            "locy": "44.0",
            "classcode": "lklx",
            "mapId": "0"
        },
        {
            "locx": "123.0",
            "locy": "44.0",
            "classcode": "cy",
            "mapId": "0"
        },
        {
            "locx": "23.0",
            "locy": "144.0",
            "classcode": "lklx",
            "mapId": "0"
        },
        {
            "locx": "223.0",
            "locy": "144.0",
            "classcode": "ggqy",
            "mapId": "0"
        },
        {
            "locx": "123.0",
            "locy": "344.0",
            "classcode": "cy",
            "mapId": "0"
        },
        {
            "locx": "203.0",
            "locy": "44.0",
            "classcode": "ggqy",
            "mapId": "0"
        },
        {
            "locx": "123.0",
            "locy": "404.0",
            "classcode": "sb",
            "mapId": "0"
        },
        {
            "locx": "203.0",
            "locy": "144.0",
            "classcode": "jt",
            "mapId": "0"
        },
        {
            "locx": "223.0",
            "locy": "194.0",
            "classcode": "sb",
            "mapId": "0"
        },
        {
            "locx": "183.0",
            "locy": "344.0",
            "classcode": "jt",
            "mapId": "0"
        }
    ],
    "detailMap": [
        {
            "fromX": "0.0",
            "fromY": "0.0",
            "toX": "300.0",
            "toY": "133.0",
            "detailMapUrl": "/resources/map/level1.jpg",
            "detailWidth": "300.0",
            "detailHeight": "400.0",
            "location": [
                {
                    "locx": "23.0",
                    "locy": "44.0",
                    "classcode": "lklx",
                    "mapId": "9",
                    "content": {
                        "title": "二层机场中转站",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "二层机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站"
                    }
                },
                {
                    "locx": "123.0",
                    "locy": "44.0",
                    "classcode": "cy",
                    "mapId": "10",
                    "content": {
                        "title": "一层二服务站",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "二层二服务站二层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站"
                    }
                },
                {
                    "locx": "23.0",
                    "locy": "144.0",
                    "classcode": "lklx",
                    "mapId": "11",
                    "content": {
                        "title": "机场中转站2",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "机场中转站222机场中转站机场中转站机22222222222222场中转站机场中转站机场中转站"
                    }
                }
            ]
        },
        {
            "fromX": "0.0",
            "fromY": "133.0",
            "toX": "300.0",
            "toY": "266.0",
            "detailMapUrl": "/resources/map/level2.jpg",
            "detailWidth": "300.0",
            "detailHeight": "400.0",
            "location": [
                {
                    "locx": "223.0",
                    "locy": "144.0",
                    "classcode": "ggqy",
                    "mapId": "12",
                    "content": {
                        "title": "二层机场中转站3",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "二层机场中转站3333333机场中转站机场中转站机33333333333场中转站机场中转站机场中转站"
                    }
                },
                {
                    "locx": "123.0",
                    "locy": "344.0",
                    "classcode": "cy",
                    "mapId": "13",
                    "content": {
                        "title": "三层机场中转站",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "三层机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站机场中转站"
                    }
                },
                {
                    "locx": "203.0",
                    "locy": "44.0",
                    "classcode": "ggqy",
                    "mapId": "14",
                    "content": {
                        "title": "三层二服务站",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "三层二服务站二层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站一层二服务站"
                    }
                },
                {
                    "locx": "123.0",
                    "locy": "404.0",
                    "classcode": "sb",
                    "mapId": "15",
                    "content": {
                        "title": "三层机场中转站2",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "机场中转站222机场中转站机场中转站机22222222222222场中转站机场中转站机场中转站"
                    }
                }
            ]
        },
        {
            "fromX": "0.0",
            "fromY": "266.0",
            "toX": "300.0",
            "toY": "400.0",
            "detailMapUrl": "/resources/map/level3.jpg",
            "detailWidth": "300.0",
            "detailHeight": "400.0",
            "location": [
                {
                    "locx": "203.0",
                    "locy": "144.0",
                    "classcode": "jt",
                    "mapId": "16",
                    "content": {
                        "title": "三层机场中转站3",
                        "imgUrl": "/resources/map/kkkk.jpg",
                        "tel": "65457665,987887888",
                        "introduction": "三层机场中转站3333333机场中转站机场中转站机33333333333场中转站机场中转站机场中转站"
                    }
                },
                {
                    "locx": "223.0",
                    "locy": "194.0",
                    "classcode": "sb",
                    "mapId": "17"
                },
                {
                    "locx": "183.0",
                    "locy": "344.0",
                    "classcode": "jt",
                    "mapId": "18"
                }
            ]
        }
    ]
}
 */
public class MapDataListVo implements Serializable{
//	public static final long serialVersionUID = -1221175291407945777L;
	
	private String resumeMapUrl;
	private String width;
	private String height;
	private List<MapDataVo> location;
	
	private String title;
	private String markImgUrl;
	private String imgUrl;
	private String tel;
	private String introduction;
	private String updateDate;
	private List<DetailMapDataVo> detailMap;
	public String getResumeMapUrl() {
		return resumeMapUrl;
	}
	public void setResumeMapUrl(String resumeMapUrl) {
		this.resumeMapUrl = resumeMapUrl;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public List<MapDataVo> getLocation() {
		return location;
	}
	public void setLocation(List<MapDataVo> location) {
		this.location = location;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMarkImgUrl() {
		return markImgUrl;
	}
	public void setMarkImgUrl(String markImgUrl) {
		this.markImgUrl = markImgUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public List<DetailMapDataVo> getDetailMap() {
		return detailMap;
	}
	public void setDetailMap(List<DetailMapDataVo> detailMap) {
		this.detailMap = detailMap;
	}
	
	
}
