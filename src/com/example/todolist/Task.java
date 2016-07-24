package com.example.todolist;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Task implements Serializable{
	/**
	 * 
	 */
	protected static final String dateFormat = "MM/dd/yyyy";
	protected static final String timeFormat ="hh:mm a";
	private static final long serialVersionUID = -8891650548674460843L;
	private long taskId;
	private String name;
	private Date dateTime;
	private Priority priority;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	@SuppressLint("SimpleDateFormat")
	public String getTime(){
		return new SimpleDateFormat(timeFormat).format(dateTime);
	}
	@SuppressLint("SimpleDateFormat")
	/**
	 * 
	 * @return Returns the date part without time in format specified by @link Task.dateFormat
	 */
	public String getDate(){
		return new SimpleDateFormat(dateFormat).format(dateTime);
	}
	/**
	 * 
	 * @param dateTime Expected format yyyy-MM-dd HH:mm
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public void setDateTime(String dateTime) throws ParseException{
		this.dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTime);
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (taskId ^ (taskId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (taskId != other.taskId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", name=" + name + ", dateTime=" + dateTime + ", priority=" + priority + "]";
	}
	
}
