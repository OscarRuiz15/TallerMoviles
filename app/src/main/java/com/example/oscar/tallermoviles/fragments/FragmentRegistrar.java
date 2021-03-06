package com.example.oscar.tallermoviles.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.oscar.tallermoviles.R;
import com.example.oscar.tallermoviles.clases.Usuario;
import com.example.oscar.tallermoviles.clases.ValidarCampos;
import com.example.oscar.tallermoviles.conexion.UsuarioBD;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegistrar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegistrar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistrar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "ExampleFragment";
    private OnFragmentInteractionListener mCallback = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText txtnombre, txtemail, txtpassword;
    private Button btnlimpiar, btnregistrar;
    private Spinner spinner;
    private static Usuario usuario;


    private OnFragmentInteractionListener mListener;


    public FragmentRegistrar() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistrar.
     */
    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_registrar, container, false);

        txtnombre = (EditText) v.findViewById(R.id.tfNombre);
        txtemail = (EditText) v.findViewById(R.id.tfEmail);
        txtpassword = (EditText) v.findViewById(R.id.tfPassword);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        btnlimpiar = (Button) v.findViewById(R.id.btnlimpiar);
        btnregistrar = (Button) v.findViewById(R.id.btnregistrar);

        // Inflate the layout for this fragment
        btnlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtnombre.setText("");
                txtemail.setText("");
                txtpassword.setText("");
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtnombre.getText().toString().trim();
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                int tipo = spinner.getSelectedItemPosition() + 1;
                ValidarCampos vc= new ValidarCampos();

                if(nombre.equals("") || email.equals("") || password.equals("") ) {
                    String message = "Hay campos vacios";
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage(message);
                    alertDialog.show();
                } else if(!(vc.Email(email)) || !(vc.Texto(nombre)) || !(vc.AlfanumericoCaracteres(password))) {
                    String mensa="";
                    if(!(vc.Email(email)) ){
                        mensa=" El email es incorrecto";
                    }
                    if(!(vc.Texto(nombre)) ){
                        mensa= mensa+"\n el nombre es incorrecto";
                    }
                    else{
                        mensa= mensa+"\n la contraseña tiene un caracter invalido";
                    }
                    String message = mensa;
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage(message);
                    alertDialog.show();
                }
                else{
                    Usuario u = new Usuario(0, nombre, tipo, email, password);
                    UsuarioBD ubd = new UsuarioBD(v.getContext(), "Cuentas", null, 1);
                    boolean query = ubd.insertarUsuario(u);
                    if (query) {
                        String message = "Registro con exito";
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setMessage(message);
                        alertDialog.show();
                        txtnombre.setText("");
                        txtemail.setText("");
                        txtpassword.setText("");
                    }
                }

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public static FragmentRegistrar newInstance(Bundle arguments) {
        FragmentRegistrar fragment = new FragmentRegistrar();
        if(arguments!=null){
            Bundle args = new Bundle();
            int id=arguments.getInt("id");
            String nombre=arguments.getString("nombre");
            String email=arguments.getString("email");
            int tipo=arguments.getInt("tipo");
            usuario=new Usuario(id,nombre,tipo,email,"");

            fragment.setArguments(args);
        }

        return fragment;
    }

    //El fragment se ha adjuntado al Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}