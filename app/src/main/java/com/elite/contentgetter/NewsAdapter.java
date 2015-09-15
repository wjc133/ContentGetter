package com.elite.contentgetter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Elite Group
 * Created by wjc133 on 2015/9/15.
 */
public class NewsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<NewsBean> mNewsList;

    public NewsAdapter(Context context, List<NewsBean> newsList) {
        this.mNewsList = newsList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, null);
            viewHolder.contentView = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.titleView = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        viewHolder.contentView.setText(mNewsList.get(position).getContent());
        viewHolder.titleView.setText(mNewsList.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        TextView titleView;
        TextView contentView;
        ImageView imageView;
    }
}
