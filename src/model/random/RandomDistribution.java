package model.random;

/**
   esta classe é um modelo para
   uma implementação de uma distribuição de tempo

 */
public abstract class RandomDistribution{
    public RandomDistribution(){
        
    }

    public abstract double generate(Random random);
}
