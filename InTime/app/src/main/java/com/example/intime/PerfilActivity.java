package com.example.intime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilActivity extends AppCompatActivity {
    EditText nombre, apellidos, codGym, correoElect, pass;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nombre=findViewById(R.id.tbusunombre);
        apellidos= findViewById(R.id.etusuApellidos);
        codGym=findViewById(R.id.etusuCodGym);
        correoElect=findViewById(R.id.tbusuelectronico);
        pass=findViewById(R.id.tbusupass);
        guardar=findViewById(R.id.btnguardarPerfil);

        asignarValores();
    }

    public void asignarValores(){
        nombre.setText(Sesion.getInstancia().getNombre());
        apellidos.setText(Sesion.getInstancia().getApellidos());
        codGym.setText(Sesion.getInstancia().getCodGym());
        correoElect.setText(Sesion.getInstancia().getNombreUsuario());
    }
    public void VolverAlMenu(View view) {
        if(Sesion.getInstancia().getAdmin()==0){
            Intent intent = new Intent(PerfilActivity.this, MenuCliActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }else{
            Intent intent = new Intent(PerfilActivity.this, MenuAdminActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }

    }

    public void actualizarDatos(View v){
        cargarValores();
    }

    public void cargarValores(){
        String nuevoNombre = nombre.getText().toString();
        String nuevosApellidos = apellidos.getText().toString();
        String nuevoCodGym = codGym.getText().toString();
        String nuevoCorreo = correoElect.getText().toString();
        String nuevaPass=pass.getText().toString();




    }





}