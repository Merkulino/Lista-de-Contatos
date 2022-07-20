package com.example.listadecontatos;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<UserData> {

    private ArrayList<UserData> userList;
    private Context context;
    //private StorageReference storageReference;


    public Adapter(@NonNull Context context , @NonNull ArrayList<UserData> objects) {
        super(context, 0, objects);
        this.userList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //verify if list is empty
        if (userList != null ){

            //xml view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_contact, parent, false);

            //ImageView img = view.findViewById(R.id.imgContactID);
            TextView contactName = view.findViewById(R.id.txtContactNameID);
            TextView num = view.findViewById(R.id.numContactID);

            //Set user data
            UserData userData = userList.get(position);
            contactName.setText(userData.getName());
            num.setText(userData.getNumber());

        }

        return view;

    }
}
