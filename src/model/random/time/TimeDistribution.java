package model.random.time;

import model.system.Systema;

/**
   esta classe é um modelo para
   uma implementação de uma distribuição de tempo

 */
public abstract class TimeDistribution {
    protected Systema system;

    public TimeDistribution(Systema system){
        this.system = system;
    }

    public abstract double generate();
}
