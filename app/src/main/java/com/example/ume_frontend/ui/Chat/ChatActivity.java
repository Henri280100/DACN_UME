package com.example.ume_frontend.ui.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ume_frontend.API.ApiMethod;
import com.example.ume_frontend.Adapter.adepterchat;
import com.example.ume_frontend.Model.ListUser;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;
import com.example.ume_frontend.Retrofit.RetrofitClient;
import com.example.ume_frontend.ui.activity.MainActivity;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {


    public static UserModel.Data myAccount;
    public static ListUser.Data friendAccount;
    private RecyclerView recycleChat;
    private adepterchat adapterchat;
    ImageView imSendChat,imPicture;
    TextView txtchatMess;
    HubConnection hubchat;
    public  static String Mycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myAccount = MainActivity.myAccount;
        friendAccount = (ListUser.Data) getIntent().getSerializableExtra("fkey");

        recycleChat= findViewById(R.id.recycleChat);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recycleChat.setLayoutManager(linearLayoutManager);
        imSendChat= findViewById(R.id.imSendSms);
        txtchatMess= findViewById(R.id.txtChatSms);
        imPicture= findViewById(R.id.imPicture);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        hubchat= HubConnectionBuilder.create("http://10.0.2.2:8090/chatHub").build();

        hubchat.start();



    }


}