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

	public static List<Category> parse(String catStr) {
		List<Category> results = new ArrayList<Category>();
		String[] cats = catStr.split(CATEGORY_SEPERATOR);
		for (String cat : cats) {
			if (!cat.isEmpty()) {
				int idx = cat.lastIndexOf(WEIGHT_SEPERATOR);
				double weight = 1.0;
				if (idx != -1) {
					String wStr = cat.substring(idx + 1);
					weight = Double.parseDouble(wStr);
					cat = cat.substring(0, idx);
				}
				if (!cat.startsWith(NAME_SEPERATOR) || cat.endsWith(NAME_SEPERATOR)) {
					throw new RuntimeException(cat + "->格式错误!");
				}
				
				Category  ct = new Category();
				ct.name = cat;
				ct.weight = weight;
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

	public boolean equals(Category another) {
		return name.equalsIgnoreCase(another.name);
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
