package com.denztri.catviewer.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denztri.catviewer.models.CatModels;

import java.util.List;

@Dao
public interface CatDao {
    @Query("SELECT * FROM CatModels")
    List<CatModels> getAllCats();

    @Insert
    void insert(List<CatModels> lists);

    @Query("SELECT COUNT(id) FROM CatModels")
    int checkDb();

    @Query("DELETE FROM CatModels")
    void deleteAll();
}
