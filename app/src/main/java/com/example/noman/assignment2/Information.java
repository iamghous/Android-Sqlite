package com.example.noman.assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Noman on 24/03/2017.
 */

public class Information extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);

          // Retreive all the data and put them into local fields
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String idd = getIntent().getStringExtra("id");
        String salary = getIntent().getStringExtra("salary");
        String sd = getIntent().getStringExtra("sd");
        String title = getIntent().getStringExtra("title");
        String gender = getIntent().getStringExtra("gender");
        String ni = getIntent().getStringExtra("ni");
        String dob = getIntent().getStringExtra("dob");
        String address = getIntent().getStringExtra("address");
        String postcode = getIntent().getStringExtra("postcode");
      // will create Listview Object
        ListView tv = (ListView) findViewById(R.id.listview);



        String id = "ID    :       " + idd   ;
        String n = "Name    :       " + name   ;
        String d = "Date of Birth    :       " + dob ;
        String e = "Email    :       " + email  ;
        String g = "Gender    :       " + gender  ;
        String t = "Job Title    :       " + title  ;
        String s = "Job Start Date    :       " + sd  ;
        String nii = "NI Number    :       " + ni   ;
        String a = "Address    :       " + address  ;
        String p = "Postcode    :       " + postcode ;
        ArrayList<String> arr = new ArrayList<String>();
        // will add every single detail in the Array List, so we can retreive in the ListView
        arr.add(id);
        arr.add(n);
        arr.add(d);
        arr.add(e);
        arr.add(g);
        arr.add(t);
        arr.add(s);
        arr.add(nii);
        arr.add(a);
        arr.add(p);
    // will pass the ArrayList to ArrayAdapter, which will then display them
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, arr);
        tv.setAdapter(arrayAdapter);
    }

}
