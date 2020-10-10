package io.vantezzen.adhocpay.Activities;

import android.os.Bundle;

import net.sharksystem.asap.android.apps.ASAPActivity;

import io.vantezzen.adhocpay.AdHocPayApplication;

public class BaseActivity extends ASAPActivity {
    public BaseActivity() {
        super(AdHocPayApplication.getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdHocPayApplication.getInstance().getASAPCommunication().startASAP();
    }
}
