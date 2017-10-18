package fcm;

/**
 * Created by marco on 17/10/17.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import supind.corp.snc.NotificationDetailActivity;
import supind.corp.snc.R;

public class SNCFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = remoteMessage.getData().get("message");
        String title = remoteMessage.getData().get("title");
        String appName = remoteMessage.getData().get("applicationName");
        String url = remoteMessage.getData().get("url");

        if (remoteMessage.getFrom().startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        sendNotification(message, title, appName, url);
    }

    private void sendNotification(String message, String title, String appName, String url) {
        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("appName", appName);
        intent.putExtra("url", url);

        //Generate a random number to assign it as notification ID
        Random random = new Random();
        int notificationId = random.nextInt((1000000 - 1) + 1) + 1;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);//getResources(). getColor(R.color.colorPrimary);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setColor(color)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon))
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setLights(Color.BLUE,300,1000)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[] {0, 1000, 500, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
