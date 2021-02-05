package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DateTime;
import com.example.myapplication.utilities.Controller;
import com.example.myapplication.fragments.DatePickerFragment;
import com.example.myapplication.Paciente;
import com.example.myapplication.R;
import com.example.myapplication.fragments.TimePickerFragment;
import com.example.myapplication.Turno;

import java.util.ArrayList;

public class NuevoTurnoActivity extends AppCompatActivity {
    private String tipoDeSesion;
    public static String fecha = "";
    public static String time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_turno);
        Initialize();
    }

    private void Initialize() {
        //InitializeSelectedDateAndTime();

        Spinner spinner = findViewById(R.id.tipoDeSesion);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Masajes");
        arrayList.add("Reiki");
        arrayList.add("Sanación Arcturiana");
        arrayList.add("Aqualead");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDeSesion = parent.getItemAtPosition(position).toString();
                ImageView imageView = findViewById(R.id.imageView4);
                switch (tipoDeSesion) {
                    case "Masajes":
                        imageView.setImageResource(R.drawable.ic_masaje);
                        break;
                    case "Reiki":
                        imageView.setImageResource(R.drawable.ic_reiki);
                        break;
                    case "Sanación Arcturiana":
                        imageView.setImageResource(R.drawable.ic_sanacion_arcturiana);
                        break;
                    case "Aqualead":
                        imageView.setImageResource(R.drawable.ic_aqualead);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ResetActivityClassVariables()
    {
        fecha = "";
        time = "";

        DatePickerFragment.nuevoTurnoActivity = null;
        TimePickerFragment.nuevoTurnoActivity = null;
    }

    public void onButtonNuevoTurnoClick(View view)
    {
        if (!fecha.isEmpty() && !time.isEmpty())
        {
            if (!Controller.HayTurnoConLaFechaYHora(fecha, time))
            {
                CheckBox checkBox = findViewById(R.id.checkBoxEstaPago);
                boolean estaPago = checkBox.isChecked();

                Paciente paciente = Controller.GetPaciente(BuscarPacienteActivity.PacienteSeleccionado);

                DateTime fechaYHora = DateTime.parseDateTime(fecha, time);

                int ID = Turno.GenerateID();

                Turno turno = new Turno(ID, tipoDeSesion, fechaYHora, paciente.getNombre(), estaPago);

                paciente.AgregarTurnoOrdenado(turno);
                MainActivity.DBTurnos.addHandler(turno);

                ResetActivityClassVariables();

                finish();
            }
            else
            {
                Toast.makeText(this, "Ya existe un turno con esa fecha y hora.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if (fecha.isEmpty() & !time.isEmpty())
            {
                Toast.makeText(this, "Falta ingresar una fecha.", Toast.LENGTH_SHORT).show();
            }
            if (!fecha.isEmpty() & time.isEmpty())
            {
                Toast.makeText(this, "Falta ingresar la hora.", Toast.LENGTH_SHORT).show();
            }
            if (fecha.isEmpty() & time.isEmpty())
            {
                Toast.makeText(this, "Falta ingresar la fecha y la hora", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onButtonCancelarClick(View view)
    {
        ResetActivityClassVariables();

        finish();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

        DatePickerFragment.nuevoTurnoActivity = this;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

        TimePickerFragment.nuevoTurnoActivity = this;
    }

    public void ChangeDateText(int day, int month, int year)
    {
        TextView dateText = findViewById(R.id.textViewFechaTurno);
        fecha = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
        dateText.setText(fecha);
    }

    public void ChangeTimeText(int minutes, int hours)
    {
        TextView timeText = findViewById(R.id.textViewHoraTurno);

        time = DateTime.formatTimeFrom(hours, minutes);

        timeText.setText(time);

    }
}