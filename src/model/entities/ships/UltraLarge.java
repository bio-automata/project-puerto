package model.entities.ships;

/**
 * Created by samuel on 13/03/17.
 */
public class UltraLarge extends Ship {

    public UltraLarge() {
        setName("Ultra Large");
        setDescription("");
        setMinimumCapacity(14501.0);
        setMaximumCapacity(18270.0);
        
        this.setTextVariable("name", "Ultra Large");
        this.setNumericVariable("minimum capacity", 14501.0);
    	this.setNumericVariable("maximum capacity", 18270.0);
    }
}
