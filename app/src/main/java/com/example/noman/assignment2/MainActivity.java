package com.example.noman.assignment2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    private String[] text;
    private String[] test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




         // will call the Async class every 2 seconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                // will execute the Aysnc class with the given parameter
                new AsyncClass().execute("http://192.168.0.5:8000/");
            }
        }, 1000, 1000);

    }

    // One method that does it all, this method will be called from Aysnc class
    public void setList() {

        // Creating ListView variable
        ListView eListView = (ListView) findViewById(R.id.eListView);
        // Inserting Global field String array to Array Adapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, text);
        eListView.setAdapter(arrayAdapter);

         // this will handle when you click on one of the list
        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view,

                                    int position, long id) {

                // will add text on String where you clicked
                String item = ((TextView) view).getText().toString();
               // will remove the line from them and split them and they will be two different Strings
                String[] parts = item.split("\n");
                String part1 = parts[0]; // Name
                String part2 = parts[1]; // Email

                // this for loop will loop through all of the Global field test Array
                for(int i = 0; i < test.length ; i++  ) {
                    // Again will remove the line from them and split them and assign them to different Strings
                    String[] result = test[i].split("\n");
                    String name = result[0];
                    String email = result[1];
                    String idd = result[2];
                    String salary = result[3];
                    String sd = result[4];
                    String title = result[5];
                    String gender = result[6];
                    String ni = result[7];
                    String dob = result[8];
                    String address = result[9];
                    String postcode = result[10];
                  // now it will check if the name equals where you clicked
                    if(name.equalsIgnoreCase(part1)){
                        // if yes , it will create new Activity and Pass it few values
                   Intent intent = new Intent(getBaseContext(), Information.class);
                         intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("id", idd);
                        intent.putExtra("salary", salary);
                        intent.putExtra("sd", sd);
                        intent.putExtra("title",title);
                        intent.putExtra("gender", gender);
                        intent.putExtra("ni", ni);
                        intent.putExtra("dob", dob);
                        intent.putExtra("address", address);
                        intent.putExtra("postcode", postcode);
                        // will Start the Activity
                         startActivity(intent);


                    }
                }


            }

        });
    };

////////////////////////////////////  ASYNC CLASS ///////////////////////////////////////

    public class AsyncClass extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
               // will assign the parameter to url field
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // will open a connection

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                // will read the data
                StringBuffer sbuffer = new StringBuffer();
                String data = "";
                // this loop will append all the data to StringBuffer
                while ((data = reader.readLine()) != null) {
                    sbuffer.append(data);
                }
                String json = sbuffer.toString();

                // will add all the data toa string and then put it into JSON Array
                JSONArray jArray = new JSONArray(json);


                StringBuffer ssbuffer = new StringBuffer();
                // will assign the String[] Arrays
                text = new String[jArray.length()];
                test = new String[jArray.length()];
                for( int i = 0 ; i < jArray.length(); i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                // will get the values and put it into the fields neatly
                    String name = jObject.getString("name");
                    String email = jObject.getString("email");
                    int id = jObject.getInt("id");
                    int salary = jObject.getInt("salary");
                    String sd = jObject.getString("startDate");
                    String title = jObject.getString("title");
                    String gender = jObject.getString("gender");
                    String ni = jObject.getString("natInscNo");
                    String dob = jObject.getString("dob");
                    String address = jObject.getString("address");
                    String postcode = jObject.getString("postcode");
                    // will append name and email to String Buffer... Just for returning purposes later on
                    ssbuffer.append(name + "    " +  email + "\n");
                    // this String array we will be using when we create new Activity
                    test[i] = name + " \n" + email +" \n" +  id +" \n" +  salary +" \n" +  sd +" \n" + title + " \n" + gender + " \n" + ni + " \n" + dob + " \n" + address +" \n" + postcode;
                    // this String array will help us display the name and Email on MainActivity's List view
                    text[i] = name + " \n" + email ;



                }




              return ssbuffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                try {
                    if (reader != null) {

                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        // will simply call the function
           setList();

           // text.setText(result);





        }
    }
}





