package com.example.listadecontatos.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public final class FirebaseConfig {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebase(){

        if (databaseReference == null ) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){

        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }


}
