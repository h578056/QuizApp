package com.example.navdrawerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public List<CatObject> catList;
    public String scoreTxt = "Score";
    public CatObject currentCat;
    public int correctAns = 0;
    public int totalAns = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        catList = new ArrayList<>();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO = db.catDAO();
        catList = catDAO.getAll();

        if (catList.size() == 0) {
           // Drawable drawable= getResources().getDrawable(R.drawable.siameser_cat_cart); // converts image from drawable folder to drawable object
            catList.add(new CatObject("siameser cat",getResources().getDrawable( R.drawable.siameser_cat_cart))); //converts image from dravable folder to drawable object and puts im constructor
            catList.add(new CatObject("bengal cat", getResources().getDrawable(R.drawable.bengal_icon)));
            catList.add(new CatObject("persian cat", getResources().getDrawable(R.drawable.persian_icon)));
            for(CatObject cat : catList){
                catDAO.insertAll(cat);

            }
            System.out.println(catList.size()+ "---------------------------------------------------------");
            catList = catDAO.getAll();
        } else {
            catList = catDAO.getAll();
        }

        getRandomImage();
    }

    private void getRandomImage() {
        ImageView mImageView = (ImageView) findViewById(R.id.myImageView);
        CatObject catRandObj = getRandomElement(catList);
        currentCat = catRandObj;
        Bitmap bitmap = catRandObj.getImageNameBitmap(); //get the bitmap not byte array
        BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
        mImageView.setBackground(ob);
    }

    public CatObject getRandomElement(List<CatObject> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public void clickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer((GravityCompat.START));
    }

    public void clickLogo(View view) {
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //close drawer if open
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickHome(View view) {
        //recreate activity
        recreate();
    }

    public void clickDashBoard(View view) {
        //redirect to dashboard
        redirectActivity(this, AddToQuiz.class);
    }

    public void clickAboutUs(View view) {
        redirectActivity(this, InfoQuizContent.class);
    }

    public void clickLogout(View view) {
        logout(this);
    }

    public static void logout(Activity activity) {
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit app?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dont exit
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }

    public void answerQuestion(View view) {
        //if correct or not
        final EditText editText1 = findViewById(R.id.editText1);
        String myString = editText1.getText().toString();
        if (myString != null) {
            myString = myString.toLowerCase();
        }

        totalAns = totalAns + 1;
        if (myString.equals(currentCat.getCatName())) {
            correctAns = correctAns + 1;
            increaseScore();
        } else {
            showAns();
        }
        increaseScore();
        getRandomImage();
    }

    public void increaseScore() {
        TextView displayInteger = (TextView) findViewById(
                R.id.score);
        displayInteger.setText("Score: " + correctAns + "/" + totalAns);
    }

    public void showAns() {
        TextView displayInteger = (TextView) findViewById(
                R.id.answer);
        displayInteger.setText("Correct answer: " + currentCat.getCatName());
    }
}