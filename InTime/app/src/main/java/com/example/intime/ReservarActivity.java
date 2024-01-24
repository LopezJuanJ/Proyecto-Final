package com.example.intime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class ReservarActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        // Obtener la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("clases");

        // Llamar al método para cargar las clases
        cargarClases();
    }

    private void cargarClases() {
        // Obtener una referencia a la vista lineal donde se agregarán los horarios y botones
        LinearLayout linearLayout = findViewById(R.id.linerlayout);

        // Leer los datos de la base de datos
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpiar la vista antes de agregar nuevos elementos
                linearLayout.removeAllViews();

                // Iterar sobre las clases en la base de datos
                for (DataSnapshot claseSnapshot : dataSnapshot.getChildren()) {
                    // Obtener la clase actual
                    Clase clase = claseSnapshot.getValue(Clase.class);

                    // Crear un nuevo TextView para mostrar el horario
                    TextView horarioTextView = new TextView(ReservarActivity.this);
                    horarioTextView.setText(clase.getHorario());
                    horarioTextView.setTextSize(24);  // Tamaño de texto
                    horarioTextView.setTypeface(ResourcesCompat.getFont(ReservarActivity.this, R.font.urbanistbold));  // Fuente
                    horarioTextView.setTextColor(Color.WHITE);  // Color del texto blanco

                    // Crear un nuevo botón para reservar
                    MaterialButton reservarButton = new MaterialButton(ReservarActivity.this);
                    reservarButton.setText("Reservar");
                    reservarButton.setTextSize(24);  // Tamaño de texto
                    reservarButton.setTypeface(ResourcesCompat.getFont(ReservarActivity.this, R.font.urbanistbold));  // Fuente

                    // Verificar si el usuario está presente en la lista de asistentes
                    if (usuarioEstaEnAsistentes(clase.getAsistentes())) {
                        // Usuario presente, cambiar el color del botón a rojo
                        reservarButton.setBackgroundColor(Color.RED);
                        reservarButton.setTextColor(Color.BLACK);  // Cambiar color del texto para que sea legible
                    } else {
                        // Usuario no presente, mantener el color original
                        reservarButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        reservarButton.setTextColor(Color.BLACK);  // Cambiar color del texto para que sea legible
                    }

                    reservarButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String horarioSeleccionado = horarioTextView.getText().toString();

                            // Verificar si el usuario ya está apuntado
                            if (usuarioEstaEnAsistentes(clase.getAsistentes())) {
                                // Desapuntar al usuario (eliminar el usuario y la "," de la columna asistentes)
                                desapuntarUsuarioEnBD(claseSnapshot.getKey());
                            } else {
                                // Apuntar al usuario (agregar el usuario a la columna asistentes)
                                apuntarUsuarioEnBD(claseSnapshot.getKey());
                            }
                        }
                    });

                    // Asegúrate de ajustar los parámetros de diseño según sea necesario
                    LinearLayout.LayoutParams paramsHorario = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    LinearLayout.LayoutParams paramsBoton = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    // Agregar un margen superior de 40dp a los TextView y 20dp a los botones
                    paramsHorario.setMargins(10, 40, 0, 0);
                    paramsBoton.setMargins(10, 20, 0, 0);

                    // Agregar el TextView y el botón a la vista lineal
                    linearLayout.addView(horarioTextView, paramsHorario);
                    linearLayout.addView(reservarButton, paramsBoton);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error de la base de datos si es necesario
            }
        });
    }

    // Método para verificar si el usuario está presente en la lista de asistentes
    private boolean usuarioEstaEnAsistentes(String asistentes) {
        // Dividir la cadena de asistentes en una lista de usuarios
        List<String> listaAsistentes = Arrays.asList(asistentes.split(","));

        // Verificar si el usuario actual está en la lista
        return listaAsistentes.contains(Sesion.getInstancia().getNombreUsuario());
    }

    private void apuntarUsuarioEnBD(String claseKey) {
        // Obtener una referencia a la clase seleccionada en la base de datos
        DatabaseReference claseRef = databaseReference.child(claseKey);

        // Leer los asistentes actuales desde la base de datos
        claseRef.child("asistentes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String asistentesActuales = dataSnapshot.getValue(String.class);

                // Agregar al usuario actual a la lista de asistentes
                String nuevosAsistentes = (asistentesActuales != null) ?
                        asistentesActuales + Sesion.getInstancia().getNombreUsuario() + "," :
                        Sesion.getInstancia().getNombreUsuario() + ",";

                // Actualizar la columna de asistentes en la base de datos
                claseRef.child("asistentes").setValue(nuevosAsistentes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error si es necesario
            }
        });
    }

    private void desapuntarUsuarioEnBD(String claseKey) {
        // Obtener una referencia a la clase seleccionada en la base de datos
        DatabaseReference claseRef = databaseReference.child(claseKey);

        // Leer los asistentes actuales desde la base de datos
        claseRef.child("asistentes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String asistentesActuales = dataSnapshot.getValue(String.class);

                // Verificar si el usuario actual está en la lista de asistentes
                if (asistentesActuales != null && asistentesActuales.contains(Sesion.getInstancia().getNombreUsuario() + ",")) {
                    // Remover al usuario actual de la lista de asistentes
                    String nuevosAsistentes = asistentesActuales.replace(Sesion.getInstancia().getNombreUsuario() + ",", "");

                    // Actualizar la columna de asistentes en la base de datos
                    claseRef.child("asistentes").setValue(nuevosAsistentes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error si es necesario
            }
        });
    }

    private void actualizarAsistentesEnBD(final String horarioBoton) {
        // Asegúrate de que databaseReference apunte a la ubicación correcta de tu base de datos
        DatabaseReference clasesRef = FirebaseDatabase.getInstance().getReference().child("clases");


        // Consulta para encontrar la clase con el horario correspondiente al botón pulsado

        clasesRef.orderByChild("horario").equalTo(horarioBoton).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Verificar si se encontró una clase con el horario del botón pulsado
                if (dataSnapshot.exists()) {
                    for (DataSnapshot claseSnapshot : dataSnapshot.getChildren()) {
                        // Obtener el valor actual de "asistentes"
                        String asistentesAnteriores = claseSnapshot.child("asistentes").getValue(String.class);

                        // Si no hay asistentes anteriores, simplemente usa el nombre de usuario
                        String nuevosAsistentes;
                        if (asistentesAnteriores == null || asistentesAnteriores.isEmpty()) {
                            nuevosAsistentes = Sesion.getInstancia().getNombreUsuario() + ",";
                        } else {
                            // Agregar el nombre de usuario a la lista existente de asistentes
                            nuevosAsistentes = asistentesAnteriores + Sesion.getInstancia().getNombreUsuario() + ",";
                        }

                        // Actualizar el campo "asistentes" en la base de datos con la lista actualizada
                        claseSnapshot.getRef().child("asistentes").setValue(nuevosAsistentes);
                    }
                } else {
                    // La clase con el horario especificado no fue encontrada en la base de datos
                    Toast.makeText(getApplicationContext(), "Clase no encontrada en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error de la base de datos si es necesario
                Log.e("DatabaseError", "Error en la base de datos: " + error.getMessage());
            }
        });
    }


    public void VolverAlMenuR(View view) {
        if(Sesion.getInstancia().getAdmin()==0){
            Intent intent = new Intent(ReservarActivity.this, MenuCliActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }else{
            Intent intent = new Intent(ReservarActivity.this, MenuAdminActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }

    }
}