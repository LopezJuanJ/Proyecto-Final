package com.example.intime;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.button.MaterialButton;

public class MenuCliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cli);

        MaterialButton salirButton = findViewById(R.id.Salir);
    }
    public void SalirClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuCliActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
    public void MiCuerpoClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuCliActivity.this, MiCuerpoActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
    public void ReservarClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuCliActivity.this, ReservarActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
    public void PerfilClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuCliActivity.this, PerfilActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
    public void GimnasioCliClick(View view) {
        // Acción a realizar al hacer clic en el botón "Salir"
        Intent intent = new Intent(MenuCliActivity.this, GimnasioClientesActivity.class);
        startActivity(intent);
        finish(); // Opcional, dependiendo de tu flujo de la aplicación
    }
}
