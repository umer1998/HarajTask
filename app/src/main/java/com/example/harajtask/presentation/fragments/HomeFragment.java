package com.example.harajtask.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harajtask.R;
import com.example.harajtask.info.CarDetail;
import com.example.harajtask.presentation.activities.MainActivity;
import com.example.harajtask.presentation.adapter.CarsListAdapter;
import com.example.harajtask.utils.CommonUtils;
import com.example.harajtask.utils.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    MainActivity context;
    private RecyclerView recyclerView;
    List<CarDetail> carDetailList = new ArrayList<>();


    public HomeFragment(MainActivity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context.searchView.setVisibility(View.VISIBLE);
        context.ivNavigation.setVisibility(View.VISIBLE);
        context.ivShare.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        try {

            JSONArray cast = new JSONArray(CommonUtils.loadJSONFromAsset(context, "data.json"));
            for (int i = 0; i < cast.length(); i++) {

                JSONObject jsonobject = cast.getJSONObject(i);

                CarDetail carDetail = new CarDetail();
                carDetail.setBody(jsonobject.getString("body"));
                carDetail.setCity(jsonobject.getString("city"));
                carDetail.setCommentCount(jsonobject.getInt("commentCount"));
                carDetail.setDate(jsonobject.getInt("date"));
                carDetail.setThumbURL(jsonobject.getString("thumbURL"));
                carDetail.setUsername(jsonobject.getString("username"));
                carDetail.setTitle(jsonobject.getString("title"));

                carDetailList.add(carDetail);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setRecyclerView(carDetailList);

        setSearchView();

        return view;
    }

    private void setSearchView() {
        context.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setRecyclerView(List<CarDetail> carDetailList) {
        final CarsListAdapter carsListAdapter = new CarsListAdapter(context, carDetailList);
        recyclerView.setAdapter(carsListAdapter);

        carsListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(CarDetail carDetail, int position) {
                context.detailScreenFragment(carDetail);
            }
        });
    }

    public String loadJSONfromFile() {
        return CommonUtils.loadJSONFromAsset(context, "data.json");
    }
}