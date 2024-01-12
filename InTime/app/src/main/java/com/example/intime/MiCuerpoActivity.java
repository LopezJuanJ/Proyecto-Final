package com.example.intime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MiCuerpoActivity extends AppCompatActivity {
    EditText etKG, etAltura, cIMC;
    Button btnGuardar;
    CheckBox cbMedicion;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuerpo);

        etKG=findViewById(R.id.tbumicukg);
        etAltura=findViewById(R.id.etmicAlt);
        cIMC=findViewById(R.id.etIM);
        btnGuardar= findViewById(R.id.btnguardarPerfil);
        cbMedicion=findViewById(R.id.checkBoxPrimeraMedi);

    }
    public void CrearMiCuerpo(View view) {
            // Obtener los valores ingresados por el usuario
            String kilos = etKG.getText().toString().trim();
            String altura = etAltura.getText().toString().trim();
            String correo = Sesion.getInstancia().getNombreUsuario();


            if(etKG.getText().toString()== "" || etAltura.getText().toString()== "" ){
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar si el correo electrónico ya está registrado
                verificarCuerpoExistente(kilos, altura, correo);
            }


    }

    private void verificarCuerpoExistente(String kilos, String altura, String correo) {
        if(cbMedicion.isChecked()){

            DatabaseReference cuerposRef = FirebaseDatabase.getInstance().getReference().child("cuerpos");
        double imc = calcularIMC(kilos, altura);
        crearCuerpo(cuerposRef, correo, kilos, altura, imc);
        }else {
            //Actualizar Campos
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("cuerpos");
            usersRef.orderByChild("correo").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Obtener la referencia al nodo del usuario
                            DatabaseReference usuarioRef = usersRef.child(userSnapshot.getKey());

                                // Actualizar los valores en la base de datos
                                usuarioRef.child("altura").setValue(altura);
                                usuarioRef.child("kilos").setValue(kilos);
                                usuarioRef.child("imc").setValue(calcularIMC(kilos, altura));

                            }


                    } else {
                        // Usuario no encontrado en la base de datos
                        Toast.makeText(MiCuerpoActivity.this, "Usuario no existente", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar cualquier error de base de datos aquí, si es necesario
                    Toast.makeText(MiCuerpoActivity.this, "Error en la consulta de cuerpos", Toast.LENGTH_SHORT).show();
                }
            });
        }
        double imcf = calcularIMC(kilos, altura);
        String imcfString = String.valueOf(imcf);
        cIMC.setText(imcfString);

    }

    //Crear cuerpos en la bd
    private void crearCuerpo(DatabaseReference cuerposRef, String correo, String kilos, String altura, double imc) {
        // Crear un nuevo objeto Cuerpo
        Cuerpo nuevoCuerpo = new Cuerpo(correo, kilos, altura, imc);

        // Obtener la clave única para el nuevo cuerpo
        String cuerpoId = cuerposRef.push().getKey();

        // Guardar el nuevo cuerpo en la base de datos bajo la tabla "cuerpos"
        cuerposRef.child(cuerpoId).setValue(nuevoCuerpo);

        // Informar al usuario que los datos han sido añadidos exitosamente
        Toast.makeText(MiCuerpoActivity.this, "Datos añadidos exitosamente", Toast.LENGTH_SHORT).show();
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