package models.comporators;

import models.Tour;

import java.util.Comparator;

public class TourPriceComparator implements Comparator<Tour> {
    @Override
    public int compare(Tour tour1, Tour tour2) {
        return Double.compare(tour1.getPrice(), tour2.getPrice());
    }
}
