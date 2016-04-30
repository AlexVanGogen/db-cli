package security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Steiner on 29.04.2016.
 */
public class Encoder {
    public static String salt = "iwefUF^Tdwq778hjsaguyTtiq30@982hUgyeuu@!&*89709FEDZE";
    public static String md5(String input) {
        String md5 = null;
        if (input == null) return null;
        input += salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
