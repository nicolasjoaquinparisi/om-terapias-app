package com.example.myapplication;

import com.example.myapplication.utilities.Controller;

import java.util.Date;
import java.util.GregorianCalendar;

public class Turno
{
    public static int NEXT_ID = 0;

    private int ID;
    private String sesion;
    private DateTime fechaYHora;
    private String paciente;
    private boolean estaPago;

    //---GETTERS AND SETTERS
    public String getSesion()
    {
        return sesion;
    }

    public void setSesion(String sesion)
    {
        this.sesion = sesion;
    }

    public DateTime getFecha() {
        return this.fechaYHora;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public boolean getEstaPago() {
        return estaPago;
    }

    public void setEstaPago(boolean estaPago) {
        this.estaPago = estaPago;
    }

    public int getID() { return ID; }

    public void setID(int id) { this.ID = id; }

    public int getYear()
    {
        return this.fechaYHora.getYear();
    }

    public int getMonth()
    {
        return this.getFecha().getMonth();
    }

    public int getDay()
    {
        return this.getFecha().getDay();
    }

    public int getHour()
    {
        return this.getFecha().getHour();
    }

    public int getMinutes()
    {
        return this.getFecha().getMinutes();
    }

    public boolean isAOldDate()
    {
        return this.fechaYHora.isAOldDate();
    }

    public boolean timeIsBiggerThan(DateTime anotherDateTime)
    {
        return this.fechaYHora.timeIsBiggerThan(anotherDateTime);
    }

    //---METHODS
    public Turno(int id, String sesion, int[] fechaYHora, String paciente, boolean estaPago)
    {
        setID(id);
        setSesion(sesion);
        setPaciente(paciente);
        setEstaPago(estaPago);
        this.fechaYHora = new DateTime(fechaYHora);
    }

    public Turno (int id, String sesion, DateTime fechaYHora, String paciente, boolean estaPago)
    {
        setID(id);
        setSesion(sesion);
        setPaciente(paciente);
        setEstaPago(estaPago);
        this.setPaciente(paciente);
        this.fechaYHora = fechaYHora;
    }

    public static int GenerateID()
    {
        int id = NEXT_ID;
        NEXT_ID++;
        return id;
    }

    public String getTime()
    {
        return this.fechaYHora.getHour() + ":" + this.fechaYHora.getMinutes();
    }

    public String getDate()
    {
        return this.fechaYHora.getDateToString();
    }

    public void ModificarTurno(String sesion, boolean estaPago)
    {
        setSesion(sesion);
        setEstaPago(estaPago);
    }
}