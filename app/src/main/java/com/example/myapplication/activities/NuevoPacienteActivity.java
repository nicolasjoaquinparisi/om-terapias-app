package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.utilities.Controller;
import com.example.myapplication.R;

public class NuevoPacienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_paciente);
    }

    public void OnButtonAceptarClick(View view)
    {
        EditText editText = findViewById(R.id.editTextNombre);
        String nombre = editText.getText().toString();

        if (!nombre.isEmpty())
        {
            editText = findViewById(R.id.editTextTelefono);
            String telefono = editText.getText().toString();

            editText = findViewById(R.id.editTextInstagram);
            String instagram = editText.getText().toString();

            Controller.AgregarPaciente(nombre, telefono, instagram);

            finish();
        }
        else
        {
            Toast.makeText(this, "Falta ingresar el nombre del paciente.", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnButtonCancelarClick(View view)
    {
        finish();
    }
}