package supind.corp.snc;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set activity Title
        setTitle("Superior Notifications Center");
        //Set layout File
        setContentView(R.layout.activity_main);

        //Set welcome text formated
        setWelcomeText();

        //Declare an intent that references TopicsActivity
        final Intent topicsActivityIntent = new Intent(this, TopicsActivity.class);


        //Handle floating button tab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar snackbar = Snackbar
                        .make(view, "Manage Subscriptions", Snackbar.LENGTH_LONG)
                        .setAction("Go", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(topicsActivityIntent);
                            }
                        });
                snackbar.show();
            }
        });
    }


    //Set initial text
    private void setWelcomeText(){
        TextView txtWelcome = (TextView) findViewById(R.id.txtWelcome);

        String welcomeText = "<b>Welcome to Superior Notification Center</b><br/><br/>" +
                "With this app you will be able to retrieve all notifications sent by Superior web portals and desktop applications.";

        txtWelcome.setText(Html.fromHtml(welcomeText));
    }
}
