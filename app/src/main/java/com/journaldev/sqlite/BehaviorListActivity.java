package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BehaviorListActivity extends AppCompatActivity {

    private DBBehaviorManager dbManager;

    private ListView listView;
    private TextView textView;
    public String itemId;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseBehaviorHelper._ID,
            DatabaseBehaviorHelper.BEHAVIORID, DatabaseBehaviorHelper.BEHAVIORNAME, DatabaseBehaviorHelper.BEHAVIORDATE };

    final int[] to = new int[] { R.id.id, R.id.studentid, R.id.studentname, R.id.studentper };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_behavior_list);

        dbManager = new DBBehaviorManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();


        textView = (TextView) findViewById(R.id.textView);
        String studentid = "Jeys";
        textView.setText(studentid);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        //This messed with me for TOO LONG!  DataItemAdapter  ITEM_ID = "item_id" and thats whats needed
        if (bundle != null) {
            itemId = bundle.getString("item_id");
        }
        Toast.makeText(this, "You selected " + itemId,
                Toast.LENGTH_SHORT).show();


        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.behavior_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView studentidTextView = (TextView) view.findViewById(R.id.studentid);
                TextView studentnameTextView = (TextView) view.findViewById(R.id.studentname);
                TextView studentperTextView = (TextView) view.findViewById(R.id.studentper);

                String id = idTextView.getText().toString();
                String studentid = studentidTextView.getText().toString();
                String studentname = studentnameTextView.getText().toString();
                String studentper = studentperTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyBehaviorActivity.class);
                modify_intent.putExtra("studentid", studentid);
                modify_intent.putExtra("studentname", studentname);
                modify_intent.putExtra("studentper", studentper);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });
/*
        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbManager.fetchCountriesByName(constraint.toString());
            }
        });
*/

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, AddBehaviorActivity.class);
            Toast.makeText(this, "You selected " + itemId,
                    Toast.LENGTH_SHORT).show();
            add_mem.putExtra("item_id", itemId);
            //this is the one for add.  See above for modify
            startActivity(add_mem);

        }
        //THIS IS THE OTHER ICON ON THE TOP.  SORT OF A SETTINGS LOOKING THING
        if (id == R.id.view_record) {

            //Intent view_mem = new Intent(this, BehaviorCheckListActivity.class);
            //startActivity(view_mem);
        }

        //THIS IS THE OTHER ICON ON THE TOP.  SORT OF A SETTINGS LOOKING THING
        if (id == R.id.another_record) {

            //Intent view_mem = new Intent(this, ImportMainActivity.class);
            //startActivity(view_mem);
        }

        return super.onOptionsItemSelected(item);
    }

}