package com.geekbrains.weather_v_08.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.weather_v_08.R;
import com.geekbrains.weather_v_08.ui.home.HomeFragment;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView cityCard;

        SearchViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.searchCardView);
            cityCard = itemView.findViewById(R.id.cityCard);
        }
    }

    List<SearchModel> cityList;

    SearchAdapter(List<SearchModel> cityList){
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_recycler, parent, false);
        SearchViewHolder pvh = new SearchViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, final int position) {
        holder.cityCard.setText(cityList.get(position).getCity());

        holder.cityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = cityList.get(position).getCity();
                Log.i("dfsdf", s);

                HomeFragment homeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("city", s);
                homeFragment.setArguments(bundle);

                Navigation.findNavController(v)
                        .navigate(R.id.action_nav_search_to_nav_home, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
