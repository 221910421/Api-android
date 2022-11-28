package com.example.api_android_103;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;



public class RegisterActivity extends AppCompatActivity {
    EditText etName, etPrimer_apellido, etSegundo_apellido,  etId_rol, etEmail, etUsuario, etPassword, etConfirmation, etEstado, etCondicion;
    Button btnRegister;
    String name, primer_apellido, segundo_apellido, id_rol, email, usuario, password, confirmation, estado, condicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("REGISTRATE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPrimer_apellido = findViewById(R.id.etPrimer_apellido);
        etSegundo_apellido = findViewById(R.id.etSegundo_apellido);
        etId_rol = findViewById(R.id.etId_rol);
        etEmail = findViewById(R.id.etEmail);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        etConfirmation = findViewById(R.id.etConfirmation);
        etEstado = findViewById(R.id.etEstado);
        etCondicion = findViewById(R.id.etCondicion);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegister();
            }
        });
    }

    private void checkRegister() {
        name = etName.getText().toString();
        primer_apellido = etPrimer_apellido.getText().toString();
        segundo_apellido = etSegundo_apellido.getText().toString();
        id_rol = etId_rol.getText().toString();
        email = etEmail.getText().toString();
        usuario = etUsuario.getText().toString();
        password = etPassword.getText().toString();
        confirmation= etConfirmation.getText().toString();
        estado = etEstado.getText().toString();
        condicion = etCondicion.getText().toString();
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            alertFail("Name, Email and Password is required");
        }
        else if ( ! password.equals(confirmation)){
            alertFail("Password and password confirmation doesn´t match.");
        }
        else {
            sendRegister();
        }
    }

    private void sendRegister() {
        JSONObject params = new JSONObject();
        try {
            params.put("name",name);
            params.put("primer_apellido",primer_apellido);
            params.put("segundo_apellido",segundo_apellido);
            params.put("id_rol",id_rol);
            params.put("email",email);
            params.put("usuario",usuario);
            params.put("password",password);
            params.put("password_confirmation",confirmation);
            params.put("estado",estado);
            params.put("condicion",condicion);
        } catch (JSONException e){
            e.printStackTrace();
        }
        String data = params.toString();
        String url = getString(R.string.api_server)+"/register";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(RegisterActivity.this, url);
                http.setMethod("post");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 201 || code == 200){
                            alertSuccess("Register Successfully");
                        }
                        else if ( code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void alertSuccess(String s) {
            new AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setIcon(R.drawable.ic_check)
                    .setMessage(s)
                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            onBackPressed();
                        }
                    })
                    .show();
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}