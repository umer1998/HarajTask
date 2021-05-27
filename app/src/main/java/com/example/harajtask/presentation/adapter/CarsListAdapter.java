package com.example.harajtask.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harajtask.R;
import com.example.harajtask.info.CarDetail;
import com.example.harajtask.utils.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CarsListAdapter extends RecyclerView.Adapter<CarsListAdapter.ViewHolder>
        implements Filterable {


    private Context context;
    private OnItemClickListener mItemClickListener;
    private ViewHolder viewHolder;
    List<CarDetail> carDetailList;
    List<CarDetail> carDetailListFilter;


    public CarsListAdapter(Context context, List<CarDetail> carDetailList) {
        this.context = context;
        this.carDetailList = carDetailList;
        this.carDetailListFilter = carDetailList;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {

        private ImageView ivImage;
        private TextView tvName, tvTitle, tvDuration, tvCity;


        public ViewHolder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.image);
            ivImage.setClipToOutline(true);
            tvCity = itemView.findViewById(R.id.city);
            tvDuration = itemView.findViewById(R.id.duration);
            tvName = itemView.findViewById(R.id.name);
            tvTitle = itemView.findViewById(R.id.title);


//            tvRequestAmount = itemView.findViewById(R.id.request_amount);
        }

        @Override
        public void onItemClick(CarDetail carDetail, int position) {
            mItemClickListener.onItemClick(carDetailList.get(position), getPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_cars_row, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.WhiteSmoke));
        }

        Picasso.get()
                .load(carDetailList.get(position).getThumbURL())
                .into(holder.ivImage);


        holder.tvTitle.setText(carDetailList.get(position).title);
        holder.tvName.setText(carDetailList.get(position).username);

        holder.tvCity.setText(carDetailList.get(position).city);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(carDetailList.get(position), position);
            }
        });

    }


    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return carDetailList.size();
    }

    public void clickLoanDetail() {

        viewHolder.itemView.performClick();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    carDetailListFilter = carDetailList;
                } else {
                    List<CarDetail> filteredList = new ArrayList<>();
                    for (CarDetail row : carDetailList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    carDetailListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = carDetailListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                carDetailListFilter = (ArrayList<CarDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}