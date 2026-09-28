package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
Создадим PDF - файл програмным образом
 */
public class Main01 {
    public static void main(String[] args) throws IOException {
        Path projectRoot = getProjectRoot();
        Path resourcePath = getResourcePath();

        System.out.println("Проект: " + projectRoot);
        System.out.println("Ресурсы: " + resourcePath);

        Path pdfDirectory = projectRoot.resolve("PDF");
        Path pdfFile = pdfDirectory.resolve("mypdf.pdf");

        // Создаём папку PDF, если её нет (иначе save() упадёт)
        java.nio.file.Files.createDirectories(pdfDirectory);

        PDDocument document = new PDDocument();
        PDPage pdPage = new PDPage();
        document.addPage(pdPage);
        document.save(pdfFile.toFile());
        document.close();
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
        URL resource = Main01.class.getClassLoader().getResource("config.yml");
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