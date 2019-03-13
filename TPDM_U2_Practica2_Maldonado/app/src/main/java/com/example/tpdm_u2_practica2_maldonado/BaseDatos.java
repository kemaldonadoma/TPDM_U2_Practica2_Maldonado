package com.example.tpdm_u2_practica2_maldonado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {



    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super (context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PROPIETARIO (TELEFONO varchar(20) NOT NULL ," +
                "NOMBRE varchar(200) NOT NULL ," +
                "DOMICILIO varchar(200) , " +
                "FECHA DATE, " +
                "PRIMARY KEY (TELEFONO));" );

        db.execSQL("CREATE TABLE SEGURO(IDSEGURO varchar(20) NOT NULL ," +
                "DESCRIPCION varchar(200) NOT NULL ," +
                "FECHA DATE ," +
                "TIPO INT ," +
                "TELEFONO varchar(20) NOT NULL , " +
                "PRIMARY KEY (IDSEGURO), " +
                "CONSTRAINT FK_TEL FOREIGN KEY (TELEFONO) REFERENCES PROPIETARIO (TELEFONO));" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
