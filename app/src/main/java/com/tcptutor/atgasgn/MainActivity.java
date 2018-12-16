package com.tcptutor.atgasgn;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String method="";
    String api_key="";
    String format="";
    String nojsoncallback="";
    String extras="";
    String text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.PhotoListRecycler);
        method = "flickr.photos.getRecent";
        api_key = "6f102c62f41998d151e5a1b48713cf13";
        format = "json";
        nojsoncallback = "1";
        extras = "url_s";
        text = "";

        getPhoto(method,text);
        Toast.makeText(MainActivity.this, "Displaying Recent images...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                recent_search("flickr.photos.search",s);
                getPhoto(method,s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "Type your search item.", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                recent_search("flickr.photos.getRecent","");
                getPhoto(method,"");
                Toast.makeText(MainActivity.this, "Back to Recent images...", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.infoBtn) {
            showDialog(this,"About",getString(R.string.aboutmsg));
        }
        return super.onOptionsItemSelected(item);
    }

    private void get Photo(String m ,String ss) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        api api = retrofit.create(api.class);

        Call<Mainphoto> call = api.getPhoto(method,api_key,format,nojsoncallback,extras,ss);

        call.enqueue(new Callback<Mainphoto>() {
            @Override
            public  void onResponse(Call<Mainphoto> call, Response<Mainphoto> response) {


                Mainphoto photoList = response.body();
                if(photoList!=null)
                {
                    String[] photoId = new String[photoList.getPhotos().getPhoto().size()];
                    String[] photoUrl = new String[photoList.getPhotos().getPhoto().size()];
                    String[] photoTitle = new String[photoList.getPhotos().getPhoto().size()];

                    for (int i = 0; i < photoList.getPhotos().getPhoto().size(); i++) {
                        photoId[i] = photoList.getPhotos().getPhoto().get(i).getId();
                        photoUrl[i] = photoList.getPhotos().getPhoto().get(i).getUrlS();
                        photoTitle[i] = photoList.getPhotos().getPhoto().get(i).getTitle();
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new PhotoAdaptor(photoId, photoUrl, photoTitle));

                }
            }

            @Override
            public void onFailure(Call<Mainphoto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recent_search(String method1, String text1)
    {
        method=method1;
        text=text1;
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("CLOSE", null);
        //builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
