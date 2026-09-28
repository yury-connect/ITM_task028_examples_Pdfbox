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
 * Разделим многостраничный файл по страницам и вытащим диапазон
 * в плейлисте: https://www.youtube.com/watch?v=0kZJI87JDkk&list=PLFh8wpMiEi88vWlQJj4KDzfpbIebBWIsX&index=6
 * отдельно: https://youtu.be/0kZJI87JDkk?si=abvl2RmmWYiym7Tn
 */
public class Main04 {
    public static void main(String[] args) throws IOException {
        Path projectRoot = getProjectRoot();
        Path nestedDir = projectRoot.resolve("PDF").resolve("extract");
        java.nio.file.Files.createDirectories(nestedDir);

        Path pdfDirectory = projectRoot.resolve("PDF");
        Path pdfExtractDirectory = projectRoot.resolve("PDF\\extract");

        // Создаём папку PDF, если её нет (иначе save() упадёт)
        File oldFile = new File(pdfDirectory + "\\sample10.pdf");
        PDDocument document = Loader.loadPDF(oldFile);


        Splitter splitter = new Splitter(); // РАЗДЕЛИТЬЕЛЬ - для разделения страницц
        splitter.setStartPage(2);
        splitter.setEndPage(3);
        List<PDDocument> splitPages = splitter.split(document); // разделим по 1 странице весь документ и вернем список этих страниц

        PDDocument newDocument = new PDDocument();
        for (PDDocument current: splitPages) {
            newDocument.addPage(current.getPage(0));
        }
        newDocument.save(pdfExtractDirectory.toFile() + "\\split.pdf");
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
        URL resource = Main04.class.getClassLoader().getResource("config.yml");
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