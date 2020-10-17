package com.dev.android.currencyconverter;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReteriveListOfCurrency extends AsyncTask<Void,Void, ArrayList<String>> {
    private ArrayList<String> curr=new ArrayList<>();
    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://rapidapi.p.rapidapi.com/symbols")
                .get()
                .addHeader("x-rapidapi-host", "fixer-fixer-currency-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "3aac3841b9msh50aca56c7cb307cp17ad18jsn2ff1a4350f41")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                Log.e("response", res);
                JSONObject obj = new JSONObject(res);
                JSONObject symbols = obj.getJSONObject("symbols".trim());
                Log.e("sym", symbols.toString());
                Iterator<String> keys = symbols.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    Log.e("Noyte", key + symbols.getString(key));
                    curr.add(key);
                }
                Log.e("CurrencyWeiht", "MSG" + keys);
                if (!curr.isEmpty()) {
                    return curr;
                }
            }
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
