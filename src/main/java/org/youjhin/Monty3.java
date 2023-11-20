package org.youjhin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Monty3 {
    public static void main(String[] args) {
        Random random = new Random();
        int totalSteps = 100;

        Map<Integer, Boolean> results = new HashMap<>();
        int winsWithoutSwitching = 0;
        int winsWithSwitching = 0;

        for (int i = 1; i <= totalSteps; i++) {
            int prizeDoor = random.nextInt(3) + 1;
            int initialChoice = random.nextInt(3) + 1;

            int doorToReveal = getDoorToReveal(random, prizeDoor, initialChoice);
            int finalChoice = getFinalChoice(random, initialChoice, doorToReveal);

            boolean winWithoutSwitch = initialChoice == prizeDoor;
            boolean winWithSwitch = finalChoice == prizeDoor;

            winsWithoutSwitching += winWithoutSwitch ? 1 : 0;
            winsWithSwitching += winWithSwitch ? 1 : 0;

            results.put(i, winWithSwitch);
        }

        int positiveResults = (int) results.values().stream().filter(Boolean::booleanValue).count();
        int negativeResults = totalSteps - positiveResults;

        double percentagePositive = (double) positiveResults / totalSteps * 100;
        double percentageNegative = (double) negativeResults / totalSteps * 100;

        System.out.println("Позитивные результаты: " + positiveResults);
        System.out.println("Негативные результаты: " + negativeResults);
        System.out.printf("Процент положительных результатов: %.2f%%\n", percentagePositive);
        System.out.printf("Процент негативных результатов: %.2f%%\n", percentageNegative);

        // Сохранение результатов в файл
        saveResultsToFile(results);
    }

    private static int getDoorToReveal(Random random, int prizeDoor, int initialChoice) {
        int doorToReveal;
        do {
            doorToReveal = random.nextInt(3) + 1;
        } while (doorToReveal == prizeDoor || doorToReveal == initialChoice);
        return doorToReveal;
    }

    private static int getFinalChoice(Random random, int initialChoice, int doorToReveal) {
        int finalChoice;
        do {
            finalChoice = random.nextInt(3) + 1;
        } while (finalChoice == initialChoice || finalChoice == doorToReveal);
        return finalChoice;
    }

    private static void saveResultsToFile(Map<Integer, Boolean> results) {
        try (FileWriter writer = new FileWriter("monty3Results.txt")) {
            for (Map.Entry<Integer, Boolean> entry : results.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            System.out.println("Результаты сохранены в файл: " + "monty3Results.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
