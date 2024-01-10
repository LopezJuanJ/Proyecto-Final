package com.example.intime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email= findViewById(R.id.editTextNombreUsu);
        pass=findViewById(R.id.editTextPass);
        btn_login=findViewById(R.id.btnLogin);



    }

    private void comprobarLogin(String emailUser, String passUser) {
    }

    public void linkCrearCuentaClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        // Iniciar la nueva actividad
        startActivity(intent);
    }
}