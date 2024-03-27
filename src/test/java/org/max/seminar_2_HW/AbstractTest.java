package org.max.seminar_2_HW;

import org.junit.jupiter.api.BeforeEach;

public class AbstractTest {

    static BoxContainer boxContainer1;

    @BeforeEach
    void CreateContainers() {
        boxContainer1 = new BoxContainer();
    }
}
