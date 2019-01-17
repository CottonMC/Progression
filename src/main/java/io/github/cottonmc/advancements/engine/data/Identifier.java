package io.github.cottonmc.advancements.engine.data;

import java.util.Objects;

public class Identifier {
    private String domain;
    private String path;

    public Identifier(String domain, String path) {
        this.domain = domain;
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(getDomain(), that.getDomain()) &&
                Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomain(), getPath());
    }

    @Override
    public String toString() {
        return domain+":"+path;
    }
}
