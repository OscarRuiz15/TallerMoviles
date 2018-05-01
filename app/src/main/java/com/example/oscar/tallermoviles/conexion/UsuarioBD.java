package com.example.oscar.tallermoviles.conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oscar.tallermoviles.clases.Usuario;

import java.util.List;

public class UsuarioBD extends ConexionBD {

    private SQLiteDatabase bd;

    public UsuarioBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        bd=super.getWritableDatabase();
    }

    public boolean insertarUsuario(Usuario usuario){

        ContentValues registro=new ContentValues();
        //.put("id",usuario.getId());
        registro.put("nombre",usuario.getNombre());
        registro.put("tipo",usuario.getTipo());
        registro.put("email",usuario.getEmail());
        registro.put("pass",usuario.getPass());
        bd.insert("usuarios",null,registro);
        bd.close();
        return true;
    }
    public boolean modificarUsuario(Usuario usuario){
        return true;
    }
    public Usuario consultarId(int id){
        Usuario u=null;
        String query="select nombre,tipo,email from usuario where id="+id;
        Cursor fila=bd.rawQuery(query,null);
        if(fila.moveToFirst()){
            String nombre=fila.getString(0);
            int tipo=fila.getInt(1);
            String email=fila.getString(2);
            u=new Usuario(id,nombre,tipo,email,"");
        }
        return u;
    }
    public List<Usuario>consultarUsuarios(){
        return null;

    }
}
