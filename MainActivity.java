package com.unir.defteros.pruebaco;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL githubEndpoint = new URL("https://api.github.com/repositories");

                    HttpsURLConnection myCon = (HttpsURLConnection) githubEndpoint.openConnection();

                    myCon.setRequestProperty("Accept","application/vnd.github.v3+json");

                    if (myCon.getResponseCode()==200) {
                        Log.e("ErrorConexion","OK");
                        InputStream response=myCon.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(response, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);

                        jsonReader.beginObject(); // Start processing the JSON object

                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            //String value = jsonReader.nextString();
                            Log.e("Json",key+" "+ 0);
                            if (key.equals("organization_url")) { // Check if desired key
                                // Fetch the value as a String
                                //String value = jsonReader.nextString();

                                // Do something with the value
                                // ...

                                break; // Break out of the loop
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.close();
                    }else{
                        Log.e("ErrorConexion", String.valueOf(myCon.getResponseCode()));
                    }

                    myCon.disconnect();

                }catch (Exception e){
                    Log.e("ErrorConexión_API",e.toString());
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
