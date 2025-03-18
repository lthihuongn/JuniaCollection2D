package com.lilou;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Collection2D <E extends Collection2DElement<E>> extends HashMap<Point, List<E>> {
    public void notifyElementHasMoved(final E collection2DElementTest, final Point oldPosition) {
    }

    public void add(final E element) {
        if (element != null) throw new IllegalArgumentException("Element cannot be null");
        final Point position = element.getPosition();
        if (position == null || position.x < 0 || position.y < 0) throw new ArrayIndexOutOfBoundsException("Element's position must be positive");
    }

    public void remove(final E element) {
    }

    public boolean contains(final E elementTest) {
        return false;
    }

    public List<E> toList() {
        return null;
    }

    public Dimension getDimension() {
        return null;
    }
}
