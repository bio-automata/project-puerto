package model.entities.ships;

import model.entities.Entity;

/**
 * Created by samuel on 13/03/17.
 */
public abstract class Ship extends Entity {

    private String name;
    private String description;
    private Double minimumCapacity;
    private Double maximumCapacity;
    private int numberOfContainers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumeberOfContainers() {
        return this.numberOfContainers;
    }

    public void setNumeberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public Double getMinimumCapacity() {
        return minimumCapacity;
    }

    public void setMinimumCapacity(Double minimumCapacity) {
        this.minimumCapacity = minimumCapacity;
    }

    public Double getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Double maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }
}
