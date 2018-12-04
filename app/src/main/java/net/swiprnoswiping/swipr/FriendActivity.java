package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
    }

    public void friendsList(View view){
        Intent fLIntent = new Intent(this, FriendsListActivity.class);
        startActivity(fLIntent);
    }

    public void manageFriendRequests(View view){
        Intent manageIntent = new Intent(this, manageFriendRequests.class);
        startActivity(manageIntent);
    }

    public void backButton(View view){
        Intent backIntent = new Intent(this, HomePage.class);
        startActivity(backIntent);
    }
}
