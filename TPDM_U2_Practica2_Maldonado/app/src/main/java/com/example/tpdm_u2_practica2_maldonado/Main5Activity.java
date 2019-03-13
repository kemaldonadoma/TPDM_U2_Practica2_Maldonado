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
import android.widget.Spinner;

import java.util.Calendar;

public class Main5Activity extends AppCompatActivity {
    EditText idseguro,descripcion,fecha;
    String telefono;
    Spinner tipo;
    String id;
    Button regresar,actualizar,eliminar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Main5Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        idseguro=findViewById(R.id.idSeguroAc);
        descripcion=findViewById(R.id.descripcionAc);

        tipo=findViewById(R.id.tiposegAc);

        fecha=findViewById(R.id.fechaSeguroAc);

        regresar=findViewById(R.id.regresarAc);
        actualizar =findViewById(R.id.actualizarAc);
        eliminar = findViewById(R.id.eliminarAc);



        Bundle parametrosS = getIntent().getExtras();

        telefono=(parametrosS.getString("TELEFONO"));
        idseguro.setText(parametrosS.getString("IDSEGURO"));
        id = parametrosS.getString("IDSEGURO");
        descripcion.setText(parametrosS.getString("DESCRIPCION"));
        fecha.setText(parametrosS.getString("FECHA"));
        tipo.setSelection(parametrosS.getInt("TIPO"));

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarSegu();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSeg();
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
                        Main5Activity.this,
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

    private void eliminarSeg() {
        Seguro seguro = new Seguro(this);
        String mensaje;
        boolean respuesta = seguro.eliminar(idseguro.getText().toString());
        if(respuesta){
            mensaje = "se pudo eliminar el seguro ";

        }else{
            mensaje = "Error! no se pudo eliminar el seguro, respuesta de SQLITE: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    private void actualizarSegu() {


        Seguro seguro = new Seguro(this);
        String mensaje;
        boolean respuesta = seguro.actualizar(new Seguro(id,descripcion.getText().toString(),fecha.getText().toString(),telefono,tipo.getSelectedItemPosition()));
        if(respuesta){
            mensaje = "se pudo actualizar el seguro "+descripcion.getText().toString();
        }else{
            mensaje = "Error! no se pudo actualizar el seguro, respuesta de SQLITE: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
}
