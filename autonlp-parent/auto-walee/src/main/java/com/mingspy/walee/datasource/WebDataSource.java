package com.mingspy.walee.datasource;

import java.util.List;

import org.apache.log4j.Logger;

import com.mingspy.utils.html.HttpDownloader;
import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Query;

public abstract class WebDataSource implements IDataSource {

	public static final Logger LOG = Logger.getLogger(WebDataSource.class);
	
	protected String url = null;
	protected String encoding = "utf-8";
	private HttpDownloader downloader = new HttpDownloader();
	
	@Override
	public List<Evidence> find(Query query) {
		String content = downloader.downloadAsString(url+query.getQueries(), encoding);
		if(content == null || content.isEmpty()){
			LOG.warn("搜索结果为空:"+url+query.getQueries());
			return null;
		}
		
		return parse(content);
	}
	
	protected abstract List<Evidence> parse(String content);

}
