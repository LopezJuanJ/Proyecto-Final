package com.example.intime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CuerpoDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CuerpoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertCuerpo(Cuerpo cuerpo) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CORREO, cuerpo.getCorreo());
        values.put(DatabaseHelper.COLUMN_KILOS, cuerpo.getKilos());
        values.put(DatabaseHelper.COLUMN_ALTURA, cuerpo.getAltura());
        values.put(DatabaseHelper.COLUMN_IMC, cuerpo.getImc());

        return database.insert(DatabaseHelper.TABLE_CUERPO, null, values);
    }

    public Cuerpo getCuerpo(String correo) {
        Cuerpo cuerpo = null;
        Cursor cursor = null;

        try {
            cursor = database.query(
                    DatabaseHelper.TABLE_CUERPO,
                    null,
                    DatabaseHelper.COLUMN_CORREO + "=?",
                    new String[]{correo},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                cuerpo = new Cuerpo(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CORREO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_KILOS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ALTURA)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMC))
                );
            }
        } catch (IllegalArgumentException e) {
            // Manejar la excepción de columna no encontrada
            e.printStackTrace(); // O utiliza un logger para registrar la excepción
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cuerpo;
    }


    // Otros métodos según tus necesidades (actualizar, eliminar, obtener todos, etc.)
}

