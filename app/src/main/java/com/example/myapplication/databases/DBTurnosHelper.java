package com.example.myapplication.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.myapplication.DateTime;
import com.example.myapplication.Turno;

import java.util.ArrayList;

public class DBTurnosHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "omterapiasturnos.db";

    public static final String TABLE_NAME_TURNOS = "TURNOS";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_PACIENTE = "Paciente";
    public static final String COLUMN_SESION = "Sesion";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_MONTH = "Month";
    public static final String COLUMN_YEAR = "Year";
    public static final String COLUMN_HOUR = "Hour";
    public static final String COLUMN_MINUTES = "Minutes";
    public static final String COLUMN_ESTAPAGO = "EstaPago";

    //initialize the database
    public DBTurnosHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE_TURNOS = "CREATE TABLE " +
                                    TABLE_NAME_TURNOS +
                                    " (" +
                                    COLUMN_ID + " STRING PRIMARY KEY," +
                                    COLUMN_PACIENTE + " TEXT," +
                                    COLUMN_SESION + " TEXT," +
                                    COLUMN_DAY + " TEXT," +
                                    COLUMN_MONTH + " TEXT," +
                                    COLUMN_YEAR + " TEXT," +
                                    COLUMN_HOUR + " TEXT," +
                                    COLUMN_MINUTES + " TEXT," +
                                    COLUMN_ESTAPAGO + " TEXT" +
                                    ")";
        db.execSQL(CREATE_TABLE_TURNOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {

    }

    public ArrayList<Turno> loadHandler()
    {
        ArrayList<Turno> turnos = new ArrayList<Turno>();
        String query = "Select*FROM " + TABLE_NAME_TURNOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            String id = cursor.getString(0);
            String paciente = cursor.getString(1);
            String sesion = cursor.getString(2);
            int day = Integer.parseInt(cursor.getString(3));
            int month = Integer.parseInt(cursor.getString(4));
            int year = Integer.parseInt(cursor.getString(5));
            int hour = Integer.parseInt(cursor.getString(6));
            int minutes = Integer.parseInt(cursor.getString(7));
            boolean estaPago = Boolean.parseBoolean(cursor.getString(8));

            int[] fechaYHora = new int[] {day, month, year, hour, minutes};

            int ID = Integer.parseInt(id);
            Turno.NEXT_ID = ID + 1;

            Turno turno = new Turno(ID, sesion, fechaYHora, paciente, estaPago);

            turnos.add(turno);
        }
        cursor.close();
        db.close();
        return turnos;
    }

    public void addHandler(Turno turno)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, String.valueOf(turno.getID()));
        values.put(COLUMN_PACIENTE, turno.getPaciente());
        values.put(COLUMN_SESION, turno.getSesion());
        values.put(COLUMN_DAY, String.valueOf(turno.getDay()));
        values.put(COLUMN_MONTH, String.valueOf(turno.getMonth()));
        values.put(COLUMN_YEAR, String.valueOf(turno.getYear()));
        values.put(COLUMN_HOUR, String.valueOf(turno.getHour()));
        values.put(COLUMN_MINUTES, String.valueOf(turno.getMinutes()));
        values.put(COLUMN_ESTAPAGO, turno.getEstaPago());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_TURNOS, null, values);
        db.close();
    }

    public void updateTurno(String id, String tipoDeSesion, String estaPago)
    {
        Log.e("DB Turnos", "Modificando datos: " + tipoDeSesion + " y " + estaPago);
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME_TURNOS +
                " SET " +
                COLUMN_SESION + "= ? , " +
                COLUMN_ESTAPAGO + " = ? " +
                " WHERE " + COLUMN_ID + " = ?";
        db.execSQL(sql, new String[]{tipoDeSesion, estaPago, id});
    }

    public void UpdateTurnoPaciente(String id, String nombrePaciente)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME_TURNOS +
                " SET " +
                COLUMN_PACIENTE + " = ? " +
                " WHERE " + COLUMN_ID + " = ?";
        db.execSQL(sql, new String[]{nombrePaciente, id});
    }

    public void deleteHandler(int ID)
    {
        String id = String.valueOf(ID);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from TURNOS  WHERE Id = '" +id+ "'");
    }
}