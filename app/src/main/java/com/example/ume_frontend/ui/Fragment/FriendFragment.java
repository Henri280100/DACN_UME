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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FriendFragment extends Fragment {
    View mView;
    private RecyclerView friendRecView;
    public static UserModel.Data myAccount= MainActivity.myAccount;
    private AdapterFriends friendsAdapter;
    List<ListUser.Data> data;
    int id;
    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_friend, container, false);
        friendRecView = mView.findViewById(R.id.recViewFriend);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        friendRecView.setLayoutManager(linearLayoutManager);
        id= myAccount.getIdUser();
        getdata();
        return mView;
    }

    private void getdata() {
        ApiMethod get= RetrofitClient.getRetrofit().create(ApiMethod.class);
        Call<ListUser> call=get.getListfriends(id);
        call.enqueue(new Callback<ListUser>() {
            @Override
            public void onResponse(Call<ListUser> call, Response<ListUser> response) {
                data=response.body().getData();
                if(data.size()!=0 && response.message() != "success")
                {
                    friendsAdapter = new AdapterFriends(data);
                    friendRecView.setAdapter(friendsAdapter);
                }
            }
            @Override
            public void onFailure(Call<ListUser> call, Throwable t) {

            }
        });
    }
}