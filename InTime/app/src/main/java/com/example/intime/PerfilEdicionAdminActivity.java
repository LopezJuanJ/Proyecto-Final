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
        String nuevoNombre = nombre.getText().toString();
        String nuevosApellidos = apellidos.getText().toString();
        String nuevoCodGym = codGym.getText().toString();
        String nuevoCorreo = correoElect.getText().toString();
        String nuevaPass=pass.getText().toString();
        String emailUser = Sesion.getInstancia().getNombreUsuario();

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
                        }else{
                            // Actualizar los valores en la base de datos
                            usuarioRef.child("nombre").setValue(nuevoNombre);
                            usuarioRef.child("apellidos").setValue(nuevosApellidos);
                            usuarioRef.child("correoElectronico").setValue(nuevoCorreo);
                            usuarioRef.child("contrasena").setValue(nuevaPass);
                            pass.setText("");

                            Sesion.getInstancia().setApellidos(nuevosApellidos);
                            Sesion.getInstancia().setNombreUsuario(nuevoCorreo);
                            Sesion.getInstancia().setNombre(nuevoNombre);
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