package com.imsher.chandan.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        readItems();
        listItemView =  (ListView) findViewById(R.id.listview1);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listItemView.setAdapter(itemsAdapter);
//        items.add("Learn Android");
//        items.add("Create a todo app");
        setupRemoveListener();
    }
    public void setupRemoveListener(){
        listItemView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                Toast.makeText(getApplicationContext(), "Moved to completed",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    public void addItem(View v){
        EditText editText= (EditText)findViewById(R.id.editText1);
        String todoItem = editText.getText().toString();
        items.add(todoItem);
        writeItems();
        editText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void readItems(){
        File filesdir= getFilesDir();
        File todofile = new File(filesdir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todofile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    public void writeItems(){
        File filesdir= getFilesDir();
        File todofile = new File(filesdir, "todo.txt");
        try{
            FileUtils.writeLines(todofile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
