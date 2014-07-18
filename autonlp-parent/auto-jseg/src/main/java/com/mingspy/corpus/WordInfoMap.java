package com.mingspy.corpus;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mingspy.utils.MapSorter;
import com.mingspy.utils.io.LineFileReader;
import com.mingspy.utils.io.LineFileWriter;

public class WordInfoMap {
	private Map<String, WordInfo> wordInfos = new HashMap<String, WordInfo>();
	public void addWordInfo(String word,
			String nature, int frq) {
		WordInfo info = wordInfos.get(word);
		if (info == null) {
			info = new WordInfo(word);
		}
		info.addNature(nature, frq);
		wordInfos.put(word, info);
	}
	
	public void clear(){
		for(WordInfo info : wordInfos.values()){
			info.clear();
		}
		wordInfos.clear();
	}
	
	public void prune( int limit) {
		List<String> keys = new ArrayList<String>(1000);
		for (Entry<String, WordInfo> en : wordInfos.entrySet()) {
			if (en.getValue().sumFreq() <= limit) {
				keys.add(en.getKey());
			}
		}

		for (String key : keys) {
			wordInfos.remove(key);
		}
		keys.clear();
	}

	public void writeWordInfo(LineFileWriter writer)
			throws UnsupportedEncodingException, FileNotFoundException {
		MapSorter<String, WordInfo> sorter = new MapSorter<String, WordInfo>();
		List<Entry<String, WordInfo>> res = sorter.sortByKeyASC(wordInfos);

		for (Entry<String, WordInfo> en : res) {
			writer.writeLine(en.getValue().toString());
		}
		writer.close();
	}
	
	public void readWordInfo(LineFileReader reader){
		String line = null;
		while ((line = reader.nextLine()) != null) {
			try{
			int wordIdx = line.indexOf('\t');
			String word = line.substring(0, wordIdx);
			line = line.substring(wordIdx + 1);
			String[] infos = line.split(",");
			for (String inf : infos) {
				int inIdx = inf.lastIndexOf(":");
				String nt = inf.substring(0, inIdx);
				String frq = inf.substring(inIdx + 1);
				addWordInfo(word, nt, Integer.parseInt(frq));
				nt = null;
				frq = null;
			}

			infos = null;
			word = null;
			}catch(Exception e){
				System.err.println(line);
				throw new RuntimeException(e);
			}
		}
	}

	public int size() {
		return wordInfos.size();
	}
}
