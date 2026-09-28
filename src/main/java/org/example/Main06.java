package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Удалим ДИАПАЗОН страниц
 * в плейлисте: https://www.youtube.com/watch?v=Wj2maqh8WJ8&list=PLFh8wpMiEi88vWlQJj4KDzfpbIebBWIsX&index=7
 * отдельно: https://youtu.be/Wj2maqh8WJ8?si=ag_OOZrTuZNC6Szr
 */
public class Main06 {
    public static void main(String[] args) throws IOException {
        Path projectRoot = getProjectRoot();
        Path nestedDir = projectRoot.resolve("PDF").resolve("extract");
        java.nio.file.Files.createDirectories(nestedDir);

        Path pdfDirectory = projectRoot.resolve("PDF");
        Path pdfExtractDirectory = projectRoot.resolve("PDF\\extract");

        // Создаём папку PDF, если её нет (иначе save() упадёт)
        File oldFile = new File(pdfDirectory + "\\sample10.pdf");
        PDDocument document = Loader.loadPDF(oldFile);

        int pageRangeStart = 2;
        int pageRangeEnd = 4;

        for (int i = pageRangeEnd; i >=pageRangeStart; i--) {
            document.removePage(i - 1);
        }

        document.save(pdfExtractDirectory.toFile() + "\\deleted_range.pdf");
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
        URL resource = Main06.class.getClassLoader().getResource("config.yml");
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