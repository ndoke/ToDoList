/**
 * MainActivity.java
 * A Yang
 * Ajay Vijayakumaran Nair
 * Nachiket Doke
 */
package com.example.todolist;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.to_dolist.R;

public class MainActivity extends Activity {

	static final int CREATE_TASK_REQ_CODE = 1000;
	static final int EDIT_TASK_REQ_CODE = 2000;
	static final String DEL_FLAG_KEY = "delete";
	static final String NEW_TASK_KEY = "task";
	static final String LOGGING_TAG = "hw2";
	
	private LinearLayout currentlyEditedTaskLayout;
	private Task currentlyEditedTask;

	private List<Task> tasks = new LinkedList<Task>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayAllTasks();
	}

	public void addTaskClicked(View view) {
		Intent intent = new Intent(this, CreateTaskActivity.class);
		startActivityForResult(intent, CREATE_TASK_REQ_CODE);
	}

	public void displayAllTasks() {
		((TextView) findViewById(R.id.textViewTaskCount)).setText(tasks.size() + " Tasks");
		for (Task task : tasks) {
			displayTask(task);
		}
	}

	private void displayTask(Task task) {
		LinearLayout row = new LinearLayout(this);
		row.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layoutParams.topMargin = 20;
		row.setLayoutParams(layoutParams);
		row.setTag(task.getTaskId());
		TextView taskName = new TextView(this);
		taskName.setTextAppearance(this, android.R.style.TextAppearance_Large);
		taskName.setTypeface(null, Typeface.BOLD);
		taskName.setText(task.getName());
		row.addView(taskName, 0);
		TextView date = new TextView(this);
		date.setText(task.getDate());
		row.addView(date, 1);
		TextView time = new TextView(this);
		time.setText(task.getTime());
		row.addView(time, 2);
		((LinearLayout) findViewById(R.id.linerLayoutScroll)).addView(row);
		row.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentlyEditedTaskLayout = (LinearLayout) v;
				Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
				long editiedTaskId = (Long) currentlyEditedTaskLayout.getTag();
				for (Task task : tasks) {
					if (task.getTaskId() == editiedTaskId) {
						currentlyEditedTask = task;
						break;
					}
				}
				intent.putExtra(NEW_TASK_KEY, currentlyEditedTask);
				startActivityForResult(intent, EDIT_TASK_REQ_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != RESULT_OK){
			Log.w(LOGGING_TAG, "Activity on result not OK :" + resultCode);
			return;
		}
		switch (requestCode) {

		case CREATE_TASK_REQ_CODE:
			if (data.getExtras() != null) {
				Task task = (Task) data.getExtras().get(NEW_TASK_KEY);
				long taskId = getNextId();
				task.setTaskId(taskId);
				tasks.add(task);
				displayTask(task);
				((TextView) findViewById(R.id.textViewTaskCount)).setText(tasks.size() + " Tasks");
			}else{
				Log.w(LOGGING_TAG, "Create task onActivityResult no extras in return data ");
			}
			break;
		case EDIT_TASK_REQ_CODE:
			if(data.getExtras() == null){
				Log.w(LOGGING_TAG, "Edit/Delete task onActivityResult no extras in return data ");
				return;
			}
			Task task = (Task) data.getExtras().get(NEW_TASK_KEY);
			boolean isDelete = false;
			if (data.getExtras().get(DEL_FLAG_KEY) != null) {
				isDelete = (Boolean) data.getExtras().get(DEL_FLAG_KEY);
			}
			if (isDelete) {
				tasks.remove(task);
				((TextView) findViewById(R.id.textViewTaskCount)).setText(tasks.size() + " Tasks");
				((LinearLayout) currentlyEditedTaskLayout.getParent()).removeView(currentlyEditedTaskLayout);
			} else {
				for (Task taskIt : tasks) {
					if (taskIt.equals(task)) {
						taskIt.setName(task.getName());
						taskIt.setDateTime(task.getDateTime());
						taskIt.setPriority(task.getPriority());
						break;
					}
				}
				((TextView) currentlyEditedTaskLayout.getChildAt(0)).setText(task.getName());
				((TextView) currentlyEditedTaskLayout.getChildAt(1)).setText(task.getDate());
				((TextView) currentlyEditedTaskLayout.getChildAt(2)).setText(task.getTime());
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private long getNextId() {
		if (tasks.size() == 0) {
			return 1;
		}
		return tasks.get(tasks.size() - 1).getTaskId() + 1;
	}
}
