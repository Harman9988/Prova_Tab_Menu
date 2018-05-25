package com.example.alber.prova_tab_menu.Alumne;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alber.prova_tab_menu.Cuina.Tab2_cuina_fragment;
import com.example.alber.prova_tab_menu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab1_alumne_fragment extends Fragment {
    private static final String TAG="Tab1Fragment";
    private JSONArray result;
    private ArrayList<String> arrayList;
    static private String id_classe, torn_classe;
    public static final String JSON_ARRAY = "result";
    Spinner spinner;
    CheckBox checkBox2;
    List<Alumne> alumneList;
    RecyclerView recyclerView;
    Button button;
    ProgressDialog progressDialog;
    Tab2_cuina_fragment tab2_cuina_fragment = new Tab2_cuina_fragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.tab1_alumne_fragment, container, false);

        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        alumneList = new ArrayList<>();
        spinner=view.findViewById(R.id.spinner_classes);
        arrayList= new ArrayList<String>();
        button = view.findViewById( R.id.boto_inserir );
        checkBox2 = view.findViewById( R.id.checkBox );

        progressDialog = new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                inserir_alumnes_checked();
                                //tab2_cuina_fragment.omplenarVectorsAlumnesMenjador();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Estas segur/a?").setPositiveButton("SI", dialogClickListener)
                        .setNegativeButton("NO", dialogClickListener).show();


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
        progressDialog.show();
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
                        alumneList.add( new Alumne( alumne.getInt( "ID" ), alumne.getString( "NOM" ), alumne.getString( "COGNOM" ), alumne.getString( "imageURL" ) ) );
                    }

                    //creating adapter object and setting it to recyclerview

                    AdaptadorListView adapter = new AdaptadorListView( alumneList, getContext());
                    recyclerView.addItemDecoration(new DividerItemDecoration( getContext(),LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
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

    private void inserir_alumnes_checked(){
        List<Alumne> llista= ((AdaptadorListView) recyclerView.getAdapter()).getAlumnesChecked();
        for (Alumne alumne : llista) {
            inserir_alumne(alumne);
        }
        Toast.makeText(getContext(), "Dades guardades correctament",
                Toast.LENGTH_LONG).show();
    }

    private void inserir_alumne(Alumne alumne){
        final String id_alumne=String.valueOf(alumne.getId());
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://azamoradam.000webhostapp.com/connect/insert_alumne.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id_alumne", id_alumne);
                params.put("id_torn", torn_classe);

                return params;
            }
        };


        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }


}
