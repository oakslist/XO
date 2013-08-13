package XO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scannerMain = new Scanner(System.in);

        Play play = new Play();
        play.beginMenu();

        boolean playAgain = false;
        while (playAgain != true) {
            play.eraseSteps();
            play.playNewGame();
            System.out.print("Еще сыграем? 1(да)/0(нет) =>");
            int again = -1;
            again = play.repeatGame(scannerMain.nextInt());
            if (again == 0) {
                playAgain = true;
            }
        }
    }
}
