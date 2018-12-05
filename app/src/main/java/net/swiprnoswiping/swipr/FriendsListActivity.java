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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;

import android.widget.ListView;
import android.util.*;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FriendsListActivity extends AppCompatActivity{

    ListView friendsList;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    private ArrayList<String> friends;
    private static final String TAG = "FriendsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        friends = new ArrayList<String>(99);
        final String userEmail = user.getEmail();
        friendsList= (ListView)findViewById(R.id.friendList);
        CollectionReference friendsRef = db.collection("Friends");
        Query findAcceptedFriends = friendsRef.whereEqualTo("accepted", true);
        findAcceptedFriends.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {

                        try {
                            JSONObject jsonObject = new JSONObject(document.getData());
                            String user2 = jsonObject.getString("user2");
                            String user1 = jsonObject.getString("user1");
                            if(user1.equals(userEmail)){
                                friends.add(user2);
                            }
                            else if(user2.equals(userEmail)){
                                friends.add(user1);
                            }
                        }catch (JSONException j){
                            j.printStackTrace();
                        }
                    }
                    ListAdapter listAdapter = new ArrayAdapter<String>(FriendsListActivity.this, R.layout.friend_list_item, friends);
                    friendsList.setAdapter(listAdapter);
                } else {
                    Toast.makeText(FriendsListActivity.this, "Database failed.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void backButton(View view){
        Intent backIntent = new Intent(this, FriendActivity.class);
        startActivity(backIntent);
    }
}
