package com.ssv.models.strategy;

import com.ssv.models.Tour;
import com.ssv.models.interfaces.TourStrategy;
import com.ssv.services.TravelAgencyService;


import java.util.List;
import java.util.Scanner;

public class ShowSortedToursStrategy implements TourStrategy {
    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        List<Tour> sortedTours = travelAgencyService.getSortedToursByPrice();
        if (sortedTours.isEmpty()) {
            System.out.println("\n ---Доступные туры отсутствуют--- \n");
        } else {
            System.out.println("\n -----Доступные туры (сортировка по цене) :");
            sortedTours.forEach(System.out::println);
            System.out.println("-----\n");
        }
    }
}
