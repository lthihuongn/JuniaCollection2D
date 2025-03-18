package com.lilou;

import java.awt.*;

public interface Collection2DElement<E extends Collection2DElement<E>> {

    Point getPosition();

    Collection2D<E> getCollection();

    void setCollection(Collection2D<E> collection2D);


    void setPosition(Point position);

}