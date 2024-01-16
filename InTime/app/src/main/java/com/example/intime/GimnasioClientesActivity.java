package com.example.intime;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GimnasioClientesActivity extends AppCompatActivity {
    TextView nombre, direccion, telefono, correoElect;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gimnasio_clientes);

        // Obtener la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("gimnasios");

        // Inicializar TextViews
        nombre = findViewById(R.id.nombreGym);
        direccion = findViewById(R.id.direccionGym);
        telefono = findViewById(R.id.telefonoGym);
        correoElect = findViewById(R.id.correoElectrGym);

        // Obtener el código de gimnasio de la sesión
        String codGymSesion = Sesion.getInstancia().getCodGym();

        // Verificar si el código de gimnasio de la sesión no está vacío
        if (!codGymSesion.isEmpty()) {
            // Realizar la consulta en la base de datos
            Query query = databaseReference.orderByChild("codGym").equalTo(codGymSesion);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Si se encuentra el gimnasio, asignar los valores a los TextViews
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Gimnasio gimnasio = snapshot.getValue(Gimnasio.class);
                            if (gimnasio != null) {
                                nombre.setText(gimnasio.getNombre());
                                direccion.setText(gimnasio.getDireccion());
                                telefono.setText(gimnasio.getTelefono());
                                correoElect.setText(gimnasio.getCorreoElectronico());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
