package com.example.actionmodedialogeadapter;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    List<String> lista = new ArrayList<>();
    String selecionado = "";
    Integer selecionadoPosicao = -1;
    ItemAdapter adapter;
    protected Object actionModeObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        adapter = new ItemAdapter(this, lista);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.act_add) {
            adapter.insert(this);
            return true;
        }
        return false;
    }
    public void sendDialogDataToActivity(String data) {
        lista.add(data);
        adapter.notifyDataSetChanged();
        listView.onSaveInstanceState();

    }
    public void sendDialogDataToActivityEdit(Integer position, String data) {
        lista.set(position, data);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selecionado = (String) adapter.getItem(i);
        selecionadoPosicao = i;
        actionModeObject = MainActivity.this.startActionMode(actionModeCallback);
    }
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.action_mode, menu);
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.act_delete) {
                adapter.remove(selecionado);
                adapter.notifyDataSetChanged();
                actionMode.finish();
                return true;
            } else if (menuItem.getItemId() == R.id.act_edit) {
                adapter.edit(MainActivity.this, selecionado, selecionadoPosicao);
                actionMode.finish();
                return true;
            }
            return false;
        }
        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
        }
    };
}