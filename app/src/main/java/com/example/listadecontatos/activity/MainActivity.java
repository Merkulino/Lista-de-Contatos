package com.example.listadecontatos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listadecontatos.Adapter;
import com.example.listadecontatos.R;
import com.example.listadecontatos.UserData;
import com.example.listadecontatos.firebase.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private DatabaseReference firebase;

    private ArrayList<UserData> userList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Activity dos Contatos
        // Dar um otimizada e pronto!
        // Se sobrar tempo cria uma tela pro user atualizar os proprios dados

        listView = findViewById(R.id.listviewID);

        //listView and Adapter config
        userList = new ArrayList<>();

        adapter = new Adapter(MainActivity.this, userList );

        listView.setAdapter(adapter);

        //Get user auth from Firebase
        firebase = FirebaseConfig.getFirebase()
                .child("users");

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();

                //Show data  List
                for (DataSnapshot data: snapshot.getChildren()){
                    UserData userData = data.getValue(UserData.class);
                    userList.add(userData );
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}