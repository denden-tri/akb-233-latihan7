package com.denztri.catviewer.api;

import com.denztri.catviewer.models.CatModels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Cat {
    @GET("images/search")
    Call<List<CatModels>> getCatList(@Header("x-api-key") String authorization,
                                     @Query("limit") int limit,
                                     @Query("page") int page,
                                     @Query("order") String order
    );
}
