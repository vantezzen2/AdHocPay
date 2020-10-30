package io.vantezzen.adhocpay.activities;

import android.os.Bundle;

import net.sharksystem.asap.android.apps.ASAPActivity;

import io.vantezzen.adhocpay.AdHocPayApplication;

public abstract class BaseActivity extends ASAPActivity {
    public BaseActivity() {
        super(AdHocPayApplication.getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdHocPayApplication.getInstance().getASAPCommunication().startASAP();
    }

    /**
     * React to changes in transaction data
     */
    abstract public void onDataChange();
}
