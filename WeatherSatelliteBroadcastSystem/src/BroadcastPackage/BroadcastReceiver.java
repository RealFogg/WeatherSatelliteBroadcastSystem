// Leo Zobel
// 8/8/2023

package BroadcastPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadcastReceiver {

    public static void main(String[] args) {
        try {
            // Create a DatagramSocket and bind it to the specified port to listen for incoming packets
            DatagramSocket socket = new DatagramSocket(Configuration.LISTEN_PORT);
            byte[] receiveData = new byte[Configuration.RECEIVE_BUFFER_SIZE];

            // Create a DatagramPacket to hold the received data
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Receive the packet from the network
            socket.receive(receivePacket);

            // Extract the data from the received packet
            byte[] data = receivePacket.getData();
            TransmissionPacket tPacket = TransmissionPacket.fromBytes(data);

            // Decompress and decrypt the Sensor Data
            try {
                SensorDataPacket receivedData = processReceivedData(tPacket);

                // Output the received Sensor Data
                outputReceivedData(receivedData);

                // Print the full system report
                System.out.print(tPacket.fullReport);

            } catch (DataProcessingException e) {
                System.out.println("*** Error Processing Data ***");
                e.printStackTrace();
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    /**
     * Process received data by decompressing and decrypting it.
     *
     * @param tPacket The received TransmissionPacket.
     * @return The processed SensorDataPacket.
     * @throws DataProcessingException If there is an error in data processing.
     */
    private static SensorDataPacket processReceivedData(TransmissionPacket tPacket) throws DataProcessingException {
        try {
            DataDecompression dataDecompression = new DataDecompression();
            byte[] decompressedData = dataDecompression.decompressDataBytes(tPacket.sensorData);

            DataDecryption dataDecryption = new DataDecryption();
            byte[] decryptedData = dataDecryption.decryptData(decompressedData, tPacket.encryptionKey);

            return SensorDataPacket.fromBytes(decryptedData);

        } catch (Exception e) {
            throw new DataProcessingException("Error processing received data.", e);
        }
    }

    /**
     * Output received Sensor Data.
     *
     * @param receivedData The received SensorDataPacket.
     */
    private static void outputReceivedData(SensorDataPacket receivedData) {
        System.out.println("Received Temperature: " + receivedData.getTemperature());
        System.out.println("Received Humidity: " + receivedData.getHumidity());
        System.out.println("Received Pressure: " + receivedData.getPressure());
    }

    // New methods and classes can be defined here

    /**
     * Configuration class to store constants and settings.
     */
    private static class Configuration {
        static final int LISTEN_PORT = 8888;                // Port to listen on
        static final int RECEIVE_BUFFER_SIZE = 1024 * 4;    // Size of the receive buffer
    }

    /**
     * Custom exception class for data processing errors.
     */
    private static class DataProcessingException extends Exception {
        public DataProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
