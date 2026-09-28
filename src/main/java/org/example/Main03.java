package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Разделим многостраничный файл по страницам
 * в плейлисте: https://www.youtube.com/watch?v=2pN6VMLeINk&list=PLFh8wpMiEi88vWlQJj4KDzfpbIebBWIsX&index=5
 * отдельно: https://youtu.be/2pN6VMLeINk?si=GPy8d-GtEfGK3N5L
 */
public class Main03 {
    public static void main(String[] args) throws IOException {
        Path projectRoot = getProjectRoot();
        Path nestedDir = projectRoot.resolve("PDF").resolve("extract");
        java.nio.file.Files.createDirectories(nestedDir);

        Path pdfDirectory = projectRoot.resolve("PDF");
        Path pdfExtractDirectory = projectRoot.resolve("PDF\\extract");

        // Создаём папку PDF, если её нет (иначе save() упадёт)
        File oldFile = new File(pdfDirectory + "\\sample5.pdf");
        PDDocument document = Loader.loadPDF(oldFile);
        Splitter splitter = new Splitter(); // РАЗДЕЛИТЬЕЛЬ - для разделения страницц
        List<PDDocument> splitPages = splitter.split(document); // разделим по 1 странице весь документ и вернем список этих страниц

        int n = 1;
        for (PDDocument current: splitPages) {
            current.save(pdfExtractDirectory.toFile() + "\\split_" + n++ + ".pdf");
            current.close();
        }
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
        URL resource = Main03.class.getClassLoader().getResource("config.yml");
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