package com.purvapatel.lightsoffapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.purvapatel.lightsoffapp.Supporting_Files.AppConfig;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Chart extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private int lastX = 0;
    String BASE_URL = "https://packers-backend.herokuapp.com";
    public String b1 = "";
    public String m1 ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Match Value");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time/s");
        // data
        series = new LineGraphSeries<DataPoint>();
        series.setColor(Color.RED);
        series.setTitle("Motion");
        series.setThickness(8);

        series2 = new LineGraphSeries<DataPoint>();
        series2.setThickness(8);
        series2.setTitle("Brightness");

        graph.addSeries(series);
        graph.addSeries(series2);

        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(100);
        viewport.setScrollable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.sensor api = adapter.create(AppConfig.sensor.class);
        api.sensorData(new Callback<Response>() {

            @Override
            public void success(Response result, Response response) {

                try {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                    String resp;
                    resp = reader.readLine();
                    android.util.Log.d("success", "" + resp);

                    JSONObject jObj = new JSONObject(resp);
                    int success = jObj.getInt("success");

                    if (success == 1) {
                        //Toast.makeText(getApplicationContext(), "led is "+led, Toast.LENGTH_SHORT).show();;

                        JSONArray obj = jObj.getJSONArray("obj");
                        JSONObject values = (JSONObject) obj.get(0);
                        b1 = values.getString("brightness");
                        m1 = values.getString("motion");
                        android.util.Log.d("brightness--------", ""+ b1);

                        lastX++;
                        series.appendData(new DataPoint(lastX, Double.parseDouble(m1)/10d), true, 100);
                        series2.appendData(new DataPoint(lastX, Double.parseDouble(b1)), true, 100);


                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    android.util.Log.d("Exception", e.toString());
                } catch (JSONException e) {
                    android.util.Log.d("JsonException", e.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }}
        );

    }

}
