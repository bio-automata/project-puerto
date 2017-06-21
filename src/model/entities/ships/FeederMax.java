package model.entities.ships;



/**
 * Created by samuel on 13/03/17.
 */
public class FeederMax extends Ship {

    public FeederMax() {
        setName("Feedder Max");
        setDescription("");
        setMinimumCapacity(2001.0);
        setMaximumCapacity(3000.0);
        
        this.setTextVariable("name", "Feeder Max");
        this.setNumericVariable("minimum capacity", 2001.0);
    	this.setNumericVariable("maximum capacity", 3000.0);
    }
}
