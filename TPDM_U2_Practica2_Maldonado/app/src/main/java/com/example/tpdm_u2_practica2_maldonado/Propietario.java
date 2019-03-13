package com.example.tpdm_u2_practica2_maldonado;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Propietario {
    private BaseDatos base;

    private String TELEFONO, NOMBRE,DOMICILIO,FECHA;
    protected String error;

    public Propietario(Activity activity) {
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public Propietario(String TELEFONO, String NOMBRE, String DOMICILIO, String FECHA) {
        this.TELEFONO = TELEFONO;
        this.NOMBRE = NOMBRE;
        this.DOMICILIO = DOMICILIO;
        this.FECHA = FECHA;
    }


    public boolean insertar(Propietario p) {
        try {
            SQLiteDatabase transaccionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO", p.getTELEFONO());
            datos.put("NOMBRE", p.getNOMBRE());
            datos.put("DOMICILIO", p.getDOMICILIO());
            datos.put("FECHA",p.getFECHA());

            long resultado = transaccionInsertar.insert("PROPIETARIO", "TELEFONO", datos);
            transaccionInsertar.close();
            if (resultado == -1) return false;
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }

        return true;
    }



    public Propietario[] consultar() {
        Propietario[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROPIETARIO ";

            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new Propietario[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new Propietario(c.getString(0), c.getString(1),
                            c.getString(2),c.getString(3));
                    pos++;
                } while (c.moveToNext());
            }
            transacciónConsulta.close();
        } catch (SQLiteException e) {
            return null;
        }
        return resultado;
    }

    public boolean eliminar(String p) {
        int resultado;
        try {
            SQLiteDatabase transaccionEliminar = base.getWritableDatabase();

            transaccionEliminar.delete("SEGURO","TELEFONO =? ",new String[]{p});


            SQLiteDatabase transaccionEliminar2 = base.getWritableDatabase();

            resultado = transaccionEliminar2.delete("PROPIETARIO","TELEFONO =? ",new String[]{p});
            transaccionEliminar2.close();

        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(Propietario p) {
        try {
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO", p.getTELEFONO());
            datos.put("NOMBRE", p.getNOMBRE());
            datos.put("DOMICILIO", p.getDOMICILIO());
            datos.put("FECHA",p.getFECHA());

            long resultado =
                    transaccionActualizar.update("PROPIETARIO", datos, "TELEFONO=?", new String[]{"" + p.getTELEFONO()});
            transaccionActualizar.close();
            if (resultado == 0) return false;
        }catch(SQLiteException e){
            setError(e.getMessage());
            return false;
        }
        return true;
    }


    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDOMICILIO() {
        return DOMICILIO;
    }

    public void setDOMICILIO(String DOMICILIO) {
        this.DOMICILIO = DOMICILIO;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
