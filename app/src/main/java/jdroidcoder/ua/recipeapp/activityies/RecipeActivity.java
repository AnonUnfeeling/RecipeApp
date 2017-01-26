package jdroidcoder.ua.recipeapp.activityies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import jdroidcoder.ua.recipeapp.R;
import jdroidcoder.ua.recipeapp.models.RecipeModel;

/**
 * Created by jdroidcoder on 25.01.17.
 */

public class RecipeActivity extends AppCompatActivity {
    private RecipeModel recipeModel;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeModel = (RecipeModel) getIntent().getSerializableExtra("recipe");

        TextView brand = (TextView) findViewById(R.id.brandEditText);
        try {
            if (!recipeModel.getBrand().equals(""))
                brand.setText(recipeModel.getBrand());
            else throw new Exception();
        } catch (Exception e) {
            brand.setVisibility(View.GONE);
        }
        TextView name = (TextView) findViewById(R.id.nameEditText);
        try {
            if (!recipeModel.getName().equals(""))
                name.setText(recipeModel.getName());
            else throw new Exception();
        } catch (Exception e) {
            name.setVisibility(View.GONE);
        }
        TextView foodCategory = (TextView) findViewById(R.id.foodCategoryEditText);
        try {
            if (!recipeModel.getFoodCategory().equals("")) {
                String cat = "";
                for (int i = 0; i < recipeModel.getFoodCategory().length; i++) {
                    if (i != recipeModel.getFoodCategory().length - 1) {
                        if (!recipeModel.getFoodCategory()[i].equals(""))
                            cat += (i + 1) + ". " + recipeModel.getFoodCategory()[i] + "\n";
                    } else {
                        if (!recipeModel.getFoodCategory()[i].equals(""))
                            cat += (i + 1) + ". " + recipeModel.getFoodCategory()[i];
                    }
                }
                foodCategory.setText(cat);
            } else throw new Exception();
        } catch (Exception e) {
            foodCategory.setVisibility(View.GONE);
        }
        TextView time = (TextView) findViewById(R.id.timeEditText);
        try {
            if (!recipeModel.getTime().equals(""))
                time.setText(recipeModel.getTime());
            else throw new Exception();
        } catch (Exception e) {
            time.setVisibility(View.GONE);
        }
        TextView food = (TextView) findViewById(R.id.foodEditText);
        try {
            if (!recipeModel.getFood().equals(""))
                food.setText(recipeModel.getFood());
            else throw new Exception();
        } catch (Exception e) {
            food.setVisibility(View.GONE);
        }
        TextView ingrs = (TextView) findViewById(R.id.ingredientsEditText);
        try {
            if (recipeModel.getIngredients().length != 0) {
                String ings = "";
                for (int i = 0; i < recipeModel.getIngredients().length; i++) {
                    if (i < recipeModel.getIngredients().length - 1) {
                        if (!recipeModel.getIngredients()[i].equals(""))
                            ings += (i + 1) + ". " + recipeModel.getIngredients()[i] + "\n";
                    } else {
                        if (!recipeModel.getIngredients()[i].equals(""))
                            ings += (i + 1) + ". " + recipeModel.getIngredients()[i];
                    }
                }
                ingrs.setText(ings);
            } else throw new Exception();
        } catch (Exception e) {
            System.out.println("ing error" + e.getMessage());
            ingrs.setVisibility(View.GONE);
        }
        TextView methods = (TextView) findViewById(R.id.methodsEditText);
        try {
            if (recipeModel.getMethods().length != 0) {
                String mtds = "";
                for (int i = 0; i < recipeModel.getMethods().length; i++) {
                    if (i < recipeModel.getMethods().length - 1)
                        if (!recipeModel.getMethods()[i].equals(""))
                            mtds += (i + 1) + ". " + recipeModel.getMethods()[i] + "\n";
                        else if (!recipeModel.getMethods()[i].equals(""))
                            mtds += (i + 1) + ". " + recipeModel.getMethods()[i];
                }
                methods.setText(mtds);
            } else throw new Exception();
        } catch (Exception e) {
            methods.setVisibility(View.GONE);
        }
        imageView = (ImageView) findViewById(R.id.imageView);
        try {
            if (!recipeModel.getImage().equals("")) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                int height = displayMetrics.heightPixels;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (width * 0.4f),
                        (int) (height * 0.4f));
                imageView.setLayoutParams(layoutParams);
                imageView.setBackgroundResource(0);
                byte[] decodedString = Base64.decode(recipeModel.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            } else throw new Exception();
        } catch (Exception e) {
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.alert_style, null, false);
            final EditText codeEditText = (EditText) view.findViewById(R.id.codeEditText);
            final AlertDialog alertDialog = new AlertDialog.Builder(RecipeActivity.this)
                    .setTitle("Admin panel")
                    .setView(view)
                    .create();
            Button login = (Button) view.findViewById(R.id.enter);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (codeEditText.getText().toString().equals("admin123admin")) {
                        startActivity(new Intent(RecipeActivity.this, AdminActivity.class).putExtra("recipe", recipeModel));
                        alertDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(RecipeActivity.this, "Code in not correct", Toast.LENGTH_LONG).show();
                    }
                }
            });
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
