package com.example.listadecontatos.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listadecontatos.R;
import com.example.listadecontatos.UserData;
import com.example.listadecontatos.firebase.FirebaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;

public class SignActivity extends AppCompatActivity {

    private EditText username, password, email, number, name, site ;
    //private ImageView img;
    private Button newUserbt;
    private UserData user;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        username = findViewById(R.id.usuarioID);
        password = findViewById(R.id.senhaID);
        email =    findViewById(R.id.emailID);
        number =   findViewById(R.id.numID);
        name =     findViewById(R.id.nomeID);
        site =     findViewById(R.id.siteID);
        //img =      findViewById(R.id.profileImgID);
        newUserbt = findViewById(R.id.cadastroBtID);
        
        //Signin Button
        newUserbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get strign from editText
                String u = username.getText().toString();
                String e = email.getText().toString();
                String n = name.getText().toString();
                String p = password.getText().toString();
                String num = number.getText().toString();
                String s = site.getText().toString();

                user = new UserData(u,e,n,s,p,num);

                if(u.isEmpty() || e.isEmpty() || n.isEmpty() || p.isEmpty() || num.isEmpty() ){
                    Toast.makeText(SignActivity.this,"Preencher todos os dados", Toast.LENGTH_SHORT).show();
                }else{
                    NewUser();
                }
            }
        });
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {

                Uri select = data.getData();

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), select);

                    //png compress
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress( Bitmap.CompressFormat.PNG, 50, stream);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                img.setImageURI(select);

        }
    }*/

    private void NewUser(){

        auth = FirebaseConfig.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //test if isSuccessfull
                if (task.isSuccessful()){
                    Toast.makeText(SignActivity.this,"Usuario cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    user.setId(task.getResult().getUser().getUid());
                    user.save();

                    //save img on Firebase
                    /*
                    storage = FirebaseStorage.getInstance().getReference();
                    StorageReference storageReference = storage.child("images/profile").child("user_"+ user.getId());

                    img.setDrawingCacheEnabled(true);
                    img.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70,baos);
                    //byte[] data = baos.toByteArray();

                    //UploadTask uploadTask = storageReference.putBytes(data);

                    //On Success and Fail Listener
                    /*
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignActivity.this, "imagem salva!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(SignActivity.this, "Falha ao salvar imagem", Toast.LENGTH_SHORT).show();
                        }
                    });*/

                    MainActivity();
                    auth.signOut();
                    finish();

                }else{

                    //Tratamento de erros
                    String errorException="";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        errorException = "Senha fraca, deve ter pelo menos 6 caracteres";

                    }catch (FirebaseAuthEmailException e) {
                        errorException = "Email invalido";

                    }catch (FirebaseAuthUserCollisionException e){
                        errorException = "Email ja cadastrado";

                    } catch (Exception e) {
                        errorException = "Erro ao cadastrar usuario, verifique seus dados";
                        e.printStackTrace();
                    }


                    Toast.makeText(SignActivity.this,errorException , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void MainActivity() {

        Intent intent = new Intent(SignActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}