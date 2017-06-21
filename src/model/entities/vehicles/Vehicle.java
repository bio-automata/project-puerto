package model.entities.vehicles;

import model.entities.Entity;

/**


 */
public class Vehicle extends Entity {
    private int transportedContainers;    //numero de containers carregados
    private double workUnit;           //unidades de trabalho (em horas, dias, etc)
    
    public int getTransportedContainers() {
        return transportedContainers;
    }

    public void incrementTransported_containers(int transported_containers) {
        this.transportedContainers++;
    }

    public double getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(double workUnit) {
        this.workUnit = workUnit;
    }

    public void incrementWork_units(double increment) {
        this.workUnit = this.workUnit +increment;
    }
}
