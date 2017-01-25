package jdroidcoder.ua.recipeapp.activityies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jdroidcoder.ua.recipeapp.R;
import jdroidcoder.ua.recipeapp.models.RecipeModel;

/**
 * Created by jdroidcoder on 25.01.17.
 */
public class ListActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Gson gson = new GsonBuilder().create();
    private ListView listView;
    private ProgressDialog progressDialog;
    private String howLoad = "";
    private List<RecipeModel> models = new ArrayList<>();
    private DatabaseReference root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        howLoad = getIntent().getStringExtra("howLoad");
        root = database.getReference().child("recipe");
        listView = (ListView) findViewById(R.id.categoryListView);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    models = Arrays.asList(gson.fromJson(String.valueOf(
                            (dataSnapshot.getChildren().iterator().next()).getValue()), RecipeModel[].class));
                    Set<String> points = new TreeSet<>();
                    for (RecipeModel recipe : models) {
                        try {
                            if ("times".equals(howLoad)) {
                                points.add(recipe.getTime());
                            } else if ("category".equals(howLoad)) {
                                points.add(recipe.getFoodCategory());
                            } else if ("brands".equals(howLoad)) {
                                points.add(recipe.getBrand());
                            }
                        }catch (Exception e){}
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ListActivity.this,
                            android.R.layout.simple_list_item_1, new ArrayList<>(points));
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(ListActivity.this,RecipeActivity.class).putExtra("recipe",models.get(position)));
                        }
                    });
                } catch (java.util.NoSuchElementException e) {
                    models = new ArrayList<>();
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
