package com.gymbuddy.backend.report;

public class ReportFactory {
    public static Report getReport(String type) {
        if ("XML".equalsIgnoreCase(type)) {
            return new XmlReport();
        }
        throw new IllegalArgumentException("Unknown report type: " + type);
    }
}
