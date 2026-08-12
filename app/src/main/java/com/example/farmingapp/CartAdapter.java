package com.example.farmingapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<String> {
    public CartAdapter(Context context, ArrayList<String> items){
        super(context, android.R.layout.simple_list_item_1, items);
    }
}
