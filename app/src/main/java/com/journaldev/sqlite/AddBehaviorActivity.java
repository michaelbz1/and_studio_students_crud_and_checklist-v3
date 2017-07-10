package com.journaldev.sqlite;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddBehaviorActivity extends Activity implements OnClickListener {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private Button addTodoBtn;
    private EditText behavioridEditText;
    private EditText behaviorNameEditText;
    private EditText behaviorDateEditText;

    private DBBehaviorManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        //This messed with me for TOO LONG!  DataItemAdapter  ITEM_ID = "item_id" and thats whats needed
        final String itemId =  bundle.getString("item_id");

        setTitle("Add Behavior Record for " + itemId);

        setContentView(R.layout.activity_add_behavior_record);

        behavioridEditText = (EditText) findViewById(R.id.behaviorid_edittext);
        behavioridEditText.setText(itemId.toString());


        Spinner mySpinner=(Spinner) findViewById(R.id.behaviorname_edittext);
        String behaviorNameEditText = mySpinner.getSelectedItem().toString();
        //behaviorNameEditText = (EditText) findViewById(R.id.behaviorname_edittext);

        //behaviorDateEditText = (EditText) findViewById(R.id.behaviordate_edittext);
        behaviorDateEditText = (EditText) findViewById(R.id.behaviordate_edittext);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        addTodoBtn = (Button) findViewById(R.id.add_record);

        dbManager = new DBBehaviorManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                final String studentid = behavioridEditText.getText().toString();
                Spinner mySpinner=(Spinner) findViewById(R.id.behaviorname_edittext);
                String studentname = mySpinner.getSelectedItem().toString();
                //final String studentname = behaviorNameEditText.getText().toString();
                final String studentper = behaviorDateEditText.getText().toString();

                dbManager.insert(studentid, studentname, studentper);

                Intent main = new Intent(AddBehaviorActivity.this, BehaviorListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        behaviorDateEditText.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}