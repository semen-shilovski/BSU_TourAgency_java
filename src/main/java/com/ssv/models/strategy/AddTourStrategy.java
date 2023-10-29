package com.ssv.models.strategy;

import com.ssv.models.Tour;
import com.ssv.models.interfaces.TourStrategy;
import com.ssv.services.ClientService;
import com.ssv.services.TourAgentService;
import com.ssv.services.TravelAgencyService;


import java.util.Objects;
import java.util.Scanner;

public class AddTourStrategy implements TourStrategy {
    ClientService clientService = new ClientService();
    TourAgentService tourAgentService = new TourAgentService();

    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        System.out.println("Введите название тура:");
        String name = scanner.nextLine();
        System.out.println("Введите тип тура:");
        String type = scanner.nextLine();
        System.out.println("Введите цену тура:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Это last-minute тур? (true/false):");
        boolean isLastMinute = scanner.nextBoolean();
        System.out.println("Введите id клиента:");
        Integer clientId = scanner.nextInt();
        var client = clientService.searchClientById(clientId);
        if (Objects.isNull(client)) {
            System.out.println("\u001B[31mДанного клиента не существует\u001B[0m \n");
            return;
        }
        System.out.println("Введите id тур-агента:");
        Integer tourAgentId = scanner.nextInt();
        var tourAgent = tourAgentService.searchTourAgentById(tourAgentId);
        if (Objects.isNull(tourAgent)) {
            System.out.println("\u001B[31mДанного тур-агента не существует\u001B[0m \n");
            return;
        }
        double discountForRegularCustomers = 0;
        if ((long) travelAgencyService.getToursByClient(clientId).size() > 5) {
            System.out.println("Введите скидку для постоянных клиентов (если нет, введите 0):");
            discountForRegularCustomers = scanner.nextDouble();
        }
        scanner.nextLine();

        Tour newTour = Tour.builder()
                .name(name)
                .type(type)
                .price(price)
                .lastMinute(isLastMinute)
                .discountForRegularCustomers(discountForRegularCustomers)
                .client(client)
                .tourAgent(tourAgent)
                .build();

        travelAgencyService.addTour(newTour);
        System.out.println("Тур успешно добавлен.");
    }
}
