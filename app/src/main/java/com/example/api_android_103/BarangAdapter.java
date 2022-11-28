package com.example.api_android_103;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.api_android_103.Client;
import com.example.api_android_103.Interface;
import com.example.api_android_103.UsuariosFragment;
import com.example.api_android_103.MainActivity;
import com.example.api_android_103.models.Barang.Datum;
import com.example.api_android_103.models.GetReponseSuccess;
import com.example.api_android_103.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.CustomViewHolder>{
    private List<Datum> dataList;
    private Context context;
    ProgressDialog progressDoalog;
    SharedPreferences myPrefs;

    public BarangAdapter(List<Datum> dataList){
        this.dataList = dataList;
    }

    public BarangAdapter(UsuariosFragment connectFragment, List<Datum> dataList) {}

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView txtTitle;
        EditText txtId;
        ImageView edit, delete;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            edit = (ImageView) mView.findViewById(R.id.edit);
            delete = (ImageView) mView.findViewById(R.id.delete);
            txtId = mView.findViewById(R.id.id);
            txtId.setVisibility(View.GONE);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter, parent, false);
        context = parent.getContext();

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getName());
        final String idData = String.valueOf(dataList.get(position).getId());
        final String titleData = String.valueOf(dataList.get(position).getEmail());

        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*Log.v("log softgain : ", String.valueOf(idData));*/
                SharedPreferences sgSharedPref = context.getSharedPreferences("STORAGE_LOGIN_API", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sgSharedPref.edit();
                editor.putString("editedId", idData);
                editor.apply();

                BarangFragmentFormEdit nextFrag= new BarangFragmentFormEdit();
                ((UserActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences sgSharedPref = context.getSharedPreferences("STORAGE_LOGIN_API", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sgSharedPref.edit();
                editor.putString("editedId", idData);
                editor.apply();
                try{
                    showDialog(String.valueOf(titleData), String.valueOf(idData));
                } catch (Exception e){
                    e.printStackTrace();
                }
                /*Log.v("log softgain : ", String.valueOf(idData));*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void showDialog(final String title, final String id) throws Exception {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Eliminar : " + title + "?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();

                progressDoalog = new ProgressDialog(context);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();

                myPrefs = context.getSharedPreferences("STORAGE_LOGIN_API", context.MODE_PRIVATE);
                String token = myPrefs.getString("TOKEN","");
                final String id = myPrefs.getString("editedId", "");
                Log.v("DELETEID : ", id.toString());
                Interface service = Client.getClient(token).create(Interface.class);
                Call<GetReponseSuccess> call = service.postBarangDelete("Bearer "+token, Integer.parseInt(id));
                call.enqueue(new Callback<GetReponseSuccess>() {
                    @Override
                    public void onResponse(Call<GetReponseSuccess> call, Response<GetReponseSuccess> response) {
                        if(response.isSuccessful()){
                            progressDoalog.dismiss();
                            Toast.makeText(context, "Usuario eliminado!", Toast.LENGTH_SHORT).show();

                            UsuariosFragment nextFrag= new UsuariosFragment();
                            ((UserActivity)context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }else{
                            Log.e("ERROR : ", String.valueOf(response));
                            progressDoalog.dismiss();
                            Toast.makeText(context ,"Something went wrong...Please try later!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetReponseSuccess> call, Throwable t) {
                        /*Log.v("log softgain : ", String.valueOf(t));*/
                        progressDoalog.dismiss();
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
