package com.example.intime;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MiCuerpoActivity extends AppCompatActivity {

    EditText etKG, etAltura, cIMC;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuerpo);

        etKG = findViewById(R.id.tbumicukg);
        etAltura = findViewById(R.id.etmicAlt);
        cIMC = findViewById(R.id.etIM);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        // Cargar datos del usuario al iniciar la actividad
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        String correo = Sesion.getInstancia().getNombreUsuario();

        Cursor cursor = database.query(DatabaseHelper.TABLE_CUERPO, null,
                DatabaseHelper.COLUMN_CORREO + "=?", new String[]{correo},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Usuario encontrado en la base de datos
            int kilosColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_KILOS);
            int alturaColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ALTURA);
            int imcColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMC);

            if (kilosColumnIndex != -1 && alturaColumnIndex != -1 && imcColumnIndex != -1) {
                // The columns exist, retrieve the data
                String kilos = cursor.getString(kilosColumnIndex);
                String altura = cursor.getString(alturaColumnIndex);
                String imc = cursor.getString(imcColumnIndex);

                // Llenar los campos de la interfaz con los datos cargados
                etKG.setText(kilos);
                etAltura.setText(altura);
                cIMC.setText(imc);
            } else {
                // Handle the case where the columns do not exist
                Toast.makeText(this, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }
    }

    public void CrearMiCuerpo(View view) {
        // Obtener los valores ingresados por el usuario
        String kilos = etKG.getText().toString().trim();
        String altura = etAltura.getText().toString().trim();
        String correo = Sesion.getInstancia().getNombreUsuario();

        if (TextUtils.isEmpty(kilos) || TextUtils.isEmpty(altura)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Verificar si el correo electrónico ya está registrado
            verificarCuerpoExistente(kilos, altura, correo);
        }
    }

    private void verificarCuerpoExistente(String kilos, String altura, String correo) {
        // Consultar si el usuario ya existe
        Cursor cursor = database.query(DatabaseHelper.TABLE_CUERPO, null,
                DatabaseHelper.COLUMN_CORREO + "=?", new String[]{correo},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Usuario encontrado en la base de datos, actualizar los valores
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ALTURA, altura);
            values.put(DatabaseHelper.COLUMN_KILOS, kilos);
            values.put(DatabaseHelper.COLUMN_IMC, calcularIMC(kilos, altura));

            database.update(DatabaseHelper.TABLE_CUERPO, values,
                    DatabaseHelper.COLUMN_CORREO + "=?", new String[]{correo});

            cursor.close();
        } else {
            // Usuario no encontrado, crear un nuevo registro
            double imc = calcularIMC(kilos, altura);
            crearCuerpo(correo, kilos, altura, imc);
        }

        double imcf = calcularIMC(kilos, altura);
        String imcfString = String.valueOf(imcf);
        cIMC.setText(imcfString);
    }



    // Crear cuerpo en la base de datos SQLite
    private void crearCuerpo(String correo, String kilos, String altura, double imc) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CORREO, correo);
        values.put(DatabaseHelper.COLUMN_KILOS, kilos);
        values.put(DatabaseHelper.COLUMN_ALTURA, altura);
        values.put(DatabaseHelper.COLUMN_IMC, imc);

        long newRowId = database.insert(DatabaseHelper.TABLE_CUERPO, null, values);

        if (newRowId != -1) {
            // Informar al usuario que los datos han sido añadidos exitosamente
            Toast.makeText(MiCuerpoActivity.this, "Datos añadidos exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            // Manejar el caso en que la inserción falla
            Toast.makeText(MiCuerpoActivity.this, "Error al añadir datos", Toast.LENGTH_SHORT).show();
        }
    }





    public void VolverAlMenuMC(View view) {
        if(Sesion.getInstancia().getAdmin()==0){
            Intent intent = new Intent(MiCuerpoActivity.this, MenuCliActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }else{
            Intent intent = new Intent(MiCuerpoActivity.this, MenuAdminActivity.class);
            // Acción a realizar al hacer clic en el botón "Salir"
            startActivity(intent);
            finish(); // Opcional, dependiendo de tu flujo de la aplicación
        }

    }
    public static double calcularIMC(String kilos, String altura) {
        // Verificar que los valores no estén vacíos antes de realizar el cálculo
        if (!kilos.isEmpty() && !altura.isEmpty()) {
            try {
                // Convertir los valores a números
                double peso = Double.parseDouble(kilos);
                double alturaMetros = Double.parseDouble(altura);

                // Calcular el IMC
                return peso / (alturaMetros * alturaMetros);
            } catch (NumberFormatException e) {
                // Manejar la excepción si los valores no son números válidos
                System.out.println("Error al convertir los valores a números.");
            }
        } else {
            // Manejar el caso en que los valores estén vacíos
            System.out.println("Por favor, ingresa valores válidos para peso y altura.");
        }

        // En caso de error, puedes devolver un valor predeterminado o lanzar una excepción, según tus necesidades.
        return -1.0; // Por ejemplo, devolver -1 como indicador de error
    }


}