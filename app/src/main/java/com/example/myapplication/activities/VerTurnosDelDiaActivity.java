package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DateTime;
import com.example.myapplication.utilities.Controller;
import com.example.myapplication.R;
import com.example.myapplication.Turno;

import java.util.ArrayList;

public class VerTurnosDelDiaActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_turnos_del_dia);
        Initialize();
    }

    private void Initialize()
    {
        ArrayList<Turno> Turnos = Controller.GetTurnosOrdenadosDelDia();

        TextView textView = findViewById(R.id.textViewTurnosDelDia);
        textView.setText("Turnos del día " + DateTime.SelectedDate);

        ArrayList<TextView> textViews = new ArrayList<TextView>();
        TextView textView1 = findViewById(R.id.textViewInformacionTurno1);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno2);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno3);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno4);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno5);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno6);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno7);
        textViews.add(textView1);
        textView1 = findViewById(R.id.textViewInformacionTurno8);
        textViews.add(textView1);

        ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
        ImageView imageView = findViewById(R.id.imageView8);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView9);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView10);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView11);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView12);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView14);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView15);
        imageViews.add(imageView);
        imageView = findViewById(R.id.imageView16);
        imageViews.add(imageView);

        for (int i = 0; i < textViews.size(); i++)
        {
            textViews.get(i).setText("");
            imageViews.get(i).setVisibility(ImageView.INVISIBLE);
        }

        for (int i = 0; i < Turnos.size(); i++)
        {
            textViews.get(i).setText(Turnos.get(i).getPaciente() + " tiene sesión de " + Turnos.get(i).getSesion() + " a las " +
                                     DateTime.formatTimeFrom(Turnos.get(i).getHour(), Turnos.get(i).getMinutes()) + ".");
            imageViews.get(i).setVisibility(ImageView.VISIBLE);
            switch (Turnos.get(i).getSesion())
            {
                case "Masajes":
                    imageViews.get(i).setImageResource(R.drawable.ic_masaje);
                    break;
                case "Reiki":
                    imageViews.get(i).setImageResource(R.drawable.ic_reiki);
                    break;
                case "Sanacion Arcturiana":
                case "Sanación Arcturiana":
                    imageViews.get(i).setImageResource(R.drawable.ic_sanacion_arcturiana);
                    break;
                case "Aqualead":
                    imageViews.get(i).setImageResource(R.drawable.ic_aqualead);
            }
        }
    }
}