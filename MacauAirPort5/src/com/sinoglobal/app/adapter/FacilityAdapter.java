package com.sinoglobal.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinoglobal.app.beans.MapDataVo;
import com.sinoglobal.app.beans.MapDataVo;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年7月22日 下午7:20:25
 * @Description: TODO(用一句话描述该类做什么) 机场设施的adapter
 */
public class FacilityAdapter extends BaseAdapter{
    Context context;
    List<MapDataVo>data;
    public void setData(List<MapDataVo> data) {
		this.data = data;
		notifyDataSetChanged();
	}
    
	public FacilityAdapter(Context context, List<MapDataVo> data) {
		super();
		this.context = context;
		this.data = data;
	}
	public void addItem(MapDataVo itemMsg) {
		if(data==null){
			data=new ArrayList<MapDataVo>();
		}
        data.add(itemMsg);
        notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data==null?0:data.size();
	}

	@Override
	public MapDataVo getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.facility_item,null);
		}
		TextView tv=(TextView)convertView.findViewById(R.id.tv);
		tv.setText(getItem(position).getTitle());
		return convertView;
	}

}
