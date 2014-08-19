package com.mingspy.walee.core;

public class Slot {
	public static final String SLOT_START = "<";
	public static final String SLOT_END = ">";
	public static boolean isSlot(String str){
		return str.startsWith(SLOT_START) && str.endsWith(SLOT_END);
	}
	
	public static String toSlot(String str){
		if(!(str.startsWith(SLOT_START) && str.endsWith(SLOT_END))){
			StringBuilder builder = new StringBuilder();
			builder.append(SLOT_START);
			builder.append(str);
			builder.append(SLOT_END);
			str = builder.toString();
		}
		
		return str;
	}
}
