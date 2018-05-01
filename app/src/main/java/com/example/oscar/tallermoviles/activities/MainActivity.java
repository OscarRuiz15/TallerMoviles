package com.example.oscar.tallermoviles.activities;

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
import com.example.oscar.tallermoviles.fragments.FragmentRegistrar;

public class MainActivity extends AppCompatActivity implements FragmentRegistrar.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Usuario usuario;


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


        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        String nombre = bundle.getString("nombre");
        String email = bundle.getString("email");
        int tipo = bundle.getInt("tipo");
        usuario = new Usuario(id, nombre, tipo, email, "");
        if (tipo == 0) {
            crearFragmentRegistrar();
        }
        ;
        setContentView(R.layout.activity_main);

    }

    public void crearFragmentRegistrar() {

        Bundle bundle = new Bundle();
        bundle.putInt("id", usuario.getId());
        bundle.putString("nombre", usuario.getNombre());
        bundle.putString("email", usuario.getEmail());
        bundle.putInt("tipo", usuario.getTipo());

        FragmentRegistrar fragment = FragmentRegistrar.newInstance(bundle);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentregistrar, fragment);


        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
