package com.baitaner.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-5-15
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    //判断content中是否匹配正则表达式regex
    public static boolean isMatch(String content,String regex){
         Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher(content);
        return matcher.matches();

    }

    public static String getUuid() {
        return DigestUtils.md5Hex(UUID.randomUUID().toString());
    }


    public static final String EMPTY = "";
    public static final String LINE = "-";
    public static final String UNDER_LINE = "_";
    public static final String BLANK = " ";
    public static final String TAB = "	";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String DOT = ".";
    public static final String SEMICOLON = ";";
    public static final String QUESTION = "?";
    public static final String SINGLE_QUOTATION = "'";
    public static final String DOUBLE_QUOTATION = "\"";
    public static final String AMPERSAND = "&";
    public static final String PERCENT = "%";
    public static final String DOLLAR = "$";
    public static final String BRACE_LEFT = "{";
    public static final String BRACE_RIGHT = "}";
    public static final String PARENTHESIS_LEFT = "(";
    public static final String PARENTHESIS_RIGHT = ")";
    public static final String BRACKET_LEFT = "[";
    public static final String BRACKET_RIGHT = "]";
    public static final String OBLIQUE_LINE = "/";
    public static final String SLASHE = "\\";
    public static final String SLASHES = "\\\\";
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        if (isEmpty(str))
            return true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    @SuppressWarnings("rawtypes")
    public static String join( Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.toArray(),separator);
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }
        int bufSize = array.length;

        bufSize *= ((array[0] == null ? 16 : array[0].toString().length()) + separator.length());

        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String toString(Object obj) {
        if (obj == null)
            return null;

        return obj.toString();
    }

    public static String toString(java.sql.Time obj) {
        if (obj == null)
            return null;

        return obj.toString();
    }

    /**
     * 判断两个字符串对象的值是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean valueEquals(String str1, String str2){
        if(str1 == null && str2 == null) return true;

        if(str1 != null) return str1.equals(str2);


        return false;
    }

    /** 计算utf-8格式的字符串字节长度
     * @param str
     * @return
     */
    public static int bytesLen(String str) {
        if(str == null) return 0;

        try {
            return str.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 计算字符串的指定编码格式的字节长度
     * @param str
     * @param encoding
     * @return
     */
    public static int bytesLen(String str, String encoding) {
        if(str == null) return 0;

        try {
            return str.getBytes(encoding).length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 比较两个字符串是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean compare(String str1,
                                  String str2) {
        if(str1 == null) {
            if(str2 == null) {
                return true;
            }else {
                return false;
            }
        }else{
            return str1.equals(str2);
        }
    }

    /**
     *
     * @param fieldName
     * @return
     */
    public static String getSetMethod(String fieldName) {
        //substring方法存在内存泄漏，请务使用
        StringBuilder sb = new StringBuilder(fieldName);
        String methodName = "set";
        methodName += String.valueOf(sb.charAt(0)).toUpperCase();
        methodName += sb.deleteCharAt(0);
        return methodName;
    }


    public static String md5(byte[] source) {
        String s = null;
        char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
        try
        {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
            md.update( source );
            byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0;                                // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i];                 // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换
            }
            s = new String(str);                                 // 换后的结果转换为字符串

        }catch( Exception e )
        {
            e.printStackTrace();
        }
        return s;
    }


    public static String rangNum(int len){
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ;i< len;i++){
            sb.append( Math.round(Math.random() * 9));
        }
        double number = Math.random();
        return sb.toString();
    }
}
