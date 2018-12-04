package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
    public void cancelButton(View view){
        Intent cancelIntent = new Intent(this, HomePage.class);
        startActivity(cancelIntent);
    }
    public void scanButton(View view){
        Intent scanIntent = new Intent(this, ScanActivity.class);
        startActivity(scanIntent);
    }

    public void genQR(View view){
        Intent genIntent = new Intent(this, QRActivity.class);
        startActivity(genIntent);
    }

    public void mapButton(View view){
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }
}
