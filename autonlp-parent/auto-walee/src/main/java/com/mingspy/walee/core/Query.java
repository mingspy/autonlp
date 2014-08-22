package com.mingspy.walee.core;

public class Query {
	public Query(){
		
	}
	
	public Query(String query){
		this.queries = query;
	}
	
	protected String queries;

	public String getQueries() {
		return queries;
	}

	public void setQueries(String queries) {
		this.queries = queries;
	}
}
