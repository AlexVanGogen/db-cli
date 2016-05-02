package database.types;

/**
 * Created by Steiner on 02.05.2016.
 */
public class Auto {
    private String id;
    private String code;
    private String mark;
    private String color;
    private String owner;
    private boolean onTrip;

    public Auto(String code, String mark, String color, String owner) {
        this.code = code;
        this.mark = mark;
        this.color = color;
        this.owner = owner;
        this.onTrip = false;
    }

    public Auto(String id, String code, String mark, String color, String owner) {
        this.id = id;
        this.code = code;
        this.mark = mark;
        this.color = color;
        this.owner = owner;
        this.onTrip = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isOnTrip() {
        return onTrip;
    }

    public void setOnTrip(boolean onTrip) {
        this.onTrip = onTrip;
    }
}
