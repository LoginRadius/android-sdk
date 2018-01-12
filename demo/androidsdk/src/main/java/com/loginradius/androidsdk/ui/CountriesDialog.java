package com.loginradius.androidsdk.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by loginradius on 7/28/2017.
 */

public class CountriesDialog extends Dialog implements TextWatcher,OnItemClickListener{

    private ArrayList<String> countries;
    private EditText etSearch;
    private ListView lvCountries;
    private CountriesAdapter adapter;
    private OnCountrySelectedListener listener;

    public CountriesDialog(@NonNull Context context, ArrayList<String> countries) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog);
        this.countries = new ArrayList<>(countries);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setTitle("Select Country");
        setCancelable(true);
        setView();
    }

    private void setView() {
        Context context = getContext();
        RequiredFieldsViewGenerator gtr = new RequiredFieldsViewGenerator(context, 0);
        LinearLayout layout = gtr.generateParentContainerView();
        etSearch = gtr.generateEditText("search_country",false);
        etSearch.addTextChangedListener(this);
        etSearch.setHint("Search");
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        params.setMargins(0,gtr.getPx(5),0,0);
        lvCountries = new ListView(context);
        lvCountries.setLayoutParams(params);
        lvCountries.setOnItemClickListener(this);
        adapter = new CountriesAdapter(context,countries);
        lvCountries.setAdapter(adapter);
        layout.addView(etSearch);
        layout.addView(lvCountries);
        setContentView(layout);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String search = s.toString().trim().toLowerCase();
        adapter.filter(search);
    }

    public void setOnCountrySelectedListener(OnCountrySelectedListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener!=null){
            listener.onCountrySelected(adapter.getItem(position));
        }
        dismiss();
    }

    public interface OnCountrySelectedListener{
        void onCountrySelected(String country);
    }

    class CountriesAdapter extends ArrayAdapter<String>{

        private ArrayList<String> countries, list;

        public CountriesAdapter(@NonNull Context context, @NonNull ArrayList<String> countries) {
            super(context, android.R.layout.simple_list_item_1, countries);
            this.countries = countries;
            list = new ArrayList<>();
            list.addAll(countries);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);
            textView.setTextColor(Color.BLACK);
            return textView;
        }

        private void filter(String text){
            text = text.toLowerCase();
            countries.clear();
            if(text.length() == 0){
                countries.addAll(list);
            }else{
                for(String search: list){
                    if(search.toLowerCase().contains(text)){
                        countries.add(search);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
