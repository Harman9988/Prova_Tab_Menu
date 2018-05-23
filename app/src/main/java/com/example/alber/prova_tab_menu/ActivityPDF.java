package com.example.alber.prova_tab_menu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.github.barteksc.pdfviewer.util.Util.toByteArray;

public class ActivityPDF extends AppCompatActivity {
    PDFView visor_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        visor_pdf=findViewById(R.id.pdfViewer);
        new RetrievePDFBytes().execute("http://azamoradam.000webhostapp.com/image/Menu_prova.pdf");
    }
    class RetrievePDFBytes extends AsyncTask<String, Void, byte[]>
    {
        @Override
        protected byte[] doInBackground(String... strings) {
            InputStream inputStream= null;
            try{
                URL url= new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            }catch(IOException e){
                e.printStackTrace();
                return null;
            }

            try {
                return toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            visor_pdf.fromBytes(bytes).load();
        }
    }
}