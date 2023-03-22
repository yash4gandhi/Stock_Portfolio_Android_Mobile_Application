package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class gdbutton {

    public static final String api_company = "https://loyal-bounty-346223.wl.r.appspot.com/company/";
    public static final String api_latest_price = "https://loyal-bounty-346223.wl.r.appspot.com/price/";
    public static final String api_autocomplete = "https://loyal-bounty-346223.wl.r.appspot.com/autocomplete/";

    Context context;

    public gdbutton(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {

        void onError(String message);

        void onResponse(companydata cd);
    }



    public void symbol(String name, VolleyResponseListener volleyResponseListener){
        String url = api_company +name;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Toast.makeText(context, response.toString(),Toast.LENGTH_SHORT).show();
                //cnt = response.getString("ticker");
                companydata cd = new companydata();

                try {
                    String country = response.getString("country");
                    cd.setCountry(country);
                    String currency = response.getString("currency");
                    cd.setCurrency(currency);
                    String exchange = response.getString("exchange");
                    cd.setExchange(exchange);
                    String finnhubIndustry = response.getString("finnhubIndustry");
                    cd.setFinnhubIndustry(finnhubIndustry);
                    String  ipo= response.getString("ipo");
                    cd.setIpo(ipo);
                    String  logo= response.getString("logo");
                    cd.setLogo(logo);
                    String  name= response.getString("name");
                    cd.setName(name);
                    String  phone= response.getString("phone");
                    cd.setPhone(phone);
                    String  ticker= response.getString("ticker");
                    cd.setTicker(ticker);
                    String  weburl= response.getString("weburl");
                    cd.setWeburl(weburl);
                    Double  marketCapitalization= response.getDouble("marketCapitalization");
                    cd.setMarketCapitalization(marketCapitalization);
                    Double  shareOutstanding= response.getDouble("shareOutstanding");
                    cd.setShareOutstanding(shareOutstanding);
                    volleyResponseListener.onResponse(cd);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something Wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface LatestPriceListener {

        void onError(String message);

        void onResponse(PriceData pd);
    }
    public void latestprice(String name, LatestPriceListener latestpriceListner ){
        String url = api_latest_price +name;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                //Toast.makeText(context, response.toString(),Toast.LENGTH_SHORT).show();
                //Log.d("In Lp",response.toString());

                PriceData pd = new PriceData();
                try {
                    pd.setC(response.getDouble("c"));
                    pd.setD(response.getDouble("d"));
                    pd.setDp(response.getDouble("dp"));
                    pd.setH(response.getDouble("h"));
                    pd.setL(response.getDouble("l"));
                    pd.setO(response.getDouble("o"));
                    pd.setPc(response.getDouble("pc"));
                    pd.setT(response.getLong("t"));
                    pd.setTicker(response.getString("ticker"));
                    latestpriceListner.onResponse(pd);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show();
                latestpriceListner.onError("Something Wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface AutoCompleteListener {

        void onError(String message);

        void onResponse(ArrayList<AutocompleteObj> resau);
    }

    public void autocompletecall(String name, AutoCompleteListener autoCompleteListener ){
        String url = api_autocomplete+name;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(context, response.toString(),Toast.LENGTH_SHORT).show();
                Log.d("In Lp",response.toString());

                ArrayList<AutocompleteObj> resau = new ArrayList<>();
                //Toast.makeText(context,"Size is: "+response.length(),Toast.LENGTH_SHORT).show();

                for(int i = 0;i< response.length();i++){
                    try {
                        //Toast.makeText(context,"number: "+i,Toast.LENGTH_SHORT).show();
                        resau.add(new AutocompleteObj(response.getJSONObject(i).getString("description"), response.getJSONObject(i).getString("displaySymbol"), response.getJSONObject(i).getString("symbol"), response.getJSONObject(i).getString("type")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                autoCompleteListener.onResponse(resau);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show();
                autoCompleteListener.onError("Something Wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }




}

