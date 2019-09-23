package com.opatan.posttest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class DialogJava extends DialogFragment implements View.OnClickListener{

    Button update_data, hapus_data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.format_dialog, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.format_dialog, null);
        builder.setView(view);

        update_data = view.findViewById(R.id.btnUpdateData);
        update_data.setOnClickListener(this);
        hapus_data = view.findViewById(R.id.btnHapusData);
        hapus_data.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View view) {

        Bundle args;

        switch (view.getId()){

            case R.id.btnUpdateData:
                Intent i = new Intent(getContext(), UpdateData.class);
                args = getArguments();
                i.putExtra("id", args.getString("id"));
                i.putExtra("judul",args.getString("judul"));
                i.putExtra("deskripsi",args.getString("deskripsi"));
                dismiss();
                startActivity(i);
                break;
            case R.id.btnHapusData:

                showDialog();

                KoneksiDatabase kd = new KoneksiDatabase(getContext());
                kd.selectUserData();
                break;

        }

    }

    public void showDialog(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        KoneksiDatabase k = new KoneksiDatabase(getContext());
                        Bundle args = getArguments();
                        Toast.makeText(getContext(),"Data "+args.getString("judul")+" sukses dihapus",Toast.LENGTH_SHORT).show();
                        k.delete(args.getString("id"));
                        dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage("Yakin Akan Menghapus?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();


    }
}
