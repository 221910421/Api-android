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

public class BarangFragmentList extends Fragment {
    ProgressDialog progressDoalog;
    SharedPreferences myPrefs;

    public BarangFragmentList() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_barang_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        myPrefs = getActivity().getSharedPreferences("sg_shared_pref", getActivity().MODE_PRIVATE);
        String token = myPrefs.getString("token", "");

        Interface service = Client.getClient(token).create(Interface.class);
        Call<Barang> call = service.postBarang("Bearer "+token);
        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                /*Log.v("log softgain : ", String.valueOf(response.body().getSuccess().getData().get(0).getNamaBarang()));*/
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
                /*Log.v("log softgain : ", String.valueOf(t));*/
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
