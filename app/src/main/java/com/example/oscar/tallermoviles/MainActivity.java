package com.example.oscar.tallermoviles;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscar.tallermoviles.conexion.ConexionBD;

public class MainActivity extends AppCompatActivity implements FragmentRegistrar.OnFragmentInteractionListener {

    private TextView mTextMessage;
    ConexionBD conexion;
    SQLiteDatabase db;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    crearFragmentRegistrar();
                    return true;
                case R.id.navigation_dashboard:

                    crearFragmentRegistrar();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crearFragmentRegistrar();

        conexion = new ConexionBD(this,"Cuentas",null,1);

        String DB_PATH = "/data/data/com.example.oscar.tallermoviles/databases/Cuentas";
        try {
            db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
            Toast.makeText(getApplication(),"Ya existe la BD",Toast.LENGTH_LONG).show();
        } catch (SQLiteException e) {
            //Si no existe la BD
            db = conexion.getWritableDatabase();
            if(conexion!=null){
                Toast.makeText(getApplication(),"BD creada",Toast.LENGTH_LONG).show();
                String query;
                query="insert into usuarios (nombre,tipo,email,pass) values ('admin',1,' ','admin');";
                db.execSQL(query);
            }
        }

        setContentView(R.layout.activity_main);

    }

    public void crearFragmentRegistrar(){

        Bundle arguments = new Bundle();

        FragmentRegistrar fragment = FragmentRegistrar.newInstance(arguments);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentregistrar,fragment);


        ft.commit();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
