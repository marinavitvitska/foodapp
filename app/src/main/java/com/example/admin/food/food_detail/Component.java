package com.example.admin.food.food_detail;

import java.util.Objects;

public class Component {

    Component(final String name, final float value) {
        this.name = name;
        this.value = value;
    }

    String name;

    float value;

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Component component = (Component) o;
        return Objects.equals(name, component.name);
    }

    @Override public int hashCode() {

        return Objects.hash(name);
    }
}
