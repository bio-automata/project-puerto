package model.entities.ships;

/**
 * Created by samuel on 13/03/17.
 */
public class Panamax extends Ship {

    public Panamax() {
        setName("Panamax");
        setDescription("");
        setMinimumCapacity(3001.0);
        setMaximumCapacity(5000.0);
        
        this.setTextVariable("name", "Panamax");
        this.setNumericVariable("minimum capacity", 3001.0);
    	this.setNumericVariable("maximum capacity", 5000.0);
    }
}
