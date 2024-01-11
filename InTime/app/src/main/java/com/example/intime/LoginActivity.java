package com.example.intime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText nombre, apellidos, cElectronico, pass,repPass, codGym;
    Button btnCrear;
    CheckBox cbTerm;
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
        cbTerm=findViewById(R.id.checkBoxTerminos);

    }
    public void CrearCuentaClick(View view) {
        if(cbTerm.isChecked()){
            // Obtener los valores ingresados por el usuario
            String nombreUsuario = nombre.getText().toString().trim();
            String apellidosUsuario = apellidos.getText().toString().trim();
            String correoUsuario = cElectronico.getText().toString().trim();
            String contrasena = pass.getText().toString().trim();
            String codGymUsuario = codGym.getText().toString().trim();
            int admin = 0;  // Cambiar a 1 si es un administrador
            if(TextUtils.isEmpty(nombreUsuario) ||TextUtils.isEmpty(apellidosUsuario) ||TextUtils.isEmpty(correoUsuario) ||TextUtils.isEmpty(contrasena) ||TextUtils.isEmpty(codGymUsuario)){
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();

            }else{

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

        }else{
            Toast.makeText(this, "Por favor, acepte los terminos y condiciones", Toast.LENGTH_SHORT).show();
        }

    }

}