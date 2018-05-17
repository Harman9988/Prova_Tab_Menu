package com.example.alber.prova_tab_menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab1_alumne_fragment extends Fragment {
    private static final String TAG="Tab1Fragment";
    public static final String JSON_ARRAY = "result";
    private JSONArray result;
    Spinner spinner;
    String  ClasseNom;
    private ArrayList<String> arrayList;
    static private String id_classe, torn_classe;
    Button boto_inserir;
    List<Alumnes> alumneList;
    RecyclerView recyclerView;
    AdaptadorListView lAdapter;
    final FragmentActivity fragmentActivity = getActivity();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab1_alumne_fragment, container, false);

        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        alumneList = new ArrayList<>();
        spinner=view.findViewById(R.id.spinner_classes);
        boto_inserir=view.findViewById( R.id.button_inserir);
        arrayList= new ArrayList<String>();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                tv_id.setText(getClasseId(position));
                tv_torn.setText(getClasseTorn(position));*/
                id_classe=getClasseId(position); //Per guardar l'id de la classe seleccionada a l'spinners
                torn_classe=getClasseTorn(position); //Per guardar el torna de la classe seleccionada a l'spinner
                loadAlumnes();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
    private void getData(){
        StringRequest stringRequest= new StringRequest("http://azamoradam.000webhostapp.com/connect/spinner_llistar_classes.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray(JSON_ARRAY);
                    classe_details(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);

    }

    private void classe_details(JSONArray j){
        arrayList.clear();
        for (int i=0; i<j.length(); i++){
            try{
                JSONObject json=j.getJSONObject(i);
                arrayList.add(json.getString("NOM"));

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList ));

    }

    private String getClasseId(int position){
        String id="";
        try{
            JSONObject json = result.getJSONObject(position);
            id=json.getString("ID");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return id;
    }
    private String getClasseTorn(int position){
        String torn="";
        try{
            JSONObject json = result.getJSONObject(position);
            torn=json.getString("ID_TORN");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return torn;
    }
    private void loadAlumnes() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest( Request.Method.GET, "http://azamoradam.000webhostapp.com/connect/llistar_alumnes_segons_id_classe.php?id_classe="+id_classe, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray( response );
                    alumneList.clear();
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting alumne object from json array
                        JSONObject alumne = array.getJSONObject( i );

                        //adding the alumne to alumne list
                        alumneList.add( new Alumnes( alumne.getInt( "ID" ), alumne.getString( "NOM" ), alumne.getString( "COGNOM" ), alumne.getString( "imageURL" ) ) );
                    }

                    //creating adapter object and setting it to recyclerview

                    AdaptadorListView adapter = new AdaptadorListView( alumneList, getContext());
                    recyclerView.addItemDecoration(new DividerItemDecoration( getContext(),LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } );

        //adding our stringrequest to queue
        Volley.newRequestQueue( this.getContext()).add( stringRequest );
    }
}
