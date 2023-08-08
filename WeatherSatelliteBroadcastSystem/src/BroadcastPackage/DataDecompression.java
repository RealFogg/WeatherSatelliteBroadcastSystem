package BroadcastPackage;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;

public class DataDecompression {

    /**
     * Decompresses a byte array using the Inflater algorithm.
     *
     * @param compressedData Compressed data to be decompressed
     * @return Decompressed data
     */
    public byte[] decompressDataBytes(byte[] compressedData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();

        try {
            // Set the input for the inflater to the compressed data
            inflater.setInput(compressedData);

            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                baos.write(buffer, 0, count);
            }

            inflater.end();

            // Retrieve the decompressed data from the ByteArrayOutputStream
            return baos.toByteArray();

        } catch (Exception e) {
            // Handle any decompression errors
            e.printStackTrace();
            return null;
        } finally {
            // Close resources (even if an exception occurs)
            try {
                baos.close();
            } catch (Exception ignored) {
            }
        }
    }
}
