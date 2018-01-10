package mk.fr.localsqlapp.model;

/**
 * Created by Formation on 10/01/2018.
 */

public class Contact {
    private String name;
    private String first_name;
    private String email;
    private long id;

    public Contact() {
    }

    public Contact(String name, String first_name, String email) {
        this.name = name;
        this.first_name = first_name;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public Contact setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getId() {
        return id;
    }

    public Contact setId(long id) {
        this.id = id;
        return this;
    }
}
