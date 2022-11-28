package com.example.api_android_103;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api_android_103.Client;
import com.example.api_android_103.Interface;
import com.example.api_android_103.BarangAdapter;
import com.example.api_android_103.models.Barang.Barang;
import com.example.api_android_103.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosFragment extends Fragment {
    ProgressDialog progressDoalog;
    SharedPreferences myPrefs;

    public UsuariosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_usuarios, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        myPrefs = getActivity().getSharedPreferences("STORAGE_LOGIN_API", getActivity().MODE_PRIVATE);
        String token = myPrefs.getString("TOKEN","");
        //Log.v("TOKEN :", token);
        Interface service = Client.getClient(token).create(Interface.class);
        Call<Barang> call = service.postBarang("Bearer "+token);
        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                /*Log.v("log softgain : ", String.valueOf(response.body().getSuccess().getData().get(0).getNamaBarang()));*/
                Log.v("res : ", String.valueOf(response.body()));
                progressDoalog.dismiss();
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                assert response.body() != null;
                BarangAdapter mAdapter = new BarangAdapter(response.body().getSuccess().getData());
                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Log.v("error : ", String.valueOf(t));
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later3!", Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);

        return rootView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.layout.top_menu_fragment_list, menu);
    }
}