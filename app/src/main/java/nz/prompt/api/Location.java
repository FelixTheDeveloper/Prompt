package nz.prompt.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.*;

import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nz.prompt.R;

public class Location extends AppCompatActivity {
    TextView textViewes;


    private final OkHttpClient httpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        textViewes = (TextView) findViewById(R.id.locationText);
        try {
            getZomatoLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getZomatoLocation() throws Exception {
        final String URL_PREFIX = "https://developers.zomato.com/api/v2.1/";
        final String USER_KEY = "e030e05da8d402eac19a2564a7ab6a87";
        final String QUERY = "Auckland";
        final Double LAT = -36.7636172;
        final double LON = 174.7188818;
        final int COUNT = 50;

        String url = URL_PREFIX + "locations?query=" + QUERY;

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("user-key", USER_KEY)  // add request headers
                .build();

        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else {
                            try {

                                JSONObject results = new JSONObject(response.body().string());
                                textViewes.setText(results.toString());


                                System.out.print(results);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


//    private StringRequest searchNameStringRequest(String nameSearch) {
//        final String API = "&api_key=<<e030e05da8d402eac19a2564a7ab6a87>>";
//        final String NAME_SEARCH = "&q=";
//        final String DATA_SOURCE = "&ds=Standard Reference";
//        final String FOOD_GROUP = "&fg=";
//        final String SORT = "&sort=r";
//        final String MAX_ROWS = "&max=25";
//        final String BEGINNING_ROW = "&offset=0";
//
//
//        final String URL_PREFIX = "https://developers.zomato.com/api/v2.1/";
//        final String USER_KEY = "e030e05da8d402eac19a2564a7ab6a87";
//        final String QUERY = "McDonald";
//        final Double LAT = -36.7636172;
//        final double LON = 174.7188818;
//        final int COUNT = 50;
//
//
////        String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;  //URL OF ZOMATO
//        String url = URL_PREFIX + "locations?query=Unsworth%20Heights";
//
//
//        //1st param => type of method (GET/PUT/POST/POST/PATCH/etc)
//        //2nd param => complete url of API
//        //3rd param => Response.Listener -> Error Procedure
//        //4th param => Response.ErrorListener -> Error procedure
//        return new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    //3rd param - method onResponse lays the code procedure of success return
//                    //SUCCESS
//                    @Override
//                    public void onResponse(String response) {
//                        // try/catch block for returned JSON data
//                        // check api documentation for returned format
//                        try {
//                            JSONObject result = new JSONObject(response).getJSONObject("list");
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");
//                        } catch (JSONException e) {
//                            Toast.makeText(Location.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                        }
//                    }//public void onResponse(String response)
//                }, //Response.Listener<String>()
//                new Response.ErrorListener() {
//                    //4thParam - method onErrorResponse lays the code procedure of error return
//                    // ERROR
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //displays a simple message on screen
////                        Toast.makeText(Location.this, "Food source is not responding (ZOMATO API)", Toast.LENGTH_LONG).show();
//                        Toast.makeText(Location.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//    }


    //Click event of the button Search
//    public void btnSearchClickEventHandler() {
//        String hello = "Henllo";
//        //cancelling all request about this search if in queue
////        queue.cancelAll("Hello");
//
//        //first StringRequest: getting items searched
//        StringRequest stringRequest = searchNameStringRequest(hello);
//        stringRequest.setTag("Hello");
//
//        queue.add(stringRequest);
////        Toast.makeText(this, "HELLO THIS WORKS,", Toast.LENGTH_LONG).show();
//    }


}
