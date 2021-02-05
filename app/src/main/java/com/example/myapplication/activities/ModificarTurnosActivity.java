package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DateTime;
import com.example.myapplication.Paciente;
import com.example.myapplication.R;
import com.example.myapplication.Turno;
import com.example.myapplication.utilities.Controller;

import java.util.ArrayList;

public class ModificarTurnosActivity extends AppCompatActivity
{
    public static Turno SelectedTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_turnos);
        Initialize();

        ModificarTurnoActivity.modificarTurnosActivity = this;
    }

    private void Initialize()
    {
        SelectedTurno = null;

        Paciente paciente = Controller.GetPaciente(BuscarPacienteActivity.PacienteSeleccionado);

        TextView textViewNombrePaciente = findViewById(R.id.textViewNombrePaciente);
        TextView textViewTelefonoPaciente = findViewById(R.id.textViewTelefonoPaciente);
        TextView textViewInstagramPaciente = findViewById(R.id.textViewInstagramPaciente);

        textViewNombrePaciente.setText(paciente.getNombre());
        textViewTelefonoPaciente.setText(paciente.getTelefono());
        textViewInstagramPaciente.setText(paciente.getInstagram());

        ShowUI();
    }

    public void ShowUI()
    {
        ArrayList<Turno> Turnos = Controller.GetTurnosDelPaciente();

        //----- Se arma un array con las TextViews -----
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

        //----- Se arma un array con las ImageViews -----
        ArrayList<ImageView> ImageViews = new ArrayList<ImageView>();
        ImageView imageView = findViewById(R.id.imageViewTurno1);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno2);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno3);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno4);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno5);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno6);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno7);
        ImageViews.add(imageView);
        imageView = findViewById(R.id.imageViewTurno8);
        ImageViews.add(imageView);

        //----- Se arma un array con los Buttons -----
        ArrayList<Button> Buttons = new ArrayList<>();
        Button button = findViewById(R.id.buttonModificarTurno1);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno2);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno3);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno4);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno5);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno6);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno7);
        Buttons.add(button);
        button = findViewById(R.id.buttonModificarTurno8);
        Buttons.add(button);

        //----- Se ocultan los TextViews, ImageViews y los Buttons -----
        for (int i = 0; i < textViews.size(); i++)
        {
            textViews.get(i).setText("");
            ImageViews.get(i).setVisibility(ImageView.INVISIBLE);
            Buttons.get(i).setVisibility(Button.INVISIBLE);
        }

        //----- Mostrar todos los turnos -----
        for (int i = 0; i < Turnos.size(); i++)
        {
            textViews.get(i).setText("Sesión de " + Turnos.get(i).getSesion() + " el día " + Turnos.get(i).getDate() + " a las " +
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

            Buttons.get(i).setVisibility(Button.VISIBLE);
        }
    }

    public void OnButtonCancelarClick(View view)
    {
        finish();
    }

    public void OnButtonModificarTurnoClick(View view)
    {
        int buttonId = view.getId();
        buttonId -= 2131230808;

        SelectedTurno = Controller.GetTurnoPacienteAt(buttonId);

        Intent intent = new Intent(this, ModificarTurnoActivity.class);
        startActivity(intent);
    }
}