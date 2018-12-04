package net.swiprnoswiping.swipr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import android.widget.ListView;

public class FriendsListActivity extends AppCompatActivity{

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
    ListView friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        friendsList= (ListView)findViewById(R.id.friendList);



    }
}
