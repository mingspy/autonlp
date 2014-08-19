package com.mingspy.walee.analysis.pattern.types;

import java.util.LinkedList;
import java.util.List;

import com.mingspy.walee.core.Category;

/**
 * 模板类。<br>
 * 在用户意图识别时，采用模板匹配的方式与用户问题进行匹配。<br>
 * 模板是一种扩展的模式匹配方式，每条模板有多个词或变量组成。<br>
 * 词是一些普通的中文字符，变量是一些字符的集合，代表一类字符<br>
 * 如宝马，奔驰，别克等统统属于汽车，可以用&ltCAR>来表示<br>
 * 这样在模板匹配的时候可以很容易和用户输入的字符进行匹配。并不要求<br>
 * 每个词都和模板相匹配。
 * 
 * @author xiuleili
 * 
 */
public class Pattern {

	public static final String SEPARATOR = ";"; // 分隔符

	protected long patternId;
	protected int score;
	protected List<Item> items;
	protected String patternStr;
	protected String category;
	protected List<Category> cats;

	public Pattern(long id, int score, String patternstr, String category) {
		this.patternId = id;
		this.score = score;
		this.patternStr = patternstr;
		this.category = category;
		if (patternstr != null) {
			parse();
		}
	}

	public Pattern(long id, int score, List<Item> items, String category) {
		this.patternId = id;
		this.score = score;
		this.items = items;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
	
	public final List<Category> getCategories(){
		if(cats == null && category != null){
			cats = Category.parse(category);
		}
		return cats;
	}

	public long getPatternId() {
		return patternId;
	}

	public int getScore() {
		return score;
	}

	public final List<Item> getItems() {
		return items;
	}

	public final Item getItem(int index) {
		return items.get(index);
	}

	/**
	 * get the items size.
	 * 
	 * @return
	 */
	public final int itemsSize() {
		if (items != null) {
			return items.size();
		}
		return 0;
	}

	public String getPatternstr() {
		return patternStr;
	}

	@Override
	public String toString() {
		if (patternStr == null && items != null) {
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < items.size(); i++) {
				s.append(items.get(i).getKey());
				if (i != items.size() - 1) {
					s.append(SEPARATOR);
				}
			}

			patternStr = s.toString();
		}

		return "pattern:{ patternId:" + patternId + ", score:" + score + ", str:\""
				+ patternStr + "\",category:" + category + "}";
	}

	private void parse() {
		if (patternStr == null)
			return;

		items = new LinkedList<Item>();
		String[] words = patternStr.split(SEPARATOR);
		for (String w : words) {
			items.add(new Item(w));
		}
	}

	public void addItem(String s) {
		if (s == null)
			return;
		addItem(new Item(s));
	}

	public void addItem(Item s) {
		if (s == null)
			return;
		if (items == null) {
			items = new LinkedList<Item>();
		}
		items.add(s);
		patternStr = null;
	}
}
