package services;

import models.Tour;
import models.comporators.TourPriceComparator;
import models.interfaces.Dao;

import java.util.List;
import java.util.Objects;

public class TravelAgencyService {
    private Dao<Tour> tourDao = new TourDao();
    private ClientService clientService = new ClientService();
    private TourAgentService tourAgentService = new TourAgentService();

    public void addTour(Tour tour) {
        tourDao.save(tour);
    }

    public List<Tour> getSortedToursByPrice() {
        return getTours().stream().sorted(new TourPriceComparator()).toList();
    }

    public void deleteByTour(Tour tour) {
        if (Objects.isNull(tour)) throw new IllegalArgumentException("tour object can't be null");
        tourDao.delete(tour);
    }

    public Tour searchTourByName(String name) {
        var tour = tourDao.getByName(name).orElse(null);
        Objects.requireNonNull(tour).setClient(clientService.searchClientById(tour.getClient().getId()));
        Objects.requireNonNull(tour).setTourAgent(tourAgentService.searchTourAgentById(tour.getTourAgent().getId()));
        return tour;
    }

    public List<Tour> getLastMinuteTours() {
        return getTours().stream().filter(Tour::isLastMinute).toList();
    }

    public List<Tour> getTours() {
        return tourDao.getAll().stream()
                .peek(tour -> {
                    Objects.requireNonNull(tour).setClient(clientService.searchClientById(tour.getClient().getId()));
                    Objects.requireNonNull(tour).setTourAgent(tourAgentService.searchTourAgentById(tour.getTourAgent().getId()));
                })
                .toList();
    }

    public List<Tour> getToursByClient(Integer clientId) {
        return getTours().stream()
                .filter(tour -> tour.getClient().getId().equals(clientId))
                .toList();
    }

}
