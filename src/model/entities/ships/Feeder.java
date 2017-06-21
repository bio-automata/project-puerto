package model.entities.ships;



/**
 * Created by samuel on 13/03/17.
 */
public class Feeder extends Ship {

    public Feeder() {
        this.setName("Feedder");
        this.setDescription("");
        this.setMinimumCapacity(1001.0);
        this.setMaximumCapacity(2000.0);
        
        this.setTextVariable("name", "Feeder");
        this.setNumericVariable("minimum capacity", 1001.0);
    	this.setNumericVariable("maximum capacity", 2000.0);
    }
}
