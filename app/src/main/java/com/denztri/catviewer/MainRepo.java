package com.denztri.catviewer;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.denztri.catviewer.db.AppDatabase;
import com.denztri.catviewer.db.CatDao;
import com.denztri.catviewer.models.CatModels;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainRepo {
    public CatDao catDao;
    public List<CatModels> allGallery;
    private final Executor executors = Executors.newSingleThreadExecutor();
    android.os.Handler handler = new Handler(Looper.getMainLooper());

    public MainRepo(Application application) {
        executors.execute(() -> {
            AppDatabase db = AppDatabase.getDbInstance(application);
            catDao = db.catDao();
            allGallery = catDao.getAllCats();
        });


    }

    public void insert(List<CatModels> gallery){
        executors.execute(() -> catDao.insert(gallery));
    }

    public void deleteAll(){
        catDao.deleteAll();
    }

    public List<CatModels> getAll(){
        return  catDao.getAllCats();
    }

    public int checkDb(){
        return catDao.checkDb();
    }
}
