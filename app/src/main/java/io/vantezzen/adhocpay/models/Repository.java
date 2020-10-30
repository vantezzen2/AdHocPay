package io.vantezzen.adhocpay.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Repository<Model> {
    private List<Model> models;

    /**
     * Initialisiere die Repository
     */
    public Repository() {
        models = new ArrayList<>();
    }

    /**
     * Liefert alle Models aus der Repository
     *
     * @return Models
     */
    public List<Model> getAll() {
        return null;
    }

    /**
     * Füge dem Repository ein Model hinzu
     *
     * @param model Model
     */
    public void add(Model model) {
        models.add(model);
    }

    /**
     * Leere die Repository
     */
    public void clear() {
        models.clear();
    }
}
