package XO;

import java.security.PrivateKey;
import java.util.Scanner;

public class Menu extends Players{

    private Scanner scanner = new Scanner(System.in);

    private int computerOrTwoPlayers = 0;

    public void beginMenu() {
        System.out.println("Приветствую в игре \"крестики-нолики\"!!!");
        System.out.println("Выберите вариант игры:");
        System.out.println("1 - одиночная (с компьютером)");
        System.out.println("2 - игрок против игрока");
        System.out.print("Введите 1 или 2 =>");
        computerOrTwoPlayers = checkEnter(scanner.nextInt());

        System.out.println();

        String itemName;
        if (computerOrTwoPlayers == 1) {
            System.out.println("Введите имя игрока (не менее " + MIN_NAME_LENGHT + " символов  и не более " + MAX_NAME_LENGHT + " символов) =>");
            itemName = scanner.nextLine();              // не знаю почему, но эта строка не хочет считывать без следующей
            itemName = scanner.nextLine();
            setNamePlayerFirst(checkNameLenght(itemName));
            setNamePlayerSecond("Computer");
        } else {
            System.out.print("Введите имя первого игрока (не менее " + MIN_NAME_LENGHT + " символов  и не более " + MAX_NAME_LENGHT + " символов) =>");
            itemName = scanner.nextLine();              // не знаю почему, но эта строка не хочет считывать без следующей
            itemName = scanner.nextLine();
            setNamePlayerFirst(checkNameLenght(itemName));
            System.out.print("Введите имя второго игрока (не менее " + MIN_NAME_LENGHT + " символов  и не более " + MAX_NAME_LENGHT + " символов) =>");
            itemName = scanner.nextLine();
            setNamePlayerSecond(checkNameLenght(itemName));
            System.out.print("OK");
        }
    }

    private int checkEnter(int number) {
        while (number != 1 && number != 2) {
            System.out.println("ВНИМАНИЕ!!! Вы ввели не правильное значение из возможных вариантов!!!");
            System.out.println("Введите значение снова!!! =>");
            number = scanner.nextInt();
        }
        return number;
    }

    private String checkNameLenght(String name) {
        if (name.length() < MIN_NAME_LENGHT || name.length() > MAX_NAME_LENGHT) {
        while (name.length() < MIN_NAME_LENGHT || name.length() > MAX_NAME_LENGHT) {
                System.out.println("ВНИМАНИЕ!!! Вы ввели имя не входящее в рамки условия!!!");
                System.out.println("Введите имя снова!!! =>");
                name = scanner.nextLine();
            }
        }
        return name;
    }

    private void setComputerOrTwoPlayers(int number) {
        computerOrTwoPlayers = checkEnter(number);
    }

    public int getComputerOrTwoPlayers() {
        return computerOrTwoPlayers;
    }
}
