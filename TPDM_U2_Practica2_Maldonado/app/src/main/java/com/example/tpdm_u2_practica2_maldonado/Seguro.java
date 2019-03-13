package com.example.tpdm_u2_practica2_maldonado;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Seguro {

    private BaseDatos base;

    private String IDSEGURO, DESCRIPCION,FECHA,TELEFONO;
    private int TIPO;
    protected String error;

    public Seguro(Activity activity) {
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public Seguro(String IDSEGURO, String DESCRIPCION, String FECHA, String TELEFONO, int TIPO) {
        this.IDSEGURO = IDSEGURO;
        this.DESCRIPCION = DESCRIPCION;
        this.FECHA = FECHA;
        this.TELEFONO = TELEFONO;
        this.TIPO = TIPO;
    }

    public boolean insertar(Seguro s) {
        try {
            SQLiteDatabase transaccionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("IDSEGURO", s.getIDSEGURO());
            datos.put("DESCRIPCION", s.getDESCRIPCION());
            datos.put("FECHA", s.getFECHA());
            datos.put("TIPO",s.getTIPO());
            datos.put("TELEFONO",s.getTELEFONO());


            long resultado = transaccionInsertar.insert("SEGURO", "IDSEGURO", datos);
            transaccionInsertar.close();
            if (resultado == -1) return false;
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }

        return true;
    }

    public Seguro[] consultar(String clave) {
        Seguro[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM SEGURO WHERE TELEFONO = '" + clave+"'";
            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new Seguro[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new Seguro(c.getString(0), c.getString(1),
                            c.getString(2),c.getString(3), c.getInt(4));
                    pos++;
                } while (c.moveToNext());
            }
            transacciónConsulta.close();
        } catch (SQLiteException e) {
            return null;
        }
        return resultado;
    }

    public Seguro[] consultar() {
        Seguro[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM SEGURO ";

            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new Seguro[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new Seguro(c.getString(0), c.getString(1),
                            c.getString(2),c.getString(3), c.getInt(4));
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
            resultado = transaccionEliminar.delete("SEGURO","IDSEGURO =? ",new String[]{p});
            transaccionEliminar.close();
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return resultado>0;
    }



    public boolean actualizar(Seguro s) {
        try {
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("IDSEGURO", s.getIDSEGURO());
            datos.put("DESCRIPCION", s.getDESCRIPCION());
            datos.put("FECHA", s.getFECHA());
            datos.put("TIPO",s.getTIPO());
            datos.put("TELEFONO",s.getTELEFONO());

            long resultado =
                    transaccionActualizar.update("SEGURO", datos, "IDSEGURO=?", new String[]{"" + s.getIDSEGURO()});
            transaccionActualizar.close();
            if (resultado == 0) return false;
        }catch(SQLiteException e){
            setError(e.getMessage());
            return false;
        }
        return true;
    }



    public String getIDSEGURO() {
        return IDSEGURO;
    }

    public void setIDSEGURO(String IDSEGURO) {
        this.IDSEGURO = IDSEGURO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
