package net.procelyte.jdbctutorial;

import java.util.List;

public interface CRUD<T, ID> {
    void create(T t);
    T read(ID id);
    void update(T t);
    void delete(ID id);
    void clear();

    List<T> getList();
    List<T> getAllFromTable();
}
