/**
 * EditActivity.java
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
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.to_dolist.R;

public class EditActivity extends Activity {
	private Calendar currentDateTimeSet;
	private Task currentlyEditedTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
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
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			currentlyEditedTask = (Task) intent.getExtras().get(MainActivity.NEW_TASK_KEY);
			((EditText) findViewById(R.id.editTextTitle)).setText(currentlyEditedTask.getName());
			((EditText) findViewById(R.id.editTextDate)).setText(currentlyEditedTask.getDate());
			((EditText) findViewById(R.id.editTextTime)).setText(currentlyEditedTask.getTime());
			if (currentlyEditedTask.getPriority() == Priority.HIGH) {
				((RadioGroup) findViewById(R.id.radioGroup1)).check(R.id.radioHigh);
			} else if (currentlyEditedTask.getPriority() == Priority.MEDIUM) {
				((RadioGroup) findViewById(R.id.radioGroup1)).check(R.id.radioMedium);
			} else {
				((RadioGroup) findViewById(R.id.radioGroup1)).check(R.id.radioLow);
			}
			currentDateTimeSet.setTime(currentlyEditedTask.getDateTime());
		}else{
			Log.w(MainActivity.LOGGING_TAG, "Intent din't have extras");
		}
	}

	public void dateTextViewClicked(View view) {
//		Calendar calendar = Calendar.getInstance();
		new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				EditActivity.this.currentDateTimeSet.set(year, monthOfYear, dayOfMonth);
				((EditText) findViewById(R.id.editTextDate)).setText(new SimpleDateFormat(Task.dateFormat, Locale.US)
						.format(EditActivity.this.currentDateTimeSet.getTime()));
			}
		}, currentDateTimeSet.get(Calendar.YEAR), currentDateTimeSet.get(Calendar.MONTH), currentDateTimeSet.get(Calendar.DAY_OF_MONTH)).show();
	}

	public void timeTextViewClicked(View view) {
		new TimePickerDialog(this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				EditActivity.this.currentDateTimeSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
				EditActivity.this.currentDateTimeSet.set(Calendar.MINUTE, minute);
				((EditText) findViewById(R.id.editTextTime)).setText(new SimpleDateFormat(Task.timeFormat, Locale.US)
						.format(EditActivity.this.currentDateTimeSet.getTime()));
			}
		}, currentDateTimeSet.get(Calendar.HOUR), currentDateTimeSet.get(Calendar.MINUTE), false).show();
	}

	public void saveBtnClicked(View view) {
		currentlyEditedTask.setName(((EditText) findViewById(R.id.editTextTitle)).getText().toString());
		currentlyEditedTask.setDateTime(this.currentDateTimeSet.getTime());
		int checkedPriority = ((RadioGroup) findViewById(R.id.radioGroup1)).getCheckedRadioButtonId();
		currentlyEditedTask.setPriority(Priority.fromString(((RadioButton) findViewById(checkedPriority)).getText().toString()));
		Intent intent = new Intent();
		intent.putExtra(MainActivity.NEW_TASK_KEY, currentlyEditedTask);
		setResult(RESULT_OK, intent);
		finish();
	}
}
