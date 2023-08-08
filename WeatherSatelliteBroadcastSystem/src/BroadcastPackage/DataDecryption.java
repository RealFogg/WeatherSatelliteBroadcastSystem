// Leo Zobel
// 4/10/2023

package BroadcastPackage;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class DataDecryption {
	
	/**
     * Decrypts a single piece of decompressed data using the provided key pair.
     *
     * @param decompressedData Decompressed data to be decrypted
     * @param keyPair KeyPair used for decryption
     * @return Decrypted data
     * @throws Exception If the decryption operation encounters an error
     */
    public byte[] decryptData(byte[] decompressedData, KeyPair keyPair) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        return cipher.doFinal(decompressedData);
    }
}
