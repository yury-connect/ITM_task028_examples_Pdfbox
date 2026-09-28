package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * СВОЙСТВА -  Document Properties, Apache PDFBox Tutorial, Apache pdfbox example, PDFBox Java example, maven,
 * в плейлисте: https://www.youtube.com/watch?v=UjAAOc-AiAc&list=PLFh8wpMiEi88vWlQJj4KDzfpbIebBWIsX&index=10
 * отдельно: https://youtu.be/UjAAOc-AiAc?si=KM6VwjZ7guA5UuxK
 */
public class Main08 {
    public static void main(String[] args) throws IOException {
        Path projectRoot = getProjectRoot();
        Path resourcePath = getResourcePath();

        System.out.println("Проект: " + projectRoot);
        System.out.println("Ресурсы: " + resourcePath);

        Path pdfDirectory = projectRoot.resolve("PDF");
        Path pdfFile = pdfDirectory.resolve("mypdf_.pdf");


        // Создаём папку PDF, если её нет (иначе save() упадёт)
        java.nio.file.Files.createDirectories(pdfDirectory);

        PDDocument document = new PDDocument();

        PDDocumentInformation information = document.getDocumentInformation();
        information.setAuthor("Yury-Author");
        information.setCreator("Yury-Creator");
        information.setProducer("Yury-Producer");
        information.setTitle("Юрий заголовок - Title");
        information.setCreationDate(Calendar.getInstance());
        information.setKeywords("ключевые слова тута");
        information.setModificationDate(
                GregorianCalendar.from(
                        ZonedDateTime.of(
                                LocalDate.of(2026, 9, 28),
                                LocalTime.of(16, 17, 18),
                                ZoneId.of("Europe/Moscow")
                        )
                )
        );

        System.out.println("Author: " + information.getAuthor());
        System.out.println("Creator: " + information.getCreator());
        System.out.println("Producer: " + information.getProducer());
        System.out.println("Title: " + information.getTitle());
        System.out.println("Keywords: " + information.getKeywords());
        System.out.println("CreationDate: " + information.getCreationDate());
        System.out.println("ModificationDate: " + information.getModificationDate());

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
        URL resource = Main08.class.getClassLoader().getResource("config.yml");
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