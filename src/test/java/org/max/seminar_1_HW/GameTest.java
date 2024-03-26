package org.max.seminar_1_HW;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest extends AbstractTest {

    @ParameterizedTest
    @ValueSource(ints = 0)
    void testNotRiskWin(int doorNumber) {
        Player player = new Player("Player", false);
        Game game = new Game(player, doors);

        Door door = game.round(doorNumber);

        assertTrue(door.isPrize());
    }

    @Test
    void testNotRiskLose() {
        Player player = new Player("Player", false);
        Game game = new Game(player, doors);

        Door door = game.round(1);

        assertFalse(door.isPrize());
    }

    @Test
    void testRiskWin() {
        Player player = new Player("Player", true);
        Game game = new Game(player, doors);

        Door door = game.round(1);

        assertTrue(door.isPrize());
    }

    @Test
    void testRiskLose() {
        Player player = new Player("Player", true);
        Game game = new Game(player, doors);

        Door door = game.round(0);

        assertFalse(door.isPrize());
    }
}
