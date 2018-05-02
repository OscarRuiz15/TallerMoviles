package com.example.oscar.tallermoviles.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oscar.tallermoviles.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConsultar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentConsultar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConsultar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<String> mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View viewRoot;
    ListView listaUsuarios;
    ArrayAdapter<String> usuariosAdapter;

    public FragmentConsultar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param usuarios Parameter 1.
     * @return A new instance of fragment FragmentConsultar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConsultar newInstance(ArrayList<String> usuarios) {
        FragmentConsultar fragment = new FragmentConsultar();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, usuarios);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArrayList(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewRoot =inflater.inflate(R.layout.fragment_fragment_consultar, container, false);
        listaUsuarios = (ListView)  viewRoot.findViewById(R.id.usuariosList);
        //Toast.makeText(getActivity(),"hola "+mParam1.size(),Toast.LENGTH_SHORT).show();
        usuariosAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mParam1);
        /*listaEstudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });*/


        /*listaEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
        listaUsuarios.setAdapter(usuariosAdapter);
        return  viewRoot;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
