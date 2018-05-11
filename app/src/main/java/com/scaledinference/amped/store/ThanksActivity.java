package com.scaledinference.amped.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.scaledinference.amped.store.model.UserSession;

public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(UserSession.getInstance().getSession().getTheme());
        setContentView(R.layout.thanks_activity);
    }

    public void startOver(View view) {
        UserSession.getInstance().endSession();

        Intent intent = new Intent(this, StoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
