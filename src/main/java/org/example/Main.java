package org.example;

import org.example.util.HttpStatusChecker;

public class Main {
    public static void main(String[] args) {
        HttpImageStatusCli image = new HttpImageStatusCli();
        image.askStatus();
    }
}