package com.example.weatherapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    EditText editText;
    TextView country, city, temper;
    Button button;
    ImageView imageView;
    TextView latitude, longitude, humidity, sunrise, winds, pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editText = findViewById(R.id.city_name);
        button = findViewById(R.id.button);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        temper = findViewById(R.id.temp);
        imageView = findViewById(R.id.img);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.Sunrise);
        winds = findViewById(R.id.windspeed);
        pressure = findViewById(R.id.pressure);

        button.setOnClickListener((v) -> {
            checkWeather();
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWeather();

            }
        });
    }

    public void checkWeather() {
        final String city_A = editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city_A + "&appid=e727aa76abd2d4f8d3170e0614e82d90";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //calling API
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //find country
                    JSONObject object1 = jsonObject.getJSONObject("sys");
                    String country_check = object1.getString("country");

                    country.setText(country_check);

                    //find city
                    String city_check = jsonObject.getString("name");
                    city.setText(city_check);

                    //find temperature
                    JSONObject object2 = jsonObject.getJSONObject("main");
                    String temp_check = object2.getString("temp");

                    temper.setText(temp_check + "° C");

                    // find image Icon
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String icon = jsonObject1.getString("icon");

                    Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imageView);

                    //find latitude
                    JSONObject jsonObject2 = jsonObject.getJSONObject("coord");
                    double find_latitude = jsonObject2.getDouble("lat");
                    latitude.setText(find_latitude + "° N");

                    //find longitude
                    JSONObject jsonObject3 = jsonObject.getJSONObject("coord");
                    double find_longitude = jsonObject3.getDouble("lon");
                    longitude.setText(find_longitude + "° E");

                    //find humidity
                    JSONObject jsonObject4 = jsonObject.getJSONObject("main");
                    int find_humidity = jsonObject4.getInt("humidity");
                    humidity.setText(find_humidity + " %");

                    //find sunrise
                    JSONObject jsonObject5 = jsonObject.getJSONObject("sys");
                    String find_sunrise = jsonObject5.getString("sunrise");
                    sunrise.setText(find_sunrise);

                    //find windSpeed
                    JSONObject jsonObject6 = jsonObject.getJSONObject("wind");
                    String find_speed = jsonObject6.getString("speed");
                    winds.setText(find_speed + "  km/h");

                    //find pressure
                    JSONObject jsonObject7 = jsonObject.getJSONObject("main");
                    String find_pressure = jsonObject7.getString("pressure");
                    pressure.setText(find_pressure + "  hPa");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);
    }
}