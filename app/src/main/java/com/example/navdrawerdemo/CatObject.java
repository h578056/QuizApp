package com.example.navdrawerdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;

@Entity
public class CatObject {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "uid")
    public int uid=0;

    @ColumnInfo(name = "cat_name")
    private String catName;

    @ColumnInfo(name = "image_name")
    private byte[] imageName;


    public CatObject(String catName, byte[] imageName) {
        this.catName = catName;
       this.imageName = imageName;
    }
    public CatObject(String catName, Drawable d) {
        this.catName = catName;
        this.imageName = imgIntToByte(d);
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
    public byte[] getImageName() {
        return imageName;
    }

    public Bitmap getImageNameBitmap() {
        return convertByteArrToBitmap(imageName);
    }

    public void setImageName(byte[] imageName) {
        this.imageName = (imageName);
    }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    private byte[] imgIntToByte(Drawable d){ //converts image int code from r.drawable to byte array
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream opstream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, opstream);
        byte[] byteArray = opstream.toByteArray();
        return byteArray;
    }
    private Bitmap convertByteArrToBitmap(byte[] byteArrImg) { // converts byte array to bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrImg, 0, byteArrImg.length);
        return bitmap;
    }
}
