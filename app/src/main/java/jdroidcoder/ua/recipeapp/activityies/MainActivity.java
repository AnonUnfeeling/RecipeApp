package jdroidcoder.ua.recipeapp.activityies;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jdroidcoder.ua.recipeapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showTime(View view) {
        startActivity(new Intent(this, ListActivity.class).putExtra("howLoad", "times"));
    }

    public void showCategory(View view) {
        startActivity(new Intent(this, ListActivity.class).putExtra("howLoad", "category"));
    }

    public void showBrand(View view) {
        startActivity(new Intent(this, ListActivity.class).putExtra("howLoad", "brands"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.admin) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.alert_style, null, false);
            final EditText codeEditText = (EditText) view.findViewById(R.id.codeEditText);
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Admin panel")
                    .setView(view)
                    .create();
            Button login = (Button) view.findViewById(R.id.enter);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (codeEditText.getText().toString().equals("admin123admin")) {
                        startActivity(new Intent(MainActivity.this, AdminActivity.class));
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Code in not correct", Toast.LENGTH_LONG).show();
                    }
                }
            });
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
