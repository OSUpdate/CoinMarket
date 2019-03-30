package com.example.ffff.android_2013150041;

import org.apache.commons.codec.binary.Hex;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String getNonce() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String encrySHA512(String secret, String url)
    {
        Mac shaHmac = null;
        String result = null;

        try {
            final String algorithm = "HmacSHA512";
            shaHmac = Mac.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(),algorithm);

            shaHmac.init(keySpec);
            byte[] hash = shaHmac.doFinal(url.getBytes());


            result = new String(Hex.encodeHex(hash));

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // System.out.println("Done");
        }
        return result;
    }
}
