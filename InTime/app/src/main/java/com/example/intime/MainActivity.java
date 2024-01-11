package com.example.intime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Declarar variables para los elementos de la interfaz de usuario
    EditText email, pass;
    Button btn_login;

    // Declarar una instancia de FirebaseAuth para gestionar la autenticación de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la instancia de FirebaseAuth

        // Asociar variables con los elementos de la interfaz de usuario mediante sus identificadores
        email = findViewById(R.id.editTextNombreUsu);
        pass = findViewById(R.id.editTextPass);
        btn_login = findViewById(R.id.btnLogin);
    }

    // Método llamado cuando se hace clic en el botón de inicio de sesión
    public void checkLogin(View v) {
        // Obtener el correo electrónico y la contraseña ingresados por el usuario
        String emailUser = email.getText().toString().trim();
        String passUser = pass.getText().toString().trim();

        // Verificar si los campos están vacíos
        if (emailUser.isEmpty() && passUser.isEmpty()) {
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Llamar al método para iniciar sesión con los datos proporcionados
            loginUser(emailUser, passUser);
        }
    }


    private void loginUser(String emailUser, String passUser) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("usuarios");

        usersRef.orderByChild("correoElectronico").equalTo(emailUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Obtener el valor del campo "admin" para el usuario encontrado
                        int isAdmin = userSnapshot.child("admin").getValue(Integer.class);
                        // Obtener el valor del campo "codGym" para el usuario encontrado
                        String codGym = userSnapshot.child("codGym").getValue(String.class);
                        String Nombre = userSnapshot.child("nombre").getValue(String.class);
                        String correoElect = userSnapshot.child("correoElectronico").getValue(String.class);
                        String apellidos = userSnapshot.child("apellidos").getValue(String.class);


                        // Verificar el valor del campo "admin" y redirigir a la actividad correspondiente
                        if (isAdmin == 0) {
                            Sesion.getInstancia().setAdmin(isAdmin);
                            Sesion.getInstancia().setNombreUsuario(emailUser);
                            Sesion.getInstancia().setCodGym(codGym);
                            Sesion.getInstancia().setNombre(Nombre);
                            Sesion.getInstancia().setNombreUsuario(correoElect);
                            Sesion.getInstancia().setApellidos(apellidos);

                            // Usuario no es administrador, redirigir a MenuCliActivity
                            Intent intent = new Intent(MainActivity.this, MenuCliActivity.class);
                            startActivity(intent);
                            finish(); // Finalizar la actividad actual para evitar que el usuario regrese al inicio de sesión
                        } else if (isAdmin == 1) {
                            Sesion.getInstancia().setAdmin(isAdmin);
                            Sesion.getInstancia().setNombreUsuario(emailUser);
                            Sesion.getInstancia().setCodGym(codGym);
                            Sesion.getInstancia().setNombre(Nombre);
                            Sesion.getInstancia().setNombreUsuario(correoElect);
                            Sesion.getInstancia().setApellidos(apellidos);
                            // Usuario es administrador, redirigir a MenuAdminActivity
                            Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                            startActivity(intent);
                            finish(); // Finalizar la actividad actual para evitar que el usuario regrese al inicio de sesión
                        } else {
                            // Valor no válido para el campo "admin"
                            showToast("Error en el campo 'admin' de la base de datos");
                        }
                    }
                } else {
                    // Usuario no encontrado en la base de datos
                    showToast("Usuario no registrado. Verifica tus credenciales.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos aquí, si es necesario
                showToast("Error en la consulta de usuarios");
            }
        });
    }




    // Método auxiliar para mostrar mensajes Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    // Método llamado cuando se hace clic en el enlace "Crear cuenta"
    public void linkCrearCuentaClick(View view) {
        // Iniciar la actividad de creación de cuenta al hacer clic en el enlace
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
