package org.max.seminar_1_HW;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTest {

    static List<Door> doors;

    @BeforeEach
    void createDoors() {
        doors = new ArrayList<>();
        doors.add(new Door(true));
        doors.add(new Door(false));
        doors.add(new Door(false));
    }
}
