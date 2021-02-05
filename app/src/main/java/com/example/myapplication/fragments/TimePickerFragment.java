package com.example.myapplication.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.activities.ModificarTurnoActivity;
import com.example.myapplication.activities.NuevoTurnoActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    public static NuevoTurnoActivity nuevoTurnoActivity;
    public static ModificarTurnoActivity modificarTurnoActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        if (nuevoTurnoActivity != null)
            nuevoTurnoActivity.ChangeTimeText(minute, hourOfDay);
        else
            modificarTurnoActivity.ChangeTimeText(minute, hourOfDay);
    }
}
