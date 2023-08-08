// Leo Zobel
// 4/10/2023

package BroadcastPackage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

public class DataCompression implements SubReport {

    private List<String> compressionReport = new ArrayList<>();
    private byte[] compressedData = null;

    // Method to get compressedData
    public byte[] getCompressedData() {
        return compressedData;
    }

    // Method to set compressedData
    public void setCompressedData(byte[] compressedData) {
        this.compressedData = compressedData;
    }

    // Method to compress the weather data
    public void compressData(byte[] encryptedData) {
        try {
            compressionReport.add("Data Compression: Started");

            // Compress the encrypted data
            byte[] compressedData = performCompression(encryptedData);

            // Update the compression report and set the compressed data
            compressionReport.add("Data Compression: Completed");
            setCompressedData(compressedData);

        } catch (Exception e) {
            // Handle compression error and update compressionReport
            compressionReport.add("Error occurred while compressing data - " + e.toString());
        }
    }

    // Perform data compression using Deflater
    private byte[] performCompression(byte[] dataToCompress) throws Exception {
        // Create a ByteArrayOutputStream to hold the compressed data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // Initialize a Deflater to perform compression
        Deflater deflater = new Deflater();
        deflater.setInput(dataToCompress);
        deflater.finish();
        
        // Create a buffer to hold temporary compressed data chunks
        byte[] buffer = new byte[1024];
        
        // Iterate through the data to compress until the Deflater finishes
        while (!deflater.finished()) {
            // Deflate (compress) a chunk of data and obtain the compressed bytes count
            int compressedBytesCount = deflater.deflate(buffer);
            
            // Write the compressed chunk to the ByteArrayOutputStream
            baos.write(buffer, 0, compressedBytesCount);
        }
        
        // Finish the compression process and release resources
        deflater.end();
        
        // Obtain the final compressed data as a byte array
        byte[] compressedData = baos.toByteArray();
        
        // Return the compressed data
        return compressedData;
    }

    @Override
    public List<String> generateSubReportData() {
        // Generates the Data Compression sub Report
        return compressionReport;
    }
}
