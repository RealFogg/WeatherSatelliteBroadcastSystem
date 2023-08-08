// Leo Zobel
// 4/10/2023

package BroadcastPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

// This Class is currently Incomplete and is not capable of reliably retrieving data of KeyPairs
public class DataStorage implements SubReport {
	
	private String dataFile = "storedData";
	private String keyPairFile = "keyPairData";
	private List<byte[]> dataList = new ArrayList<byte[]>();
	private List<KeyPair> keyPairList = new ArrayList<KeyPair>();
	private List<String> storageReport = new ArrayList<String>();
	
	// Method to store weather data in a file
	public void storeData(byte[] compressedData) {
		storageReport.add("Task - Store Data: Started");
		
		// If dataFile (weather data's file) is not empty then retrieve the data in the file
		File storeDataFile = new File(dataFile);
		if (storeDataFile.length() != 0) {
			// Override the current dataList with the data list from dataFile
			dataList = retrieveData();
		}
		
		// Add the compressed data to dataList
		dataList.add(compressedData);
		
		try {
			// Write the list of data to the storedData file
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(dataFile));
			out.writeObject(dataList);
			
			// Update storage report and close the output stream
			storageReport.add("Task - Store Data: Completed");
			out.close();
			
		} catch (Exception e) {
			// Error encountered return an error message
			storageReport.add("Error occured while storing data - " + e.toString());
		}
	}
	
	// Method to retrieve weather data from the storedData File
	public List<byte[]> retrieveData() {
		storageReport.add("Task - Retrieve Data: Started");
		
		try {
			// Read the list of data from dataFile
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
			List<byte[]> dataBytes = (List<byte[]>) in.readObject();
			in.close();
			
			// Empty the dataFile file
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(dataFile));
			List<byte[]> emptyDataList = new ArrayList<byte[]>();
			out.writeObject(emptyDataList);
			out.close();
			
			// Update the report and return the contents of dataFile prior to it being emptied
			storageReport.add("Task - Retrieve Data: Completed");
			return dataBytes;
			
		} catch (Exception e) {
			// Error occurred update storage report
			storageReport.add("Error occurred while retieving data - " + e.toString());
			return null;
		}
	}
	
	// Method to retrieve weather data from the storedData File
	public byte[] retrieveDataBytes() {
		storageReport.add("Task - Retrieve Data: Started");
		
		try {
			// Read the list of data from dataFile
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
			byte[] dataBytes = (byte[]) in.readObject();
			in.close();
			
			// Empty the dataFile file
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(dataFile));
			List<byte[]> emptyDataList = new ArrayList<byte[]>();
			out.writeObject(emptyDataList);
			out.close();
			
			// Update the report and return the contents of dataFile prior to it being emptied
			storageReport.add("Task - Retrieve Data: Completed");
			return dataBytes;
			
		} catch (Exception e) {
			// Error occurred update storage report
			storageReport.add("Error occurred while retieving data - " + e.toString());
			return null;
		}
	}
	
	// Method to store keyPairs in a file
	public void storeKeyPairs(KeyPair pair) {
		storageReport.add("Task - Store KeyPair: Started");
		
		// If the keyPairFile is not empty retrieve the data from it before adding to the keyPairList
		File kpFile = new File(keyPairFile);
		if (kpFile.length() != 0) {
			// Retrieve the stored keyPairs (overrides existing keyPairList)
			keyPairList = retrieveKeyPairs();
		}
		
		// Add the given pair to the keyPairList
		keyPairList.add(pair);
		
		try {
			// Write the list of keyPairs to the keyPairData file
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(keyPairFile));
			out.writeObject(keyPairList);
			
			// Update storage report and close output stream
			storageReport.add("Task - Store KeyPair: Completed");
			out.close();
			
		} catch (Exception e) {
			// Error encountered return an error message
			storageReport.add("Error occured while storing KeyPair - " + e.toString());
		}
	}
	
	// Method to retrieve keyPairs from a file
	public List<KeyPair> retrieveKeyPairs() {
		storageReport.add("Task - Retrieve KeyPairs: Started");
		
		try {
			// Read in the list of keyPairs from the keyPairFile
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyPairFile));
			List<KeyPair> keyPairs = (List<KeyPair>) in.readObject();
			in.close();
			
			// Empty the key Pair List file
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(keyPairFile));
			List<KeyPair> emptyKeyPairList = new ArrayList<KeyPair>();
			out.writeObject(emptyKeyPairList);
			out.close();
			
			// Update the report and return the contents of keyPairFile prior to it being emptied
			storageReport.add("Task - Retrieve KeyPairs: Completed");
			return keyPairs;
			
		} catch (Exception e) {
			// Error encountered return an error message
			storageReport.add("Error occured while retieving keyPairs - " + e.toString());
			return null;
		}
	}
	
	// Method to retrieve keyPair from a file
	public KeyPair retrieveKeyPair() {
		storageReport.add("Task - Retrieve KeyPairs: Started");
			
		try {
			// Read in the list of keyPairs from the keyPairFile
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyPairFile));
			KeyPair keyPair = (KeyPair) in.readObject();
			in.close();
			
			// Empty the key Pair List file
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(keyPairFile));
			List<KeyPair> emptyKeyPairList = new ArrayList<KeyPair>();
			out.writeObject(emptyKeyPairList);
			out.close();
			
			// Update the report and return the contents of keyPairFile prior to it being emptied
			storageReport.add("Task - Retrieve KeyPairs: Completed");
			return keyPair;
			
		} catch (Exception e) {
			// Error encountered return an error message
			storageReport.add("Error occured while retieving keyPairs - " + e.toString());
			return null;
		}
	}

	@Override
	public List<String> generateSubReportData() {
		// Generates the Storage sub Report
		return storageReport;
	}
}
