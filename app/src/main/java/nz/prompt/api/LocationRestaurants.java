package nz.prompt.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nz.prompt.R;

public class LocationRestaurants extends AppCompatActivity {
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_restaurants);
        queue = Volley.newRequestQueue(this);
    }

    private StringRequest searchNameStringRequest(String nameSearch) {
        final String API = "&api_key=<<e030e05da8d402eac19a2564a7ab6a87>>";
        final String NAME_SEARCH = "&q=";
        final String DATA_SOURCE = "&ds=Standard Reference";
        final String FOOD_GROUP = "&fg=";
        final String SORT = "&sort=r";
        final String MAX_ROWS = "&max=25";
        final String BEGINNING_ROW = "&offset=0";


        final String URL_PREFIX = "https://developers.zomato.com/api/v2.1/";
        final String USER_KEY = "e030e05da8d402eac19a2564a7ab6a87";
        final String QUERY = "McDonald";
        final Double LAT = -36.7636172;
        final double LON = 174.7188818;
        final int COUNT = 50;


//        String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;  //URL OF ZOMATO
        String url = URL_PREFIX + "locations?query=Queen%20Street";


        //1st param => type of method (GET/PUT/POST/POST/PATCH/etc)
        //2nd param => complete url of API
        //3rd param => Response.Listener -> Error Procedure
        //4th param => Response.ErrorListener -> Error procedure
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    //3rd param - method onResponse lays the code procedure of success return
                    //SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // check api documentation for returned format
                        try {
                            JSONObject result = new JSONObject(response).getJSONObject("list");
                            int maxItems = result.getInt("end");
                            JSONArray resultList = result.getJSONArray("item");
                        } catch (JSONException e) {
                            Toast.makeText(LocationRestaurants.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }//public void onResponse(String response)
                }, //Response.Listener<String>()
                new Response.ErrorListener() {
                    //4thParam - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displays a simple message on screen
                        Toast.makeText(LocationRestaurants.this, "Food source is not responding (ZOMATO API)", Toast.LENGTH_LONG).show();
                    }
                });
    }


    //Click event of the button Search
    public void btnSearchClickEventHandler(View view) {
        String hello = "Henllo";
        //cancelling all request about this search if in queue
        queue.cancelAll("Hello");

        //first StringRequest: getting items searched
        StringRequest stringRequest = searchNameStringRequest(hello);
        stringRequest.setTag("Hello");

        queue.add(stringRequest);
    }
}
