package database.types;

/**
 * Created by Steiner on 01.05.2016.
 */
public class Personnel {
    private String id;
    private String secondName;
    private String firstName;
    private String patherName;

    public Personnel(String secondName, String firstName, String patherName) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.patherName = patherName;
    }

    public Personnel(String id, String secondName, String firstName, String patherName) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.patherName = patherName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatherName() {
        return patherName;
    }

    public void setPatherName(String patherName) {
        this.patherName = patherName;
    }
}
