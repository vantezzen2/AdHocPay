package io.vantezzen.adhocpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.asap.android.apps.ASAPApplication;

public class MainActivity extends ASAPActivity {

    public MainActivity() {
        super(AdHocPayApplication.getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdHocPayApplication.getInstance().getASAPCommunication().startASAP();

        TextView text = findViewById(R.id.credits);
        text.setText(AdHocPayApplication.getInstance().getOwnerId());
    }

    public void onSendExample(View v) {
        AdHocPayApplication.getInstance().getASAPCommunication().transmit("Hey there!");
    }
}