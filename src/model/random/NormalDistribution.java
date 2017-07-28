package model.random;


public class NormalDistribution extends RandomDistribution{
    private Double mean;
    private Double variance;
    

    public NormalDistribution(Double a, Double b) {
        this.mean = a;
        this.variance = b;
    }
    
	@Override
	public double generate(Random random) {        
        int i, n;
        double sum;

        n = 128;
        sum = 0;
        for(i=0;i<n;i++){
            sum = sum+random.percentual();
        }

        return this.mean+this.variance*( ( (sum-(n/2)) / ((Math.sqrt(n/12.0)) ) ) );
	}
}
