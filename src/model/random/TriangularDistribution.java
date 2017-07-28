package model.random;


public class TriangularDistribution extends RandomDistribution{
    private Double a;
    private Double b;
    private Double c;

    public TriangularDistribution(Double a, Double b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

	@Override
	public double generate(Random random) {
		double x;

        x = random.percentual();
        if(x>=a && x<=c){
            return (2*(x-a))/((b-a)*(c-a));
        }
        if(x>=c && x<=b){
            return (2*(b-x))/((b-a)*(b-c));
        }
        else{
            return 0;
        }
	}
}
