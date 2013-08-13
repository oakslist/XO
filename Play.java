package XO;

import java.util.Random;
import java.util.Scanner;

public class Play {

    private static final char DEFAULT_SYMBOL = ' ';

    //DEFAULT_X_POLE должен быть раыен DEFAULT_Y_POLE т.к. вся программа заточена на квадратное поле - так же
    //возможна проблема с вызовом автоматического просчета ничьи
    private static final int DEFAULT_X_POLE = 3;
    private static final int DEFAULT_Y_POLE = 3;

    private Random random = new Random();

    public char steps[][] = new char[DEFAULT_X_POLE][DEFAULT_Y_POLE];

    private Scanner scannerPlay = new Scanner(System.in);

    private Menu menu = new Menu();

    public void beginMenu() {
        menu.beginMenu();

        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1ый игрок - " + menu.getNamePlayerFirst());
        System.out.println("2ой игрок - " + menu.getNamePlayerSecond());
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
    }

    public void playNewGame() {
        drawCells();
        eraseSteps();
        int winner = 0;
        boolean finish = false;
        while (finish != true) {
            if (finish == false) {
                finish = stepFirstPlayer();
                drawLastCells();
                boolean checkDrawGame = drawGame();
                if (finish == false && checkDrawGame == true) {
                    System.out.println("Не осталось решающих ходов!");
                    System.out.println("Ничья!!!");
                    finish = true;
                    winner = 3;
                } else {
                    winner = 1;
                }
            }
            int secondPlayerOrComputer = 0;
            secondPlayerOrComputer = menu.getComputerOrTwoPlayers();
            if (secondPlayerOrComputer == 2) {
                if (finish == false) {
                    finish = stepSecondPlayer();
                    drawLastCells();
                    boolean checkDrawGame = drawGame();
                    if (finish == false && checkDrawGame == true) {
                        System.out.println("Не осталось решающих ходов!");
                        System.out.println("Ничья!!!");
                        finish = true;
                        winner = 3;
                    } else {
                        winner = 2;
                    }
                }
            } else {
                // здесь идет выход на искусственный интеллект
                if (secondPlayerOrComputer == 1) {
                    if (finish == false) {
                        finish = stepComputerPlayer();
                        drawLastCells();
                        boolean checkDrawGame = drawGame();
                        if (finish == false && checkDrawGame == true) {
                            System.out.println("Не осталось решающих ходов!");
                            System.out.println("Ничья!!!");
                            finish = true;
                            winner = 3;
                        } else {
                            winner = 2;
                        }
                    }
                }
            }
        }
        if (winner == 1) {
            System.out.println("Победил " + winner + " игрок - " + menu.getNamePlayerFirst() + "!!!");
        }   else {
            if (winner == 2) {
                System.out.println("Победил " + winner + " игрок - " + menu.getNamePlayerSecond() + "!!!");
            }
        }
    }

    public   void drawCells() {
        System.out.print("  ");
        for (int i = 0; i < DEFAULT_X_POLE; i++ ){
            System.out.print("  " + (i + 1));
        }
        System.out.println();
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            System.out.print((i + 1) + "  ");
            drawStringPole(i);
            System.out.println();
        }
    }

    private void drawStringPole(int i) {
        for (int j = 0; j < DEFAULT_Y_POLE; j++) {
            System.out.print("[" + steps[i][j] + "]");
        }
    }

    public void eraseSteps() {
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            eraseStepsLine(i);
        }
    }

    private void eraseStepsLine(int i) {
        for (int j = 0; j < DEFAULT_Y_POLE; j++) {
            steps[i][j] = DEFAULT_SYMBOL;
        }
    }

    //рисование табло после хода
    private void drawLastCells() {
        drawCells();
    }

    private boolean stepFirstPlayer() {
        System.out.println("Ходит игрок №1 " + menu.getNamePlayerFirst() + "! Куда ставим крестик?");
        boolean goodCell = false;
        int i = 0, j = 0;
        while (goodCell != true) {
            System.out.print("выбор строки =>");                              // координата Х
            i = checkEnterPositions(scannerPlay.nextInt());
            System.out.print("выбор позиции в строке " + i + " =>");          //координата У
            j = checkEnterPositions(scannerPlay.nextInt());
            goodCell = checkFillPosition(i,j);
        }
        steps [i-1][j-1] = 'X';
        boolean check = false;
        check = checkForWin();
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean stepSecondPlayer() {
        System.out.println("Ходит игрок №2 " + menu.getNamePlayerSecond() + "! Куда ставим нолик?");
        boolean goodCell = false;
        int i = 0, j = 0;
        while (goodCell != true) {
            System.out.print("выбор строки =>");                              // координата Х
            i = checkEnterPositions(scannerPlay.nextInt());
            System.out.print("выбор позиции в строке " + i + " =>");          //координата У
            j = checkEnterPositions(scannerPlay.nextInt());
            goodCell = checkFillPosition(i,j);
        }
        steps [i-1][j-1] = 'O';
        boolean check = false;
        check = checkForWin();
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean stepComputerPlayer() {

        int flagUsed = 0;
        int sumCommonO = 0;
        int sumEmpty = 0;
        int sumX = 0;
        int sumCommon = 0;
        //проверка на ситуацию OXO по диагонали - поставить О рядом с Х!!!
        //проверка на ситуацию XOX по диагонали - поставить О в угол!!!    <======= еще не сделано!!!!
        //сбор сведений по диагоналям главная
        for (int q = 0; q < DEFAULT_X_POLE; q++){
            for (int w = 0; w < DEFAULT_Y_POLE; w++){
                if (steps[q][w] == ' ') {
                    flagUsed++;
                }
            }
        }
        if (flagUsed == 6) {
            for (int i = 0; i < DEFAULT_X_POLE; i++) {
                if (steps[i][i] == 'O') {
                    sumCommonO++;
                }
                if (steps[i][i] == 'X') {
                    sumX++;
                }
                if (sumCommonO == 1 && sumX == 2) {
                    //поставить рандом возле любого Х
                    if (steps[1][1] == 'O') {
                        int flag = 0;
                        int k, m;
                        while (flag != 1){
                            do {
                                k = random.nextInt(DEFAULT_X_POLE);
                                m = random.nextInt(DEFAULT_X_POLE);
                            }
                            while ((k != 0 && m != 1) && (k != 1 && m != 0) && (k != 1 && m != 2) && (k != 2 && m != 1));
                           // while ((k == 0 && m == 1) && (k == 1 && m == 0) && (k == 1 && m == 2) && (k == 2 && m == 1));
                            if (steps[k][m] == ' ') {
                                steps[k][m] = 'O';
                                flag = 1;
                            }
                        }
                        return checkSimple();
                    } else {
                        if (steps[1][1] == 'X') {
                            int flag = 0;
                            int k, m;
                            while (flag != 1) {
                                do {
                                    k = random.nextInt(DEFAULT_X_POLE);
                                    m = random.nextInt(DEFAULT_X_POLE);
                                }
                                while ((k != 2 && m != 0) && (k != 0 && m != 2) && (k != 0 && m != 0) && (k != 2 && m != 2));
                                //while ((k == 2 && m == 0) && (k == 0 && m == 2) && (k == 0 && m == 0) && (k == 2 && m == 2));
                                if (steps[k][m] == ' ') {
                                    steps[k][m] = 'O';
                                    flag = 1;
                                }
                            }
                            return checkSimple();
                        }
                    }

                }
            }
            sumCommonO = 0;
            sumEmpty = 0;
            sumX = 0;
            //сбор сведений по диагоналям второстепенная
            int f = DEFAULT_Y_POLE - 1;
            for (int i = 0; i < DEFAULT_X_POLE; i++) {
                if (steps[i][f] == 'O') {
                    sumCommonO++;
                }
                if (steps[i][f] == 'X') {
                    sumX++;
                }
                f--;
                if (sumCommonO == 1 && sumX == 2) {
                    //поставить рандом возле любого Х
                    if (steps[1][1] == 'O') {
                        int flag = 0;
                        int k, m;
                        while (flag != 1){
                            do {
                                k = random.nextInt(DEFAULT_X_POLE);
                                m = random.nextInt(DEFAULT_X_POLE);
                            }
                            while ((k != 0 && m != 1) && (k != 1 && m != 0) && (k != 1 && m != 2) && (k != 2 && m != 1));
                            //while ((k == 0 && m == 1) && (k == 1 && m == 0) && (k == 1 && m == 2) && (k == 2 && m == 1));
                            if (steps[k][m] == ' ') {
                                steps[k][m] = 'O';
                                flag = 1;
                            }
                        }
                        return checkSimple();
                    } else {
                        if (steps[1][1] == 'X') {
                            int flag = 0;
                            int k, m;
                            while (flag != 1) {
                                do {
                                    k = random.nextInt(DEFAULT_X_POLE);
                                    m = random.nextInt(DEFAULT_X_POLE);
                                }
                                while ((k != 0 && m != 0) && (k != 2 && m != 2) && (k != 2 && m != 0) && (k != 0 && m != 2));
                                //while ((k == 0 && m == 0) && (k == 2 && m == 2) && (k == 2 && m == 0) && (k == 0 && m == 2));
                                if (steps[k][m] == ' ') {
                                    steps[k][m] = 'O';
                                    flag = 1;
                                }
                            }
                            return checkSimple();
                        }
                    }

                }
            }

        }
        //Проверка заполнения клетки посередине в начале игры //при условии компьютер ходит вторым
        sumCommon = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < DEFAULT_Y_POLE; j++) {
                if (steps[i][j] == ' ') {
                    sumCommon++;
                }
            }
        }
        //если средняя клетка заполнена - заполнять рандомно угловые клетки
        int flag = 0;
        if (sumCommon > 7) {
            if (steps[1][1] == ' ') {
                steps[1][1] = 'O';
            } else {
                int i, j;
                while (flag != 1){
                    do {
                        i = random.nextInt(DEFAULT_X_POLE);
                    }
                    while (i != 0 && i != 2);
                    do {
                        j = random.nextInt(DEFAULT_Y_POLE);
                    }
                    while (j != 0 && j != 2);
                    if (steps[i][j] == ' ') {
                        steps[i][j] = 'O';
                        flag = 1;
                    }
                }
            }
            return checkSimple();
        }

        //проверка на 2 одинаковых своих символа по линиям и выйграть
        //проверка по горизонталям
        sumCommonO = 0;
        sumEmpty = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < DEFAULT_Y_POLE; j++) {
                if (steps[i][j] == 'O') {
                    sumCommonO++;
                }
                if (steps[i][j] == ' ') {
                    sumEmpty++;
                }
            }
            if (sumCommonO == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_X_POLE; k++){
                    if (steps[i][k] == ' ') {
                        steps[i][k] = 'O';
                        return checkSimple();  //*************************
                    }
                }
            }
            sumCommonO = 0;
            sumEmpty = 0;
        }
        //проверка по вертикалям одинаковые OO
        sumCommonO = 0;
        sumEmpty = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < DEFAULT_Y_POLE; j++) {
                if (steps[j][i] == 'O') {
                    sumCommonO++;
                }
                if (steps[j][i] == ' ') {
                    sumEmpty++;
                }
            }
            if (sumCommonO == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_Y_POLE; k++){
                    if (steps[k][i] == ' ') {
                        steps[k][i] = 'O';
                        return checkSimple();  //****************
                    }
                }
            }
            sumCommonO = 0;
            sumEmpty = 0;
        }
        //Проверка по диагоналям одинаковые ОО
        //сбор сведений по диагоналям главная
        sumCommonO = 0;
        sumEmpty = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            if (steps[i][i] == 'O') {                      
                sumCommonO++;
            }
            if (steps[i][i] == ' ') {                         
                sumEmpty++;
            }
            if (sumCommonO == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_Y_POLE; k++){
                    if (steps[k][k] == ' ') {
                        steps[k][k] = 'O';
                        return checkSimple();  //*************
                    }
                }
            }
        }
        sumCommonO = 0;
        sumEmpty = 0;
        //сбор сведений по диагоналям второстепенная
        int f = DEFAULT_Y_POLE - 1;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            if (steps[i][f] == 'O') {
                sumCommonO++;
            }
            if (steps[i][f] == ' ') {                     //!=
                sumEmpty++;
            }
            f--;
        }
        f = DEFAULT_Y_POLE - 1;
        if (sumCommonO == 2 && sumEmpty == 1) {
            for (int k = 0; k < DEFAULT_Y_POLE; k++){
                if (steps[k][f] == ' ') {
                    steps[k][f] = 'O';
                    return checkSimple();  //*****************
                }
                f--;
            }
        }

        sumCommonO = 0;
        sumEmpty = 0;

        //***************************************
        //проверка на 2 одинаковых чужих символа по линиям и не дать победу сопернику
        //проверка по горизонталям
        int sumCommonX = 0;
        sumEmpty = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < DEFAULT_Y_POLE; j++) {
                if (steps[i][j] == 'X') {
                    sumCommonX++;
                }
                if (steps[i][j] == ' ') {
                    sumEmpty++;
                }
            }
            if (sumCommonX == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_Y_POLE; k++){
                    if (steps[i][k] == ' ') {
                        steps[i][k] = 'O';
                        return checkSimple(); //****************
                    }
                }
            }
            sumCommonX = 0;
            sumEmpty = 0;
        }

        //проверка по вертикали одинаковых ХХ
        sumCommonX = 0;
        sumEmpty = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < DEFAULT_Y_POLE; j++) {
                if (steps[j][i] == 'X') {
                    sumCommonX++;
                }
                if (steps[j][i] == ' ') {
                    sumEmpty++;
                }
            }
            if (sumCommonX == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_Y_POLE; k++){
                    if (steps[k][i] == ' ') {
                        steps[k][i] = 'O';
                        return checkSimple(); //**************
                    }
                }
            }
            sumCommonX = 0;
            sumEmpty = 0;
        }
        //Проверка по диагоналям одинаковые ХХ
        //сбор сведений по диагоналям главная
        sumCommonX = 0;
        sumEmpty = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            if (steps[i][i] != 'X') {
                sumCommonX++;
            }
            if (steps[i][i] == ' ') {                //!=
                sumEmpty++;
            }
            if (sumCommonX == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_Y_POLE; k++){
                    if (steps[k][k] == ' ') {
                        steps[k][k] = 'O';
                        return checkSimple(); //**************
                    }
                }
            }
        }
        sumCommonX = 0;
        sumEmpty = 0;
        //сбор сведений по диагоналям второстепенная
        f = DEFAULT_Y_POLE - 1;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            if (steps[i][f] == 'O') {
                sumCommonX++;
            }
            if (steps[i][f] == ' ') {                   //!=
                sumEmpty++;
            }
            f--;
            if (sumCommonX == 2 && sumEmpty == 1) {
                for (int k = 0; k < DEFAULT_Y_POLE; k++){
                    if (steps[k][k] == ' ') {
                        steps[k][k] = 'O';
                        return checkSimple(); //****************
                    }
                }
            }
        }
        //пока в доработке остальные условия хода. На данном этапе просто указывает, что ничего не делает для хода
        System.out.println("Выполняю непонятно почему!!!!");
        return false;
    }

    private boolean checkSimple() {
        boolean check = false;
        check = checkForWin();
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkForWin() {
        //проверка по горизонталям
        int winGoriz = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            if  (winGoriz == DEFAULT_X_POLE-1) {
                return true;
            }
            winGoriz = 0;
            for (int j = 1; j < DEFAULT_Y_POLE; j++) {
                if (steps[i][j-1] != ' ' && steps[i][j-1] == steps[i][j] && steps[i][j] != ' ') {
                    winGoriz++;
                }
            }
        }
        //проверка по вертикали
        int winVertical = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            if  (winVertical == DEFAULT_Y_POLE-1) {
                return true;
            }
            winVertical = 0;
            for (int j = 1; j < DEFAULT_Y_POLE; j++) {
                if (steps[j-1][i] != ' ' && steps[j-1][i] == steps[j][i] && steps[j][i] != ' ') {
                    winVertical++;
                }
            }
        }
        //проверка по диагонали главной
        int winDiagonalMain = 0;
        for (int i = 1; i < DEFAULT_X_POLE; i++) {
            if (steps[i-1][i-1] != ' ' && steps[i-1][i-1] == steps[i][i] && steps[i][i] != ' ') {
                winDiagonalMain++;
            }
        }
        //проверка диагонали второстепенной
        int winDiagonalDop = 0;
        int j = DEFAULT_Y_POLE-1;
        for (int i = 0; i < DEFAULT_X_POLE-1; i++) {
            if (steps[i][j] != ' ' && steps[i+1][j-1] == steps[i][j] && steps[i+1][j-1] != ' ') {
                winDiagonalDop++;
            }
            j--;
        }
        //проверка на победителя
        if (winGoriz == (DEFAULT_X_POLE-1) || winVertical == (DEFAULT_Y_POLE-1) || winDiagonalMain == (DEFAULT_X_POLE-1) || winDiagonalDop == (DEFAULT_Y_POLE-1) ) {
            return true;
        }


        return false;
    }

    private int checkEnterPositions(int number) {
        while (number < 1 || number > DEFAULT_X_POLE+1) {
            System.out.println("Введенное число не входит в рамки больше 1 и меньше " + (DEFAULT_Y_POLE+1) + "!!!");
            System.out.print("Ввдите число заново =>");
            number = scannerPlay.nextInt();
        }
        return number;
    }

    private boolean checkFillPosition(int i, int j) {
        if (steps[i-1][j-1] == ' ') {
            return true;
        }
        System.out.println("Данное поле уже было заполнено!!! Повторите ход!!!");
        return false;
    }

    private boolean drawGame() {
        int draw = 0;
        int commonScore = 0;
        //сбор сведений по строкам
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < (DEFAULT_Y_POLE - 1); j++) {
                if (steps[i][j] != ' ' && steps[i][j+1] != ' ' && steps[i][j] != steps[i][j+1]) {
                    draw++;
                }
            }
            if (draw > 0) {
                commonScore++;
            }
            draw = 0;
        }
        //сбор сведений по колонкам
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int j = 0; j < (DEFAULT_Y_POLE - 1); j++) {
                if (steps[j][i] != ' ' && steps[j+1][i] != ' ' && steps[j][i] != steps[j+1][i]) {
                    draw++;
                }
            }
            if (draw > 0) {
                commonScore++;
            }
            draw = 0;
        }
        //сбор сведений по диагоналям главная
        for (int i = 0; i < (DEFAULT_X_POLE - 1); i++) {
            if (steps[i][i] != ' ' && steps[i][i] != steps[i+1][i+1] && steps[i+1][i+1] != ' ') {
                draw++;
            }
            if (draw > 0) {
                commonScore++;
            }
            draw = 0;
        }
        //сбор сведений по диагоналям второстепенная
        int j = DEFAULT_Y_POLE - 1;
        for (int i = 0; i < (DEFAULT_X_POLE - 1); i++) {
            if (steps[i][j] != ' ' && steps[i+1][j-1] != steps[i][j] && steps[i+1][j-1] != ' ') {
                draw++;
            }
            j--;
            if (draw > 0) {
                commonScore++;
            }
            draw = 0;
        }
        //На всякий пожарный если все клетки заполнены - вызов ничьи
        int commonDraw = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int k = 0; k < DEFAULT_Y_POLE; k++) {
                if (steps[i][k] == ' ') {
                    commonDraw++;
                }
            }
        }
        //проверка на последний решающий ход для первого игрока
        int lastStepX = 0;
        for (int i = 0; i < DEFAULT_X_POLE; i++) {
            for (int k = 0; k < DEFAULT_Y_POLE; k++) {
                if (steps[i][k] == ' ') {
                    lastStepX++;
                }
            }
        }
        int drawEmpty = 0;
        int drawXX = 0;
        int drawEmptyAndXX = 0;
        if (lastStepX == 1) {
            //просчитываем есть ли линия типа х ' ' х  при всех заполненных
            for (int i = 0; i < DEFAULT_X_POLE; i++) {
                for (int k = 0; k < (DEFAULT_Y_POLE); k++) {
                    if (steps[i][k] == ' ') {
                        drawEmpty++;
                    }
                    if (steps[i][k] == 'X') {
                        drawXX++;
                    }
                }
                if (drawEmpty == 1 && drawXX == (DEFAULT_X_POLE - 1)) {
                    return false;
                }
                drawEmpty = 0;
                drawXX = 0;
            }

        }
        //Решение на ничью
        if (commonDraw == 0) {
            return true;
        }
        if (commonScore > 6) {
            int variantDraw = 0;
            // проверка на пустые диагонали 11-33
            for (int i = 0; i < (DEFAULT_X_POLE - 1); i++) {
                if ((steps[i][i] == steps[i+1][i+1]) && (steps[i][i] != ' ')) {
                    variantDraw++;
                }
                if (variantDraw == 1) {
                    return false;
                }
            }
            //проверка диагонали 31-13
            variantDraw = 0;
            int k = 2;
            for (int i = 0; i < (DEFAULT_Y_POLE - 1); i++) {
                if ((steps[i][k] == steps[i+1][k-1]) && (steps[i][k] != ' ')) {
                    variantDraw++;
                }
                if (variantDraw == 1) {
                    return false;
                }
                k--;
            }
            return true;
        }
        return false;
    }

    public int repeatGame(int number) {
        while (number != 1 && number != 0) {
            System.out.println("Значение должно быть 1 или 0!!!");
            System.out.print("Повторите ввод =>");
            number = scannerPlay.nextInt();
        }
        return number;
    }

}
