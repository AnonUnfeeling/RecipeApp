package jdroidcoder.ua.recipeapp.activityies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jdroidcoder.ua.recipeapp.R;
import jdroidcoder.ua.recipeapp.models.RecipeModel;

/**
 * Created by jdroidcoder on 25.01.17.
 */
public class AdminActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Gson gson = new GsonBuilder().create();
    private RecipeModel recipeModel = new RecipeModel();
    private DatabaseReference root;
    private ArrayList<RecipeModel> models = new ArrayList<>();
    private boolean isEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        try {
            recipeModel = (RecipeModel) getIntent().getSerializableExtra("recipe");
            if (!recipeModel.getBrand().equals("") || !recipeModel.getFoodCategory().equals("") ||
                    !recipeModel.getTime().equals("") || !recipeModel.getFood().equals("") ||
                    !recipeModel.getIngredients().equals("") ||
                    !recipeModel.getMethods().equals("")) {
                isEdit = true;
                EditText brand = (EditText) findViewById(R.id.brandEditText);
                try {
                    brand.setText(recipeModel.getBrand());
                } catch (Exception e) {
                }
                EditText foodCategory = (EditText) findViewById(R.id.foodCategoryEditText);
                try {
                    foodCategory.setText(recipeModel.getFoodCategory());
                } catch (Exception e) {
                }
                EditText time = (EditText) findViewById(R.id.timeEditText);
                try {
                    time.setText(recipeModel.getTime());
                } catch (Exception e) {
                }
                EditText food = (EditText) findViewById(R.id.foodEditText);
                try {
                    food.setText(recipeModel.getFood());
                } catch (Exception e) {
                }
                EditText ingrs = (EditText) findViewById(R.id.ingredientsEditText);
                try {
                    ingrs.setText(recipeModel.getIngredients());
                } catch (Exception e) {
                }
                EditText methods = (EditText) findViewById(R.id.methodsEditText);
                try {
                    methods.setText(recipeModel.getMethods());
                } catch (Exception e) {
                }
            } else {
                recipeModel = new RecipeModel();
                isEdit = false;
            }
        } catch (Exception e) {
            recipeModel = new RecipeModel();
            isEdit = false;
        }

        root = database.getReference().child("recipe");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    models = new ArrayList<>(Arrays.asList(gson.fromJson(String.valueOf(
                            (dataSnapshot.getChildren().iterator().next()).getValue()), RecipeModel[].class)));
                } catch (java.util.NoSuchElementException e) {
                    models = new ArrayList<>();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveButton(View view) {
        if (isEdit) {
            for (int i = 0; i < models.size(); i++) {
                if(recipeModel.getCode().equals(models.get(i).getCode())){
                    models.remove(i);
                }
            }
        }
        recipeModel = new RecipeModel();
        recipeModel.setBrand(((EditText) findViewById(R.id.brandEditText)).getText().toString());
        recipeModel.setFoodCategory(((EditText) findViewById(R.id.foodCategoryEditText)).getText().toString());
        recipeModel.setTime(((EditText) findViewById(R.id.timeEditText)).getText().toString());
        recipeModel.setFood(((EditText) findViewById(R.id.foodEditText)).getText().toString());
        recipeModel.setIngredients(((EditText) findViewById(R.id.ingredientsEditText)).getText().toString());
        recipeModel.setMethods(((EditText) findViewById(R.id.methodsEditText)).getText().toString());
        recipeModel.setCode(UUID.randomUUID().toString());
        models.add(recipeModel);
        Map<String, Object> map = new HashMap<>();
        map.put("recipes", gson.toJson(models));
        root.updateChildren(map);

        Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
    }
}
