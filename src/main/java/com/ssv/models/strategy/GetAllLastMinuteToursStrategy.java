package com.ssv.models.strategy;

import com.ssv.models.Tour;
import com.ssv.models.interfaces.TourStrategy;
import com.ssv.services.TravelAgencyService;

import java.util.List;
import java.util.Scanner;

public class GetAllLastMinuteToursStrategy implements TourStrategy {
    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        List<Tour> lastMinuteTours = travelAgencyService.getLastMinuteTours();
        if (!lastMinuteTours.isEmpty()) {
            System.out.println("\n -----Горящие туры :");
            lastMinuteTours.forEach(System.out::println);
            System.out.println("-----\n");
        } else {
            System.out.println("\n ---Горящие туры отсутствуют--- \n");
        }
    }
}