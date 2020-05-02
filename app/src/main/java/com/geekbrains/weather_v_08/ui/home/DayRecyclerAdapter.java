package com.geekbrains.weather_v_08.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.weather_v_08.R;

import java.util.List;

public class DayRecyclerAdapter extends RecyclerView.Adapter<DayRecyclerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<TimeWeather> listTime;

    DayRecyclerAdapter(Context context, List<TimeWeather> listTime) {
        this.listTime = listTime;
//        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.item_day_recycler, parent, false);
//        return new DayRecyclerAdapter.ViewHolder(view);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeWeather itemTimer = listTime.get(position);
        holder.time.setText(itemTimer.getTimeDay());
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.fut_time);
            temp = itemView.findViewById(R.id.fut_temp);
            icon = itemView.findViewById(R.id.fut_icon);

        }
    }
}
