package com.baitaner.common.utils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
public class DBUtil {
    public static String mapToWhere(Map<String,Object> where){
        StringBuilder sqlWhereSbf = new StringBuilder();
        for(Map.Entry<String, Object> entry:where.entrySet()){
            String param = entry.getKey();
            if(entry.getValue()==null){
                continue;
            }
            if("".equals(sqlWhereSbf.toString())){
                sqlWhereSbf.append(param+"=\""+entry.getValue()+"\" ");
            } else{
                sqlWhereSbf.append(" and "+param+"=\""+entry.getValue()+"\" ");
            }
        }
        return sqlWhereSbf.toString();
    }


}
