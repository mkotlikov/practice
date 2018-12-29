package com.nemitek.practice.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {
    public static void zipFile(
            final String filePath,
            final String archivePath
    ) {
        try (final FileOutputStream fos = new FileOutputStream(archivePath)) {
            try (final ZipOutputStream zipOut = new ZipOutputStream(fos)) {
                File fileToZip = new File(filePath);
                try (final FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
        } catch(final Exception e) { }
    }

    public static String inMemoryUnzipFile(
            final File archive
    ) {
        final StringBuilder outputBuffer = new StringBuilder();
        byte[] buffer = new byte[1024];
        try (final FileInputStream fis = new FileInputStream(archive)) {
            try (final ZipInputStream zis = new ZipInputStream(fis)) {
                ZipEntry zipEntry = zis.getNextEntry();

                while (zipEntry != null) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        outputBuffer.append(new String(buffer, 0, len));
                    }
                    zipEntry = zis.getNextEntry();
                }
            }
        } catch(final Exception e) {
            return "";
        }

        return outputBuffer.toString();
    }
}
