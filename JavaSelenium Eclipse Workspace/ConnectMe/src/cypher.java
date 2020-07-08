
import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.Charsets;

public class cypher {
    public void run() {
        try 
        {
//            String text = "Goodbye123";
        	String text = "îÈNMAúR>V¶m\"¹ø";
            String key = "abcdefghijklmnop"; // 128 bit key
            
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

//            byte[] encrypted = cipher.doFinal(text.getBytes());
            byte[] encrypted = text.getBytes();
            
            System.err.println(text);
            System.err.println(new String(encrypted));
            
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(encrypted));
            System.err.println(decrypted);
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) 
    {
        cypher app = new cypher();
        app.run();
    }
}