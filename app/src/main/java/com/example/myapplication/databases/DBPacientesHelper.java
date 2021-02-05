package com.example.myapplication.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.myapplication.Paciente;

import java.util.ArrayList;

public class DBPacientesHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "omterapiaspacientes.db";

    public static final String TABLE_NAME_PACIENTES = "PACIENTES";
    public static final String COLUMN_NOMBRE = "Nombre";
    public static final String COLUMN_TELEFONO = "Telefono";
    public static final String COLUMN_INSTAGRAM = "Instagram";

    //initialize the database
    public DBPacientesHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE_PACIENTES = "CREATE TABLE " +
                        TABLE_NAME_PACIENTES +
                        " (" +
                        COLUMN_NOMBRE + " STRING PRIMARY KEY," +
                        COLUMN_TELEFONO + " TEXT," +
                        COLUMN_INSTAGRAM + " TEXT" +
                        ")";

        db.execSQL(CREATE_TABLE_PACIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {

    }

    public ArrayList<Paciente> loadHandler()
    {
        ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
        String query = "Select*FROM " + TABLE_NAME_PACIENTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            String nombre = cursor.getString(0);
            String telefono = cursor.getString(1);
            String instagram = cursor.getString(2);
            Paciente paciente = new Paciente(nombre, telefono, instagram);
            pacientes.add(paciente);
        }
        cursor.close();
        db.close();
        return pacientes;
    }

    public void addHandler(Paciente paciente)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, paciente.getNombre());
        values.put(COLUMN_TELEFONO, paciente.getTelefono());
        values.put(COLUMN_INSTAGRAM, paciente.getInstagram());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_PACIENTES, null, values);
        db.close();
    }

    public Paciente findHandler(String nombrePaciente)
    {
        String query = "Select * FROM " + TABLE_NAME_PACIENTES + " WHERE " + COLUMN_NOMBRE + " = " + "'" + nombrePaciente + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Paciente paciente = new Paciente();
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            paciente.setNombre(cursor.getString(0));
            paciente.setTelefono(cursor.getString(1));
            paciente.setInstagram(cursor.getString(2));
            cursor.close();
        }
        else
        {
            paciente = null;
        }
        db.close();
        return paciente;
    }

    public void updateHandler(String nombre, String telefono, String instagram, String nombreModificado)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME_PACIENTES +
                     " SET " +
                     COLUMN_NOMBRE + " = ?, " +
                     COLUMN_TELEFONO + "= ? , " +
                     COLUMN_INSTAGRAM + " = ? " +
                    " WHERE " + COLUMN_NOMBRE + " = ?";
        db.execSQL(sql, new String[]{nombreModificado, telefono, instagram, nombre});
    }
}
