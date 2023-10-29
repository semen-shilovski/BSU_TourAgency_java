package services;

import models.TourAgent;
import models.interfaces.Dao;
import services.dao.TourAgentDao;

public class TourAgentService {
    private Dao<TourAgent> tourAgentDao = new TourAgentDao();

    public TourAgent searchTourAgentById(Integer id) {
        return tourAgentDao.getById(id).orElse(null);
    }

}
