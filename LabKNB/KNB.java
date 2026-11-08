package LabKNB;
import java.util.Random;
import java.util.Scanner;

public class KNB {

    static int cntofrock = 0;
    static int cntofnozh = 0;
    static int cntofpaper = 0;

    static int whowins(int p1choice, int p2choice) {
        if (p1choice == p2choice) return 0;
        if ((p1choice == 0 && p2choice == 1) || (p1choice == 1 && p2choice == 2) || (p1choice == 2 && p2choice == 0)) return 1;
        else return 2;
    }

    static int oneround(String p1, String p2, int rounds) {
        Random random = new Random();
        String[] choices = {"Камень", "Ножницы", "Бумага"};
        int p1choice = random.nextInt(3);
        int p2choice = random.nextInt(3);
        if (p1choice == 0) cntofrock++;
        if (p1choice == 1) cntofnozh++;
        if (p1choice == 2) cntofpaper++;
        if (p2choice == 0) cntofrock++;
        if (p2choice == 1) cntofnozh++;
        if (p2choice == 2) cntofpaper++;
        System.out.println("\n***** Игра " + rounds + " *****");
        System.out.println(p1 + " решил(а) выбрать: " + choices[p1choice] + " | " + p2 + " решил(а) выбрать: " + choices[p2choice]);
        int result = whowins(p1choice, p2choice);
        if (result == 0) {
            System.out.println("Ничья. Какая досада!");
        } else if (result == 1) {
            System.out.println("Победа: " + p1);
        } else {
            System.out.println("Победа: " + p2);
        }
        return result;
    }

    static String onegame(String p1, String p2, int matchNumber) {
        int p1wins = 0;
        int p2wins = 0;
        int rounds = 0;
        System.out.println("\n~~~~~~~~~~ Матч #" + matchNumber + " ~~~~~~~~~~");
        System.out.println(p1 + " VS " + p2);
        while (p1wins < 3 && p2wins < 3) {
            rounds++;
            int result = oneround(p1, p2, rounds);
            if (result == 1) {
                p1wins++;
            } else if (result == 2) {
                p2wins++;
            }
        }

        System.out.println("\nРезультат: " + p1 + " " + p1wins + ":" + p2wins + " " + p2);

        if (p1wins == 3) {
            System.out.println("\n******* Победа игрока " + p1 + " *******");
            return p1;
        } else {
            System.out.println("\n******* Победа игрока " + p2 + " *******");
            return p2;
        }
    }

    static void roulette(String[] players) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Выберите стадию турнира:");
        System.out.println("1. 1/16 финала (32 игрока, 16 матчей)");
        System.out.println("2. 1/8 финала (16 игроков, 8 матчей)");
        int choice = scanner.nextInt();
        int nummatches = 0;
        int numplayers = 0;
        if (choice == 1) {
            numplayers = 32;
            nummatches = numplayers/2;
        } else if (choice == 2) {
            numplayers = 16;
            nummatches = numplayers/2;
        } else {
            System.out.println("Неверно. У вас есть только две опции (1/2)");
        }
        String[][] matches = new String[nummatches][2];
        int[] randomPlayersInd = new int[numplayers];
        for (int i = 0; i < numplayers; i++) {
            randomPlayersInd[i] = i;
        }
        for (int i = numplayers - 1; i > 0; i--) {
            int gk = random.nextInt(i + 1);
            int newplayer = randomPlayersInd[i];
            randomPlayersInd[i] = randomPlayersInd[gk];
            randomPlayersInd[gk] = newplayer;
        }

        for (int i = 0; i < nummatches; i++) {
            matches[i][0] = players[randomPlayersInd[2 * i]];
            matches[i][1] = players[randomPlayersInd[2 * i + 1]];
        }
        System.out.println("\nА вот и наши бойцы:");
        for (int i = 0; i < nummatches; i++) {
            System.out.println((i + 1) + ". " + matches[i][0] + " vs " + matches[i][1]);
        }
        System.out.println("\nНу что, вы уже готовы? (Введите 1 для начала)");
        int startChoice = scanner.nextInt();

        if (startChoice == 1) {
            String[] www = new String[nummatches];
            for (int i = 0; i < nummatches; i++) {
                www[i] = onegame(matches[i][0], matches[i][1], i + 1);
            }

            while (www.length > 1) {
                int newnummatches = www.length / 2;
                String[] wwl = new String[newnummatches];
                String[][] nextmatches = new String[newnummatches][2];
                for (int i = 0; i < newnummatches; i++) {
                    nextmatches[i][0] = www[2 * i];
                    nextmatches[i][1] = www[2 * i + 1];
                }
                for (int i = 0; i < newnummatches; i++) {
                    wwl[i] = onegame(nextmatches[i][0], nextmatches[i][1], i + 1);
                }
                www = wwl;
            }
            System.out.println("\nКажется у нас есть чемпион: " + www[0] + ", для него это было слишком легко!");
            System.out.println("\nСтатистика по выбору камня, ножниц и бумаги:");
            System.out.println("Камень выбрали: " + cntofrock );
            System.out.println("Ножницы выбрали: " + cntofnozh );
            System.out.println("Бумагу выбрали: " + cntofpaper);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] players = {
                "Анипкин Ярослав", "Анищенко Людмила", "Ахмазикова Софья", "Баскаян Сергей", "Бубнов Максим", "Ванда Илья","Волкова Алёна",
                "Галанжа Валерия", "Галкин Игорь", "Григорьев Павел", "Гусева Дарья", "Дмитриенко Михаил", "Колчаев Даниил", "Кондрашов Егор",
                "Кулыгин Михаил", "Луцкович Виктория", "Мендалиев Даниал",
                "Мершиев Станислав", "Песков Кирилл", "Поликарпов Фёдор", "Поцелуев Дмитрий", "Преснякова Анфиса", "Рабочий Платон", "Салихов Мухаммед"
                , "Сафонов Владимир", "Синицын Захар", "Суркова Мария", "Чигвинцева Мария", "Шавгулидзе Даниил", "Чижиков Александр", "Прозорова Елизавета"
                , "Уолтер Уайт Рабочий"};
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Провести жеребьёвку");
            System.out.println("2. Начать турнир");
            System.out.println("3. Выход");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    roulette(players);
                    break;
                case 2:
                    System.out.println("Как я начну турнир, если мы еще не узнали наших кандидатов? (Проведи жеребьевку)");
                    break;
                case 3:
                    System.out.println("Хорошо, всего доброго!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Нет, нажимай только один, два или три");
            }
        }
    }
}
