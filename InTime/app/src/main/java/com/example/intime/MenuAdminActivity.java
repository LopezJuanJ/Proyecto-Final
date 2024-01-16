package com.example.intime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
    }
    public void SalirClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuAdminActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
    public void PerfilClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuAdminActivity.this, PerfilActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }

    public void GymClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuAdminActivity.this, GimnasioAdminActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
}