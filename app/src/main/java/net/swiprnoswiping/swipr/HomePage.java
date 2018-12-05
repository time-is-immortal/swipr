package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import android.util.*;
//import android.R;
import android.Manifest;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.lang.Object;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.CollectionReference;

public class HomePage extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private FusedLocationProviderClient mFusedLocationClient;
    String idToken;
    int docCount = 0;

    Map<String, Object> request = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FloatingActionButton logOut = findViewById(R.id.logOutButton);
        int MY_PERMISSIONS_LOCATION = 99;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_LOCATION);

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
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            request.put("token", idToken);
                            db.collection("Notifications").add(request);
                        } else {
                            Toast.makeText(HomePage.this, "Failed to acquire token.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    public void addRequestToDatabase(){
        final double longitude = 0;
        final double latitude = 0;
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
        final CollectionReference dbRef = db.collection("Requests");
        Query query = dbRef.whereEqualTo("Receiver",firebaseAuth.getCurrentUser().getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot document : task.getResult()) {
                        docCount += 1;
                    }
                    if(docCount > 0){
                        for (DocumentSnapshot document : task.getResult()){
                            dbRef.document(document.getId()).delete();
                        }
                        InputData(longitude, latitude);
                    }
                    else{
                        InputData(longitude, latitude);
                    }
                } else {
                    Toast.makeText(HomePage.this, "Database failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        docCount = 0;
    }
    public void InputData(double lng, double lat){
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
        request.put("token", idToken);
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
        addRequestToDatabase();
        Intent requestIntent = new Intent(this, MapActivity.class);
        startActivity(requestIntent);
    }



}

