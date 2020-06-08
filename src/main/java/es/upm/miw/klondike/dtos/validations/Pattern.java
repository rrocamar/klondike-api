package es.upm.miw.klondike.dtos.validations;

public final class Pattern {

    public static final String DNI_PATTERN = "\\d{8}[A-HJ-NP-TV-Z]";
    public static final String EMAIL_PATTERN = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    private Pattern() {
        // Nothing to do
    }
}