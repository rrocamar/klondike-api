package es.upm.miw.klondike.models;

public enum Role {
    PLAYER;

    public String roleName() {
        return "ROLE_" + this.toString();
    }

}