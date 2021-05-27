package com.example.harajtask.presentation.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.harajtask.R;
import com.example.harajtask.info.CarDetail;
import com.example.harajtask.presentation.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailScreenFragment extends Fragment {

    private ImageView ivImage;
    private TextView tvName, tvTitle, tvBody, tvCity, tvPhoneno, tvDate;

    private MainActivity context;
    private CarDetail carDetail;

    String phoneno = "+966500100100";


    public DetailScreenFragment(MainActivity context, CarDetail carDetail) {
        this.context = context;
        this.carDetail = carDetail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_screen, container, false);

        context.searchView.setVisibility(View.GONE);
        context.ivNavigation.setVisibility(View.GONE);
        context.ivShare.setVisibility(View.VISIBLE);
        context.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, carDetail.title);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        ivImage = view.findViewById(R.id.image);

        tvPhoneno = view.findViewById(R.id.phonenno);
        tvDate = view.findViewById(R.id.date);

        tvBody = view.findViewById(R.id.body);
        tvCity = view.findViewById(R.id.city);
        tvTitle = view.findViewById(R.id.title);
        tvName = view.findViewById(R.id.name);

        setContent();


        return view;
    }

    private void setContent() {
        Picasso.get()
                .load(carDetail.thumbURL)
                .into(ivImage);
        tvBody.setText(carDetail.body);
        tvName.setText(carDetail.username);
        tvTitle.setText(carDetail.title);
        tvBody.setText(carDetail.body);
        tvCity.setText(carDetail.city);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mma");
        String dateString = formatter.format(new Date(Long.parseLong(String.valueOf(carDetail.date))));
        tvDate.setText(dateString);

        tvPhoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneno));
                startActivity(intent);
            }
        });
    }

    private Bitmap convertURLtoBitmap(String thumbURL) throws IOException {
        URL url = new URL(thumbURL);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return bmp;
    }
}