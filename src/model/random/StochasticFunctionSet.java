package model.random;


import java.util.HashMap;
import java.util.Map;

/**
 This class stores randomic distributions
 each distribution is a instance of a statistical distribution

 we can store these statistical distributions with a key, then we can
 get a randomic value that corresponds to this statistical distribution
 throught generate() method


 */
public class StochasticFunctionSet {
	private Random random;
    private Map<String,RandomDistribution> distributions;

    public StochasticFunctionSet(Random random){
    	this.random = random;
        this.distributions = new HashMap<>();
    }

    public void add(String key, RandomDistribution distribution){
    	this.distributions.put(key, distribution);
    }

    public RandomDistribution get(String key){
        return this.distributions.get(key);
    }

    public double generate(String key){
        return this.get(key).generate(this.random);
    }
}
