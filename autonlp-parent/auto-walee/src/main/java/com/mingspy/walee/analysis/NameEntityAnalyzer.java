package com.mingspy.walee.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingspy.jseg.Token;
import com.mingspy.utils.io.LineFileReader;
import com.mingspy.walee.core.Question;
import com.mingspy.walee.core.Slot;

/**
 * 命名实体识别器。
 * 
 * @depends TokenAnalyzer已对问题进行分词
 * @author xiuleili
 * 
 */
public class NameEntityAnalyzer implements IQAnalyzer {

	public static final String SEPARATOR = "\t";

	public NameEntityAnalyzer() {
		load();
	}

	private void load() {
		// TODO
		{
			String path = "nameEntity.txt";
			LineFileReader reader = new LineFileReader(path);
			String line = null;
			while ((line = reader.nextLine()) != null) {
				int idx = line.indexOf(SEPARATOR);
				String word = line.substring(0, idx);
				String slot = line.substring(idx + 1);
				nameEntityMap.put(word, Slot.toSlot(slot));
			}
		}

		String path = "natureTransfer.txt";
		LineFileReader reader = new LineFileReader(path);
		String line = null;
		while ((line = reader.nextLine()) != null) {
			int idx = line.indexOf(SEPARATOR);
			String word = line.substring(0, idx);
			String slot = line.substring(idx + 1);
			natureTransferMap.put(word, Slot.toSlot(slot));
		}
	}

	private Map<String, String> nameEntityMap = new HashMap<String, String>();
	private Map<String, String> natureTransferMap = new HashMap<String, String>();

	@Override
	public boolean analysis(Question question) {
		List<Token> tokens = (List<Token>) question
				.getProperty(Question.TOKENS);
		for(Token t : tokens){
			String slot = natureTransferMap.get(t.nature);
			if(slot == null){
				slot = nameEntityMap.get(t.word);
			}
			
			if(slot != null){
				t.nature = slot;
			}
		}
		return true;
	}

}
