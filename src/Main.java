import models.interfaces.TourStrategy;
import models.strategy.*;
import services.TravelAgencyService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TravelAgencyService travelAgencyService = new TravelAgencyService();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        Map<Integer, TourStrategy> strategies = new HashMap<>();
        strategies.put(1, new AddTourStrategy());
        strategies.put(2, new ShowSortedToursStrategy());
        strategies.put(3, new DeleteTourStrategy());
        strategies.put(4, new SearchTourByNameStrategy());
        strategies.put(5, new GetAllLastMinuteToursStrategy());
        strategies.put(6, new GetAllToursStrategy());
        strategies.put(7, new GetToursByClientStrategy());
        strategies.put(8, new GetAllClientsStrategy());

        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить тур");
            System.out.println("2. Показать отсортированные туры по цене");
            System.out.println("3. Удалить тур");
            System.out.println("4. Поиск тура по имени");
            System.out.println("5. Показать горящие туры");
            System.out.println("6. Вывести все туры");
            System.out.println("7. Поиск туров по клиенту");
            System.out.println("8. Список клиентов");
            System.out.println("9. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (strategies.containsKey(choice)) {
                strategies.get(choice).execute(travelAgencyService, scanner);
            } else if (choice == 9) {
                exit = true;
            } else {
                System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}
