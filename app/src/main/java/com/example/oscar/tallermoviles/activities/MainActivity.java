package com.example.oscar.tallermoviles.activities;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscar.tallermoviles.R;
import com.example.oscar.tallermoviles.clases.Usuario;
import com.example.oscar.tallermoviles.conexion.ConexionBD;
import com.example.oscar.tallermoviles.fragments.FragmentConsultar;
import com.example.oscar.tallermoviles.fragments.FragmentRegistrar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentRegistrar.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Usuario usuario;
    ConexionBD conexion;
    SQLiteDatabase db;
    FragmentConsultar fragcon;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_registrar:
                    Toast.makeText(getApplication(), "clic registrar", Toast.LENGTH_LONG).show();

                    crearFragmentRegistrar();
                    return true;
                case R.id.navigation_consultar:
                    Toast.makeText(getApplication(), "clic consultar", Toast.LENGTH_LONG).show();

                    crearFragmentConsultar();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.navigation_registrar:
                crearFragmentRegistrar();
                return true;
            case R.id.navigation_consultar:
                crearFragmentConsultar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        String nombre = bundle.getString("nombre");
        String email = bundle.getString("email");
        int tipo = bundle.getInt("tipo");
        usuario = new Usuario(id, nombre, tipo, email, "");

        /*if (tipo == 0) {
            crearFragmentRegistrar();
        }*/

        setContentView(R.layout.activity_main);

    }

    public void crearFragmentRegistrar() {
        Toast.makeText(getApplication(), "Registrar", Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putInt("id", usuario.getId());
        bundle.putString("nombre", usuario.getNombre());
        bundle.putString("email", usuario.getEmail());
        bundle.putInt("tipo", usuario.getTipo());

        FragmentRegistrar fragment = FragmentRegistrar.newInstance(bundle);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment, fragment);


        ft.commit();
    }

    public void crearFragmentConsultar() {
        Toast.makeText(getApplication(), "Consultar", Toast.LENGTH_LONG).show();

        /*Bundle bundle = new Bundle();
        bundle.putInt("id", usuario.getId());
        bundle.putString("nombre", usuario.getNombre());
        bundle.putString("email", usuario.getEmail());
        bundle.putInt("tipo", usuario.getTipo());*/

        Cursor c = db.rawQuery("SELECT * FROM usuarios", null);
        ArrayList<String> estudiantes= new ArrayList<String>();
        if(c.moveToFirst()){
            do {
                estudiantes.add(c.getString(1));
            }while (c.moveToNext());
        }
        c.close();

        fragcon = new FragmentConsultar().newInstance(estudiantes);
        /*android.support.v4.app.FragmentTransaction transaccion = this.getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmentA,fragcon);
        transaccion.addToBackStack(null);*/
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment, fragcon);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
