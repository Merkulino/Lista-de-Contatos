package com.example.listadecontatos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listadecontatos.R;
import com.example.listadecontatos.UserData;
import com.example.listadecontatos.firebase.FirebaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button   button;
    private TextView signLink;
    private FirebaseAuth auth ;

    private UserData user = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userLoginID);
        password = findViewById(R.id.senhaLoginID);
        button =   findViewById(R.id.btLoginID);
        signLink = findViewById(R.id.signLinkID);

        //verifyLoggedUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = username.getText().toString();
                String p = password.getText().toString() ;
                user.setEmail(e);
                user.setPassword( p );

                if (e.isEmpty() || p.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Preencher os dados", Toast.LENGTH_SHORT).show();
                }else{
                    validateLogin();
                }

            }
        });

        signLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignActivity();
            }
        });

    }

    private void verifyLoggedUser() {

        auth = FirebaseConfig.getFirebaseAuth();
        if (auth.getCurrentUser() != null){
            MainActivity();
        }
    }

    private void validateLogin() {

        auth = FirebaseConfig.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    MainActivity();

                }else{

                    String errorException="";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        errorException = "Email ou senha invalidos";
                    } catch (FirebaseAuthInvalidUserException e) {
                        errorException = "Usuario não cadastrado";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this, errorException, Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void MainActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void SignActivity(){

        Intent intent = new Intent(LoginActivity.this, SignActivity.class);
        startActivity(intent);

    }
}