package com.example.harajtask.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harajtask.R;
import com.example.harajtask.info.CarDetail;
import com.example.harajtask.ui.activities.MainActivity;
import com.example.harajtask.ui.adapter.CarsListAdapter;
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
    CarsListAdapter carsListAdapter;


    public HomeFragment(MainActivity context) {
        this.context = context;
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //home screen app bar only show search and navigation drawer icon
        //share icon is invisiable on home screen

        context.searchView.setVisibility(View.VISIBLE);
        context.ivNavigation.setVisibility(View.VISIBLE);
        context.ivShare.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        try {

            JSONArray cast = new JSONArray(CommonUtils.loadJSONFromAsset(context, "data.json")); //converting json data from file to string and then from string to json array
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

                carDetailList.add(carDetail); //storing json data into carDetail object and storing carDetail object to list of carDetail
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setRecyclerView(carDetailList);

        setSearchView();

        return view;
    }

    // set search view so user can search any car from list through title
    private void setSearchView() {
        context.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                carsListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                carsListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    //displaying the list of cars using recyclerView.

    private void setRecyclerView(List<CarDetail> carDetailList) {
        carsListAdapter = new CarsListAdapter(context, carDetailList);
        recyclerView.setAdapter(carsListAdapter);

        carsListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(CarDetail carDetail, int position) {
                context.detailScreenFragment(carDetail);
            }
        });
    }


}