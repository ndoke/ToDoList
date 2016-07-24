package com.example.todolist;

public enum Priority {
	HIGH("High"), MEDIUM("Medium"), LOW("Low");
	private String desc;

	private Priority(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	public static Priority fromString(String text){
		if(text != null){
			for(Priority p : Priority.values()){
				if (text.equalsIgnoreCase(p.desc)){
					return p;
				}
			}
		}
		return null;
	}
}
