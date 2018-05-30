package com.example.alber.prova_tab_menu.Cuina;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alber.prova_tab_menu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab2_cuina_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Tab2Fragment";
    RecyclerView recyclerViewcuina;
    private JSONArray result;
    private JSONArray result2;
    List<Observacio_IdAlumne> listAGestionar;
    List<Observacio_IdAlumne> listDefinitva;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.tab2_cuina_fragment, container, false );

        recyclerViewcuina = view.findViewById( R.id.recylcerViewcuina );
        recyclerViewcuina.setHasFixedSize( true );
        recyclerViewcuina.setLayoutManager( new LinearLayoutManager( this.getContext() ) );
        listAGestionar = new ArrayList<>();
        listDefinitva = new ArrayList<>();
        omplenarVectorsAlumnesMenjador();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_container );
        mSwipeRefreshLayout.setOnRefreshListener( this );
        mSwipeRefreshLayout.setColorSchemeResources( R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark );

        return view;
    }

    //consulta_alumnes_queden_menjador
    public void omplenarVectorsAlumnesMenjador() {
        StringRequest stringRequest = new StringRequest( Request.Method.GET, "http://azamoradam.000webhostapp.com/connect/consulta_alumnes_queden_menjador.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int alumnestotals;
                JSONObject j = null;
                try {
                    j = new JSONObject( response );
                    result = j.getJSONArray( "result" );
                    alumnestotals = j.getInt( "result2" );
                    omplenarVector( result );
                    gestioAlumnesMenjador();
                    omplenarVectorAmbAlumnesTotal( alumnestotals );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                } );

        RequestQueue requestQueue = Volley.newRequestQueue( this.getContext() );
        requestQueue.add( stringRequest );

    }

    private void omplenarVector(JSONArray j) {
        listAGestionar.clear();
        JSONObject obs_idalum;
        for (int i = 0; i < j.length(); i++) {
            try {
                obs_idalum = j.getJSONObject( i );
                listAGestionar.add( new Observacio_IdAlumne( obs_idalum.getString( "NOM" ), obs_idalum.getInt( "ID_ALUMNE" ), 0 ) );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void gestioAlumnesMenjador() {
        listDefinitva.clear();
        boolean afegir = true;
        int pos;
        String obs;
        for (int i = 0; i < listAGestionar.size() - 1; i++) {
            if (listAGestionar.get( i ).getId_alumne() == listAGestionar.get( i + 1 ).getId_alumne()) { //Si son iguals
                obs = listAGestionar.get( i ).getObservacio() + " i " + listAGestionar.get( i + 1 ).getObservacio();
                pos = existeixObservacio( obs );
                if (pos == -1) {
                    listDefinitva.add( new Observacio_IdAlumne( obs, 0, 1 ) );
                } else {
                    listDefinitva.get( pos ).augmentarAlumneContador();
                }

                if (i == listAGestionar.size() - 2) afegir = false;
                i = i + 1;
            } else {
                obs = listAGestionar.get( i ).getObservacio();
                pos = existeixObservacio( obs );
                if (pos == -1) {
                    listDefinitva.add( new Observacio_IdAlumne( obs, 0, 1 ) );
                } else {
                    listDefinitva.get( pos ).augmentarAlumneContador();
                }

            }
        }
        if (afegir && listDefinitva.size() > 0) {
            obs = listAGestionar.get( listAGestionar.size() - 1 ).getObservacio();
            pos = existeixObservacio( obs );
            if (pos == -1) {
                listDefinitva.add( new Observacio_IdAlumne( obs, 0, 1 ) );
            } else {
                listDefinitva.get( pos ).augmentarAlumneContador();
            }
        }

        AdaptadorListViewCuina adapter = new AdaptadorListViewCuina( listDefinitva, getContext() );
        recyclerViewcuina.setAdapter( adapter );

        mSwipeRefreshLayout.setRefreshing( false );

    }

    private int existeixObservacio(String obs) {
        int pos = -1;
        boolean trobat = false;
        int i = 0, max = listDefinitva.size();
        while (i < max && !trobat) {
            if (listDefinitva.get( i ).getObservacio().equals( obs )) {
                pos = i;
                trobat = true;
            }
            i++;
        }
        return pos;
    }

    private void omplenarVectorAmbAlumnesTotal(final int total) {
        int size = 0;
        for (int i = 0; i < listDefinitva.size(); i++) {
            if (listDefinitva.get( i ).getContador_alumnes() > 1) {
                size = size + listDefinitva.get( i ).getContador_alumnes() - 1;
            }
        }
        size = size + listDefinitva.size();
        listDefinitva.add( new Observacio_IdAlumne( "Menús Normals", 0, total - size ) );
        //recyclerViewcuina.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        omplenarVectorsAlumnesMenjador();
    }
}
