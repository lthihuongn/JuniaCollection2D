package com.lilou;

import java.awt.*;

public interface Collection2DElement <E extends Collection2DElement<E>>{
    Point getPosition();
}
