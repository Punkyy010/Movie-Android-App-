package com.example.stoic.googleatelier_app;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox chRemember;
    private FirebaseAuth mAuth;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        chRemember = findViewById(R.id.checkRemember);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        SharedPreferences sharedPrefs = getSharedPreferences(Constants.SHARED_PREF_KEY_USERINFORMATION,MODE_PRIVATE);

        String saved_username = null;
        String saved_password = null;

        if (saveLogin == true) {
            saved_username = sharedPrefs.getString(Constants.SHARED_PREF_KEY_USERNAME,"");
            saved_password = sharedPrefs.getString(Constants.SHARED_PREF_KEY_PASSWORD,"");
            chRemember.setChecked(true);
        }


        etUserName.setText(saved_username);
        etPassword.setText(saved_password);



        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                final String userNameFromForm = etUserName.getText().toString().trim();
                final String userPassFromForm = etPassword.getText().toString().trim();

                if (chRemember.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", userNameFromForm);
                    loginPrefsEditor.putString("password", userPassFromForm);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                mAuth.signInWithEmailAndPassword(userNameFromForm, userPassFromForm)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPrefs =
                                            getSharedPreferences(Constants.SHARED_PREF_KEY_USERINFORMATION,MODE_PRIVATE);
                                    SharedPreferences.Editor sharedPrefEditor = sharedPrefs.edit();
                                    if(shouldRemember()){
                                        sharedPrefEditor.putString(Constants.SHARED_PREF_KEY_USERNAME, userNameFromForm);
                                        sharedPrefEditor.putString(Constants.SHARED_PREF_KEY_PASSWORD, userPassFromForm);
                                        //sharedPrefEditor.putBoolean(Constants.SHARED_PREF_KEY_REMEMBER,saved_rememberme);
                                    }else{
                                        sharedPrefEditor.clear();
                                    }
                                    sharedPrefEditor.apply();

                                    startActivity(new Intent(LoginActivity.this, MoviesActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
    private boolean shouldRemember(){
        return chRemember.isChecked();
    }

}
