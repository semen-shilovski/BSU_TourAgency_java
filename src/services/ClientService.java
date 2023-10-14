package services;

import models.Client;
import models.interfaces.Dao;

import java.util.List;
import java.util.Objects;

public class ClientService {
    private Dao<Client> clientDao = new ClientDao();

    public boolean existClientById(Integer id) {
        return Objects.nonNull(clientDao.getById(id).orElse(null));
    }

    public Client searchClientById(Integer id) {
        return clientDao.getById(id).orElse(null);
    }

    public List<Client> getAllClients() {
        return clientDao.getAll();
    }
}
