package com.example.intime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GimnasioAdminActivity extends AppCompatActivity {
    EditText nombre, direccion, telefono, correoElectronico, codGym;
    Button btGuardar;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gimnasio_admin);

        // Configurar la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("gimnasios");

        // Realizar la consulta
        consultarGimnasio();

        // Inicializar los EditText
        nombre = findViewById(R.id.tbgimnombre);
        direccion = findViewById(R.id.etgymDireccion);
        telefono = findViewById(R.id.etugymTelefono);
        correoElectronico = findViewById(R.id.tbgymelectronico);
        codGym = findViewById(R.id.tbgymcod);
        btGuardar = findViewById(R.id.btnguardarGym);

    }
    private void consultarGimnasio() {
        String codGymSesion = Sesion.getInstancia().getCodGym();

        // Verificar si el código de gimnasio de la sesión no está vacío
        if (!TextUtils.isEmpty(codGymSesion)) {
            // Realizar la consulta en la base de datos
            Query query = databaseReference.orderByChild("codGym").equalTo(codGymSesion);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Si se encuentra el gimnasio, asignar los valores a los EditText
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Gimnasio gimnasio = snapshot.getValue(Gimnasio.class);
                            if (gimnasio != null) {
                                nombre.setText(gimnasio.getNombre());
                                direccion.setText(gimnasio.getDireccion());
                                telefono.setText(gimnasio.getTelefono());
                                correoElectronico.setText(gimnasio.getCorreoElectronico());
                                codGym.setText(gimnasio.getCodGym());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error si la consulta es cancelada
                    Toast.makeText(GimnasioAdminActivity.this, "Error en la consulta", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void guardarGimnasio(View view) {
        // Obtener los valores de los EditText
        String nombreGimnasio = nombre.getText().toString();
        String direccionGimnasio = direccion.getText().toString();
        String telefonoGimnasio = telefono.getText().toString();
        String correoGimnasio = correoElectronico.getText().toString();
        String codGimnasio = codGym.getText().toString();

        // Verificar si los campos están vacíos
        if (nombreGimnasio.equals("") || direccionGimnasio.equals("") ||
                telefonoGimnasio.equals("") || correoGimnasio.equals("") ||
                codGimnasio.equals("")) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto Gimnasio
        final Gimnasio gimnasio = new Gimnasio(nombreGimnasio, direccionGimnasio, telefonoGimnasio, correoGimnasio, codGimnasio);

        // Realizar la consulta en la base de datos
        Query query = databaseReference.orderByChild("codGym").equalTo(codGimnasio);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Si existe el gimnasio, actualizar los valores
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String gimnasioKey = snapshot.getKey();
                        databaseReference.child(gimnasioKey).setValue(gimnasio);
                        Toast.makeText(GimnasioAdminActivity.this, "Gimnasio actualizado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si no existe el gimnasio, crearlo
                    databaseReference.push().setValue(gimnasio);
                    Toast.makeText(GimnasioAdminActivity.this, "Gimnasio creado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si la consulta es cancelada
                Toast.makeText(GimnasioAdminActivity.this, "Error en la consulta", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void VolverAlMenuGA(View view) {
        if(Sesion.getInstancia().getAdmin()==0){
            Intent intent = new Intent(GimnasioAdminActivity.this, MenuCliActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }else{
            Intent intent = new Intent(GimnasioAdminActivity.this, MenuAdminActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }

    }

}