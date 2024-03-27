package org.max.seminar_2_HW;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoxContainerTest extends AbstractTest {

    @Test
    void testAddToBox() {

        boxContainer1.addToBox(new PurchaseLeaf(100));
        boxContainer1.addToBox(new PurchaseLeaf(200));

        assertEquals(2, boxContainer1.getChildren().size());
    }

    @Test
    void testRemoveToBox() {

        boxContainer1.addToBox(new PurchaseLeaf(100));
        boxContainer1.addToBox(new PurchaseLeaf(200));
        boxContainer1.addToBox(new PurchaseLeaf(200));
        boxContainer1.removeFromBox(0);

        assertEquals(2, boxContainer1.getChildren().size());
    }

    @Test
    void testBoxContainerCountSum() {

        BoxContainer boxContainer2 = new BoxContainer();
        BoxContainer boxContainer3 = new BoxContainer();
        BoxContainer boxContainer4 = new BoxContainer();

        boxContainer1.addToBox(new PurchaseLeaf(100));
        boxContainer2.addToBox(new PurchaseLeaf(200));
        boxContainer3.addToBox(boxContainer2);
        boxContainer3.addToBox(new PurchaseLeaf(250));
        boxContainer4.addToBox(boxContainer1);
        boxContainer4.addToBox(boxContainer2);
        boxContainer4.addToBox(boxContainer3);

        assertEquals(750, boxContainer4.countPrice());
    }

}
