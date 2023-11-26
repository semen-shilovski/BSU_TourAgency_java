package com.ssv.services;

import com.ssv.models.TourAgent;
import com.ssv.models.interfaces.Dao;
import com.ssv.services.dao.TourAgentDao;

import java.util.List;

public class TourAgentService {
    private Dao<TourAgent> tourAgentDao = new TourAgentDao();

    public TourAgent searchTourAgentById(Integer id) {
        return tourAgentDao.getById(id).orElse(null);
    }

    public List<TourAgent> findAll() {
        return tourAgentDao.getAll();
    }

}
