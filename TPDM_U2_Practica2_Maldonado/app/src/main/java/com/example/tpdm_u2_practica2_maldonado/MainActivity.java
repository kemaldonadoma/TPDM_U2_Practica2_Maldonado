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

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Propietario vector[];
    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.listaPropietarios);
        agregar = findViewById(R.id.agregar);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                monstrarAlerta(position);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void monstrarAlerta(final int pos) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if (!(vector==null)){
            alerta.setTitle("Attencion")
                    .setMessage("Deseas visualizar el Propietario "+vector[pos].getNOMBRE()+"?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            verDetallesPropietario(pos);
                        }
                    }).setNegativeButton("NO",null)
                    .show();
        }

    }

    public void start(){
        Intent nuevo;
        nuevo = new Intent(this,Main2Activity.class);
        startActivity(nuevo);
    }

    private void verDetallesPropietario(int pos) {
        Intent detallesPropietario = new Intent(this,Main4Activity.class);

        detallesPropietario.putExtra("TELEFONO",vector[pos].getTELEFONO());
        detallesPropietario.putExtra("NOMBRE",vector[pos].getNOMBRE());
        detallesPropietario.putExtra("DOMICILIO",vector[pos].getDOMICILIO());
        detallesPropietario.putExtra("FECHA",vector[pos].getFECHA());

        startActivity(detallesPropietario);
    }
    protected void onStart(){
        Propietario pryecto = new Propietario(this);
        vector = pryecto.consultar();
        String[] descUbi = null;
        if (vector==null){
            descUbi=new String[1];
            descUbi[0]="no hay Propietarios capturados";
        }else{
            descUbi = new String[vector.length] ;
            for (int i=0;i<vector.length;i++){
                Propietario temp = vector[i];
                descUbi[i]= temp.getNOMBRE()+"\n"+temp.getTELEFONO();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,descUbi);

        lista.setAdapter(adaptador);

        super.onStart();
    }


}
