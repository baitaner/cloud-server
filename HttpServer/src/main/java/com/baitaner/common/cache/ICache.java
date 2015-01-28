package com.baitaner.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICache {

	public abstract void put(final String key, final String value);

	public abstract String get(final String key);

	public abstract void delete(final String key);

	//Set put get delete
	public abstract void put(final String key, final Set<String> valueSet);

	public abstract void putToSet(final String key, final String value);

	public abstract String getFromSet(final String key);

	public abstract Set<String> getSet(final String key);

	public abstract Long getSetLen(final String key);

	public abstract Long deleteFromSet(final String key, final String value);

	//List put get delete
	public abstract void put(final String key, final List<String> valueList);

	public abstract void putToList(final String key, final String value);

	public abstract String getFromList(final String key, final long index);

	public abstract List<String> getList(final String key, final long start,
										 final long end);

	public abstract Long getListLen(final String key);

	public abstract Long deleteFromList(final String key, final String value,
										final long count);

	//Map put get delete
	public abstract void put(final String key, final Map<String, String> map);

	public abstract String getFromMap(final String key, final String mapKey);

	public abstract Map<String, String> getMap(final String key);

	public abstract Long getMapLen(final String key);

	public abstract Long deleteFromMap(final String key, final String value);

    /* (non-Javadoc)
         * @see com.baitaner.common.dao.IRedisCache#saveToList(java.lang.String, java.lang.String)
         */
    void putToMap(String key, String mapKey, String value);

	void putEx(String key, long seconds, String value);

	String popFromSet(String key);
	
	List<String> mGet(final String[] keys);
}