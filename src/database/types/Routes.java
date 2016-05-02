package database.types;

/**
 * Created by Steiner on 02.05.2016.
 */
public class Routes {

    private String id;
    private String name;
    private boolean hasTrips;

    public Routes(String name) {
        this.name = name;
    }

    public Routes(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Routes(String id, String name, boolean hasTrips) {
        this.id = id;
        this.name = name;
        this.hasTrips = hasTrips;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasTrips() {
        return hasTrips;
    }

    public void setHasTrips(boolean hasTrips) {
        this.hasTrips = hasTrips;
    }
}
