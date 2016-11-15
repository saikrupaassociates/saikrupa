package com.saikrupa.app.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.thoughtworks.xstream.core.util.Base64Encoder;

public class SecurityUtil {
	
	private static final String ALGORITHM = "AES";
	private static final String KEY = "1Hbfh667adfDEJ78";

	public SecurityUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(SecurityUtil.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new Base64Encoder().encode(encryptedByteValue);
        return encryptedValue64;
	}

	public static String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(SecurityUtil.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new Base64Encoder().decode(value);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
                
    }
    
    private static Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(SecurityUtil.KEY.getBytes(),SecurityUtil.ALGORITHM);
        return key;
    }
    
    public static void main(String[] test) {
    	String password = "nimda";
    	System.out.println("Password : "+password);
    	try {
			String encPassword = SecurityUtil.encrypt(password);
			System.out.println("Encrypted Password : "+encPassword);
			String decPassword = SecurityUtil.decrypt(encPassword);
			System.out.println("Decrypted Password : "+decPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
