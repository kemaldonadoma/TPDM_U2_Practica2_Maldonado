package com.example.tpdm_u2_practica2_maldonado;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    ListView listaSeg;
    Seguro vector[];
    TextView tel,nom,dom,fecha;
    Button regresar,actualizar,eliminar,agregar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        listaSeg = findViewById(R.id.listaSeguros);
        Bundle parametros = getIntent().getExtras();
        regresar = findViewById(R.id.reg);
        actualizar= findViewById(R.id.actualizar);
        eliminar=findViewById(R.id.eliminar);
        agregar= findViewById(R.id.agregarSeguro);

        tel = findViewById(R.id.telefonoPro);
        nom = findViewById(R.id.nombrePro);
        dom = findViewById(R.id.domicilioPro);
        fecha = findViewById(R.id.fechPro);


        tel.setText(parametros.getString("TELEFONO"));
        dom.setText(parametros.getString("DOMICILIO"));
        nom.setText(parametros.getString("NOMBRE"));
        fecha.setText(parametros.getString("FECHAR"));


        listaSeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                monstrarAlerta(position);
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActualziarPropietario();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPro();
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAgregarSeguro();
            }
        });

    }

    private void startAgregarSeguro() {
        Intent agregarSeguro = new Intent(this,Main6Activity.class);


        agregarSeguro.putExtra("TELEFONO",tel.getText().toString());

        startActivity(agregarSeguro);
    }

    private void eliminarPro() {
        Propietario propietario = new Propietario(this);
        String mensaje;
        boolean respuesta = propietario.eliminar(tel.getText().toString());
        if(respuesta){
            mensaje = "se pudo eliminar el propietario ";

        }else{
            mensaje = "Error! no se pudo eliminar el propietario, respuesta de SQLITE: "+propietario.error;
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
    private void startActualziarPropietario() {
        Intent eliminaractualiza = new Intent(this,Main3Activity.class);

        eliminaractualiza.putExtra("TELEFONO",tel.getText().toString());
        eliminaractualiza.putExtra("NOMBRE",nom.getText().toString());
        eliminaractualiza.putExtra("DOMICILIO",dom.getText().toString());
        eliminaractualiza.putExtra("FECHA",fecha.getText().toString());

        startActivity(eliminaractualiza);
    }

    private void monstrarAlerta(final int pos) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if (!(vector==null)){
            alerta.setTitle("Attencion")
                    .setMessage("Deseas visualizar el Seguro "+vector[pos].getDESCRIPCION()+"?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            invocarEliminarActualizar(pos);
                        }
                    }).setNegativeButton("NO",null)
                    .show();
        }
    }
    private void invocarEliminarActualizar(int pos) {
        Intent eliminaractualiza = new Intent(this,Main5Activity.class);

        eliminaractualiza.putExtra("TELEFONO",vector[pos].getTELEFONO());
        eliminaractualiza.putExtra("DESCRIPCION",vector[pos].getDESCRIPCION());
        eliminaractualiza.putExtra("TIPO",vector[pos].getTIPO());
        eliminaractualiza.putExtra("FECHA",vector[pos].getFECHA());
        eliminaractualiza.putExtra("IDSEGURO",vector[pos].getIDSEGURO());

        startActivity(eliminaractualiza);
    }

    protected void onStart(){
        Seguro seguro = new Seguro(this);
        vector = seguro.consultar(tel.getText().toString());
        String[] descUbi = null;
        if (vector==null){
            descUbi=new String[1];
            descUbi[0]="no hay Segurps capturados";
        }else{
            descUbi = new String[vector.length] ;
            for (int i=0;i<vector.length;i++){
                Seguro temp = vector[i];
                descUbi[i]= temp.getDESCRIPCION()+"\n"+temp.getTELEFONO();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,descUbi);

        listaSeg.setAdapter(adaptador);

        super.onStart();
    }
}
