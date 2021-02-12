package com.example.navdrawerdemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatDAO {
    @Query("SELECT * FROM catobject")
    List<CatObject> getAll();

    @Query("SELECT * FROM catobject WHERE uid IN (:catobjectIds)")
    List<CatObject> loadAllByIds(int[] catobjectIds);

    @Query("SELECT * FROM catobject WHERE cat_name LIKE :cat AND " +
            "image_name LIKE :image LIMIT 1")
    CatObject findByNameAndArr(String cat, String image);

    @Query("SELECT * FROM catobject WHERE cat_name LIKE :cat ")
    CatObject findByName(String cat);

    @Insert
    void insertAll(CatObject... catObjects);

    @Delete
    void delete(CatObject catObject);

}
