package com.mingspy.walee.datasource;

import java.util.List;

import com.mingspy.walee.core.Evidence;
import com.mingspy.walee.core.Query;

public interface IDataSource {
	List<Evidence> find(Query query);
}
