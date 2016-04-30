package database.types;

import javafx.beans.property.StringProperty;

/**
 * Created by Steiner on 30.04.2016.
 */
public class Journal {
    private String carNum;
    private String routeName;
    private String derive;
    private String arrive;

    public Journal(String carNum, String routeName, String derive, String arrive) {
        this.carNum = carNum;
        this.routeName = routeName;
        this.derive = derive;
        this.arrive = arrive;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDerive() {
        return derive;
    }

    public void setDerive(String derive) {
        this.derive = derive;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }
}
