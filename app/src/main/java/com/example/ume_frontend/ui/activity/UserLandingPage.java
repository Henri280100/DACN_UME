package com.example.ume_frontend.ui.activity;

import static com.example.ume_frontend.Retrofit.RetrofitClient.getRetrofit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ume_frontend.API.ApiMethod;
import com.example.ume_frontend.Adapter.AdapterFriends;
import com.example.ume_frontend.Model.ListUser;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;
import com.example.ume_frontend.ui.Fragment.FriendFragment;
import com.example.ume_frontend.ui.Fragment.HomeFragment;
import com.example.ume_frontend.ui.Fragment.ProfileFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserLandingPage extends AppCompatActivity {

    //    private static final String TAG = "UserLandingPage";
    ChipNavigationBar bottomNavi;
    //    View navHeader;
//    private ImageView imgProfile;
//    Toolbar toolbar;
    private Handler handler;
    //    private ActionBarDrawerToggle drawerToggle;
//    private Uri filePath;
//    TextView txtsearch;
//    ImageView imgfind;
    androidx.appcompat.widget.SearchView searchView;
    public RecyclerView recyclerchatFriends;
    FirebaseStorage storage;
    StorageReference storageReference;
    public static UserModel.Data account1;
    int idUser;
    List<ListUser.Data> data;
    private AdapterFriends friendsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing_page);
        account1 = MainActivity.myAccount;
        searchView = findViewById(R.id.search);
//        idUser = Integer.parseInt(account1.getIdUser());
//        recyclerchatFriends = findViewById(R.id.recyclerchatFriends);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerchatFriends.setLayoutManager(linearLayoutManager);


//        imgfind = findViewById(R.id.imgfind);
//        imgfind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(txtsearch.getText().toString().equals(""))
//                {
//                    txtsearch.setError("chưa nhập");
//                    txtsearch.requestFocus();
//                }
//                else
//
//                    getListUser();
//            }
//        });

//        txtsearch = findViewById(R.id.search);
        // get the FB storage reference
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        drawerLayout = findViewById(R.id.drawer);
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
        handler = new Handler();
        // Set a bottom navigation
        bottomNavi = findViewById(R.id.bottom_navi);

        bottomNaviItemSelected();

        // This should be started at the home layout as the beginning.
        if (savedInstanceState == null) {
            bottomNavi.setItemSelected(R.id.nav_home, true);

        }


        ///// IMAGE PROFILE
//        imgProfile = navHeader.findViewById(R.id.img_profile);
//        imgProfile.setOnClickListener(v -> {
//            mGetContent.launch("image/*");
//            choosePicture();
//        });
    }


    private void bottomNaviItemSelected() {
        bottomNavi.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                switch (i) {
                    case R.id.nav_home:
                        HomeFragment homeFragment = new HomeFragment();
                        searchView.setVisibility(View.VISIBLE);
                        bundle.putSerializable("data", account1);

                        homeFragment.setArguments(bundle);
                        fragmentTransaction
                                .replace(R.id.frame_layout, homeFragment);
                        break;
                    case R.id.nav_friend:
                        FriendFragment friendFragment = new FriendFragment();
                        searchView.setVisibility(View.GONE);

                        bundle.putSerializable("data", account1);
                        friendFragment.setArguments(bundle);

                        friendFragment.setArguments(bundle);
                        fragmentTransaction
                                .replace(R.id.frame_layout, friendFragment);

                        break;
                    case R.id.nav_profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        searchView.setVisibility(View.GONE);

                        bundle.putSerializable("data", account1);
                        profileFragment.setArguments(bundle);

                        profileFragment.setArguments(bundle);
                        fragmentTransaction
                                .replace(R.id.frame_layout, profileFragment);
                        break;
                }
                fragmentTransaction.commit();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_friends, menu);
        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.onActionViewExpanded();// new Added line
        searchView.setQueryHint("Search here");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchView.toString().equals("")) {
                    Toast.makeText(UserLandingPage.this, "Cannot find user", Toast.LENGTH_SHORT).show();
                    searchView.requestFocus();
                } else {
//                    getListUser();
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        if (id == R.id.nav_home) {
////            startActivity(new Intent(this, Main.class).putExtra("data", account));
//            Intent intent = new Intent(this, UserLandingPage.class);
//            intent.putExtra("data", account1);
//            startActivity(intent);
//        } else if (id == R.id.nav_profile) {
//            Intent intent = new Intent(this, ProfileActivity.class);
//            intent.putExtra("data", account1);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_friend) {
//            Intent intent = new Intent(this, FriendsActivity.class);
//            intent.putExtra("data", account1);
//            startActivity(intent);
//        }
////        else if (id == R.id.nav_settings) {
////            startActivity(new Intent(this, SettingsActivity.class).putExtra("data", account));
////        } else if (id == R.id.nav_about_us) {
////            startActivity(new Intent(this, AboutUsActivity.class));
////        } else if (id == R.id.nav_privacy_policy) {
////            startActivity(new Intent(this, PrivacyPolicyActivity.class));
////        }
////        else if (id == R.id.nav_logout) {
////            handler = new Handler();
////            handler.postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    Intent intent = new Intent(UserLandingPage.this, ProgressActivity.class);
////                    startActivity(intent);
////                }
////            }, 3000);
////        }
////        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

//    void updateOndatabase()
//    {
//        ApiMethod method =  getRetrofit().create(ApiMethod.class);
//        Call<UserProfile> call= method.userProfile(String.valueOf(account1.getUserName()), avatar);
//        call.enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                Toast.makeText(Main.this, "thanh cong", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<UserProfile> call, Throwable t) {
//                Toast.makeText(Main.this, "kiem tra", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


    public void getListUser() {
        ApiMethod method = getRetrofit().create(ApiMethod.class);
        String phoneNumber = searchView.toString();
        Call<UserModel> call = method.searchingUser(phoneNumber);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body().getMessage().equals("success")) {
                    UserModel.Data user = response.body().getData();
                    Intent intent = new Intent(UserLandingPage.this, ProfileActivity.class);
                    intent.putExtra("fkey", user);
                    startActivity(intent);
                } else
                    Toast.makeText(UserLandingPage.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(UserLandingPage.this, "Kiểm tra kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///// UPLOAD IMAGE TO FIREBASE /////
//    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri result) {
//                    if (result != null) {
//                        imgProfile.setImageURI(result);
//                        filePath = result;
//                        updateImageToFB();
//
//                    }
//                }
//            });
//
//    private String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//
//    private void updateImageToFB() {
//        if (filePath != null) {
//            String str = System.currentTimeMillis() + "." + getFileExtension(filePath);
//            StorageReference fileRef = storageReference.child(str);
//            fileRef.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        storageReference.child(str).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(@NonNull Uri uri) {
//                                Glide.with(UserLandingPage.this).load(uri)
//                                        .transform(new CircleTransform())
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .into(imgProfile);
//                                avatar = uri.toString();
//                            }
//                        });
//                    }
//                }
//            });
//        } else {
//            Toast.makeText(UserLandingPage.this, "Update Failed!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void choosePicture() {
//        Intent intent = new Intent();
//        intent.setType("image/");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
//    }

}