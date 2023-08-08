// Leo Zobel
// 8/8/2023

package BroadcastPackage;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class DataEncryption implements SubReport {

    private List<String> encryptionReport = new ArrayList<>();
    private byte[] encryptedData = null;
    private KeyPair keyPair = null;

    // Method to set encryptedData
    public void setEncryptedData(byte[] encryptedData) {
        this.encryptedData = encryptedData;
    }

    // Method to get encryptedData
    public byte[] getEncryptedData() {
        return encryptedData;
    }

    // Method to set KeyPair
    public void setKeyPair(KeyPair pair) {
        this.keyPair = pair;
    }

    // Method to get KeyPair
    public KeyPair getKeyPair() {
        return keyPair;
    }

    // Method to encrypt the weather data
    public void encryptData(byte[] dataToEncrypt) {
        try {
            encryptionReport.add("Data Encryption: Started");

            // Generate the key pair
            KeyPair pair = generateKeyPair();

            // Create and initialize the cipher using RSA,ECB, and PKCS1Padding transformations
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());

            // Encrypt the data
            byte[] cipherText = cipher.doFinal(dataToEncrypt);

            // Update encryptStatus
            encryptionReport.add("Data Encryption: Completed");

            setKeyPair(pair);
            setEncryptedData(cipherText);

        } catch (Exception e) {
            // Handle encryption error and update encryptionReport
            encryptionReport.add("Error occurred while encrypting data - " + e.toString());
        }
    }

    // Generate a new RSA KeyPair
    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }

    @Override
    public List<String> generateSubReportData() {
        // Generates the Data Encryption sub Report
        return encryptionReport;
    }
}
