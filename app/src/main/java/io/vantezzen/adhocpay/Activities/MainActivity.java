package io.vantezzen.adhocpay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.asap.android.apps.ASAPApplication;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.credits);
        text.setText(AdHocPayApplication.getInstance().getOwnerId());
    }

    public void onSendExample(View v) {
        AdHocPayApplication.getInstance().getASAPCommunication().transmit("Hey there!");
    }
}