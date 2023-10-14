package models.strategy;

import models.Client;
import models.Tour;
import models.interfaces.TourStrategy;
import services.ClientService;
import services.TravelAgencyService;

import java.util.List;
import java.util.Scanner;

public class GetAllClientsStrategy implements TourStrategy {
    @Override
    public void execute(TravelAgencyService travelAgencyService, Scanner scanner) {
        ClientService clientService = new ClientService();
        List<Client> clients = clientService.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("\n ---Клиенты отсутствуют--- \n");
        } else {
            System.out.println("\n -----Клиенты :");
            clients.forEach(System.out::println);
            System.out.println("-----\n");
        }
    }
}
