package com.geekbrains.weather_v_08.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.weather_v_08.R;

import java.util.List;

public class HourRecyclerAdapter extends RecyclerView.Adapter<HourRecyclerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<TimeWeather> listTime;

    HourRecyclerAdapter(Context context, List<TimeWeather> listTime) {
        this.listTime = listTime;
//        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public HourRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.item_hour_recycyler, parent, false);
//        return new ViewHolder(view);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hour_recycyler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HourRecyclerAdapter.ViewHolder holder, int position) {
        TimeWeather itemTimer = listTime.get(position);
        holder.time.setText(itemTimer.getTime());
        holder.temp.setText(itemTimer.getTemp());
        holder.icon.setImageResource(itemTimer.getIcon());
    }

    @Override
    public int getItemCount() {
        return listTime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, temp;
        ImageView icon;
        ViewHolder(View view){
            super(view);
        time = view.findViewById(R.id.hor_time);
        temp = view.findViewById(R.id.hor_temp);
        icon = view.findViewById(R.id.hor_icon);
        }
    }

}

//    ViewHolder(final View view) {
//        super(view);
//        time = view.findViewById(R.id.hor_time);
//        temp = view.findViewById(R.id.hor_temp);
//        icon = view.findViewById(R.id.hor_icon);
//    }
