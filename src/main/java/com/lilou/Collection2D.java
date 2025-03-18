package com.lilou;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Collection2D <E extends Collection2DElement<E>> extends HashMap<Point, List<E>> {

    public void notifyElementHasMoved(final E element, final Point oldPosition) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        if (element.getCollection() != this) throw new IllegalArgumentException("Element is not in this collection");
        if (oldPosition == null ) throw new ArrayIndexOutOfBoundsException("Old position cannot be null.");
        final List<E> oldList = this.get(oldPosition);
        if (oldList == null || !oldList.contains(element)) throw new IllegalArgumentException("Element not found at old position " + oldPosition);
        oldList.remove(element);
        if (oldList.isEmpty()) this.remove(oldPosition);
        element.setCollection(null);
        this.add(element);
    }

    public void add(final E element) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        final Point position = element.getPosition();
        if (position == null || position.x < 0 || position.y < 0) {
            throw new ArrayIndexOutOfBoundsException("Element's position must be positive");
        }
        if (element.getCollection() == this) return;
        if (element.getCollection() != null) throw new IllegalArgumentException("Element is already in another collection");
        List<E> actualList = this.get(element.getPosition());
        if (actualList == null) actualList = new ArrayList<>();
        actualList.add(element);
        this.put(new Point(element.getPosition()), actualList);
        element.setCollection(this);

    }

    public void remove(final E element) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        if (element.getCollection() != this) throw new IllegalArgumentException("Element is not in this collection");
        List<E> actualList = this.get(element.getPosition());
        if (!actualList.contains(element) || actualList == null) throw new IllegalArgumentException("Element not found at position " + element.getPosition());
        actualList.remove(element);
        element.setCollection(null);
        if (actualList.isEmpty()) this.remove(element.getPosition());

    }


    public boolean contains(final E element) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        if (element.getCollection() != this) throw new IllegalArgumentException("Element is not in this collection");
        return this.get(element.getPosition()).contains(element);
    }

    public List<E> toList() {
        final List<E> list = new ArrayList<>();
        for (final List<E> elements : this.values()) {
            list.addAll(elements);
        }
        return list;
    }

    public Dimension getDimension() {
        int width = 0;
        int height = 0;
        for (Point point : this.keySet()) {
            if (width < point.x ) width = point.x;
            if (height < point.y) height = point.y;
        }
        return new Dimension(width+1, height+1);
    }
}