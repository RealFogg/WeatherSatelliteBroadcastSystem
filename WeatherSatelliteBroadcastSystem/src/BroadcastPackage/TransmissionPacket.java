package BroadcastPackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


public class TransmissionPacket {
    byte[] sensorData;              // The Sensor data after encryption and compression
    KeyPair encryptionKey;          // The key needed to decrypt the sensor data (In a final iteration this would not be necessary)
    String fullReport;              // The full system report for the satellite

    TransmissionPacket(byte[] sensorData, KeyPair encryptionKey, String fullReport) {
        this.sensorData = sensorData;
        this.encryptionKey = encryptionKey;
        this.fullReport = fullReport;
    }

    /*public byte[] toBytes() {
        int dataSize = sensorData.length;
        byte[] keyBytes = encryptionKeyToBytes(encryptionKey);
        byte[] reportBytes = fullReport.getBytes();
        int bufferSize = dataSize + keyBytes.length + reportBytes.length + 12; // Add some extra padding

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.putInt(dataSize);
        buffer.put(sensorData);
        buffer.putInt(keyBytes.length);
        buffer.put(keyBytes);
        buffer.putInt(reportBytes.length);
        buffer.put(reportBytes);

        return buffer.array();
    }*/

    public byte[] toBytes() {
        byte[] sensorDataBytes = sensorData;
        byte[] keyBytes = encryptionKeyToBytes(encryptionKey);
        byte[] reportBytes = fullReport.getBytes();
        int bufferSize = 4 + sensorDataBytes.length + 4 + keyBytes.length + 4 + reportBytes.length; // Add 4 bytes for length of each data element

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.putInt(sensorDataBytes.length); // Write the length of sensorDataBytes
        buffer.put(sensorDataBytes);
        buffer.putInt(keyBytes.length); // Write the length of keyBytes
        buffer.put(keyBytes);
        buffer.putInt(reportBytes.length); // Write the length of reportBytes
        buffer.put(reportBytes);

        return buffer.array();
    }
    
    public static TransmissionPacket fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        int dataSize = buffer.getInt();
        byte[] sensorData = new byte[dataSize];
        buffer.get(sensorData);

        int keyBytesLength = buffer.getInt();
        byte[] keyBytes = new byte[keyBytesLength];
        buffer.get(keyBytes);
        KeyPair encryptionKey = bytesToEncryptionKey(keyBytes); // Method to create KeyPair from keyBytes

        int reportBytesLength = buffer.getInt();
        byte[] reportBytes = new byte[reportBytesLength];
        buffer.get(reportBytes);
        String fullReport = new String(reportBytes);
        
        // Clear the buffer
        buffer.clear();

        return new TransmissionPacket(sensorData, encryptionKey, fullReport);
    }
    
    // Convert a KeyPair to bytes (Serialize the keypair)
    private byte[] encryptionKeyToBytes(KeyPair keyPair) {
        // Implementation of the logic to serialize the KeyPair into byte array
    	try {
            // Create a byte array output stream to hold the serialized data
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // Create an object output stream to serialize objects into the byte array output stream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            
            // Extract the public and private keys from the KeyPair
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            // Write the public and private keys to the object output stream
            objectOutputStream.writeObject(publicKey);
            objectOutputStream.writeObject(privateKey);
            objectOutputStream.close();
            
            // Return the byte array containing the serialized keys
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static KeyPair bytesToEncryptionKey(byte[] keyBytes) {
        // Implementation of the logic to deserialize the byte array back into KeyPair
    	try {
            // Create a byte array input stream to read the serialized data
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(keyBytes);
            // Create an object input stream to deserialize objects from the byte array input stream
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            
            // Read the public and private keys from the object input stream
            PublicKey publicKey = (PublicKey) objectInputStream.readObject();
            PrivateKey privateKey = (PrivateKey) objectInputStream.readObject();
            
            objectInputStream.close();
            
            // Construct and return a new KeyPair from the deserialized keys
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
