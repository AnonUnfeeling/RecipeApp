package jdroidcoder.ua.recipeapp.activityies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import jdroidcoder.ua.recipeapp.R;
import jdroidcoder.ua.recipeapp.models.RecipeModel;

/**
 * Created by jdroidcoder on 26.01.17.
 */

public class RecipeisActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<RecipeModel> models = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.categoryListView);
        String what = "";
        String param = "";
        try {
            what = getIntent().getStringExtra("what");
        } catch (Exception e) {
            try {
                what = getIntent().getStringExtra("what");
            } catch (Exception ex) {
                what = getIntent().getStringExtra("what");
            }
        }
        try {
            param = getIntent().getStringExtra("param");
        } catch (Exception e) {
            param = "";
        }
        try {
            models = (ArrayList<RecipeModel>) getIntent().getSerializableExtra("data");
        } catch (Exception e) {
            models = new ArrayList<>();
        }
        ArrayList<String> strings = new ArrayList<>();
        if ("times".equals(what)) {
            for (RecipeModel recipeModel : models) {
                if (recipeModel.getTime().equals(param)) {
                    strings.add(recipeModel.getName());
                }
            }
        } else if ("category".equals(what)) {
            for (RecipeModel recipeModel : models) {
                if (searchByCategory(recipeModel, param)) {
                    strings.add(recipeModel.getName());
                }
            }
        } else if ("brands".equals(what)) {
            for (RecipeModel recipeModel : models) {
                if (recipeModel.getBrand().equals(param)) {
                    strings.add(recipeModel.getName());
                }
            }
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(RecipeisActivity.this, RecipeActivity.class).putExtra("recipe", models.get(position)));
                finish();
            }
        });
    }

    private boolean searchByCategory(RecipeModel recipeModel, String param) {
        for (String cat : recipeModel.getFoodCategory()) {
            if (cat.equals(param)) {
                return true;
            }
        }
        return false;
    }
}
