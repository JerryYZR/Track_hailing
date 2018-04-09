package com.example.track_hailing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by 叶泽锐 on 2018/4/9.
 */

public class OrdersAdapter extends ArrayAdapter<Orders> {

    private int resourceId;
    private OrdersAdapter.ViewHolder viewHolder;
    private Orders orders;


    public OrdersAdapter(Context context, int textViewResourceId,
                        List<Orders> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        orders = getItem(position);

        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new OrdersAdapter.ViewHolder();
            viewHolder.car_type = (TextView) view.findViewById (R.id.car_type);
            viewHolder.car_status = (TextView)view.findViewById(R.id.car_status);
            viewHolder.oDate = (TextView)view.findViewById(R.id.oDate);
            viewHolder.dDate = (TextView) view.findViewById (R.id.dDate);
            viewHolder.expense = (TextView)view.findViewById(R.id.expense);
            viewHolder.o_address = (TextView)view.findViewById(R.id.o_address);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (OrdersAdapter.ViewHolder) view.getTag();
        }

        viewHolder.car_type.setText(orders.getName());
        viewHolder.car_status.setText(orders.getStatus());
        viewHolder.oDate.setText(orders.getOriginDate());
        viewHolder.dDate.setText(orders.getDestinateDate());
        viewHolder.expense.setText("￥" + orders.getPrice() + "");
        viewHolder.o_address.setText(orders.getAddress());

        return view;
    }

    class ViewHolder {
        TextView car_type;
        TextView car_status;
        TextView oDate;
        TextView dDate;
        TextView expense;
        TextView o_address;
    }

}