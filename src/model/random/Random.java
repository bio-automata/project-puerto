package model.random;

import java.util.HashMap;

/**
 * Created by dicus on 28/04/17.
 */
public class Random {
    private long A = 69069;
    private int C = 1;
    private long M = (long)Math.pow(2, 32);
    private double currentValue;

    public Random(){
    	this.setSeed(new java.util.Random().nextInt());
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

    public double exponential(double lambda){
        return -(1.0/lambda)*Math.log(1.0-this.percentual());
    }

    public double triangular(double a, double b, double c){
        double x;

        x = this.percentual();
        if(x>=a && x<c){
            return (2*(x-a))/((b-a)*(c-a));
        }
        if(x>c && x<=b){
            return (2*(b-x))/((b-a)*(b-c));
        }
        else{
            return 2/(b-a);
        }
    }

    public double poison(){     //ainda não implementada
        return 0;
    }

    //sorteia qualquer tipo de distribuição
    public double raffle(int distribution, HashMap<String,Double> params){
        switch(distribution){
            case RandomConstants.UNIFORM:
                switch(params.size()){
                    case 0:
                        return this.uniform();

                    case 1:
                        return this.uniform(params.get("n").intValue());

                    case 2:
                        return this.uniform(params.get("m").intValue(),params.get("n").intValue());
                }

            case RandomConstants.PERCENTUAL:
                return this.percentual();

            case RandomConstants.NORMAL:
                return this.normal(params.get("mean"),params.get("variance"));

            case RandomConstants.EXPOENENTIAL:
                return this.exponential(params.get("lambda"));

            case RandomConstants.TRIANGULAR:
                return this.triangular(params.get("a"),params.get("b"),params.get("c"));

            case RandomConstants.POISON:

                break;
        }

        return 0;
    }
}
