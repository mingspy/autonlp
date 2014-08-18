package com.mingspy.walee.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.mingspy.walee.core.Question;

/**
 * 分析器总管，负责调用其他分析器。这里只是用了一个Composed模式，方便调用。
 * 
 * @author xiuleili
 * 
 */
public class ChiefAnalyzer implements Collection<IQAnalyzer>, IQAnalyzer {

	private List<IQAnalyzer> workers = new ArrayList<IQAnalyzer>();

	
	public ChiefAnalyzer(){
		// TODO add some analyzer
	}
	
	@Override
	public boolean analysis(Question question) {
		for(IQAnalyzer analyzer : workers){
			if(!analyzer.analysis(question)){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public int size() {
		return workers.size();
	}

	@Override
	public boolean isEmpty() {
		return workers.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return workers.contains(o);
	}

	@Override
	public Iterator<IQAnalyzer> iterator() {
		return workers.iterator();
	}

	@Override
	public Object[] toArray() {
		return workers.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return workers.toArray(a);
	}

	@Override
	public boolean add(IQAnalyzer e) {
		return workers.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return workers.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return workers.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends IQAnalyzer> c) {
		return workers.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return workers.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return workers.retainAll(c);
	}

	@Override
	public void clear() {
		workers.clear();
	}

}
