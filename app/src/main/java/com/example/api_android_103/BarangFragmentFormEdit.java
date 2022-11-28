package com.example.api_android_103;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.api_android_103.Client;
import com.example.api_android_103.Interface;
import com.example.api_android_103.models.Barang.SingleDataBarang;
import com.example.api_android_103.models.GetReponseSuccess;
import com.example.api_android_103.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangFragmentFormEdit extends Fragment {
    ProgressDialog progressDoalog;
    SharedPreferences myPrefs;
    EditText name,email,pass,pr_apellido,sg_apellido,usuario;
    String estado,condicion,rol;

    public BarangFragmentFormEdit() {}

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_barang_form, container, false);

        name = (EditText)rootView.findViewById(R.id.name);
        pr_apellido = (EditText)rootView.findViewById(R.id.pr_apellido);
        sg_apellido = (EditText)rootView.findViewById(R.id.sg_apellido);
        rol = "3";
        email = (EditText)rootView.findViewById(R.id.email);
        usuario = (EditText)rootView.findViewById(R.id.usuario);
        pass = (EditText)rootView.findViewById(R.id.password);
        estado = "Activo";
        condicion = "Autorizado";

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        myPrefs = getActivity().getSharedPreferences("STORAGE_LOGIN_API", getActivity().MODE_PRIVATE);
        String token = myPrefs.getString("TOKEN","");
        final String id = myPrefs.getString("editedId", "");
        //Log.v("ID : ", id);
        Interface service = Client.getClient(token).create(Interface.class);
        Call<SingleDataBarang> call = service.postBarangEdit("Bearer "+token,Integer.parseInt(id) );
        call.enqueue(new Callback<SingleDataBarang>() {
            @Override
            public void onResponse(Call<SingleDataBarang> call, Response<SingleDataBarang> response) {
                if(response.isSuccessful()){
                    progressDoalog.dismiss();
                    /*Log.v("log softgain : ", String.valueOf(response.body().getSuccess().getData().getNamaBarang()));*/
                    name.setText(String.valueOf(response.body().getSuccess().getData().getName()));
                    pr_apellido.setText(String.valueOf(response.body().getSuccess().getData().getEmail()));
                    sg_apellido.setText(String.valueOf(response.body().getSuccess().getData().getEmail()));
                    email.setText(String.valueOf(response.body().getSuccess().getData().getEmail()));
                    usuario.setText(String.valueOf(response.body().getSuccess().getData().getEmail()));
                    //pass.setText(String.valueOf(response.body().getSuccess().getData().getEmail()));
                }else{
                    /*Log.v("log softgain : ", String.valueOf(response.errorBody()));*/
                    progressDoalog.dismiss();
                    Toast.makeText(getActivity() ,"Error al recuperar los datos!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleDataBarang> call, Throwable t) {
                Log.v("log softgain : ", String.valueOf(t));
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button) rootView.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();

                Interface service = Client.getClient(token).create(Interface.class);
                Call<GetReponseSuccess> call = service.postBarangUpdate("Bearer "+token, name.getText().toString(), pr_apellido.getText().toString(),sg_apellido.getText().toString(),rol,email.getText().toString(), usuario.getText().toString(),pass.getText().toString(),estado,condicion, Integer.parseInt(id));
                call.enqueue(new Callback<GetReponseSuccess>() {
                    @Override
                    public void onResponse(Call<GetReponseSuccess> call, Response<GetReponseSuccess> response) {
                        if(response.isSuccessful()){
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity(), "Datos actualizados!", Toast.LENGTH_SHORT).show();

                            BarangFragmentList nextFrag= new BarangFragmentList();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }else{
                            /*Log.v("log softgain : ", String.valueOf(response.errorBody()));*/
                            progressDoalog.dismiss();
                            Toast.makeText(getActivity() ,"Error al recuperar los datos!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetReponseSuccess> call, Throwable t) {
                        /*Log.v("log softgain : ", String.valueOf(t));*/
                        progressDoalog.dismiss();
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return rootView;
    }
}
