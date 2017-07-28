package model.random;

/**
 * Created by samuel on 12/06/17.
 */
public class ExponentialDistribution extends RandomDistribution {
    Double lambda;

    public ExponentialDistribution(Double lambda) {
        this.lambda = lambda;
    }

	@Override
	public double generate(Random random) {
		return -(1.0/lambda)*Math.log(1.0-random.percentual());
	}
}
