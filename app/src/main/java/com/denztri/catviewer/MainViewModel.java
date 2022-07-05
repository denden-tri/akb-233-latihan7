package com.denztri.catviewer;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.denztri.catviewer.api.Cat;
import com.denztri.catviewer.api.ServiceGenerator;
import com.denztri.catviewer.db.CatDao;
import com.denztri.catviewer.models.CatModels;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<CatModels>> catList;
    private final MainRepo mainRepo;
    private final Executor executor = Executors.newSingleThreadExecutor();
    android.os.Handler handler = new Handler(Looper.getMainLooper());

    private int PAGE = 0;
    private int LIMIT = 12;
    private String ORDER = "Rand";

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainRepo(application);
    }

    private void loadCatDb() {
        executor.execute(() -> {
            List<CatModels> gall = mainRepo.getAll();
            handler.post(() -> catList.setValue(gall));
        });
    }

    public LiveData<List<CatModels>> getList() throws IOException {
        if (catList == null) catList = new MutableLiveData<>();
        executor.execute(() -> {
            if (mainRepo.checkDb() != 0) {
                loadCatDb();
            } else {
                loadCatList();
            }
        });

        return catList;
    }

    public void loadCatList() {
        Cat service = ServiceGenerator.createService(Cat.class);
        String API_KEY = BuildConfig.API_KEY;
        service.getCatList(API_KEY,this.LIMIT,this.PAGE,this.ORDER).enqueue(new Callback<List<CatModels>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatModels>> call, @NonNull Response<List<CatModels>> response) {
                catList.setValue(response.body());
                Executors.newSingleThreadExecutor().execute(() -> mainRepo.insert(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<CatModels>> call, @NonNull Throwable t) {

            }
        });
    }

    public void deleteGallery(){
        mainRepo.deleteAll();
    }

    public int getPAGE() {
        return PAGE;
    }

    public void setPAGE(int PAGE) {
        this.PAGE = PAGE;
    }

    public int getLIMIT() {
        return LIMIT;
    }

    public void setLIMIT(int LIMIT) {
        this.LIMIT = LIMIT;
    }

    public String getORDER() {
        return ORDER;
    }

    public void setORDER(String ORDER) {
        this.ORDER = ORDER;
    }
}
