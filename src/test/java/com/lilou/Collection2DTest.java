package com.lilou;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Collection2DTest {

    public static final int NB_ELEMENTS_TO_ADD = 100;
    private static final int NB_ROWS = 107;
    private static final int NB_COLUMNS = 123 ;
    private static Collection2D<Collection2DElementTest> collection2D;
    private static List<Collection2DElementTest> elements;

    @BeforeEach
    void beforeEach() {
        Collection2DTest.collection2D = new Collection2D<>();
        Collection2DTest.elements = new ArrayList<>();
        for (int numElement = 0; numElement < NB_ELEMENTS_TO_ADD; numElement++) {
            for (int row = 0; row < NB_ROWS; row++) {
                for (int column = 0; column < NB_COLUMNS; column++) {
                    Collection2DElementTest element = new Collection2DElementTest();
                    element.setPosition(new Point(column, row));
                    Collection2DTest.collection2D.add(element);
                    Collection2DTest.elements.add(element);
                }
            }
        }
    }

    @Test
    void addAndGet() {
        for (Collection2DElementTest elementTest : Collection2DTest.elements) {
            List<Collection2DElementTest> actualElementsAtPosition = Collection2DTest.collection2D.get(elementTest.position);
            assertNotNull(actualElementsAtPosition);
            assertEquals(1, actualElementsAtPosition.size());
            assertEquals(elementTest, actualElementsAtPosition.get(0));
        }
        Collection2DElementTest newElement = new Collection2DElementTest();
        newElement.setPosition(new Point(-100, -10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Collection2DTest.collection2D.add(newElement));
        newElement.setPosition(new Point(100, -10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Collection2DTest.collection2D.add(newElement));
        newElement.setPosition(new Point(-100, 10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Collection2DTest.collection2D.add(newElement));
        assertThrows(IllegalAccessException.class, () -> Collection2DTest.collection2D.add(null));
    }

    @Test
    void remove() {
        for (Collection2DElementTest elementTest : Collection2DTest.elements) {
            Collection2DTest.collection2D.remove(elementTest);
            List<Collection2DElementTest> actualElementsAtPosition = Collection2DTest.collection2D.get(elementTest.position);
            assertNotNull(actualElementsAtPosition);
            assertFalse(actualElementsAtPosition.contains(elementTest));
        }
        assertThrows(IllegalAccessException.class, () -> Collection2DTest.collection2D.remove(null));
    }

    @Test
    void contains() {
        for (Collection2DElementTest elementTest : Collection2DTest.elements) {
            assertTrue(Collection2DTest.collection2D.contains(elementTest));
        }
        assertThrows(IllegalAccessException.class, () -> Collection2DTest.collection2D.contains(null));
    }

    @Test
    void toList() {
        assertNotNull(Collection2DTest.collection2D.toList());
        assertEquals(Collection2DTest.elements.size(),Collection2DTest.collection2D.toList().size());
        assertTrue(Collection2DTest.elements.containsAll(Collection2DTest.collection2D.toList()));
        assertTrue(Collection2DTest.collection2D.toList().containsAll(Collection2DTest.elements));
    }

    @Test
    void dimension() {
        assertEquals(new Dimension(NB_COLUMNS, NB_ROWS), Collection2DTest.collection2D.getDimension());
        Collection2DElementTest newElement = new Collection2DElementTest();
        newElement.setPosition(new Point(1000, 2000));
        Collection2DTest.collection2D.add(newElement);
        assertEquals(new Dimension(1000, 2000), Collection2DTest.collection2D.getDimension());
    }

    @Test
    void elementHasMoved() {
        Collection2DElementTest elementTest = Collection2DTest.elements.get(0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elementTest.setPosition(new Point(-100, -10)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elementTest.setPosition(new Point(100, -10)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> elementTest.setPosition(new Point(-100, 10)));

    }

    @Test
    void isEmpty() {
        assertFalse(Collection2DTest.collection2D.isEmpty());
        assertTrue(new Collection2D<Collection2DElementTest>().isEmpty());
    }

    @Getter
    @Setter
    private static class Collection2DElementTest implements Collection2DElement<Collection2DElementTest> {
        private Point position;
        private Collection2D<Collection2DElementTest> collection;
        public void moveTo(final Point newPosition) {
            final Point oldPosition = this.position;
            this.collection.notifyElementHasMoved(this, oldPosition);
            this.position = newPosition;
        }
    }


}