package com.lilou;

import java.awt.*;

public interface Collection2DElement <E extends Collection2DElement<E>>{
    Point getPosition();

    void setCollection(Collection2D<E> collection2D);

    Collection2DElement<E> getCollection();

}
