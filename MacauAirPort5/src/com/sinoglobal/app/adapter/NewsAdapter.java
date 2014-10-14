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

import com.sinoglobal.app.beans.PushMessageVo;
import com.sinoglobal.app.util.TimeUtil;
import com.aims.mia.R;
/**
 * 
 * @author zhourihu
 * @createdate 2014年6月16日 下午5:26:07
 * @Description: TODO(用一句话描述该类做什么) 新闻的adapter
 */
public class NewsAdapter extends BaseAdapter{
    Context context;
    List<PushMessageVo>data;
    
    public List<PushMessageVo> getData() {
		return data;
	}

	public void setData(List<PushMessageVo> data) {
		this.data = data;
		notifyDataSetChanged();
	}
    
	public NewsAdapter(Context context, List<PushMessageVo> data) {
		super();
		this.context = context;
		this.data = data;
	}
	public void addItem(PushMessageVo itemMsg) {
		if(data==null){
			data=new ArrayList<PushMessageVo>();
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
	public PushMessageVo getItem(int arg0) {
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
			convertView=LayoutInflater.from(context).inflate(R.layout.news_item,null);
		}
		TextView tvTime=(TextView)convertView.findViewById(R.id.tv1);
		TextView tvNews=(TextView)convertView.findViewById(R.id.tv2);
		PushMessageVo messageVo=getItem(position);
		
		tvTime.setText(TimeUtil.parseTimeStampToString(messageVo.getTime()));
		tvNews.setText(messageVo.getTitle());
		return convertView;
	}
    
}
