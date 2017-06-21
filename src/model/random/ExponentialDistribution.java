package model.random;

/**
 * Created by samuel on 12/06/17.
 */
public class ExponentialDistribution {

    Double lambda;

    public ExponentialDistribution(Double lambda) {
        this.lambda = lambda;
    }

    public Double getLambda() {
        return lambda;
    }

    public void setLambda(Double lambda) {
        this.lambda = lambda;
    }

}
