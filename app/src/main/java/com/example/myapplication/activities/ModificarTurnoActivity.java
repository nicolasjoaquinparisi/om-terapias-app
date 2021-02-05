package com.example.myapplication.activities;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.DateTime;
import com.example.myapplication.Paciente;
import com.example.myapplication.R;
import com.example.myapplication.Turno;
import com.example.myapplication.fragments.DatePickerFragment;
import com.example.myapplication.fragments.TimePickerFragment;
import com.example.myapplication.utilities.Controller;

import java.util.ArrayList;
import java.util.Date;

public class ModificarTurnoActivity extends AppCompatActivity {
    private String tipoDeSesion;
    public static String fecha;
    public static String time;
    public static ModificarTurnosActivity modificarTurnosActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_turno);
        Initialize();
    }

    private void Initialize() {
        //InitializeSelectedDateAndTime();

        Spinner spinner = findViewById(R.id.tipoDeSesionMT);
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
                ImageView imageView = findViewById(R.id.imageSesionMT);
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

        InitializeSelectedTurno();
    }

    private void InitializeSelectedTurno()
    {
        Turno t = ModificarTurnosActivity.SelectedTurno;

        Log.e("ID Turno", String.valueOf(t.getID()));

        Spinner spinner = findViewById(R.id.tipoDeSesionMT);
        ImageView imageView = findViewById(R.id.imageSesionMT);
        TextView dateText = findViewById(R.id.textViewFechaTurnoMT);
        TextView timeText = findViewById(R.id.textViewHoraTurnoMT);
        CheckBox checkBox = findViewById(R.id.checkBoxEstaPagoMT);

        switch (t.getSesion()) {
            case "Masajes":
                imageView.setImageResource(R.drawable.ic_masaje);
                spinner.setSelection(0);
                break;
            case "Reiki":
                imageView.setImageResource(R.drawable.ic_reiki);
                spinner.setSelection(1);
                break;
            case "Sanación Arcturiana":
                imageView.setImageResource(R.drawable.ic_sanacion_arcturiana);
                spinner.setSelection(2);
                break;
            case "Aqualead":
                imageView.setImageResource(R.drawable.ic_aqualead);
                spinner.setSelection(3);
                break;
        }

        dateText.setText(t.getDate());
        timeText.setText(t.getTime());
        checkBox.setChecked(t.getEstaPago());

        fecha = t.getDate();
        time = t.getTime();
    }

    private void ResetActivityClassVariables()
    {
        fecha = "";
        time = "";

        DatePickerFragment.modificarTurnoActivity = null;
        TimePickerFragment.modificarTurnoActivity = null;
    }

    public void onButtonModificarTurnoClick(View view) {
        if (!fecha.isEmpty() && !time.isEmpty()) {
            if (ModificarTurnosActivity.SelectedTurno.getFecha().isEqualsTo(DateTime.parseDateTime(fecha, time)))
            {
                crearTurno(false);
            }
            else
            {
                if (!Controller.HayTurnoConLaFechaYHora(fecha, time))
                {
                    crearTurno(true);
                }
                else
                {
                    Toast.makeText(this, "Ya existe un turno con esa fecha y hora.", Toast.LENGTH_SHORT).show();
                }
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

    private void crearTurno(boolean mustCreateID)
    {
        CheckBox checkBox = findViewById(R.id.checkBoxEstaPagoMT);
        boolean estaPago = checkBox.isChecked();

        Paciente paciente = Controller.GetPaciente(BuscarPacienteActivity.PacienteSeleccionado);

        DateTime fechaYHora = DateTime.parseDateTime(fecha, time);

        int id;
        if (mustCreateID)
        {
            id = Turno.GenerateID();

            Turno turno = new Turno(id, tipoDeSesion, fechaYHora, paciente.getNombre(), estaPago);

            paciente.EliminarTurno(ModificarTurnosActivity.SelectedTurno);
            paciente.AgregarTurnoOrdenado(turno);

            MainActivity.DBTurnos.deleteHandler(ModificarTurnosActivity.SelectedTurno.getID());
            MainActivity.DBTurnos.addHandler(turno);
        }
        else
        {
            Log.e("Modificando turno", String.valueOf(ModificarTurnosActivity.SelectedTurno.getID()));

            id = ModificarTurnosActivity.SelectedTurno.getID();

            paciente.ModificarTurno(id, tipoDeSesion, estaPago);

            MainActivity.DBTurnos.updateTurno(String.valueOf(id), tipoDeSesion, String.valueOf(estaPago));
        }

        modificarTurnosActivity.ShowUI();

        ResetActivityClassVariables();

        finish();
    }

    public void onButtonCancelarClick(View view)
    {
        ResetActivityClassVariables();

        finish();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

        DatePickerFragment.modificarTurnoActivity = this;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

        TimePickerFragment.modificarTurnoActivity = this;
    }

    public void ChangeDateText(int day, int month, int year)
    {
        TextView dateText = findViewById(R.id.textViewFechaTurnoMT);
        fecha = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
        dateText.setText(fecha);
    }

    public void ChangeTimeText(int minutes, int hours)
    {
        TextView timeText = findViewById(R.id.textViewHoraTurnoMT);

        time = DateTime.formatTimeFrom(hours, minutes);

        timeText.setText(time);

    }
}