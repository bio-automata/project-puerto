package model.random.time;
import model.system.Systema;

public class TriangularTimeDistribution extends TimeDistribution{

    private Double a;
    private Double b;
    private Double c;


    public TriangularTimeDistribution(Double a, Double b, Double c, Systema systema){
        super(systema);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double generate() {
        return this.system.random.triangular(this.a, this.b, this.c);
    }
}
