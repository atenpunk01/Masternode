package co.th.aten.network.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.jboss.solder.core.Veto;

@Veto
public class HashUtil {
    String hashFunction = "MD5";
    String charset      = "UTF-8";
    
    public String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(hashFunction);
            md.update(password.getBytes(charset));
            byte[] raw = md.digest();
            return new String(Hex.encodeHex(raw));
        } 
        catch (Exception e) {
            throw new RuntimeException(e);        
        }
    }
    
    public String hash2(String hashType, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            md.update(password.getBytes(charset));
            byte[] raw = md.digest();
            return new String(Hex.encodeHex(raw));
        } 
        catch (Exception e) {
            throw new RuntimeException(e);        
        }
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getHashFunction() {
        return hashFunction;
    }

    public void setHashFunction(String hashFunction) {
        this.hashFunction = hashFunction;
    }    
    
}
