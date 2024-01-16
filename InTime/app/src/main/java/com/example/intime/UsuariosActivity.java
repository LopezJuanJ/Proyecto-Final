package com.example.intime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView listViewUsuarios;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> usuariosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        // Inicializar Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");

        // Inicializar vistas
        listViewUsuarios = findViewById(R.id.listViewUsuarios);
        usuariosList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuariosList);
        listViewUsuarios.setAdapter(adapter);
        // Agregar OnItemClickListener al ListView
        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarOpcionesEditarEliminar(position);
            }
        });
        // Obtener datos de Firebase
        obtenerUsuarios();
    }

    public void VolverAlMenuU(View view) {
        if (Sesion.getInstancia().getAdmin() == 0) {
            Intent intent = new Intent(UsuariosActivity.this, MenuCliActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(UsuariosActivity.this, MenuAdminActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void obtenerUsuarios() {
        mDatabase.orderByChild("codGym").equalTo(Sesion.getInstancia().getCodGym())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        usuariosList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Adaptar el código según la estructura de tu base de datos
                            String nombre = snapshot.child("nombre").getValue(String.class);
                            String apellidos = snapshot.child("apellidos").getValue(String.class);
                            String correo = snapshot.child("correoElectronico").getValue(String.class);
                            usuariosList.add(nombre + " " + apellidos );
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(UsuariosActivity.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void mostrarOpcionesEditarEliminar(final int position) {
        // Puedes implementar un cuadro de diálogo o un menú contextual aquí
        // Por ejemplo, utilizando AlertDialog para mostrar las opciones
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(new CharSequence[]{"Editar", "Eliminar"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Editar
                                // Agrega el código para editar el elemento en la posición 'position'
                                editarUsuario(position);
                                break;
                            case 1: // Eliminar
                                // Agrega el código para eliminar el elemento en la posición 'position'
                                eliminarUsuario(position);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    // Método para editar el usuario en la posición especificada
    private void editarUsuario(int position) {
        // Aquí puedes implementar la lógica para editar el usuario en la posición 'position'
        // Por ejemplo, puedes abrir una nueva actividad para editar los detalles del usuario
        Toast.makeText(this, "Editar usuario en posición " + position, Toast.LENGTH_SHORT).show();
    }

    // Método para eliminar el usuario en la posición especificada
    private void eliminarUsuario(int position) {
        // Aquí puedes implementar la lógica para eliminar el usuario en la posición 'position'
        // Por ejemplo, puedes mostrar un cuadro de diálogo de confirmación antes de eliminar
        Toast.makeText(this, "Eliminar usuario en posición " + position, Toast.LENGTH_SHORT).show();
    }
}
