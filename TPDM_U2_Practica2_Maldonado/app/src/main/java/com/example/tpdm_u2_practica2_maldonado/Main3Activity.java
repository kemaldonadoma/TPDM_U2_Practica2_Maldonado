package com.example.tpdm_u2_practica2_maldonado;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Main3Activity extends AppCompatActivity {
    EditText telefono,nombre,domicilio,fecha;
    Button regresar,actualizar;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Main3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        telefono=findViewById(R.id.telefonoP2);
        nombre=findViewById(R.id.nombreP2);
        domicilio=findViewById(R.id.domicilioP2);
        fecha=findViewById(R.id.fechaP2);

        regresar=findViewById(R.id.regresarP2);
        actualizar=findViewById(R.id.actualizarP2);

        Bundle parametros = getIntent().getExtras();

        telefono.setText(parametros.getString("TELEFONO"));
        nombre.setText(parametros.getString("DOMICILIO"));
        domicilio.setText(parametros.getString("NOMBRE"));

        fecha.setText(parametros.getString("FECHA"));



        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPro();
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Main3Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyy/mm/dd: " + year + "/" + month + "/" + day);

                String date = year + "/" + month + "/" + day;
                fecha.setText(date);
            }
        };
    }
    private void actualizarPro() {
        Propietario propietario = new Propietario(this);
        String mensaje;
        boolean respuesta = propietario.actualizar(new Propietario(telefono.getText().toString(),nombre.getText().toString()
                ,domicilio.getText().toString(),fecha.getText().toString()));
        if(respuesta){
            mensaje = "se pudo actualizar el propietario "+nombre.getText().toString();
        }else{
            mensaje = "Error! no se pudo actualizar el propietario, respuesta de SQLITE: "+propietario.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }


}
