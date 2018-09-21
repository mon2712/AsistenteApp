package com.vane.montse.asistente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private String name;
    private String id;
    private String students;

    Variables variables;

    protected RequestQueue fRequestQueue;
    public JsonObjectRequest jsArrayRequest;
    private VolleySingleton volley;

    //TextView txtViewInfo;
    TextView nameStudent;
    TextView levelStudent;
    //TextView icon;

    Icon_Manager icon_manager;

    List<Alumno> lstAlumno;

    RecyclerView myrv;
    RecyclerViewAdapter myAdapter;

    SwipeRefreshLayout swiperefresher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.control_students);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //txtViewInfo = findViewById(R.id.textViewInfo);
        //nameStudent = findViewById(R.id.name);
        //levelStudent = findViewById(R.id.level);
        volley = VolleySingleton.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

        name  = getIntent().getStringExtra("userName");
        id = getIntent().getStringExtra("idUser");
        students = getIntent().getStringExtra("students");

        swiperefresher = (SwipeRefreshLayout) findViewById(R.id.swipe_id);


        swiperefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                @Override
                                                public void onRefresh() {
                                                    getStudents();
                                                }
                                            });


        lstAlumno = new ArrayList<>();

        try {
            System.out.println(students);

            JSONObject infoLogin =  new JSONObject(students);

            JSONArray studentss = infoLogin.getJSONArray("students");

            System.out.println("despues de  el array " + studentss.length());


                for (int i = 0; i < studentss.length(); i++) {
                    System.out.println(" alumno " + studentss.getJSONObject(i) + " nombre " + studentss.getJSONObject(i).getString("name"));

                    lstAlumno.add(new Alumno(studentss.getJSONObject(i).getInt("id"), studentss.getJSONObject(i).getString("name"), studentss.getJSONObject(i).getString("lastName"), studentss.getJSONObject(i).getString("level"), studentss.getJSONObject(i).getString("entranceTime"), studentss.getJSONObject(i).getString("time"), studentss.getJSONObject(i).getString("reducedTime")));
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (lstAlumno.size() != 0) {
            myrv = (RecyclerView) findViewById(R.id.recycler_id);
            myAdapter = new RecyclerViewAdapter(this, lstAlumno);

            //myrv.setOnScrollChangeListener(onScrollListener());

            myrv.setLayoutManager(new GridLayoutManager(this, 3));

            myrv.setAdapter(myAdapter);

            System.out.println("num " + myAdapter.getItemCount());


            //icon_manager = new Icon_Manager();


            //((TextView) findViewById(R.id.iconImg)).setTypeface(icon_manager.get_icons("fonts/trabajoterminalfonts.ttf", this));

        }else{

        }
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void getStudents() {
        String peticion = Requests.URL_GETSTUDENTS.replace("#id", id).replace("#name", name);
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                peticion,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VerifyUser", response.toString());

                        try {

                            JSONObject infoLogin =  response.getJSONObject("infoLogin");
                            JSONArray studentss = infoLogin.getJSONArray("students");

                            int code = infoLogin.getInt("code");

                            if(code == 1){
                                List<Alumno> lstAlumnoNew;
                                lstAlumnoNew = new ArrayList<>();


                                //System.out.println("estudiantes " + infoLogin.getJSONArray("students").toString());

                                //Intent student = new Intent(LoginActivity.this, StudentActivity.class);
                                //student.putExtra("userName", user);
                                //student.putExtra("idUser", id);
                                //student.putExtra("students", infoLogin.toString());
                                //startActivity(student);
                                //finish();
                                int insertIndex = lstAlumno.size();

                                for (int i = 0; i < studentss.length(); i++) {
                                    //System.out.println(" alumno " + studentss.getJSONObject(i) + " nombre " + studentss.getJSONObject(i).getString("name"));
                                    //if(lstAlumno.contains(new Alumno(studentss.getJSONObject(i).getInt("id"), studentss.getJSONObject(i).getString("name"), studentss.getJSONObject(i).getString("lastName"), studentss.getJSONObject(i).getString("level"), studentss.getJSONObject(i).getString("entranceTime"), studentss.getJSONObject(i).getString("time"), studentss.getJSONObject(i).getString("reducedTime"))) == false) {
                                        lstAlumnoNew.add(new Alumno(studentss.getJSONObject(i).getInt("id"), studentss.getJSONObject(i).getString("name"), studentss.getJSONObject(i).getString("lastName"), studentss.getJSONObject(i).getString("level"), studentss.getJSONObject(i).getString("entranceTime"), studentss.getJSONObject(i).getString("time"), studentss.getJSONObject(i).getString("reducedTime")));
                                    //}
                                }


                                lstAlumno.clear();
                                lstAlumno.addAll(lstAlumnoNew);
                                myAdapter.notifyDataSetChanged();

                                //lstAlumno.addAll(insertIndex,lstAlumnoNew);

                                //myAdapter.notifyItemRangeInserted(insertIndex, lstAlumnoNew.size());

                            }else{

                                //alertUserFailed().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //alertUserFailed().show();
                        }



                        //Log.d("InfoLoginOnAuth", "User: " + String.valueOf(variables.getIdAsistente()) + "LogIn: " + String.valueOf(variables.getOnAuth()));


                        //showProgress(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginActivity", "Error Respuesta en JSON: " + error.getMessage());
                        //showProgress(false);
                        //alertUserFailed().show();
                    }
                }
        );

        fRequestQueue.add(jsArrayRequest);

        swiperefresher.setRefreshing(false);
    }

}
