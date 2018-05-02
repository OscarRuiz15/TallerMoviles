package com.example.oscar.tallermoviles.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscar.tallermoviles.R;
import com.example.oscar.tallermoviles.clases.Usuario;
import com.example.oscar.tallermoviles.conexion.ConexionBD;
import com.example.oscar.tallermoviles.conexion.UsuarioBD;
import com.example.oscar.tallermoviles.fragments.FragmentConsultar;
import com.example.oscar.tallermoviles.fragments.FragmentRegistrar;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.BottomNavigationView.*;

public class MainActivity extends AppCompatActivity implements FragmentRegistrar.OnFragmentInteractionListener, FragmentConsultar.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Usuario usuario;
    ConexionBD conexion;
    SQLiteDatabase db;
    FragmentConsultar fragcon;

    private OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_registrar:
                    crearFragmentRegistrar();
                    return true;
                case R.id.navigation_consultar:
                    crearFragmentConsultar();
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

        /*if (tipo == 0) {
            crearFragmentRegistrar();
        }*/

        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if (tipo==1) {
            navigation.findViewById(R.id.navigation_registrar).setVisibility(View.VISIBLE);
            crearFragmentRegistrar();
        }else{
            navigation.findViewById(R.id.navigation_registrar).setVisibility(View.GONE);
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

    public void crearFragmentRegistrar() {
        //Toast.makeText(getApplication(), "Registrar", Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putInt("id", usuario.getId());
        bundle.putString("nombre", usuario.getNombre());
        bundle.putString("email", usuario.getEmail());
        bundle.putInt("tipo", usuario.getTipo());

        FragmentRegistrar fragment = FragmentRegistrar.newInstance(bundle);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.add(R.id.fragment, fragment);
        ft.replace(R.id.fragment,fragment);
        ft.commit();
    }

    public void crearFragmentConsultar() {
        //Toast.makeText(getApplication(), "Consultar", Toast.LENGTH_LONG).show();

        /*Bundle bundle = new Bundle();
        bundle.putInt("id", usuario.getId());
        bundle.putString("nombre", usuario.getNombre());
        bundle.putString("email", usuario.getEmail());
        bundle.putInt("tipo", usuario.getTipo());*/
        List<Usuario> users;
        UsuarioBD us=new UsuarioBD(this, "Cuentas", null, 1);
        users=us.consultarUsuarios();

        ArrayList<String> usuarios=new ArrayList<String>();
        for (int i = 0; i < users.size(); i++) {
            String nombre=users.get(i).getNombre();
            String email=users.get(i).getEmail();
            int tipo= users.get(i).getTipo();
            if(tipo==1){
                usuarios.add("Nombre: "+nombre+"\nEmail: "+email+"\nTipo: Administrador\n");
            }
            else{

                usuarios.add("Nombre: "+nombre+"\nEmail: "+email+"\nTipo: Natural\n");
            }

        }

        FragmentConsultar fragment = FragmentConsultar.newInstance(usuarios);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fragment);
        ft.addToBackStack(null);
        //ft.add(R.id.fragment, fragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    ////////MÉTODOS NECESARIOS PARA INFLAR LA VISTA CON UN MENU//////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate activity menu items.
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalir:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //Toast.makeText(this,"hola "+item.getTitle(),Toast.LENGTH_LONG).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
