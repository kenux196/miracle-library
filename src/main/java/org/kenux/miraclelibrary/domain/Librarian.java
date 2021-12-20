package org.kenux.miraclelibrary.domain;

public class Librarian {
    private final String id;
    private final String name;
    private String password;

    public Librarian(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
