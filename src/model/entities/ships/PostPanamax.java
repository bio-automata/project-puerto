package model.entities.ships;

/**
 * Created by samuel on 13/03/17.
 */
public class PostPanamax extends Ship {

    public PostPanamax() {
        setName("Post Panamax");
        setDescription("");
        setMinimumCapacity(5.101);
        setMaximumCapacity(10000.0);
        
        this.setTextVariable("name", "Post Panamax");
        this.setNumericVariable("minimum capacity", 5101.0);
    	this.setNumericVariable("maximum capacity", 10000.0);
    }
}
