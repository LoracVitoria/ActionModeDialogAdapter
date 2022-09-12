package com.example.actionmodedialogeadapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private AlertDialog.Builder builder;
    private final Context context;
    private List<String> items;
    public ItemAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item, viewGroup, false);
        TextView text = view.findViewById(R.id.txtItem);
        String item = items.get(i);
        text.setText(item);
        return view;
    }
    public void remove(String selecionado) {
        items.remove(selecionado);
    }
    public void edit(MainActivity view, String selecionado, Integer selecionadoPosicao) {
        builder = new AlertDialog.Builder(view);
        builder.setTitle(R.string.item);
        final View customLayout = view.getLayoutInflater().inflate(R.layout.custom_dialog, null);
        EditText editText = customLayout.findViewById(R.id.edtDialog);
        editText.setText(selecionado);
        builder.setView(customLayout);
        builder
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int i) {
                        view.sendDialogDataToActivityEdit(selecionadoPosicao, editText.getText().toString());
                    }
                });
        builder
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }
    public void insert(MainActivity view) {
        builder = new AlertDialog.Builder(view);
        builder.setTitle(R.string.item);
        final View customLayout = view.getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(customLayout);
        builder
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int i) {
                        EditText editText = customLayout.findViewById(R.id.edtDialog);
                        view.sendDialogDataToActivity(editText.getText().toString());
                    }
                });
        builder
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }
}
