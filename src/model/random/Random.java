package model.random;

/**

 */
public class Random {
    private long A = 69069;
    private int C = 1;
    private long M = (long)Math.pow(2, 32);
    private double currentValue;

    public Random(){
    	
    	this.setSeed(System.currentTimeMillis());
    	//this.setSeed(Math.abs(new java.util.Random().nextInt()));
    }
    
    public void setSeed(double seed){
        this.currentValue = seed;
        
    }

    //distribuição uniforme e suas variações
    public long uniform(){
        this.currentValue = ((long)A*this.currentValue+C)%M;
        return (long)this.currentValue;
    }

    public long uniform(long n){
        //this.currentValue = (long)(A*this.currentValue+C)%M;
        return Math.abs(this.uniform())%n;
    }

    public long uniform(long m, long n){
        //this.currentValue = (long)(A*this.currentValue+C)%M;
        return m+Math.abs(this.uniform()%(n-m));
    }

    public double percentual(){
        //this.currentValue = (C*this.currentValue+A)%M;
        return (double)this.uniform()/M;
    }

    
    
    
    
    //funções derivadas da função percentual
    public double normal(double mean, double variance){
        int i, n;
        double sum;

        n = 128;
        sum = 0;
        for(i=0;i<n;i++){
            sum = sum+this.percentual();
        }

        return mean+variance*( ( (sum-(n/2)) / ((Math.sqrt(n/12.0)) ) ) );
    }



    public double poison(){     //ainda não implementada
        return 0;
    }
    
    public double generate(){
    	
    	
    	return -1;
    }
}
