package com.example.ume_frontend.ui.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ume_frontend.API.ApiMethod;
import com.example.ume_frontend.Adapter.AdapterFriends;
import com.example.ume_frontend.Model.ListUser;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;
import com.example.ume_frontend.Retrofit.RetrofitClient;
import com.example.ume_frontend.ui.activity.MainActivity;
import com.example.ume_frontend.ui.activity.UserLandingPage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    UserLandingPage userLandingPage;
    View mView;
    RecyclerView recyclerchatFriends;
    List<ListUser.Data> data;
    private AdapterFriends friendsAdapter;
    int idUser;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        userLandingPage = (UserLandingPage) getActivity();
//        idUser = Integer.parseInt(account1.getIdUser());

        recyclerchatFriends = mView.findViewById(R.id.recyclerchatFriends);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerchatFriends.setLayoutManager(linearLayoutManager);

        getdata();
        return mView;
    }

    private void getdata() {
        ApiMethod get = RetrofitClient.getRetrofit().create(ApiMethod.class);
        Call<ListUser> call = get.getListChatfriends(idUser);
        call.enqueue(new Callback<ListUser>() {
            @Override
            public void onResponse(Call<ListUser> call, Response<ListUser> response) {
                data = response.body().getData();
                if (data.size() != 0 && response.message() != "success") {
                    friendsAdapter = new AdapterFriends(data);
                    recyclerchatFriends.setAdapter(friendsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ListUser> call, Throwable t) {

            }
        });
    }
}