package com.example.tpdm_u2_practica2_maldonado;

import android.app.DatePickerDialog;
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
import android.widget.Spinner;

import java.util.Calendar;

public class Main6Activity extends AppCompatActivity {
    EditText idseguro,descripcion,fecha;
    String telefono;
    Spinner tipo;
    Button regresar,insertar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Main6Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        idseguro=findViewById(R.id.idSeguro);
        descripcion=findViewById(R.id.descripcion);

        tipo=findViewById(R.id.tiposeg);

        fecha=findViewById(R.id.fechaSeguro);

        regresar=findViewById(R.id.regresarP);
        insertar =findViewById(R.id.insertarP);
        Bundle parametrosS = getIntent().getExtras();

        telefono=(parametrosS.getString("TELEFONO"));

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarPro();
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
                        Main6Activity.this,
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
    private void insertarPro() {

        Seguro seguro = new Seguro(this);
        String mensaje;
        boolean respuesta = seguro.insertar(new Seguro(idseguro.getText().toString(),descripcion.getText().toString(),fecha.getText().toString(),telefono,tipo.getSelectedItemPosition()));
        if(respuesta){
            mensaje = "se pudo insertar el seguro "+descripcion.getText().toString();
        }else{
            mensaje = "Error! no se pudo crear el seguro, respuesta de SQLITE: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
}
