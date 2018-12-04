package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import java.util.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;

import android.widget.ListView;
import android.util.*;

import java.util.List;

public class manageFriendRequests extends AppCompatActivity {

    ListView friendsList;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    private ArrayList<String> friendRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friend_requests);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        // List of friend requests that will be displayed. Max is 50.
        friendRequests = new ArrayList<String>(50);

        final String userEmail = user.getEmail();

        // Getting all friends who have accepted friend requests.
        db.collection("Friends")
            .whereEqualTo("accepted", "false")
            //.whereArrayContains("user1", userEmail)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map friendUser = document.getData();
                        if (userEmail != friendUser.get("user1"))
                            friendRequests.add(friendUser.get("user1").toString());
                        if (userEmail != friendUser.get("user2"))
                            friendRequests.add(friendUser.get("user2").toString());
                    }
                }
                }
            });

        friendsList= (ListView)findViewById(R.id.requestList);

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, friendRequests);

        friendsList.setAdapter(listAdapter);
    }

    public void backButton(View view){
        Intent backIntent = new Intent(this, HomePage.class);
        startActivity(backIntent);
    }
}
