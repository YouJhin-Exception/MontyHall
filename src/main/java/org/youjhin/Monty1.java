package org.youjhin;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Monty1 {
    private static final int TOTAL_TRIES = 1000;
    private static final int NUM_DOORS = 3;

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());

        int switchWins = 0;
        int switchLosses = 0;
        int stayWins = 0;
        int stayLosses = 0;

        try (FileWriter writer = new FileWriter("monty1Results.txt")) {
            simulateMontyHall(random, switchWins, switchLosses, stayWins, stayLosses, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void simulateMontyHall(Random random, int switchWins, int switchLosses,
                                          int stayWins, int stayLosses, FileWriter writer) throws IOException {
        for (int i = 0; i < TOTAL_TRIES; i++) {
            boolean carBehindThisDoor = random.nextBoolean();
            int playerChoice = random.nextInt(NUM_DOORS);

            // Ведущий открывает одну из пустых дверей
            int revealedDoor;
            do {
                revealedDoor = random.nextInt(NUM_DOORS);
            } while (revealedDoor == playerChoice || (revealedDoor == (carBehindThisDoor ? 1 : 0) && random.nextBoolean()));

            // Игрок решает остаться при своем выборе или сменить дверь
            boolean switchDoor = random.nextBoolean();

            // Подсчет результатов
            if (switchDoor) {
                if (playerChoice == (carBehindThisDoor ? 1 : 0)) {
                    switchLosses++;
                } else {
                    switchWins++;
                }
            } else {
                if (playerChoice == (carBehindThisDoor ? 1 : 0)) {
                    stayWins++;
                } else {
                    stayLosses++;
                }
            }
            // Запись результатов в файл
            writer.write("Попытка " + (i + 1) + ": " + (switchDoor ? "Смена двери" : "Оставить выбор") +
                    ", " + (playerChoice == (carBehindThisDoor ? 1 : 0) ? "Победа" : "Проигрыш") + "\n");
        }

        // Вывод статистики
        System.out.println("Выигрыши при смене двери: " + switchWins);
        System.out.println("Проигрыши при смене двери: " + switchLosses);
        System.out.println("Выигрыши при оставлении выбора: " + stayWins);
        System.out.println("Проигрыши при оставлении выбора: " + stayLosses);
    }
}