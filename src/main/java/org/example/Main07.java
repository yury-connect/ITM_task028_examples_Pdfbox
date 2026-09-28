package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * объединение PDF-файлов в один, объединение PDF, объединение PDF-файлов, руководство по Apache PDF...
 * в плейлисте: https://www.youtube.com/watch?v=ZS1YSNor6-I&list=PLFh8wpMiEi88vWlQJj4KDzfpbIebBWIsX&index=8
 * отдельно: https://youtu.be/ZS1YSNor6-I?si=4y82OnNcybh7DTHc
 */
public class Main07 {
    public static void main(String[] args) throws IOException {
        Path projectRoot = getProjectRoot();
        Path nestedDir = projectRoot.resolve("PDF").resolve("extract");
        java.nio.file.Files.createDirectories(nestedDir);

        Path pdfDirectory = projectRoot.resolve("PDF");
        Path pdfExtractDirectory = projectRoot.resolve("PDF\\extract");

        // Создаём папку PDF, если её нет (иначе save() упадёт)
        File file1 = new File(pdfDirectory + "\\sample1.pdf");
        File file2 = new File(pdfDirectory + "\\sample10.pdf");
        File fileNew = new File(pdfDirectory + "\\extract");

        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        pdfMergerUtility.setDestinationFileName(fileNew + "\\fileNew.pdf");

        pdfMergerUtility.addSource(file1);
        pdfMergerUtility.addSource(file2);

        pdfMergerUtility.mergeDocuments(null); // PDFBox 3.x: mergeDocuments() без аргументов
    }





    // ***** ***** ***** ***** ***** ***** ***** ***** ***** *****
    // *****                Сервисные методы                 *****
    // ***** ***** ***** ***** ***** ***** ***** ***** ***** *****

    // Получить путь к папке проекта
    private static Path getProjectRoot() {
        return Paths.get(System.getProperty("user.dir"));
    }

    // Получить путь к ресурсу в classpath
    private static Path getResourcePath() {
        URL resource = Main07.class.getClassLoader().getResource("config.yml");
        if (resource == null) {
            throw new IllegalStateException("config.yml not found");
        }
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid resource URI", e);
        }
    }
}