package com.example.navdrawerdemo;

import android.content.Context;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.After;
;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest{
    private CatDAO catDao;
    private AppDatabase db;
    private CatObject cat;
    private CatObject cat2;
    private int lengthBefore=-1;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        catDao = db.catDAO();
        byte[] myvar = "Any String you want".getBytes();
        cat =  new CatObject("test", myvar);
        byte[] myvar2 = "Any String you want".getBytes();
        cat2 =  new CatObject("test2", myvar2);
        catDao.insertAll(cat);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }



    @Test
    public void navigationTest() {
        onView(withId(R.id.toolbarBurger)).perform(click());
        onView(withId(R.id.addToQuiz)).perform(click());
    }


    @Test
    public void updateScoreOnCorrectAns(){ // insert value edit check if score update
        MainActivity.getCurrentCat();
        String fasit =   MainActivity.getCurrentCat().getCatName();
        onView(withId(R.id.editText1)).perform((typeText(fasit)), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonAnswer)).perform(click());
        onView(withId(R.id.score)).check(matches(withText("Score: 1/1")));

    }
    /*
    @Test
    public void AddToList() throws Exception {
        catDao.insertAll(cat2);
        int amount= catDao.getAll().size();
        CatObject catRet= catDao.findByName(cat2.getCatName());
        assertThat(catRet.getCatName(), equalTo(cat2.getCatName()));
        assertTrue(Arrays.equals(catRet.getImageName(), cat2.getImageName()));
        assertTrue(2 == amount);
    }
    */

    @Test
    public void AddToList2() throws Exception {
        onView(withId(R.id.toolbarBurger)).perform(click());
        onView(withId(R.id.addToQuiz)).perform(click());
        onView(withId(R.id.catNameInput)).perform((typeText("Test")), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.addCatButton)).perform(click());
        assertNotNull(catDao.findByName("Test"));

    }

    @Test
    public void DeleteFromList() throws Exception {
        List<CatObject> catList=catDao.getAll();
        for(CatObject c : catList){
            catDao.delete(c);
        }
        int amount=catDao.getAll().size();
        assertTrue(amount==0);
    }

}