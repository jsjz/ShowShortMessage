package com.example.pan.showshortmessage;

/**
 * Created by pan on 2017/5/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<Msg> mData;

    public MyAdapter(Context context, List<Msg> mData, ListView msglist) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        Viewholder holder;//holder=null
        if (convertview == null) {
            convertview = LayoutInflater.from(context).inflate(R.layout.msg, null,false);
            holder = new Viewholder();
            holder.number = (TextView) convertview.findViewById(R.id.number);
            holder.content = (TextView) convertview.findViewById(R.id.content);
            holder.time = (TextView) convertview.findViewById(R.id.time);
            convertview.setTag(holder);//设置标记
        } else {

            holder = (Viewholder) convertview.getTag();
        }
        holder.number.setText(mData.get(i).getNumber());
        holder.content.setText(mData.get(i).getContent());
        holder.time.setText(mData.get(i).getTime());
        return convertview;
    }

    class Viewholder {
        TextView number;
        TextView content;
        TextView time;
    }
}

