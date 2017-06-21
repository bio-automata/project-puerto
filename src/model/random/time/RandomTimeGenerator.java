package model.random.time;


import java.util.HashMap;
import java.util.Map;

/**
 esta classe armazena distribuições de tempo
 através de chaves

 o nome de cada distribuição corresponde
 a sigla utilizada para tal atividade
 no arquivo de descrição do cenário

 cada distribuição será salva atraveś de um
 objeto já configurado com os parametros de
 uma dada distribuição

 através do método getTime() ela
 retorna um tempo aleatório para uma
 dada distribuição

 */
public class RandomTimeGenerator {
    private Map<String,TimeDistribution> distributions;

    public RandomTimeGenerator(){
        this.distributions = new HashMap<>();
    }

    public void addTimeDistribution(String key, TimeDistribution distribution){
    	//System.out.println();
    	this.distributions.put(key, distribution);
    }

    public TimeDistribution getTimeDistribution(String key){
        return this.distributions.get(key);
    }

    public double getTime(String key){
        return this.getTimeDistribution(key).generate();

    }
}
