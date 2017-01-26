package jdroidcoder.ua.recipeapp.activityies;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import jdroidcoder.ua.recipeapp.Manifest;
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
    private String encodedImage;
    private ImageView imageView;
    private Integer idIngr = 100;
    private Integer idMerhods = 0;
    private Integer idCategory = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }
        imageView = (ImageView) findViewById(R.id.imageView);
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
                EditText name = (EditText) findViewById(R.id.nameEditText);
                try {
                    name.setText(recipeModel.getName());
                } catch (Exception e) {
                }
                EditText foodCategory = (EditText) findViewById(R.id.foodCategoryEditText);
                try {
                    String[] categoies = recipeModel.getFoodCategory();
                    foodCategory.setText(categoies[0]);
                    for (int i = 1; i < categoies.length; i++) {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addCategoryLayout);
                        EditText editText = new EditText(this);
                        editText.setId(idCategory);
                        editText.setText(categoies[i]);
                        idCategory++;
                        editText.setLayoutParams((findViewById(R.id.foodCategoryEditText)).getLayoutParams());
                        linearLayout.addView(editText);
                    }
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
                    String[] ings = recipeModel.getIngredients();
                    ingrs.setText(ings[0]);
                    for (int i = 1; i < ings.length; i++) {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addIngredientsLayout);
                        EditText editText = new EditText(this);
                        editText.setId(idIngr);
                        editText.setText(ings[i]);
                        idIngr++;
                        editText.setLayoutParams((findViewById(R.id.ingredientsEditText)).getLayoutParams());
                        linearLayout.addView(editText);
                    }
                } catch (Exception e) {
                }
                EditText methods = (EditText) findViewById(R.id.methodsEditText);
                try {
                    String[] mtds = recipeModel.getMethods();
                    methods.setText(mtds[0]);
                    for (int i = 1; i < mtds.length; i++) {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addMethodsLayout);
                        EditText editText = new EditText(this);
                        editText.setId(idMerhods);
                        editText.setText(mtds[i]);
                        idMerhods++;
                        editText.setLayoutParams((findViewById(R.id.methodsEditText)).getLayoutParams());
                        linearLayout.addView(editText);
                    }
                } catch (Exception e) {
                }
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
                    }
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
                } catch (NoSuchElementException e) {
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
                if (recipeModel.getCode().equals(models.get(i).getCode())) {
                    models.remove(i);
                }
            }
            encodedImage = recipeModel.getImage();
        }
        String[] methods = new String[idMerhods + 1];
        methods[0] = ((EditText) findViewById(R.id.methodsEditText)).getText().toString();
        if (idMerhods != 0) {
            for (int i = 0; i < idMerhods; i++) {
                EditText method = (EditText) findViewById(i);
                methods[i + 1] = method.getText().toString();
            }
        }
        int size = idIngr - 100;
        int counter = 0;
        String[] ingrs = new String[size + 1];
        ingrs[counter] = ((EditText) findViewById(R.id.ingredientsEditText)).getText().toString();
        if (idIngr != 0) {
            for (int i = 100; i < idIngr; i++) {
                EditText ingr = (EditText) findViewById(i);
                ingrs[(i - 100) + 1] = ingr.getText().toString();
            }
        }
        int sizeCategory = idCategory - 1000;
        int counterCategory = 0;
        String[] categoies = new String[sizeCategory + 1];
        categoies[counterCategory] = ((EditText) findViewById(R.id.foodCategoryEditText)).getText().toString();
        if (idIngr != 0) {
            for (int i = 1000; i < idCategory; i++) {
                EditText category = (EditText) findViewById(i);
                categoies[(i - 1000) + 1] = category.getText().toString();
            }
        }
        recipeModel = new RecipeModel();
        recipeModel.setBrand(((EditText) findViewById(R.id.brandEditText)).getText().toString());
        recipeModel.setFoodCategory(categoies);
        recipeModel.setTime(((EditText) findViewById(R.id.timeEditText)).getText().toString());
        recipeModel.setFood(((EditText) findViewById(R.id.foodEditText)).getText().toString());
        recipeModel.setIngredients(ingrs);
        recipeModel.setMethods(methods);
        recipeModel.setCode(UUID.randomUUID().toString());
        recipeModel.setImage(encodedImage);
        recipeModel.setName(((EditText) findViewById(R.id.nameEditText)).getText().toString());
        models.add(recipeModel);
        Map<String, Object> map = new HashMap<>();
        map.put("recipes", gson.toJson(models));
        root.updateChildren(map);

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        if(isEdit){
            startActivity(new Intent(AdminActivity.this, RecipeActivity.class).putExtra("recipe", recipeModel));
            finish();
        }else {
            finish();
        }
    }

    public void loadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            InputStream imageStream = null;
            try {
                Uri selectedImage = data.getData();
                imageStream = getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (width * 0.4f),
                    (int) (height * 0.4f));
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(0);
            imageView.setImageBitmap(bmp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    public void addMethod(View view) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addMethodsLayout);
        EditText editText = new EditText(this);
        editText.setId(idMerhods);
        idMerhods++;
        editText.setLayoutParams((findViewById(R.id.methodsEditText)).getLayoutParams());
        linearLayout.addView(editText);
    }

    public void addIngredient(View view) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addIngredientsLayout);
        EditText editText = new EditText(this);
        editText.setId(idIngr);
        idIngr++;
        editText.setLayoutParams((findViewById(R.id.ingredientsEditText)).getLayoutParams());
        linearLayout.addView(editText);
    }

    public void addCategory(View view) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addCategoryLayout);
        EditText editText = new EditText(this);
        editText.setId(idCategory);
        idCategory++;
        editText.setLayoutParams((findViewById(R.id.foodCategoryEditText)).getLayoutParams());
        linearLayout.addView(editText);
    }
}