package com.ssv.models.strategy;

import com.ssv.models.Tour;
import com.ssv.models.interfaces.TourStrategy;
import com.ssv.services.TravelAgencyService;

import java.util.Scanner;

public class SearchTourByNameStrategy  implements TourStrategy {
    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        System.out.println("Введите название тура, который вы хотите найти:");
        String tourNameToSearch = scanner.nextLine();
        Tour foundTour = travelAgencyService.searchTourByName(tourNameToSearch);

        if (foundTour != null) {
            System.out.println("Найден тур:\n" + foundTour);
        } else {
            System.out.println("Тур с таким названием не найден.");
        }
    }
}