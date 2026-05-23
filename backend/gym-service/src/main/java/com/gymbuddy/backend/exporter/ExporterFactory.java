package com.gymbuddy.backend.exporter;

import java.util.HashMap;
import java.util.Map;

public class ExporterFactory {
    private static final Map<String, FileExporter> exporters = new HashMap<>();

    static {
        exporters.put("xml", new XMLFileExporter());
        exporters.put("txt", new TXTFileExporter());
    }

    public static FileExporter getExporter(String format) {
        FileExporter exporter = exporters.get(format.toLowerCase());
        if (exporter == null) {
            throw new IllegalArgumentException("Unsupported export format: " + format);
        }
        return exporter;
    }
}
