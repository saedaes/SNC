package supind.corp.snc;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationDetailActivity extends AppCompatActivity {

    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        //Get notification Data
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        String appName = intent.getStringExtra("appName");
        url = intent.getStringExtra("url");

        //Get view elements
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
        TextView txtAppName = (TextView) findViewById(R.id.txtAppName);

        //Display notification Data
        txtTitle.setText(title);
        txtMessage.setText(message);
        txtAppName.setText(appName);

        //Handle buttons tab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(view, "Go to Application", Snackbar.LENGTH_LONG)
                        .setAction("Go!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                }catch (Exception e){
                                    Toast.makeText(NotificationDetailActivity.this, "There was an error, unable to open provided link.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                snackbar.show();
            }
        });
    }

}
