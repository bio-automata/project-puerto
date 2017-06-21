package model.entities.ships;

/**
 * Created by samuel on 13/03/17.
 */
public class SmallFeeder extends Ship {

    public SmallFeeder() {
        
        setDescription("");
        setMinimumCapacity(0.0);
        setMaximumCapacity(1000.0);
        
        this.setTextVariable("name", "Small Feedder");
        this.setNumericVariable("minimum capacity", 0.0);
    	this.setNumericVariable("maximum capacity", 1000.0);
    }
}
