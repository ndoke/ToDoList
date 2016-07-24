/**
 * DisplayActivity.java
 * A Yang
 * Ajay Vijayakumaran Nair
 * Nachiket Doke
 */
package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.to_dolist.R;

public class DisplayActivity extends Activity {

	private Task currentlyEditedTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent = getIntent();
		alterDisplay(intent);
	}

	private void alterDisplay(Intent intent) {
		if (intent.getExtras() != null) {
			currentlyEditedTask = (Task) intent.getExtras().get(MainActivity.NEW_TASK_KEY);
			((TextView) findViewById(R.id.textViewName)).setText(currentlyEditedTask.getName());
			((TextView) findViewById(R.id.textViewDate)).setText(currentlyEditedTask.getDate());
			((TextView) findViewById(R.id.textViewTime)).setText(currentlyEditedTask.getTime());
			((TextView) findViewById(R.id.textViewPriority)).setText(currentlyEditedTask.getPriority().getDesc());
		}
	}

	public void editClicked(View view) {
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra(MainActivity.NEW_TASK_KEY, currentlyEditedTask);
		startActivityForResult(intent, MainActivity.EDIT_TASK_REQ_CODE);
	}

	public void deleteClicked(View view) {
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra(MainActivity.NEW_TASK_KEY, currentlyEditedTask);
		intent.putExtra(MainActivity.DEL_FLAG_KEY, true);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MainActivity.EDIT_TASK_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				alterDisplay(data);
				setResult(RESULT_OK, data);
				//finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStop() {
		Log.d(MainActivity.LOGGING_TAG, "On Stop Called");
		super.onStop();
	}

}
