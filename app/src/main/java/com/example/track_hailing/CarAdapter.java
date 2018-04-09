package com.example.track_hailing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 叶泽锐 on 2018/4/4.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{

    private List<Car> mCarList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View CarView;
        TextView CarName;

        public ViewHolder(View view) {
            super(view);
            CarView = view;
            CarName = (TextView) view.findViewById(R.id.Car_name);
        }
    }

    public CarAdapter(List<Car> CarList) {
        mCarList = CarList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.CarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Car Car = mCarList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + Car.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Car car = mCarList.get(position);
        holder.CarName.setText(car.getName());
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

}
