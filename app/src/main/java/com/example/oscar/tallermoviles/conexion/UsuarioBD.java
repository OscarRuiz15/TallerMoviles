package com.example.oscar.tallermoviles.conexion;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oscar.tallermoviles.clases.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBD extends ConexionBD {

    private SQLiteDatabase bd;

    public UsuarioBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        bd = super.getWritableDatabase();
    }

    public boolean insertarUsuario(Usuario usuario) {

        ContentValues registro = new ContentValues();
        //.put("id",usuario.getId());
        registro.put("nombre", usuario.getNombre());
        registro.put("tipo", usuario.getTipo());
        registro.put("email", usuario.getEmail());
        registro.put("pass", md5(usuario.getPass()));
        bd.insert("usuarios", null, registro);
        bd.close();
        return true;
    }

    public boolean modificarUsuario(Usuario usuario) {
        ContentValues registro = new ContentValues();
        //.put("id",usuario.getId());
        registro.put("nombre", usuario.getNombre());
        registro.put("tipo", usuario.getTipo());
        registro.put("email", usuario.getEmail());
        registro.put("pass", usuario.getPass());
        bd.update("usuarios", registro, "codigo=" + usuario.getId(), null);
        bd.close();
        return true;
    }

    public Usuario consultarId(int id) {
        Usuario u = null;
        String query = "select nombre,tipo,email from usuario where id=" + id;
        Cursor fila = bd.rawQuery(query, null);
        if (fila.moveToFirst()) {
            String nombre = fila.getString(0);
            int tipo = fila.getInt(1);
            String email = fila.getString(2);
            u = new Usuario(id, nombre, tipo, email, "");
        }
        return u;
    }

    public List<Usuario> consultarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario u = null;
        String query = "select * from usuarios";
        Cursor fila = bd.rawQuery(query, null);
        if (fila.moveToFirst()) {
            do {
                int id = fila.getInt(0);
                String nombre = fila.getString(1);
                int tipo = fila.getInt(2);
                String email = fila.getString(3);
                u = new Usuario(id, nombre, tipo, email, "");
                usuarios.add(u);
            } while (fila.moveToNext());
        }
        fila.close();

        return usuarios;

    }

    public Usuario verificarID(String correo, String contrasena) {


        Usuario u = null;
        String pass= md5(contrasena);
        String query = "select * from usuarios where email='" + correo + "' and pass='" + pass + "';";
        Cursor fila = bd.rawQuery(query, null);
        if (fila.moveToFirst()) {
            int id = fila.getInt(0);
            String nombre = fila.getString(1);
            int tipo = fila.getInt(2);
            String email = fila.getString(3);
            u = new Usuario(id, nombre, tipo, email, "");
        }
        return u;
    }

    public String md5 (String md5pass) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5pass.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
