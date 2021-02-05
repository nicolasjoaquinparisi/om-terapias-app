package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DateTime;
import com.example.myapplication.utilities.Controller;
import com.example.myapplication.Paciente;
import com.example.myapplication.R;
import com.example.myapplication.Turno;

import java.util.ArrayList;

public class VerPacienteActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_paciente);
        Initialize();
    }

    private void Initialize()
    {
        Paciente paciente = Controller.GetPaciente(BuscarPacienteActivity.PacienteSeleccionado);

        TextView textViewNombrePaciente = findViewById(R.id.textViewNombrePaciente);
        TextView textViewTelefonoPaciente = findViewById(R.id.textViewTelefonoPaciente);
        TextView textViewInstagramPaciente = findViewById(R.id.textViewInstagramPaciente);

        textViewNombrePaciente.setText(paciente.getNombre());
        textViewTelefonoPaciente.setText(paciente.getTelefono());
        textViewInstagramPaciente.setText(paciente.getInstagram());

        ArrayList<Turno> Turnos = Controller.GetTurnosDelPaciente();

        ArrayList<TextView>textViews = new ArrayList<TextView>();
        TextView textView1 = findViewById(R.id.textViewInformacionPacienteTurno1);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno2);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno3);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno4);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno5);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno6);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno7);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionPacienteTurno8);
        textViews.add(textView1);

        ArrayList<ImageView> ImageViews = new ArrayList<ImageView>();
        ImageView imageView = findViewById(R.id.imageViewVerPacienteTurno1);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno2);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno3);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno4);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno5);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno6);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno7);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewVerPacienteTurno8);
        ImageViews.add(imageView);

        for (int i = 0; i < textViews.size(); i++)
        {
            textViews.get(i).setText("");
            ImageViews.get(i).setVisibility(ImageView.INVISIBLE);
        }

        for (int i = 0; i < Turnos.size(); i++)
        {
            textViews.get(i).setText(Turnos.get(i).getPaciente() + " tiene sesión de " + Turnos.get(i).getSesion() + "el día" + Turnos.get(i).getDate() + " a las " +
                                     DateTime.formatTimeFrom(Turnos.get(i).getHour(), Turnos.get(i).getMinutes()) + ".");

            ImageViews.get(i).setVisibility(ImageView.VISIBLE);
            switch (Turnos.get(i).getSesion())
            {
                case "Masajes":
                    ImageViews.get(i).setImageResource(R.drawable.ic_masaje);
                    break;
                case "Reiki":
                    ImageViews.get(i).setImageResource(R.drawable.ic_reiki);
                    break;
                case "Sanacion Arcturiana":
                case "Sanación Arcturiana":
                    ImageViews.get(i).setImageResource(R.drawable.ic_sanacion_arcturiana);
                    break;
                case "Aqualead":
                    ImageViews.get(i).setImageResource(R.drawable.ic_aqualead);
                    break;
            }
        }
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