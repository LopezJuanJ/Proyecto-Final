package com.example.intime;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.ValueEventListener;

public class AdministrarActivity extends AppCompatActivity {

    // Variables para los elementos de la interfaz de usuario
    private ImageView btnBack;
    private EditText etHora1, etMinuto1, etHora2, etMinuto2, etNumPersonas;
    private MaterialButton btnComprobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar);
        btnBack=findViewById(R.id.btnBack);
        etHora1 = findViewById(R.id.etHora1);
        etMinuto1 = findViewById(R.id.etMinuto1);
        etHora2 = findViewById(R.id.etHora2);
        etMinuto2 = findViewById(R.id.etMinuto2);
        etNumPersonas = findViewById(R.id.etNumPersonas);
        btnComprobar = findViewById(R.id.btncomprobar);
        TableLayout miTableRow = findViewById(R.id.miTableRow); // Reemplaza con el ID de tu TableRow
        mostrarClasesEnTableLayout(miTableRow);

    }

    public void mostrarClasesEnTableLayout(TableLayout tableLayout) {
        // Obtener la referencia a la base de datos de Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Crear la referencia a la tabla "clases"
        DatabaseReference clasesReference = databaseReference.child("clases");

        // Realizar la consulta para obtener todos los registros de la tabla "clases"
        clasesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot claseSnapshot : dataSnapshot.getChildren()) {
                    // Obtener los datos de la clase actual
                    Clase clase = claseSnapshot.getValue(Clase.class);

                    // Crear un nuevo TableRow para mostrar los datos
                    TableRow newRow = new TableRow(AdministrarActivity.this);

                    // Crear TextViews para Horario, Asistentes y Maximo
                    TextView tvHorario = new TextView(AdministrarActivity.this);
                    TextView tvAsistentes = new TextView(AdministrarActivity.this);
                    TextView tvMaximo = new TextView(AdministrarActivity.this);

                    // Configurar el texto para cada TextView
                    tvHorario.setText(clase.getHorario());
                    tvAsistentes.setText(clase.getAsistentes());
                    tvMaximo.setText(clase.getMaxPersonas());

                    // Aplicar la fuente @font/urbanistbold a cada TextView
                    tvHorario.setTypeface(ResourcesCompat.getFont(AdministrarActivity.this, R.font.urbanistbold));
                    tvAsistentes.setTypeface(ResourcesCompat.getFont(AdministrarActivity.this, R.font.urbanistbold));
                    tvMaximo.setTypeface(ResourcesCompat.getFont(AdministrarActivity.this, R.font.urbanistbold));

                    // Configurar LayoutParams para cada TextView
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            0, // Ancho 0 para distribuir el espacio de manera uniforme
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f // Peso 1 para distribuir el espacio de manera uniforme
                    );

                    // Configurar gravedad para centrar el texto en cada TextView
                    tvHorario.setGravity(Gravity.CENTER);
                    tvAsistentes.setGravity(Gravity.CENTER);
                    tvMaximo.setGravity(Gravity.CENTER);

                    // Aplicar LayoutParams a cada TextView
                    tvHorario.setLayoutParams(layoutParams);
                    tvAsistentes.setLayoutParams(layoutParams);
                    tvMaximo.setLayoutParams(layoutParams);

                    // Agregar TextViews al nuevo TableRow
                    newRow.addView(tvHorario);
                    newRow.addView(tvAsistentes);
                    newRow.addView(tvMaximo);

                    // Agregar el nuevo TableRow al TableLayout existente proporcionado como parámetro
                    tableLayout.addView(newRow);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error según sea necesario
            }
        });
    }


    public void AddClase(View v) {
        // Obtener los valores de los EditText
        String hora1 = etHora1.getText().toString();
        String minuto1 = etMinuto1.getText().toString();
        String hora2 = etHora2.getText().toString();
        String minuto2 = etMinuto2.getText().toString();
        String personasMax = etNumPersonas.getText().toString();

        // Crear la variable String con el formato requerido
        String horario = hora1 + ":" + minuto1 + "-" + hora2 + ":" + minuto2;

        // Obtener la referencia a la base de datos de Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Crear la referencia a la tabla "clases"
        DatabaseReference clasesReference = databaseReference.child("clases");

        // Verificar si la tabla "clases" ya existe
        clasesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Si la tabla "clases" no existe, la creamos
                    clasesReference.setValue(true);
                }

                // Ahora podemos agregar la nueva clase con asistentes vacíos
                Clase nuevaClase = new Clase(horario, personasMax, "");
                DatabaseReference nuevaClaseReference = clasesReference.push();
                nuevaClaseReference.setValue(nuevaClase);

                // Limpieza de los EditText después de agregar la clase
                etHora1.setText("");
                etMinuto1.setText("");
                etHora2.setText("");
                etMinuto2.setText("");
                etNumPersonas.setText("");
                recargarTabla();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error según sea necesario
            }
        });
    }
    // Método para recargar la tabla
    private void recargarTabla() {
        // Obtener la referencia a la base de datos de Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Crear la referencia a la tabla "clases"
        DatabaseReference clasesReference = databaseReference.child("clases");

        // Obtener la referencia al TableLayout
        TableLayout miTableLayout = findViewById(R.id.miTableRow); // Reemplaza con el ID de tu TableLayout

        // Obtener el número de filas en la tabla (sin contar el encabezado)
        int rowCount = miTableLayout.getChildCount();

        // Eliminar solo las filas de datos (dejando el encabezado)
        for (int i = 1; i < rowCount; i++) {
            miTableLayout.removeViewAt(1);
        }

        // Volver a cargar los datos en la tabla
        mostrarClasesEnTableLayout(miTableLayout);
    }

    public void VolverAlMenuA(View view) {
        Intent intent = new Intent(AdministrarActivity.this, MenuAdminActivity.class);
        startActivity(intent);
        finish();
    }
}
