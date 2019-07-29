package com.arun.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.os.Bundle;
import android.widget.TextView;

public class AutoReplyActivity extends AppCompatActivity {

    TextView txtReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_reply);
        txtReply = findViewById(R.id.txtReply);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            Bundle remoteReply = RemoteInput.getResultsFromIntent(getIntent());

            if (remoteReply != null) {
                String message = remoteReply.getCharSequence(MainActivity.TXT_REPLY).toString();
                txtReply.setText(message);
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(MainActivity.NOTIFICATION_ID);

        }

    }
}
