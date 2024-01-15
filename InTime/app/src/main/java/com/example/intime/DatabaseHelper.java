package com.example.intime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mi_base_de_datos";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_CUERPO = "cuerpo";
    public static final String COLUMN_CORREO = "correo";
    public static final String COLUMN_KILOS = "kilos";
    public static final String COLUMN_ALTURA = "altura";
    public static final String COLUMN_IMC = "imc";

    // Consulta SQL para crear la tabla
    private static final String CREATE_TABLE_CUERPO =
            "CREATE TABLE " + TABLE_CUERPO + " (" +
                    COLUMN_CORREO + " TEXT PRIMARY KEY," +
                    COLUMN_KILOS + " TEXT," +
                    COLUMN_ALTURA + " TEXT," +
                    COLUMN_IMC + " REAL)";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUERPO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de la base de datos si es necesario
    }
}

