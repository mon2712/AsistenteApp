package com.vane.montse.asistente;

import android.content.Context;
import android.content.SharedPreferences;

public class Variables {

        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        Context ctx;

        public Variables(Context ctx){
            this.ctx = ctx;
            prefs = ctx.getSharedPreferences("Asistente", Context.MODE_PRIVATE);
            editor = prefs.edit();
        }


        public void setNameUser(String name){
            editor.putString("nameUser", name);
            editor.commit();
        }


        public String getNameUser(){
            return prefs.getString("nameUser", "Asistente");
        }

        public void setIdAsistente(int id) {
            editor.putInt("idAsistente", id);
            editor.commit();
        }

        public int getIdAsistente(){
            return prefs.getInt("idAsistente", 0);

        }

        public void setTypeUser(String type) {
            editor.putString("type", type);
            editor.commit();
        }

        public String getTypeUser(){
            return prefs.getString("type", "asistente");

        }


        public void setOnAuth(boolean auth){
            editor.putBoolean("onAuth", auth);
            editor.commit();
        }

        public boolean getOnAuth(){
            return prefs.getBoolean("onAuth", false);
        }

}
