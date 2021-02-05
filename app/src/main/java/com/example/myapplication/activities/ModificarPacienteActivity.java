package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.utilities.Controller;
import com.example.myapplication.utilities.Locator;
import com.example.myapplication.Paciente;
import com.example.myapplication.R;

public class ModificarPacienteActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_paciente);

        Initialize();
    }

    private void Initialize()
    {
        TextView textView = findViewById(R.id.textViewNombrePacienteModificar);
        textView.setText("Modificar paciente - " + BuscarPacienteActivity.PacienteSeleccionado);
        Paciente paciente = Controller.GetPaciente(BuscarPacienteActivity.PacienteSeleccionado);
        EditText editText = findViewById(R.id.editTextNombreModificar);
        editText.setText(paciente.getNombre());

        editText = findViewById(R.id.editTextTelefonoModificar);
        editText.setText(paciente.getTelefono());

        editText = findViewById(R.id.editTextInstagramModificar);
        editText.setText(paciente.getInstagram());
    }

    public void OnButtonAceptarClick(View view)
    {
        EditText editText = findViewById(R.id.editTextNombreModificar);
        String nombre = editText.getText().toString();

        editText = findViewById(R.id.editTextTelefonoModificar);
        String telefono = editText.getText().toString();

        editText = findViewById(R.id.editTextInstagramModificar);
        String instagram = editText.getText().toString();

        Controller.ModificarPaciente(nombre, telefono, instagram);

        Locator.StaticLocator.buscarPacienteActivity.Initialize();

        finish();
    }

    public void OnButtonCancelarClick(View view)
    {
        finish();
    }
}