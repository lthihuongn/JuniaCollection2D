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

    private static final int NB_ELEMENTS_TO_ADD = 100;
    private static final int NB_ROWS = 100;
    private static final int NB_COLUMNS = 200;
    private static final Collection2D<Collection2DElementTest> collection2D = new Collection2D<>();
    private static final List<Collection2DElementTest> elements = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        Collection2DTest.collection2D.clear();
        Collection2DTest.elements.clear();
        for (int numElement = 0; numElement < Collection2DTest.NB_ELEMENTS_TO_ADD; numElement++) {
            for (int row = 0; row < Collection2DTest.NB_ROWS; row++) {
                for (int column = 0; column < Collection2DTest.NB_COLUMNS; column++) {
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
            assertNotNull(actualElementsAtPosition, "Element's list at position " + elementTest.position + " is null");
            assertEquals(Collection2DTest.NB_ELEMENTS_TO_ADD, actualElementsAtPosition.size(), "Expected one element at position " + elementTest.position);
            assertTrue(actualElementsAtPosition.contains(elementTest), "Expected element at position " + elementTest.position + " to be " + elementTest);
        }

        Collection2DElementTest newElement = new Collection2DElementTest();
        newElement.setPosition(new Point(-100, -10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Collection2DTest.collection2D.add(newElement), "Expected ArrayIndexOutOfBoundsException to be thrown");
        newElement.setPosition(new Point(100, -10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Collection2DTest.collection2D.add(newElement), "Expected ArrayIndexOutOfBoundsException to be thrown");
        newElement.setPosition(new Point(-100, 10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Collection2DTest.collection2D.add(newElement), "Expected ArrayIndexOutOfBoundsException to be thrown");
        assertThrows(IllegalArgumentException.class, () -> Collection2DTest.collection2D.add(null), "Expected IllegalArgumentException to be thrown when adding null element");
    }

    @Test
    void remove() {
        for (Collection2DElementTest elementTest : Collection2DTest.elements) {
            Collection2DTest.collection2D.remove(elementTest);
            List<Collection2DElementTest> actualElementsAtPosition = Collection2DTest.collection2D.get(elementTest.position);
            assertNotNull(actualElementsAtPosition, "Element's list at position " + elementTest.position + " is null after removal");
            assertFalse(actualElementsAtPosition.contains(elementTest), "Expected element " + elementTest + " to be removed from position " + elementTest.position);
        }
        assertThrows(IllegalArgumentException.class, () -> Collection2DTest.collection2D.remove(null));
    }

    @Test
    void contains() {
        for (Collection2DElementTest elementTest : Collection2DTest.elements) {
            assertTrue(Collection2DTest.collection2D.contains(elementTest), "Expected collection to contain element " + elementTest);
        }
        assertThrows(IllegalArgumentException.class, () -> Collection2DTest.collection2D.contains(null), "Expected IllegalArgumentException to be thrown when checking for null element");
    }

    @Test
    void isEmpty() {
        assertFalse(Collection2DTest.collection2D.isEmpty(), "Expected collection to be not empty");
        assertTrue(new Collection2D<Collection2DElementTest>().isEmpty(), "Expected new collection to be empty");
    }

    @Test
    void toList() {
        assertNotNull(Collection2DTest.collection2D.toList(), "Expected collection to be not empty after conversion to list");
        assertNotEquals(Collection2DTest.elements.size(), Collection2DTest.collection2D.toList().size(), "Expected collection to be not empty after conversion to list");
        assertTrue(Collection2DTest.elements.containsAll(Collection2DTest.collection2D.toList()), "Expected all elements to be in collection after conversion to list");
        assertTrue(Collection2DTest.collection2D.toList().containsAll(Collection2DTest.elements), "Expected all elements to be in collection after conversion to list");
    }

    @Test
    void dimension() {
        assertEquals(new Dimension(Collection2DTest.NB_COLUMNS, Collection2DTest.NB_ROWS), Collection2DTest.collection2D.getDimension(), "Expected dimension to be " + new Dimension(Collection2DTest.NB_COLUMNS, Collection2DTest.NB_ROWS));
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

    @Getter
    @Setter
    private static class Collection2DElementTest implements Collection2DElement<Collection2DElementTest> {
        private Point position;
        private Collection2D<Collection2DElementTest> collection;

        public void move(final Point newPosition) {
            final Point oldPosition = this.position;
            this.collection.notifyElementHasMoved(this, oldPosition);
            this.position = newPosition;
        }
    }
}
