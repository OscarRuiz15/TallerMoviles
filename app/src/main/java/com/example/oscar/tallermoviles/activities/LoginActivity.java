package com.example.oscar.tallermoviles.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oscar.tallermoviles.R;
import com.example.oscar.tallermoviles.clases.Usuario;
import com.example.oscar.tallermoviles.conexion.ConexionBD;
import com.example.oscar.tallermoviles.conexion.UsuarioBD;

public class LoginActivity extends AppCompatActivity {

    private EditText txtlogin, txtpassword;
    ConexionBD conexion;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtlogin = (EditText) findViewById(R.id.txtlogin);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        txtlogin.setText("admin");
        txtpassword.setText("admin");

        conexion = new ConexionBD(this, "Cuentas", null, 1);

        String DB_PATH = "/data/data/com.example.oscar.tallermoviles/databases/Cuentas";
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
            Toast.makeText(getApplication(), "Ya existe la BD", Toast.LENGTH_LONG).show();
        } catch (SQLiteException e) {
            //Si no existe la BD
            db = conexion.getWritableDatabase();
            if (conexion != null) {
                Toast.makeText(getApplication(), "BD creada", Toast.LENGTH_LONG).show();
                String query;
                UsuarioBD u = new UsuarioBD(this, "Cuentas", null, 1);
                String pass= u.md5("admin");
                query = "insert into usuarios (nombre,tipo,email,pass) values ('admin',1,'admin','"+pass+"');";
                db.execSQL(query);
            }
        }
    }

    //El View es para que lo reconozca el xml
    //El intent es para crear la otra actividad
    //El bundle es para pasar los parametros que se necesitan
    public void ingresarSistema(View view) {
        String correo = txtlogin.getText().toString().trim();
        String pass = txtpassword.getText().toString().trim();


        if (correo.equals("") || pass.equals("")) {
            String message = "Hay campos vacios";
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage(message);
            alertDialog.show();
        } else {
            UsuarioBD u = new UsuarioBD(this, "Cuentas", null, 1);
            Usuario us = u.verificarID(correo, pass);
            if (us != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle bundle = new Bundle();
                bundle.putInt("id", us.getId());
                bundle.putString("nombre", us.getNombre());
                bundle.putString("email", us.getEmail());
                bundle.putInt("tipo", us.getTipo());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                String message = "Usuario erroneo";
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage(message);
                alertDialog.show();
                txtlogin.setText("");
                txtpassword.setText("");
            }
        }


    }
}
