package com.example.ume_frontend.ui.Fragment;

import static com.example.ume_frontend.Retrofit.RetrofitClient.getRetrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ume_frontend.API.ApiMethod;
import com.example.ume_frontend.Model.PosterModel;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;

import com.example.ume_frontend.Adapter.PosterAdapter;



import com.example.ume_frontend.ui.activity.UserLandingPage;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    RecyclerView recProfile;
    List<UserModel.Data> dataList = Collections.singletonList(UserLandingPage.account1);
    List<PosterModel.Data> posterData;
    public static final String TAG = "ProfileFragment";


    View mView;
    PosterAdapter posterAdapter;

    UserModel.Data userData = UserLandingPage.account1;

    int idU;

    Toolbar toolbar;
    TextView txtUserNameProfile, txtUsername;
    TextView txtUserContent;
    // Don't delete it
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        idU = userData.getIdUser();


        txtUserContent = mView.findViewById(R.id.txtUserContent);
        txtUsername = mView.findViewById(R.id.txtUsername);
        txtUserNameProfile = mView.findViewById(R.id.txtUserNameProfile);

        toolbar = mView.findViewById(R.id.toolbarBack);
        toolbar.setVisibility(View.VISIBLE);
        recProfile = mView.findViewById(R.id.recProfile);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recProfile.setLayoutManager(gridLayoutManager);

        txtUserContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


//        loadAdapter = new LoadAdapter();
//
//        loadAdapter.setInfoData(data);
//        recProfile.setAdapter(loadAdapter);

//        infoAdpater = new InfoAdpater(data);
//        recProfile.setAdapter(infoAdpater);



        LoadUserPoster();
        return mView;
    }

    private void LoadUserPoster() {

        ApiMethod method = getRetrofit().create(ApiMethod.class);
        Call<PosterModel> callUserPoster = method.loadPoster(idU);
        callUserPoster.enqueue(new Callback<PosterModel>() {
            @Override
            public void onResponse(Call<PosterModel> call, Response<PosterModel> response) {
                posterData = response.body().getData();
                if (response.body().getMessage().equals("success")) {
                    posterAdapter = new PosterAdapter(posterData);
                    recProfile.setAdapter(posterAdapter);
                }
            }

            @Override
            public void onFailure(Call<PosterModel> call, Throwable t) {
                Log.d(TAG, "Error: ");
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (userData != null) {
            txtUserNameProfile.setText(userData.getUserName());
            txtUsername.setText(userData.getUserName());
        }
    }
}