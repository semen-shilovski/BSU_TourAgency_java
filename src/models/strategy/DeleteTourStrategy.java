package models.strategy;

import models.Tour;
import models.interfaces.TourStrategy;
import services.TravelAgencyService;

import java.util.Scanner;

public class DeleteTourStrategy implements TourStrategy {
    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        System.out.println("Введите название тура, который вы хотите удалить:");
        String tourNameToDelete = scanner.nextLine();
        Tour tourToDelete = travelAgencyService.searchTourByName(tourNameToDelete);

        if (tourToDelete != null) {
            travelAgencyService.deleteByTour(tourToDelete);
            System.out.println("Тур успешно удален.");
        } else {
            System.out.println("Тур с таким названием не найден.\n");
        }
    }
}