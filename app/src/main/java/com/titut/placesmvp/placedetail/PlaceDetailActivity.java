package com.titut.placesmvp.placedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.titut.placesmvp.R;

public class PlaceDetailActivity extends AppCompatActivity {

    public static final String PLACE_ID = "PLACEID";

    public static void start(Context context, String placeId) {
        context.startActivity(newIntent(context, placeId));
    }

    public static Intent newIntent(Context context, String placeId) {
        Intent intent = new Intent(context, PlaceDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PLACE_ID, placeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        String placeId = getIntent().getStringExtra(PLACE_ID);

        PlaceDetailFragment placeDetailFragment = (PlaceDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentContainer);
        if (placeDetailFragment == null) {
            placeDetailFragment = PlaceDetailFragment.newInstance(placeId);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentContainer, placeDetailFragment);
            transaction.commit();
        }

    }
}
