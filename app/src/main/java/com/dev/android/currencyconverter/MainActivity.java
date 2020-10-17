package com.dev.android.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;



import android.app.ProgressDialog;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
  private Spinner spinner;
  private ProgressDialog dialog;
  private Spinner currencyFrom;
  private EditText amount;
  private TextView currencyResult;
  private Button convertCurrency;
  private String selectedStringTo;
  private String selectedStringFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    bindActivity();

        try {
          ArrayList<String> arrayList = new ReteriveListOfCurrency().execute().get();
          if(arrayList!=null){
              Log.e("arr", String.valueOf(arrayList.size()));
              addInSpinner(arrayList);
          }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void bindActivity() {
        spinner=findViewById(R.id.spinner);
        dialog=new ProgressDialog(this);
        currencyFrom=findViewById(R.id.spinnerFrom);
        currencyResult=findViewById(R.id.currencyResult);
        convertCurrency=findViewById(R.id.convertButton);
       amount=findViewById(R.id.amount);
        convertCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(amount.getText().toString()!=null && selectedStringFrom!=null && selectedStringTo!=null){
                   try {
                       changeCurrency();
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        });
    }

    private void changeCurrency() throws ExecutionException, InterruptedException {
        Double arr=new ConvertCurrencyClass(selectedStringFrom,selectedStringTo,Integer.parseInt(amount.getText().toString())).execute().get();
        if(arr!=null){
            currencyResult.setText(String.valueOf(arr));
        }
    }


    private void addInSpinner(final ArrayList<String> curr) {
        ArrayList<String> arrayList=curr;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        currencyFrom.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedStringTo=curr.get(position);
                Log.e("SelectString",selectedStringTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStringFrom=curr.get(position);
                Log.e("SelectString",selectedStringFrom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}