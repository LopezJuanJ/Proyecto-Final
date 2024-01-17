package com.example.intime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilEdicionAdminActivity extends AppCompatActivity {
    EditText nombre, apellidos, codGym, correoElect, pass;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Obtener datos del Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String nombreUsuario = bundle.getString("nombre");
            String apellidosUsuario = bundle.getString("apellidos");
            String codGymUsuario = bundle.getString("codGym");
            String correoElectronicoUsuario = bundle.getString("correoElectronico");

            // Asignar valores a las vistas
            nombre = findViewById(R.id.tbusunombre);
            apellidos = findViewById(R.id.etusuApellidos);
            codGym = findViewById(R.id.etusuCodGym);
            correoElect = findViewById(R.id.tbusuelectronico);
            pass = findViewById(R.id.tbusupass);
            guardar = findViewById(R.id.btnguardarPerfil);

            // Asignar valores a las vistas con los datos del Bundle
            nombre.setText(nombreUsuario);
            apellidos.setText(apellidosUsuario);
            codGym.setText(codGymUsuario);
            correoElect.setText(correoElectronicoUsuario);
        } else {
            // Manejar el caso en el que no se proporcionaron datos en el Bundle
            // Puedes mostrar un mensaje de error o tomar alguna otra acción
            Toast.makeText(this, "Error: No se recibieron datos del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void VolverAlMenu(View view) {
        if(Sesion.getInstancia().getAdmin()==0){
            Intent intent = new Intent(PerfilEdicionAdminActivity.this, MenuCliActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }else{
            Intent intent = new Intent(PerfilEdicionAdminActivity.this, MenuAdminActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void actualizarDatos(View v){
        cargarValores();
    }

    public void cargarValores(){
        Bundle bundle = getIntent().getExtras();
        String nuevoNombre = nombre.getText().toString();
        String nuevosApellidos = apellidos.getText().toString();
        String nuevoCorreo = correoElect.getText().toString();
        String nuevaPass=pass.getText().toString();
        String emailUser = bundle.getString("correoElectronico");

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
        usersRef.orderByChild("correoElectronico").equalTo(emailUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Obtener la referencia al nodo del usuario
                        DatabaseReference usuarioRef = usersRef.child(userSnapshot.getKey());


                        if (nuevaPass.isEmpty()){
                            // Actualizar los valores en la base de datos
                            usuarioRef.child("nombre").setValue(nuevoNombre);
                            usuarioRef.child("apellidos").setValue(nuevosApellidos);
                            usuarioRef.child("correoElectronico").setValue(nuevoCorreo);
                            showToast("Campos actualizados");

                        }else{
                            // Actualizar los valores en la base de datos
                            usuarioRef.child("nombre").setValue(nuevoNombre);
                            usuarioRef.child("apellidos").setValue(nuevosApellidos);
                            usuarioRef.child("correoElectronico").setValue(nuevoCorreo);
                            usuarioRef.child("contrasena").setValue(nuevaPass);
                            pass.setText("");
                            showToast("Campos actualizados");

                        }
                    }

                } else {
                    // Usuario no encontrado en la base de datos
                    showToast("Imposible cambiar datos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos aquí, si es necesario
                showToast("Error en la consulta de usuarios");
            }
        });




    }





}