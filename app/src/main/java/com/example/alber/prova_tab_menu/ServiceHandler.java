package com.example.alber.prova_tab_menu;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ServiceHandler {
    static public String enviarPostUsuari(String usuari, String contrasenya) {
        String parametres = "username=" + usuari + "&password=" + contrasenya;
        HttpURLConnection conexio = null;
        String resposta = "";
        try {
            URL url = new URL("http://azamoradam.000webhostapp.com/connect/login.php");
            conexio = (HttpURLConnection) url.openConnection();
            conexio.setRequestMethod("POST");
            conexio.setRequestProperty("Content-Length", "" + Integer.toString(parametres.getBytes().length));

            conexio.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conexio.getOutputStream());
            wr.writeBytes(parametres);
            wr.close();

            Scanner inStream = new Scanner(conexio.getInputStream());
            while (inStream.hasNextLine()) {
                resposta += (inStream.nextLine());
            }

        } catch (Exception e) {
            return null;
        }
        return resposta.toString();
    }

    static public String enviarPost(String url_mirar) {
        String parametres = "";
        HttpURLConnection conexio = null;
        String resposta = "";
        try {
            URL url = new URL(url_mirar);
            conexio = (HttpURLConnection) url.openConnection();

            conexio.setRequestMethod("POST");
            conexio.setRequestProperty("Content-Length", "" + Integer.toString(parametres.getBytes().length));

            conexio.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conexio.getOutputStream());
            wr.writeBytes(parametres);
            wr.close();

            Scanner inStream = new Scanner(conexio.getInputStream());
            while (inStream.hasNextLine()) {
                resposta += (inStream.nextLine());
            }

        } catch (Exception e) {
            return null;
        }
        return resposta.toString();
    }


    static public int objJSON(String resposta) {
        int res = 0;
        try {
            JSONArray json = new JSONArray(resposta);
            if (json.length() > 0) {
                res = 1;
            }

        } catch (Exception e) {

        }
        return res;
    }
    static public String get_data(String url_mirar){
        String resultat="";
        try{

            URL url= new URL(url_mirar);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream= httpURLConnection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line!=null){
                line=bufferedReader.readLine();
                resultat=resultat+line;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultat;
    }
}
