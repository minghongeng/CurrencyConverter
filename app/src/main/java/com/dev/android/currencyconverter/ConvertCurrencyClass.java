package com.dev.android.currencyconverter;

import android.app.ProgressDialog;
import android.content.Context;
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

public class ConvertCurrencyClass extends AsyncTask<Void,Void, Double> {
    private double result = 0.0f;
  private ProgressDialog dialog;
    public String from, to;
    public int amount;

    public ConvertCurrencyClass(String from, String to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }



    @Override
    protected Double doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Log.e("Form", from + to);
        Request request = new Request.Builder()
                .url("https://rapidapi.p.rapidapi.com/convert?from=" + from + "&to=" + to + "&amount=" + amount)
                .get()
                .addHeader("x-rapidapi-host", "fixer-fixer-currency-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "3aac3841b9msh50aca56c7cb307cp17ad18jsn2ff1a4350f41")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                Log.e("responseConversion", res);
                JSONObject obj = new JSONObject(res);
                Iterator<String> keys = obj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    Log.e("NoyteCo", key + obj.getString(key));
                    if (key.equalsIgnoreCase("result")) {
                        result = obj.getDouble(key);
                        Log.e("Value", String.valueOf(result));
                        return result;
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}