package com.academy.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ReportService {

    private final LibraryService libraryService;

    public ReportService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void displaySummaryReport() {

        int totalBooks = libraryService.getBooks().size();
        int borrowedBooks = libraryService.getBorrowRecords().size();
        int availableBooks = totalBooks - borrowedBooks;
        int totalMembers = libraryService.getMembers().size();

        System.out.println("===== Library Summary Report =====");
        System.out.println("Total Books      : " + totalBooks);
        System.out.println("Borrowed Books   : " + borrowedBooks);
        System.out.println("Available Books  : " + availableBooks);
        System.out.println("Total Members    : " + totalMembers);
        System.out.println("Most Popular Category : " + findMostPopularCategory());
    }

    public Path exportReportToFile(String fileName) throws IOException {

        int totalBooks = libraryService.getBooks().size();
        int borrowedBooks = libraryService.getBorrowRecords().size();
        int availableBooks = totalBooks - borrowedBooks;
        int totalMembers = libraryService.getMembers().size();

        StringBuilder report = new StringBuilder();

        report.append("\n===== Library Summary Report =====\n");
        report.append("Total Books      : ").append(totalBooks).append("\n");
        report.append("Borrowed Books   : ").append(borrowedBooks).append("\n");
        report.append("Available Books  : ").append(availableBooks).append("\n");
        report.append("Total Members    : ").append(totalMembers).append("\n");
        report.append("Most Popular Category : ")
                .append(findMostPopularCategory())
                .append("\n\n");

        report.append("Books Per Category\n");

        for (Map.Entry<String, Integer> entry :
                libraryService.getCategoryBookCount().entrySet()) {
            report.append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append("\n");
        }

        Path path = Path.of(fileName);
        Files.writeString(path, report.toString());

        return path;
    }

    private String findMostPopularCategory() {

        return libraryService.getCategoryBookCount()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}