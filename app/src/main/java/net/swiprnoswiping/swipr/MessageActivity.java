package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    public void requestButton(View view){
        Intent requestIntent = new Intent(this, MapActivity.class);
        startActivity(requestIntent);
    }

    public void backButton(View view){
        Intent backIntent = new Intent(this, HomePage.class);
        startActivity(backIntent);
    }
}
