package com.example.myapplication.utilities;

import android.util.Log;

import com.example.myapplication.DateTime;
import com.example.myapplication.Paciente;
import com.example.myapplication.Turno;
import com.example.myapplication.activities.BuscarPacienteActivity;
import com.example.myapplication.activities.MainActivity;

import java.util.ArrayList;
import java.util.Date;

public class Controller {
    public static String ActivitySelected;

    public static ArrayList<Paciente> PACIENTES;

    public static void Initialize() {
        PACIENTES = new ArrayList<Paciente>();
    }


    //----- PACIENTES -----

    public static void AgregarPaciente(String nombre, String telefono, String instagram)
    {
        Paciente paciente = new Paciente(nombre, telefono, instagram);
        PACIENTES.add(paciente);

        MainActivity.DBPacientes.addHandler(paciente);
    }

    public static void ModificarPaciente(String nombreModificado, String telefono, String instagram)
    {
        GetPaciente(BuscarPacienteActivity.PacienteSeleccionado).ModificarPaciente(nombreModificado, telefono, instagram);
    }

    public static Paciente GetPaciente(String nombrePaciente)
    {
        for (int i = 0; i < PACIENTES.size(); i++)
        {
            if (PACIENTES.get(i).getNombre().equals(nombrePaciente))
            {
                return PACIENTES.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Turno> GetTurnosDelPaciente()
    {
        return GetPaciente(BuscarPacienteActivity.PacienteSeleccionado).getTurnos();
    }

    public static Turno GetTurnoPacienteAt(int index)
    {
        return GetPaciente(BuscarPacienteActivity.PacienteSeleccionado).GetTurnoAt(index);
    }

    public static void SetTurnosPacientes()
    {
        ArrayList<Turno> TURNOS = MainActivity.DBTurnos.loadHandler();
        Log.e("Cantidad de turnos", String.valueOf(TURNOS.size()));
        Log.e("Turnos ID", String.valueOf(Turno.NEXT_ID));
        for (int i = 0; i < TURNOS.size(); i++)
        {
            for (int x = 0; x < PACIENTES.size(); x++)
            {
                if (TURNOS.get(i).getPaciente().equals(PACIENTES.get(x).getNombre()))
                {
                    PACIENTES.get(x).AgregarTurnoOrdenado(TURNOS.get(i));
                }
            }
        }
    }

    //----- TURNOS -----

    public static boolean hayAlMenosUnTurnoEnLaFecha()
    {
        for (int i = 0; i < PACIENTES.size(); i++)
        {
            ArrayList<Turno> TurnosPacienteActual = PACIENTES.get(i).getTurnos();
            for (int x = 0; x < TurnosPacienteActual.size(); x++)
            {
                if (DateTime.isTheSelectedDate(TurnosPacienteActual.get(x).getFecha()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static void CheckTurnosPasados()
    {
        for (int i = 0; i < PACIENTES.size(); i++)
        {
            ArrayList<Turno> TurnosPacienteActual = PACIENTES.get(i).getTurnos();

            for (int x = 0; x < TurnosPacienteActual.size(); x++)
            {
                if (TurnosPacienteActual.get(x).isAOldDate())
                {
                    MainActivity.DBTurnos.deleteHandler(TurnosPacienteActual.get(x).getID());
                    TurnosPacienteActual.remove(x);
                }
            }
        }
    }

    public static void GuardarTurnos()
    {
        for (int i = 0; i < PACIENTES.size(); i++)
        {
            ArrayList<Turno> TurnosPacienteActual = PACIENTES.get(i).getTurnos();
            for (int x = 0; x < TurnosPacienteActual.size(); x++)
            {
                MainActivity.DBTurnos.addHandler(TurnosPacienteActual.get(x));
            }
        }
    }

    public static ArrayList<Turno> GetTurnosOrdenadosDelDia()
    {
        ArrayList<Turno> TurnosOrdenados = new ArrayList<>();

        for (int i = 0; i < PACIENTES.size(); i++)
        {
            Paciente paciente = PACIENTES.get(i);
            TurnosOrdenados.addAll(paciente.GetTurnosOrdenadosDelDia());
        }

        return TurnosOrdenados;
    }

    public static boolean HayTurnoConLaFechaYHora(String fecha, String hora)
    {
        for (int i = 0; i < PACIENTES.size(); i++)
        {
            if (PACIENTES.get(i).TieneTurnoEnElDia(fecha, hora))
                return true;
        }
        return false;
    }

    public static void EliminarTurno(int index)
    {
        Paciente paciente = GetPaciente(BuscarPacienteActivity.PacienteSeleccionado);
        paciente.EliminarTurno(index);
        Locator.StaticLocator.mainActivity.GuardarTurnosDataBase();
    }
}
