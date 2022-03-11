package com.example.mirelojprogramable;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Estado3 extends Fragment implements View.OnClickListener, SensorEventListener {
   private SensorManager sensorManager;
    private OnFragmentInteractionListener mListener;
    public RadioButton tono1, tono5, tono2, tono3, tono4, tono6, tono7;
    private OutputStream outputStream;
    private String sensorSelected;

    public Estado3() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputStream = ((ConexionActivity)getActivity()).getOutputStream();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_estado3, container, false);
        tono1 = (RadioButton) myView.findViewById(R.id.tono1);
        tono1.setOnClickListener(this);
        tono5 = (RadioButton) myView.findViewById(R.id.tono5);
        tono5.setOnClickListener(this);
        tono2 = (RadioButton) myView.findViewById(R.id.tono2);
        tono2.setOnClickListener(this);
        tono3 = (RadioButton) myView.findViewById(R.id.tono3);
        tono3.setOnClickListener(this);
        tono4 = (RadioButton) myView.findViewById(R.id.tono4);
        tono4.setOnClickListener(this);
        tono6 = (RadioButton) myView.findViewById(R.id.tono6);
        tono6.setOnClickListener(this);
        tono7 = (RadioButton) myView.findViewById(R.id.tono7);
        tono7.setOnClickListener(this);

        return myView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        String chain = "h";
        Context context = Objects.requireNonNull(getContext()).getApplicationContext();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        deviceSensors.forEach(System.out::println);
        System.out.println("----");
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (v == tono1)
            setSensorEventListener(sensor, "Acelerometro>", Sensor.TYPE_ACCELEROMETER);

        if (v == tono2)
            setSensorEventListener(sensor, "Podometro>", Sensor.TYPE_POSE_6DOF);

        if (v == tono3)
            setSensorEventListener(sensor, "Orientacion>", Sensor.TYPE_ORIENTATION);

        if (v == tono4)
            setSensorEventListener(sensor, "Luz Ambiente>", Sensor.TYPE_LIGHT);

        if (v == tono5)
            setSensorEventListener(sensor, "Gravedad>", Sensor.TYPE_GRAVITY);

        if (v == tono6)
            setSensorEventListener(sensor, "Giroscopio>", Sensor.TYPE_GYROSCOPE);

        if (v == tono7)
            setSensorEventListener(sensor, "Rotacion Geo>", Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);

    }

    private void setSensorEventListener(Sensor sensor, String sensorTitle, int TYPE_LIGHT) {
        sensorManager.getDefaultSensor(TYPE_LIGHT);
        sensorManager.unregisterListener(this, sensor);
        sensorSelected = sensorTitle;
        sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, 500);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // TODO Agregar la lista de sensores, averiguar que datos arroja el sensor, controlar excepciones locas y pantalla en blanco
        // TODO Refactorizar codigo
        // TODO Implementar NFC
        try {
            Thread.sleep(1000);

            String chain = "h" + sensorSelected + ">" + Arrays.toString(sensorEvent.values) + "<";
            outputStream.write(chain.getBytes());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
