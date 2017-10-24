package com.iharding.participle.cfg;


import com.iharding.participle.core.Segment;

import java.util.List;

public interface Configuration {
	public List<Character[]> loadAnalyticalFile(String path);
	public Segment loadMainDict(String path);
}
