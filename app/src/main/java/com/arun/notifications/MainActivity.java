package com.arun.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnNotification;
    public static final String CHANNEL_ID = "personal_notifications";
    public static final int NOTIFICATION_ID = 001;
    public static final String TXT_REPLY = "text_reply";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotification = findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnNotification) {
            displayNotification();
        }

    }

    private void displayNotification() {
        createNotificationChannel();


       /* Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Intent yesIntent = new Intent(this, YesActivity.class);
        yesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent yesPendingIntent = PendingIntent.getActivity(this, 0, yesIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent noIntent = new Intent(this, NoActivity.class);
        noIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent noPendingIntent = PendingIntent.getActivity(this, 0, noIntent, PendingIntent.FLAG_ONE_SHOT);*/


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_get_app_black_24dp);
        builder.setContentTitle("Image Download");
        builder.setContentText("Download in progress");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //builder.setAutoCancel(true);
        //builder.setContentIntent(pendingIntent);
//        builder.addAction(R.drawable.ic_done_black_24dp, "YES", yesPendingIntent);
//        builder.addAction(R.drawable.ic_clear_black_24dp, "NO", noPendingIntent);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLY)
                    .setLabel("Reply")
                    .build();

            Intent replyIntent = new Intent(this, AutoReplyActivity.class);
            replyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, replyIntent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Action action = new NotificationCompat.Action
                    .Builder(R.drawable.ic_sms_black_24dp, "Reply"
                    , replyPendingIntent).addRemoteInput(remoteInput).build();

            builder.addAction(action);
        }
*/

       final int maxProgress = 100;
       int currentProgress = 0;

       builder.setProgress(maxProgress, currentProgress, false);

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


        Thread thread = new Thread() {
            @Override
            public void run() {
                int count = 0;

                while (count <= 100){
                    count = count + 10;
                    try {
                        sleep(1000);
                        builder.setProgress(maxProgress, count, false);
                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                builder.setContentText("Download Completed");
                builder.setProgress(0, 0, false);
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

            }
        };

        thread.start();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notifications";
            String des = "Include all notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(des);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
