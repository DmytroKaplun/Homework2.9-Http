package org.example;

import org.example.util.HttpStatusImageDownloader;

import java.util.Scanner;
import java.util.logging.Logger;

import static org.example.util.HttpStatusChecker.isStatusValid;

public class HttpImageStatusCli {
    private static final Logger logger = Logger.getLogger(HttpImageStatusCli.class.getName());
    public void askStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a valid status code to fetch the corresponding picture");
        while (true) {
            if (scanner.hasNextInt()) {
                int enter = scanner.nextInt();

                if (!isStatusValid(enter)) {
                    logger.warning("You entered an invalid status code\n" +
                            "There is no an image for this HTTP status: " + enter);
                } else {
                    logger.info("a valid status code: " + enter);
                    HttpStatusImageDownloader.downloadStatusImage(enter);
                    break;
                }

            } else {
                logger.warning("Invalid input. Please enter numbers only.");
                scanner.next();
            }
        }
        scanner.close();
    }
}
