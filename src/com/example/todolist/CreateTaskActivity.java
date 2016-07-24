/**
 * CreateTaskActivity.java
 * A Yang
 * Ajay Vijayakumaran Nair
 * Nachiket Doke
 */
package com.example.todolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.to_dolist.R;

public class CreateTaskActivity extends Activity {

	private Calendar currentDateTimeSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
		currentDateTimeSet = Calendar.getInstance();
		EditText editTextDate = ((EditText) findViewById(R.id.editTextDate));
		editTextDate.setKeyListener(null);
		editTextDate.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					dateTextViewClicked(v);
				}
			}
		});
		EditText editTextTime = ((EditText) findViewById(R.id.editTextTime));
		editTextTime.setKeyListener(null);
		editTextTime.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					timeTextViewClicked(v);
				}
			}
		});
	}

	public void dateTextViewClicked(View view) {
//		Calendar calendar = Calendar.getInstance();
		new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				CreateTaskActivity.this.currentDateTimeSet.set(year, monthOfYear, dayOfMonth);
				((EditText) findViewById(R.id.editTextDate)).setText(new SimpleDateFormat(Task.dateFormat, Locale.US)
						.format(CreateTaskActivity.this.currentDateTimeSet.getTime()));
			}
		}, CreateTaskActivity.this.currentDateTimeSet.get(Calendar.YEAR), CreateTaskActivity.this.currentDateTimeSet.get(Calendar.MONTH), CreateTaskActivity.this.currentDateTimeSet.get(Calendar.DAY_OF_MONTH)).show();
	}

	public void timeTextViewClicked(View view) {
//		Calendar calendar = Calendar.getInstance();
		new TimePickerDialog(this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				CreateTaskActivity.this.currentDateTimeSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
				CreateTaskActivity.this.currentDateTimeSet.set(Calendar.MINUTE, minute);
				((EditText) findViewById(R.id.editTextTime)).setText(new SimpleDateFormat(Task.timeFormat, Locale.US)
						.format(CreateTaskActivity.this.currentDateTimeSet.getTime()));
			}
		}, CreateTaskActivity.this.currentDateTimeSet.get(Calendar.HOUR), CreateTaskActivity.this.currentDateTimeSet.get(Calendar.MINUTE), false).show();
	}

	public void saveBtnClicked(View view) {
		Task task = new Task();
		task.setName(((EditText) findViewById(R.id.editTextTitle)).getText().toString());
		task.setDateTime(this.currentDateTimeSet.getTime());
		int checkedPriority = ((RadioGroup)findViewById(R.id.radioGroup1)).getCheckedRadioButtonId();
		task.setPriority(Priority.fromString(((RadioButton)findViewById(checkedPriority)).getText().toString()));
		Intent intent = new Intent();
		intent.putExtra(MainActivity.NEW_TASK_KEY, task);
		setResult(RESULT_OK, intent);
		finish();
	}
}
