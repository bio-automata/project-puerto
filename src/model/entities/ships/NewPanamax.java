package model.entities.ships;



/**
 * Created by samuel on 13/03/17.
 */
public class NewPanamax extends Ship {

    public NewPanamax() {
        setName("New Panamax");
        setDescription("");
        setMinimumCapacity(10001.0);
        setMaximumCapacity(14500.0);
        
        this.setTextVariable("name", "New Panamax");
        this.setNumericVariable("minimum capacity", 10001.0);
    	this.setNumericVariable("maximum capacity", 14500.0);
    }
}
