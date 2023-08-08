// Leo Zobel
// 4/10/2023

package BroadcastPackage;

import java.util.List;

public abstract class StatusReport {
	private StringBuilder fullReportBuilder = new StringBuilder();

    protected void addToReport(String sectionTitle, List<String> reportData) {
        fullReportBuilder.append(formatSectionTitle(sectionTitle)).append("\n");
        for (String report : reportData) {
            fullReportBuilder.append(report).append("\n");
        }
    }

    protected String formatSectionTitle(String sectionTitle) {
        return "=== " + sectionTitle + " ===";
    }

    public String generateReport() {
        return fullReportBuilder.toString();
    }
}
