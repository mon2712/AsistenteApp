package com.vane.montse.asistente;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * A login screen that offers login via user/password.
 */
public class LoginActivity extends AppCompatActivity {


    protected RequestQueue fRequestQueue;
    public JsonObjectRequest jsArrayRequest;
    private VolleySingleton volley;

    Variables variables;

    // UI references.
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        volley = VolleySingleton.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        variables = new Variables(LoginActivity.this);

        // Set up the login form.
        mUserView = (AutoCompleteTextView) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUserSignInButton = (Button) findViewById(R.id.user_sign_in_button);
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid user, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if  (!isPasswordValid(password))  {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user address.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!isUserValid(user)) {
            mUserView.setError(getString(R.string.error_invalid_user));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            loginIn(user, password);

        }
    }


    public void loginIn(String user, String pass){
        String peticion;

        System.out.println("pass " + pass + "user " + user);
        peticion = Requests.URL_LOGIN.replace("#pass", pass).replace("#user", user);
        Log.d("URL", peticion);
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
                        int code = infoLogin.getInt("code");
                        String type = infoLogin.getString("type");

                        if(code == 1 && type.equals("asistente")){

                            String user = infoLogin.getString("name");
                            String id = infoLogin.getString("id");

                            variables.setIdAsistente(Integer.parseInt(id));
                            variables.setNameUser(user);
                            variables.setTypeUser(type);

                            variables.setOnAuth(true);

                            System.out.println("estudiantes " + infoLogin.getJSONArray("students").toString());

                            Intent student = new Intent(LoginActivity.this, StudentActivity.class);
                            student.putExtra("userName", user);
                            student.putExtra("idUser", id);
                            student.putExtra("students", infoLogin.toString());
                            startActivity(student);
                            finish();


                        }else{

                            alertUserFailed().show();
                        }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            alertUserFailed().show();
                        }



                        Log.d("InfoLoginOnAuth", "User: " + String.valueOf(variables.getIdAsistente()) + "LogIn: " + String.valueOf(variables.getOnAuth()));


                        showProgress(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginActivity", "Error Respuesta en JSON: " + error.getMessage());
                        showProgress(false);
                        alertUserFailed().show();
                    }
                }
        );

        fRequestQueue.add(jsArrayRequest);

    }


    private boolean isUserValid(String user) {
        //TODO: Replace this with your own logic
        return user.length() >= 1;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        //        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public AlertDialog alertUserFailed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Error")
                .setMessage("Usuario o contraseña incorrecta. Intente de nuevo.")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }


}

