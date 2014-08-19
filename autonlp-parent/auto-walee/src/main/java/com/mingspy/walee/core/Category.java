package com.mingspy.walee.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类<br>
 * 采用形如linux文件路径的形式，表示层级分类。比如：<br>
 * /买车/价格/比较:0.5, /二手车/估价:0.3,/维修/发动机故障:0.2<br>
 * /买车/价格/比较, /二手车/估价,/维修/发动机故障
 * 
 * @author xiuleili
 * 
 */
public class Category {
	private static final String WEIGHT_SEPERATOR = ":";
	private static final String NAME_SEPERATOR = "/";
	private static final String CATEGORY_SEPERATOR = ",";
	private String name;
	private double weight = 1.0;

	public Category(){
	}
	
	public Category(String name, double weight){
		checkFormat(name);
		this.name = name;
		this.weight = weight;
	}

	private static void checkFormat(String name) {
		if (!name.startsWith(NAME_SEPERATOR) || name.endsWith(NAME_SEPERATOR)) {
			throw new RuntimeException(name + "->格式错误!");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(WEIGHT_SEPERATOR);
		builder.append(weight);
		return builder.toString();
	}

	public static List<Category> parse(String catStr) {
		List<Category> results = new ArrayList<Category>();
		String[] cats = catStr.split(CATEGORY_SEPERATOR);
		for (String cat : cats) {
			if (!cat.isEmpty()) {
				Category  ct = new Category();
				int idx = cat.lastIndexOf(WEIGHT_SEPERATOR);
				if (idx != -1) {
					String wStr = cat.substring(idx + 1);
					ct.weight = Double.parseDouble(wStr);
					cat = cat.substring(0, idx);
				}
				
				checkFormat(cat);
				
				ct.name = cat;
				results.add(ct);
			}
		}
		return results.size() > 0 ? results : null;
	}

	public static String toString(List<Category> categories) {
		StringBuilder builder = new StringBuilder();
		for(Category cat : categories){
			builder.append(cat.name);
			builder.append(WEIGHT_SEPERATOR);
			builder.append(cat.weight);
			builder.append(CATEGORY_SEPERATOR);
		}
		return builder.toString();
	}

	public static int distance(Category c1, Category c2){
		String [] names1 = c1.name.split(NAME_SEPERATOR);
		String [] names2 = c2.name.split(NAME_SEPERATOR);
		int i = 0, j = 0;
		for(; i < names1.length &&j < names2.length;i++, j++){
			if(!names1[i].equalsIgnoreCase(names2[j])){
				break;
			}
		}
		return names1.length + names2.length - i - j;
	}
	
	public int distance(Category another){
		return distance(this, another);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Category){
			Category another = (Category)obj;
			return name.equalsIgnoreCase(another.name);
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
