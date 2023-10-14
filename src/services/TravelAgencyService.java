package services;

import models.Tour;
import models.comporators.TourPriceComparator;

import java.util.ArrayList;
import java.util.List;

public class TravelAgencyService {
    private List<Tour> tours = new ArrayList<>();

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    public void sortToursByPrice() {
        tours.sort(new TourPriceComparator());
    }

    public Tour searchTourByName(String name) {
        for (Tour tour : tours) {
            if (tour.getName().equalsIgnoreCase(name)) {
                return tour;
            }
        }
        return null;
    }

    public List<Tour> getLastMinuteTours() {
        List<Tour> lastMinuteTours = new ArrayList<>();
        for (Tour tour : tours) {
            if (tour.isLastMinute()) {
                lastMinuteTours.add(tour);
            }
        }
        return lastMinuteTours;
    }

    public List<Tour> getTours() {
        return tours;
    }

}
