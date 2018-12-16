package com.tcptutor.atgasgn;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api {
    public static String BASE_URL = "https://api.flickr.com/services/";


        @GET("rest/?")
        Call<Mainphoto> getPhoto (@Query("method") String method, @Query("api_key") String api_key,
            @Query("format") String format, @Query("nojsoncallback") String
        nojsoncallback, @Query("extras") String extras, @Query("text") String text);


}
