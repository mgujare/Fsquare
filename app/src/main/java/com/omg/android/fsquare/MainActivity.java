package com.omg.android.fsquare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.omg.android.fsquare.sdk.connection.FourSquareAPI;
import com.omg.android.fsquare.sdk.connection.FourSquareAPIFactory;
import com.omg.android.fsquare.sdk.data.SearchResponse;
import com.omg.android.fsquare.sdk.data.Venue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.txt_fs_venue_data);
        getVenuesNearMe();
    }

    private void getVenuesNearMe() {
        FourSquareAPIFactory fourSquareFactory = new FourSquareAPIFactory(CLIENT_ID, CLIENT_SECRET);
        FourSquareAPI fourSquareAPI = fourSquareFactory.createAPI();
        Map<String, String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        //Read more at https://developer.foursquare.com/overview/versioning.
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String today = formatter.format(date);
        params.put("v", today);
        params.put("m", "foursquare");
        Call<SearchResponse> call = fourSquareAPI.searchVenue("40.7,-74", params);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", response.body().toString());
                    displayVenueData(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
    }

    private void displayVenueData(SearchResponse response) {
        StringBuffer venueData = new StringBuffer("");
        List<Venue> venues = response.response().venues();
        for (Venue v:venues) {
            venueData.append(v.name() + ": " + v.location().address() + "\n \n");
        }

        mTextView.setText(venueData.toString());
    }
}
