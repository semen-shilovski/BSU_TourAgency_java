package models.interfaces;

import services.TravelAgencyService;

import java.util.Scanner;

public interface TourStrategy {
    void execute(TravelAgencyService travelAgencyService, Scanner scanner);
}

