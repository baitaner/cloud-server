/**
 * 
 */
package com.baitaner.common.utils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author starry
 *
 */
public class JsonUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper json;
	
	static{
		json = new ObjectMapper();
		json.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		json.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		json.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	public static String object2String(Object object) {
		if (object != null) {
			try {
				return(json.writeValueAsString(object));
			} catch (Exception e) {
				LOG.warn("Failed to serialize given object to json string: " + e.getMessage(), e);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> string2Map(String jsonString) {
		Map<String, Object> emptyMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(jsonString)) {
			return emptyMap;
		}else{
			try{
				return json.readValue(jsonString, emptyMap.getClass());
			}catch(Exception e){
                e.printStackTrace();
				LOG.error("fail to deserialize json to Map<String, Object>:" + jsonString, e);
				return emptyMap;
			}
		}
	}
	
	public static <T> T string2Object(String jsonString, Class<T> clazz) {
		if (jsonString != null) {
			try {
				T toReturn = json.readValue(jsonString, clazz);
				return toReturn;
			} catch (Exception e) {
				LOG.warn("Failed to deserialize json to type " + clazz.getName() + ": " + jsonString, e);
			}
		}
		return null;
	}
	
	/*
	public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
		try {
			T toReturn = json.convertValue(map, clazz);
			return toReturn;
		} catch (Exception e) {
			LOG.warn("Failed to convert map to type " + clazz.getName() + ": " + map, e);
		}
		return null;
	}
	*/
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> object2Map(Object obj) {
		if (obj != null) {
			try {
				Map<String, Object> toReturn = json.convertValue(obj, Map.class);
				return toReturn;
			} catch (Exception e) {
				LOG.warn("Failed to convert object to map : " + obj, e);
			}
		}
		return null;
	}
}
