package com.example.alber.prova_tab_menu.Extras;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

public class shared_Preferences {

    public static final String PACKAGE = "com.example.alber";
    public static final String SESION_GUARDADA = "sessio_guardada";

    public static void setSharedPreferences(Context c, boolean bool){
        //Le pasamos por parametro el package y el modo
        android.content.SharedPreferences sPreferences = c.getSharedPreferences(PACKAGE, MODE_PRIVATE);

        //Le pasamos por parametro el nombre de la key y la boolean
        sPreferences.edit().putBoolean(SESION_GUARDADA, bool).apply();

    }

    public static void changeString(Context c, String string, String key){
        //Le pasamos por parametro el package y el modo
        android.content.SharedPreferences sPreferences = c.getSharedPreferences(PACKAGE, MODE_PRIVATE);

        //Le pasamos por parametro el nombre de la key y la boolean
        sPreferences.edit().putString(key, string).apply();

    }


    //Funcion para obtener el boolean SharedPreferences
    public static boolean getSharedPreferences(Context c) {
        //Le pasamos por parametro el package y el modo.
        android.content.SharedPreferences sPreferences = c.getSharedPreferences(PACKAGE, MODE_PRIVATE);

        //Devuelve el valor de la booleana.
        return sPreferences.getBoolean(SESION_GUARDADA, false);

    }
}