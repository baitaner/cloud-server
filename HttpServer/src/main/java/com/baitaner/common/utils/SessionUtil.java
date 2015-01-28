/**
 * 
 */
package com.baitaner.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * @author starry
 * 
 */
public class SessionUtil {

	public static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String randomSessionID() {
		StringBuilder sb = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 16; i++) {
			String str = uuid.substring(i * 2, i * 2 + 2);
			int x = Integer.parseInt(str, 16);
			sb.append(chars[x % 0x3E]);
		}
		return sb.toString();

	}

    private static final int SESSION_ID_BYTES = 16;

    public static synchronized String generateSessionId() {

        // Generate a byte array containing a session identifier

        Random random = new SecureRandom();  // 取随机数发生器, 默认是SecureRandom

        byte bytes[] = new byte[SESSION_ID_BYTES];

        random.nextBytes(bytes); //产生16字节的byte

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(bytes); // 取摘要,默认是"MD5"算法

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        // Render the result as a String of hexadecimal digits

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {     //转化为16进制字符串

            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);

            byte b2 = (byte) (bytes[i] & 0x0f);

            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }

        return (result.toString());



    }

    public static String getBindCode(){
        Random r = new Random();
        int bcode = 0;
        while(bcode<100000){
            bcode = r.nextInt(999999);
        }
        return String.valueOf(bcode);
    }
}
