// Leo Zobel
// 8/8/2023

package BroadcastPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class BroadcastTransmitter {

    public static void main(String[] args) {
        try {
            // Generate sensor data packet
            SensorDataPacket dataPacket = new SensorDataPacket();
            
            // Display data to be transmitted
            System.out.println("Sensor Data - Temperature: " + dataPacket.getTemperature() +
                               ", Humidity: " + dataPacket.getHumidity() +
                               ", Pressure: " + dataPacket.getPressure());

            // Transmit the data
            transmitData(dataPacket);
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    /**
     * Transmits processed sensor data over the network.
     * 
     * @param dataPacket The sensor data to transmit.
     * @throws IOException If there is an error with network communication.
     */
    private static void transmitData(SensorDataPacket dataPacket) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);

            // Encrypt sensor data
            DataEncryption dataEncryption = new DataEncryption();
            dataEncryption.encryptData(dataPacket.toBytes());

            // Compress encrypted data
            DataCompression dataCompression = new DataCompression();
            dataCompression.compressData(dataEncryption.getEncryptedData());

            // Store compressed data and encryption key pairs
            DataStorage dataStorage = new DataStorage();
            dataStorage.storeData(dataCompression.getCompressedData());
            dataStorage.storeKeyPairs(dataEncryption.getKeyPair());

            // Generate full system report
            FullReport fReport = new FullReport(dataEncryption, dataCompression, dataStorage);

            // Create transmission packet
            TransmissionPacket tPacket = new TransmissionPacket(dataCompression.getCompressedData(),
                                                                dataEncryption.getKeyPair(),
                                                                fReport.generateReport());
            byte[] sendData = tPacket.toBytes();

            // Get broadcast address and port from configuration
            InetAddress broadcastAddress = InetAddress.getByName(Configuration.BROADCAST_ADDRESS);
            int port = Configuration.BROADCAST_PORT;

            // Create DatagramPacket and send data over the network
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, port);
            socket.send(packet);
        }
    }

    // New methods and classes can be defined here

    /**
     * Configuration class to store constants and settings.
     */
    private static class Configuration {
        static final String BROADCAST_ADDRESS = "255.255.255.255";    // Address to connect to
        static final int BROADCAST_PORT = 8888;                       // Port to Broadcast on
    }
}
