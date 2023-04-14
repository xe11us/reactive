package model.request;

public class AddUserRequest implements Request {
    private String name;
    private String currency;

    public AddUserRequest(String name, String currency) {
        this.name = name;
        this.currency = currency;
    }

    public AddUserRequest() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
