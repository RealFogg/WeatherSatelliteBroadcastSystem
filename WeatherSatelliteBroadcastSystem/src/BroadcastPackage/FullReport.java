package BroadcastPackage;

import java.util.List;

//SatelliteTransmissionReport class using the SubReportGenerator interface
public class FullReport extends StatusReport {
	private SubReport subReportEncryption;
	private SubReport subReportCompression;
	private SubReport subReportStorage;

	public FullReport(
			SubReport subReportEncryption,
			SubReport subReportCompression,
			SubReport subReportStorage) {
		this.subReportEncryption = subReportEncryption;
		this.subReportCompression = subReportCompression;
		this.subReportStorage = subReportStorage;
		buildFullReport();
	}

	private void buildFullReport() {
		List<String> encryptionReportData = subReportEncryption.generateSubReportData();
		List<String> compressionReportData = subReportCompression.generateSubReportData();
		List<String> storageReportData = subReportStorage.generateSubReportData();

		addToReport("Encryption Report", encryptionReportData);
		addToReport("Compression Report", compressionReportData);
		addToReport("Storage Report", storageReportData);

		// ... (add other report sections as needed)
	}

	@Override
	public String generateReport() {
		return super.generateReport();
	}
}
