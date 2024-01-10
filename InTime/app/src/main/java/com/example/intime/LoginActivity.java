package com.example.intime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText nombre, apellidos, cElectronico, pass,repPass, codGym;
    Button btnCrear;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        nombre= findViewById(R.id.editTextNombre);
        apellidos=findViewById(R.id.editTextApellidos);
        cElectronico = findViewById(R.id.editTextCorreoE);
        pass=findViewById(R.id.editTextContrasena);
        codGym=findViewById(R.id.editTextCodGym);
        repPass=findViewById(R.id.editTextRepContrasena);
        btnCrear=findViewById(R.id.btnCrearCuenta);

    }
    public void CrearCuentaClick(View view) {
        // Obtener los valores ingresados por el usuario
        String nombreUsuario = nombre.getText().toString();
        String apellidosUsuario = apellidos.getText().toString();
        String correoUsuario = cElectronico.getText().toString();
        String contrasena = pass.getText().toString();
        String codGymUsuario = codGym.getText().toString();
        int admin = 0;  // Cambiar a true si es un administrador

        // Crear un objeto Usuario con la información ingresada y asignar el valor de admin
        Usuario nuevoUsuario = new Usuario(nombreUsuario, apellidosUsuario, correoUsuario, contrasena, codGymUsuario, admin);

        // Obtener la clave única para el nuevo usuario
        String userId = databaseReference.push().getKey();

        // Guardar el nuevo usuario en la base de datos
        databaseReference.child(userId).setValue(nuevoUsuario);

        // Limpiar los campos de entrada después de guardar en la base de datos
        nombre.setText("");
        apellidos.setText("");
        cElectronico.setText("");
        nombre.setText("");
        pass.setText("");
        repPass.setText("");
        codGym.setText("");
    }

}