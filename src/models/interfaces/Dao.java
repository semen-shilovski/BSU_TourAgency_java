package models.interfaces;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getByName(String name);
    Optional<T> getById(Integer id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
