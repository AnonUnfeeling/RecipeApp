package jdroidcoder.ua.recipeapp.activityies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jdroidcoder.ua.recipeapp.R;

/**
 * Created by jdroidcoder on 25.01.17.
 */
public class ListActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Gson gson = new GsonBuilder().create();
    private ListView listView;
    private ProgressDialog progressDialog;
    private String howLoad = "";
    private DatabaseReference root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        howLoad = getIntent().getStringExtra("howLoad");
        root = database.getReference().child(howLoad);
        listView = (ListView) findViewById(R.id.categoryListView);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String[] points = gson.fromJson(String.valueOf((dataSnapshot.getChildren().iterator().next()).getValue()), String[].class);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ListActivity.this,android.R.layout.simple_list_item_1,points);
                        listView.setAdapter(arrayAdapter);
                } catch (java.util.NoSuchElementException e) {
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog = ProgressDialog.show(this, "", "Loading...");
    }
}
