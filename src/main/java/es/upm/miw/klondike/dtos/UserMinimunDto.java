package es.upm.miw.klondike.dtos;

public class UserMinimunDto {

    private String login;

    private boolean available;

    public UserMinimunDto(){
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "UserMinimunDto{" +
                "login='" + login + '\'' +
                ", available=" + available +
                '}';
    }
}
