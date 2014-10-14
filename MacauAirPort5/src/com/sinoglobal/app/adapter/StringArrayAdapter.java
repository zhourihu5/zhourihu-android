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

import com.aims.mia.R;

public class StringArrayAdapter extends BaseAdapter{
    Context context;
    List<String>data;
    public void setData(List<String> data) {
		this.data = data;
		notifyDataSetChanged();
	}
    
	public StringArrayAdapter(Context context, List<String> data) {
		super();
		this.context = context;
		this.data = data;
	}
	public void addItem(String itemMsg) {
		if(data==null){
			data=new ArrayList<String>();
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
	public String getItem(int arg0) {
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
			convertView=LayoutInflater.from(context).inflate(R.layout.city_list_item,null);
		}
		TextView tv=(TextView)convertView.findViewById(R.id.tv);
		tv.setText(getItem(position));
		return convertView;
	}

}
