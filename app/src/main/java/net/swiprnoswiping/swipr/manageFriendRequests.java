package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class manageFriendRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friend_requests);
    }

    public void backButton(View view){
        Intent backIntent = new Intent(this, HomePage.class);
        startActivity(backIntent);
    }
}
