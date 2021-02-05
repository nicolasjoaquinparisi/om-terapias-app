package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.utilities.Controller;
import com.example.myapplication.utilities.Locator;
import com.example.myapplication.R;

import java.util.ArrayList;

public class BuscarPacienteActivity extends AppCompatActivity
{
    public static String PacienteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_paciente);

        Initialize();

        Locator.StaticLocator.buscarPacienteActivity = this;
    }

    public void Initialize()
    {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < Controller.PACIENTES.size(); i++)
        {
            arrayList.add(Controller.PACIENTES.get(i).getNombre());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                PacienteSeleccionado = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent)
            {

            }
        });
    }

    public void onClickButtonAceptar(View view)
    {
        if (PacienteSeleccionado != null)
        {
            Intent intent = null;
            switch (Controller.ActivitySelected)
            {
                case "Ver paciente":
                    intent = new Intent(this, VerPacienteActivity.class);
                    break;
                case "Modificar paciente":
                    intent = new Intent(this, ModificarPacienteActivity.class);
                    break;
                case "Nuevo turno":
                    intent = new Intent(this, NuevoTurnoActivity.class);
                    break;
                case "Modificar turnos":
                    intent = new Intent(this, ModificarTurnosActivity.class);
                    break;
                case "Eliminar turno":
                    intent = new Intent(this, EliminarTurnosActivity.class);
                    break;
            }
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "No hay ningún paciente cargado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButtonCancelar(View view)
    {
        finish();
    }
}