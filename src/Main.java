import models.Tour;
import services.TravelAgencyService;

public class Main {
    public static void main(String[] args) {
        TravelAgencyService travelAgencyService = new TravelAgencyService();
        travelAgencyService.addTour(Tour.builder()
                .name("Beach Vacation")
                .type("Vacation")
                .price(900)
                .lastMinute(true)
                .discountForRegularCustomers(0.2)
                .build());
        travelAgencyService.addTour(Tour.builder()
                .name("City Excursion")
                .type("Excursion")
                .price(400)
                .lastMinute(false)
                .discountForRegularCustomers(0.1)
                .build());
        travelAgencyService.addTour(Tour.builder()
                .name("Shopping mall")
                .type("Shopping")
                .price(200)
                .lastMinute(false)
                .discountForRegularCustomers(0.3)
                .build());

        System.out.println("Before sorting by Price : " + travelAgencyService.getTours().stream().map(Tour::getName).toList());
        travelAgencyService.sortToursByPrice();
        System.out.println("After sorting by Price : " + travelAgencyService.getTours().stream().map(Tour::getName).toList());

        System.out.println("Find by name (Beach Vacation) : " + travelAgencyService.searchTourByName("Beach Vacation"));
        System.out.println("Find by lastMinute : " + travelAgencyService.getLastMinuteTours());
    }
}