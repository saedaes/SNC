package supind.corp.snc;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Map;

import listAdapters.TopicsListAdapter;
import models.Topic;
import services.ReadJSON;

public class TopicsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String topic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        final ListView topicsListView = (ListView) findViewById(R.id.listViewTopics);

        try {
            //Create a list adapter with the topics list from webservice
            TopicsListAdapter adapter = new TopicsListAdapter(this, (ArrayList<Topic>) getTopics());
            //Set list adapter to listview
            topicsListView.setAdapter(adapter);
        } catch (Exception e){
            //Nothing to do.
        }

        //Handle listView item click
        topicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                topic = topicsListView.getItemAtPosition(position).toString();

                //verify if the device is already subscribed to the topic, if it is then return and show the message.
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean sentToken = sharedPreferences.getBoolean(topic, false);
                if (sentToken) {
                    displayUnsubscribeTopicMessage(topic);
                    return;
                }

                //create a snackbar to ask the user if he wants to subscribe to the selected topic
                Snackbar snackbar = Snackbar
                        .make(view, "Subscribe to " + topicsListView.getItemAtPosition(position).toString() + "?", Snackbar.LENGTH_LONG)
                        .setAction("Subscribe", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseMessaging.getInstance().subscribeToTopic(topic);
                                sharedPreferences.edit().putBoolean(topic, true).apply();
                                displaySubscriptionToast(topic);
                            }
                        });
                snackbar.show();
            }
        });

        //Handle floating button tab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.subcriptions);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "";
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, ?> allEntries = sharedPreferences.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    message += entry.getKey() + "\n";
                }
                if(message == ""){
                    message = "No Subscriptions";
                }
                displaySubscribedTopics(message);
            }
        });
    }

    //Display an alert dialog with the user subscriptions
    private void displaySubscribedTopics(String message){
        new AlertDialog.Builder(this)
                .setTitle("Subscriptions")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    //display an alert dialog to ask the user if he wants to unsubscribe a given topic
    private void displayUnsubscribeTopicMessage(final String topic){
        new AlertDialog.Builder(this)
                .setTitle("Subscribed")
                .setMessage("You are already subscribed to this topic, do you want to unsubscribe off this topic?")
                .setPositiveButton("Unsubscribe", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
                        //remove topic registration from local storage
                        sharedPreferences.edit().remove(topic).apply();
                        //show message
                        displayUnsubscriptionToast(topic);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //Display a toast message when subscription is successful
    private void displaySubscriptionToast(String topic){
        Toast.makeText(this,"Now you are Subscribed to " + topic + " topic", Toast.LENGTH_LONG).show();
    }

    //Display a toast message when unsubscription is successful
    private void displayUnsubscriptionToast(String topic){
        Toast.makeText(this,"Now you are unsubscribed from " + topic + " topic", Toast.LENGTH_LONG).show();
    }

    //Get topics from JSON and returns a List object
    private List<Topic> getTopics(){
        String json = getJSON();
        Gson gson = new Gson();

        if(json.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "There was an error \n please try again.", Toast.LENGTH_LONG);
            toast.show();
            return null;
        }
        Type listType = new TypeToken<List<Topic>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    //Call the class that handles the call to webservice and retrieves the JSON response
    private String getJSON(){
        String response;
        GetTopics topics = new GetTopics("");
        topics.execute();

        try {
            response = topics.get();
            if (response.equals("null")){
                return "";
            }
            //return response json
            return response;
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressWarnings("deprecation")
    public class GetTopics extends AsyncTask<Void, Integer, String> {

        ProgressDialog loadingMessage;
        String url;

        public GetTopics(String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMessage = new ProgressDialog(TopicsActivity.this);
            loadingMessage.setMessage("Getting topics, please wait.");
            loadingMessage.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingMessage.setCancelable(true);
            loadingMessage.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loadingMessage.dismiss();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... params) {
            return ReadJSON.readJSON(url);
        }
    }
}
