package com.example.navdrawerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InfoQuizContent extends AppCompatActivity {
    DrawerLayout drawerLayout;
    List<CatObject> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        drawerLayout = findViewById(R.id.drawer_layout);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO = db.catDAO();
        catList = new ArrayList<>();
        catList = catDAO.getAll();

        dynamicTable();
    }

    public void clickMenu(View view) {
        //open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void clickLogo(View view) {
        //close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void clickHome(View view) {
        //redirect home
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void clickDashBoard(View view) {
        //redirect activity to dashboard
        MainActivity.redirectActivity(this, AddToQuiz.class);
    }

    public void clickAboutUs(View view) {
        //recreate activyty
        recreate();
    }

    public void clickLogout(View view) {
        //close app
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void dynamicTable() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO = db.catDAO();

        TableLayout table = (TableLayout) findViewById(R.id.table_main);
        for (int i = 0; i < catList.size(); i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            ImageView iView = new ImageView(this);
            iView.setImageBitmap(catList.get(i).getImageNameBitmap());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(100, 80);
            iView.setLayoutParams(layoutParams);
            TextView text = new TextView(this);
            text.setText(catList.get(i).getCatName());
            //       text.setGravity(Gravity.CENTER);

            final int i2 = i;

            Button btn = new Button(this);
            btn.setId(i);
            btn.setText("DELETE");
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    table.removeView(tr);
                    catDAO.delete(catList.get(i2));
                }
            });
            tr.addView(iView);
            tr.addView(text);
            tr.addView(btn);

            table.addView(tr);
        }
    }
}