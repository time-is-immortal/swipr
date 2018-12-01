package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FloatingActionButton logOut = findViewById(R.id.logOutButton);
        logOut.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent(HomePage.this, Test.class);
                startActivity(logOutIntent);
            }
        });
        FloatingActionButton message = findViewById(R.id.messageButton);
        message.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent(HomePage.this, FriendActivity.class);
                startActivity(logOutIntent);
            }
        });
        FloatingActionButton friend = findViewById(R.id.friendButton);
        friend.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent(HomePage.this, MessageActivity.class);
                startActivity(logOutIntent);
            }
        });
    }

    public void requestButton(View view){
        Intent requestIntent = new Intent(this, MapActivity.class);
        startActivity(requestIntent);
    }
}

