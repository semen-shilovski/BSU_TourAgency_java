package com.ssv.models.strategy;

import com.ssv.models.Tour;
import com.ssv.models.interfaces.TourStrategy;
import com.ssv.services.TravelAgencyService;

import java.util.List;
import java.util.Scanner;

public class GetToursByClientStrategy implements TourStrategy {
    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        System.out.println("Введите id клиента:");
        Integer clientId = scanner.nextInt();

        List<Tour> allTours = travelAgencyService.getToursByClient(clientId);

        if (allTours.isEmpty()) {
            System.out.println("\n ---У данного клиента не найдено туров--- \n");
        } else {
            System.out.println("\n -----Туры клиента №" + clientId + " :");
            allTours.forEach(System.out::println);
            System.out.println("-----\n");
        }
    }
}
