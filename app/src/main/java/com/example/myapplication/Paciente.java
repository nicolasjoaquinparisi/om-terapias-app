package com.example.myapplication;

import android.util.Log;

import com.example.myapplication.activities.MainActivity;

import java.util.ArrayList;

public class Paciente
{
    private String nombre;
    private String instagram;
    private String telefono;
    private ArrayList<Turno> turnos;

    //---GETTERS AND SETTERS
    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getInstagram()
    {
        return instagram;
    }

    public void setInstagram(String instagram)
    {
        this.instagram = instagram;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public ArrayList<Turno> getTurnos()
    {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos)
    {
        this.turnos = turnos;
    }

    //---Methods

    public Paciente()
    {
        turnos = new ArrayList<Turno>();
    }

    public Paciente(String nombre, String telefono, String instagram)
    {
        setNombre(nombre);
        setTelefono(telefono);
        setInstagram(instagram);
        turnos = new ArrayList<Turno>();
    }

    public Paciente(String nombre)
    {
        turnos = new ArrayList<Turno>();
    }

    public void AgregarTurnoOrdenado(Turno turno)
    {
        if (turnos.size() == 0)
        {
            turnos.add(turno);
        }
        else
        {
            int index = 0;

            int[] fechaYAhoraActual = new int[] {turnos.get(index).getDay(), turnos.get(index).getMonth(), turnos.get(index).getYear(),
                    turnos.get(index).getHour(), turnos.get(index).getMinutes()};
            DateTime horaActual = new DateTime(fechaYAhoraActual);

            while (index < turnos.size() && turno.timeIsBiggerThan(horaActual))
            {
                index++;
                if (index < turnos.size())
                {
                    fechaYAhoraActual = new int[] {turnos.get(index).getDay(), turnos.get(index).getMonth(), turnos.get(index).getYear(),
                            turnos.get(index).getHour(), turnos.get(index).getMinutes()};
                    horaActual = new DateTime(fechaYAhoraActual);
                }
            }
            turnos.add(index, turno);
        }
    }

    public void ModificarPaciente(String nombreModificado, String telefono, String instagram)
    {
        MainActivity.DBPacientes.updateHandler(this.nombre, this.telefono, this.instagram, nombreModificado);

        //Se modifican los datos del paciente
        setNombre(nombreModificado);
        setTelefono(telefono);
        setInstagram(instagram);

        //Se actualizan el nombre del paciente de cada turno
        for (int x = 0; x < turnos.size(); x++)
        {
            Turno t = turnos.get(x);
            t.setPaciente(this.nombre);

            MainActivity.DBTurnos.UpdateTurnoPaciente(String.valueOf(t.getID()), nombreModificado);
        }
    }

    public void EliminarTurno(int index)
    {
        turnos.remove(index);
    }

    public void EliminarTurno(Turno turno)
    {
        turnos.remove(turno);
    }

    public Turno GetTurnoAt(int index)
    {
        return turnos.get(index);
    }

    public void ModificarTurno(int id, String tipoDeSesion, boolean estaPago)
    {
        for (int i = 0; i < turnos.size(); i++)
        {
            if (turnos.get(i).getID() == id)
            {
                turnos.get(i).ModificarTurno(tipoDeSesion, estaPago);
                return;
            }
        }
    }

    public boolean TieneTurnoEnElDia(String fecha, String hora)
    {
        for (int x = 0; x < turnos.size(); x++)
        {
            DateTime fechaYHora = DateTime.parseDateTime(fecha, hora);

            if (fechaYHora.isEqualsTo(turnos.get(x).getFecha()))
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Turno> GetTurnosOrdenadosDelDia()
    {
        ArrayList<Turno> turnosDelDia = new ArrayList<>();

        for (int x = 0; x < turnos.size(); x++)
        {
            if (DateTime.isTheSelectedDate(turnos.get(x).getFecha()))
            {
                turnosDelDia.add(turnos.get(x));
            }
        }

        return turnosDelDia;
    }
}
