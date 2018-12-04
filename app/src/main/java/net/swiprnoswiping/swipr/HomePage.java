package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import android.util.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.firestore.GeoPoint;

public class HomePage extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FloatingActionButton logOut = findViewById(R.id.logOutButton);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
                Intent logOutIntent = new Intent(HomePage.this, MessageActivity.class);
                startActivity(logOutIntent);
            }
        });
        FloatingActionButton friend = findViewById(R.id.friendButton);
        friend.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent(HomePage.this, FriendActivity.class);
                startActivity(logOutIntent);
            }
        });
    }

    Map<String, Object> request = new HashMap<>();

    public void addRequestToDatabase(double lng, double lat){
        Timestamp currentTime = Timestamp.now();

        GeoPoint receiverPos = new GeoPoint(lng, lat);
        GeoPoint swiperPos = new GeoPoint(0, 0);

        request.put("IsCompleted", false);
        request.put("Receiver", firebaseAuth.getCurrentUser().getUid());
        request.put("Swiper", "Swiper"); // Add this later
        request.put("qr_val", "QR_VAL"); // Add this later when the request has been accepted
        request.put("receiverPos", receiverPos); // Add this later
        request.put("request_time", currentTime);
        request.put("swiperPos", swiperPos); // Add this later as a GeoPoint
        db.collection("Requests").add(request).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference){
                Toast.makeText(HomePage.this, "Request successfully added.", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(HomePage.this, "Request failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestButton(View view){
        double longitude = 0;
        double latitude = 0;
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                }
            }
        });
        addRequestToDatabase(longitude, latitude);
        Intent requestIntent = new Intent(this, MapActivity.class);
        startActivity(requestIntent);
    }
}

