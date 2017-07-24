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

public class ModifyBehaviorActivity extends Activity implements OnClickListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private EditText studentidText;
    private TextView studentnameText;
    private EditText studentperText;
    private Button updateBtn, deleteBtn;
    private EditText behaviorCommentEditText;

    private long _id;

    public String itemId;
    public String itemName;


    private DBBehaviorManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        //This messed with me for TOO LONG!  DataItemAdapter  ITEM_ID = "item_id" and thats whats needed
        itemId =  bundle.getString("item_id");
        itemName =  bundle.getString("item_name");
        //Toast.makeText(this, "Sent " + itemId + " " + itemName,
        //        Toast.LENGTH_SHORT).show();

        setTitle("Modify Record");

        setContentView(R.layout.behavior_modify_record);

        dbManager = new DBBehaviorManager(this);
        dbManager.open();

        studentidText = (EditText) findViewById(R.id.studentid_edittext);

        //studentnameText = (TextView) findViewById(R.id.studentname);
        //String studentnameTextValue = studentnameText.toString();

        //Spinner mySpinner = (Spinner) findViewById(R.id.studentname_edittext);
        //String behaviorNameEditText = mySpinner.getSelectedItem().toString();

        studentperText = (EditText) findViewById(R.id.studentper_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

//        Intent intent = getIntent();
//        String studentid = intent.getStringExtra("item_id");

        //String name = intent.getStringExtra("item_name");

        String name = intent.getStringExtra("studentname");
        Spinner spinner=(Spinner) findViewById(R.id.studentname_edittext);
        spinner.setSelection(getIndex(spinner, name));

        String consequence = intent.getStringExtra("studentcons");
        Spinner spinnerCons=(Spinner) findViewById(R.id.spinner_consequence);
        spinner.setSelection(getIndex(spinnerCons, consequence));

        String parent_contact = intent.getStringExtra("studentParentContact");
        Spinner spinnerParentContact=(Spinner) findViewById(R.id.spinner_parent_contact);
        spinner.setSelection(getIndex(spinnerParentContact, parent_contact));

        EditText behaviorCommentEditText = (EditText) findViewById(R.id.comments);
        String comment = intent.getStringExtra("studentComment");
//        if (comment == null) {
//            comment = " ";
//        }
        String period = intent.getStringExtra("studentper");
        //studentperText = (EditText) findViewById(R.id.studentper_edittext);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        String id = intent.getStringExtra("id");
        _id = Long.parseLong(id);

        studentidText.setText(itemId);
        //studentnameText.setText(name);
        studentperText.setText(period);
        behaviorCommentEditText.setText(comment);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String studentid = studentidText.getText().toString();
                //String  studentname = studentnameText.getText().toString();

                Spinner mySpinner=(Spinner) findViewById(R.id.studentname_edittext);
                String studentname = mySpinner.getSelectedItem().toString();

                Spinner spinnerCons=(Spinner) findViewById(R.id.spinner_consequence);
                String studentcons = spinnerCons.getSelectedItem().toString();

                Spinner spinnerParentContact=(Spinner) findViewById(R.id.spinner_parent_contact);
                String studentParentContact = spinnerParentContact.getSelectedItem().toString();

                EditText behaviorCommentEditText = (EditText) findViewById(R.id.comments);
                String studentComment = behaviorCommentEditText.getText().toString();

                String studentper = studentperText.getText().toString();

                dbManager.update(_id, studentid, studentname, studentper, studentcons, studentParentContact, studentComment);

                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), BehaviorListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home_intent.putExtra("item_id", itemId);
        home_intent.putExtra("item_name", itemName);
        startActivity(home_intent);
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
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
        studentperText.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}