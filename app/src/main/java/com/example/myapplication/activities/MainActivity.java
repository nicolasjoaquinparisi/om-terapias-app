package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DateTime;
import com.example.myapplication.utilities.Controller;
import com.example.myapplication.R;
import com.example.myapplication.databases.DBPacientesHelper;
import com.example.myapplication.databases.DBTurnosHelper;
import com.example.myapplication.utilities.Locator;

public class MainActivity extends AppCompatActivity
{
    public static DBPacientesHelper DBPacientes;
    public static DBTurnosHelper DBTurnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller.Initialize();
        Initialize();
        setCalendarViewDateChangeListener();
    }

    private void Initialize()
    {
        Locator.StaticLocator.mainActivity = this;

        DBPacientes = new DBPacientesHelper(this, null, null, 1);
        DBTurnos = new DBTurnosHelper(this, null, null, 1);

        Controller.PACIENTES = DBPacientes.loadHandler();

        Controller.SetTurnosPacientes();
        CheckearTurnosPasados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public void onClickNuevoPaciente(MenuItem item)
    {
        Intent intent = new Intent(this, NuevoPacienteActivity.class);
        startActivity(intent);
    }

    public void onClickModificarPaciente(MenuItem item)

    {
        Controller.ActivitySelected = "Modificar paciente";
        buscarPaciente();
    }

    public void onClickVerPaciente(MenuItem item)
    {
        Controller.ActivitySelected = "Ver paciente";
        buscarPaciente();
    }

    public void onClickNuevoTurno(MenuItem item)
    {
        Controller.ActivitySelected = "Nuevo turno";
        buscarPaciente();
    }

    public void onClickModificarTurno(MenuItem item)
    {
        Controller.ActivitySelected = "Modificar turnos";
        buscarPaciente();
    }

    public void onClickEliminarTurno(MenuItem item)
    {
        Controller.ActivitySelected = "Eliminar turno";
        buscarPaciente();
    }

    private void setCalendarViewDateChangeListener()
    {
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                DateTime.SelectedDate = i2 + "/" + (i1+1) + "/" + i;
                if (Controller.hayAlMenosUnTurnoEnLaFecha())
                {
                    verTurnosDelDia();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No hay turnos en la fecha.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buscarPaciente()
    {
        Intent intent;
        intent = new Intent(this, BuscarPacienteActivity.class);
        startActivity(intent);
    }

    private void verTurnosDelDia()
    {
        Intent intent = new Intent(this, VerTurnosDelDiaActivity.class);
        startActivity(intent);
    }

    private void CheckearTurnosPasados()
    {
        Controller.CheckTurnosPasados();
        GuardarTurnosDataBase();
    }

    public void GuardarTurnosDataBase()
    {
        deleteDatabase("omterapiasturnos.db");
        DBTurnos = new DBTurnosHelper(this, null, null, 1);
        Controller.GuardarTurnos();
    }
}