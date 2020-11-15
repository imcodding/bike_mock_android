package com.mia.bikeproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mia.bikeproject.models.BikeInfo;

import java.util.ArrayList;

public class BikeListAdapter extends RecyclerView.Adapter<BikeListAdapter.BikeHolder> implements Filterable {

    ArrayList<BikeInfo> bikeList;
    ArrayList<BikeInfo> filteredList;

    public BikeListAdapter(ArrayList<BikeInfo> bikeList) {
        this.bikeList = bikeList;
        this.filteredList = bikeList;
    }

    @NonNull
    @Override
    public BikeListAdapter.BikeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bike, null);
        BikeHolder holder = new BikeHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BikeListAdapter.BikeHolder holder, int position) {
        BikeInfo bikeInfo = filteredList.get(position);

        int rack = bikeInfo.getParkingBikeTotCnt();
        int total = bikeInfo.getRackTotCnt();

        String rackStr = "남은 자전거: " + rack;
        String totalStr = "전체 : " + total;

        String result = rackStr + "  " + totalStr;

        holder.tvBikeCnt.setText(result);
        holder.tvStationName.setText(bikeInfo.getStationName());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class BikeHolder extends RecyclerView.ViewHolder {
        TextView tvStationName;
        TextView tvBikeCnt;
        public BikeHolder(@NonNull View itemView) {
            super(itemView);

            tvStationName = itemView.findViewById(R.id.tv_station_name);
            tvBikeCnt = itemView.findViewById(R.id.tv_bike_cnt);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    filteredList = bikeList;
                } else {
                    ArrayList<BikeInfo> filteringList = new ArrayList<>();
                    for(BikeInfo info : bikeList) {
                        if(info.getStationName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(info);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<BikeInfo>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
