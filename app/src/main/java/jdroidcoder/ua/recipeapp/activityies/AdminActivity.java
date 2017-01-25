package jdroidcoder.ua.recipeapp.activityies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jdroidcoder.ua.recipeapp.R;

/**
 * Created by jdroidcoder on 25.01.17.
 */
public class AdminActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Gson gson = new GsonBuilder().create();
    private String howLoad = "";
    private String newValue;
    private DatabaseReference root;
    private ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().equals("Food Category")) {
                    howLoad = "category";
                } else if (spinner.getSelectedItem().equals("Time range")) {
                    howLoad = "times";
                } else if (spinner.getSelectedItem().equals("Brand")) {
                    howLoad = "brands";
                }
                root = database.getReference().child(howLoad);
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            strings = gson.fromJson(String.valueOf((dataSnapshot.getChildren().iterator().next()).getValue()), ArrayList.class);
                        } catch (java.util.NoSuchElementException e) {
                            strings = new ArrayList<>();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveButton(View view) {
        newValue = ((EditText) findViewById(R.id.editText)).getText().toString();
        strings.add(newValue);
        Map<String, Object> map = new HashMap<>();
        map.put(howLoad, gson.toJson(strings));
        root.updateChildren(map);
    }
}
