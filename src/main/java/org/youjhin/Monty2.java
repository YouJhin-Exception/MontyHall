package org.youjhin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Monty2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Добро пожаловать в симуляцию парадокса Монти Холла!");

        // Обработка исключений при вводе количества испытаний
        int numberOfTrials;
        try {
            System.out.print("Введите количество испытаний: ");
            numberOfTrials = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Введите целое число.");
            scanner.close();
            return;
        }

        // Инициализация счетчиков
        int stayWins = 0;
        int switchWins = 0;

        for (int i = 0; i < numberOfTrials; i++) {
            // Случайно выбираем, за какую дверь спрятан приз (1, 2 или 3)
            int prizeDoor = random.nextInt(3) + 1;

            // Предложение компьютеру выбрать одну из трех дверей
            int selectedDoor = random.nextInt(3) + 1;
            System.out.println("Компьютер выбирает дверь " + selectedDoor);

            // Определение, какую дверь открывает ведущий
            int doorToOpen;
            do {
                doorToOpen = random.nextInt(3) + 1;
            } while (doorToOpen == prizeDoor || doorToOpen == selectedDoor);

            System.out.println("Ведущий открывает дверь " + doorToOpen + " и там нет приза.");

            // Предложение компьютеру переключить выбор случайным образом
            boolean switchChoice = random.nextBoolean();
            System.out.println("Компьютер решает " + (switchChoice ? "переключить" : "не переключать") + " выбор.");

            // Определение окончательного выбора участника
            int finalChoice;
            if (switchChoice) {
                do {
                    finalChoice = random.nextInt(3) + 1;
                } while (finalChoice == selectedDoor || finalChoice == doorToOpen);
            } else {
                finalChoice = selectedDoor;
            }

            // Проверка выигрыша
            if (finalChoice == prizeDoor && !switchChoice) {
                stayWins++;
            } else if (finalChoice == prizeDoor) {
                switchWins++;
            }
        }

        // Закрытие Scanner
        scanner.close();

        // Вывод результатов с процентами выигрышей
        double stayWinPercentage = (double) stayWins / numberOfTrials * 100;
        double switchWinPercentage = (double) switchWins / numberOfTrials * 100;

        System.out.println("Результаты после " + numberOfTrials + " испытаний:");
        System.out.printf("Процент выигрышей при оставлении: %.2f%%\n", stayWinPercentage);
        System.out.printf("Процент выигрышей при переключении: %.2f%%\n", switchWinPercentage);

        saveStatisticsToFile(numberOfTrials, stayWins, switchWins, stayWinPercentage, switchWinPercentage);
    }

    private static void saveStatisticsToFile(int numberOfTrials, int stayWins, int switchWins,
                                             double stayWinPercentage, double switchWinPercentage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("monty2Results.txt"))) {
            writer.write("Результаты после " + numberOfTrials + " испытаний:\n");
            writer.write("Процент выигрышей при оставлении: " + String.format("%.2f%%", stayWinPercentage) + "\n");
            writer.write("Процент выигрышей при переключении: " + String.format("%.2f%%", switchWinPercentage) + "\n");
            writer.write("Выигрыши при оставлении выбора: " + stayWins + "\n");
            writer.write("Выигрыши при переключении выбора: " + switchWins + "\n");
            System.out.println("Статистика успешно сохранена в файл 'monty2Results.txt'");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении статистики в файл.");
            e.printStackTrace();
        }
    }
}



