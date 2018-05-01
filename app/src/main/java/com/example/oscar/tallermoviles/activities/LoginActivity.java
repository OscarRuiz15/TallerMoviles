package com.example.oscar.tallermoviles.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.oscar.tallermoviles.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //El View es para que lo reconozca el xml
    //El intent es para crear la otra actividad
    //El bundle es para pasar los parametros que se necesitan
    public void ingresarSistema(View view){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle=new Bundle();
        bundle.putInt("id",1);
        bundle.putString("nombre","Juan");
        bundle.putString("email","Email@e-mail.com");
        bundle.putInt("tipo",0);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
