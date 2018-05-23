package com.example.alber.prova_tab_menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    EditText et_usuari, et_contrasenya;
    Button btn_login;
    ProgressDialog progressDialog;

    INTERNET internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(ActivityLogin.this,R.style.AppCompatAlertDialogStyle);
        et_usuari=findViewById(R.id.etUsername);
        et_contrasenya=findViewById(R.id.etPassword);
        btn_login=findViewById(R.id.btnLogin);
        progressDialog.setMessage("Iniciant Sessió....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Thread tr= new Thread(){
                    @Override
                    public void run() {
                        final String res=ServiceHandler.enviarPostUsuari(et_usuari.getText().toString(), et_contrasenya.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r=ServiceHandler.objJSON(res);
                                progressDialog.dismiss();
                                if(res==null){
                                    Toast.makeText(getApplicationContext(), "No tens conexió a internet!",
                                            Toast.LENGTH_LONG).show();
                                }
                                else{
                                    if(r>0){
                                        Intent i=new Intent(getApplicationContext(), MainActivityMenu.class);
                                        startActivity(i);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Usuari o Contrasenya incorrectes!",
                                                Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                        });
                    }
                };
                tr.start();
            }
        });

    }
}
