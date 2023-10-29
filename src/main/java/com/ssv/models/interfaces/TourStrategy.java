package com.ssv.models.interfaces;


import com.ssv.services.TravelAgencyService;

import java.util.Scanner;

public interface TourStrategy {
    void execute(TravelAgencyService travelAgencyService, Scanner scanner);
}

