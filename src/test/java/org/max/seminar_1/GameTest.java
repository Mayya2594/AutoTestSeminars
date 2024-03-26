package org.max.seminar_1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.seminar_1.type.Ranks;
import org.max.seminar_1.type.Suits;

public class GameTest {

    @Test
    void testPlayerWin() {
        //given
        Game game = new Game();
        //when
        game.getGamer().addCard(new Card(Ranks.ACE, Suits.CLUBS));
        game.getGamer().addCard(new Card(Ranks.TEN, Suits.CLUBS));
        game.getCasino().addCard(new Card(Ranks.EIGHT, Suits.CLUBS));
        game.getCasino().addCard(new Card(Ranks.SEVEN, Suits.CLUBS));
        Player player = game.getWinner(game.getGamer(), game.getCasino());
        //then
        Assertions.assertEquals(game.getGamer(), player);
    }

    @Test
    void testCasinoWin() {
        //given
        Gamer gamer = new Gamer(4);
        Casino casino = new Casino(0);
        Game game = new Game(gamer, casino);
        //when
        gamer.addCard(new Card(Ranks.ACE, Suits.CLUBS));
        gamer.addCard(new Card(Ranks.SEVEN, Suits.CLUBS));
        casino.addCard(new Card(Ranks.EIGHT, Suits.CLUBS));
        casino.addCard(new Card(Ranks.SEVEN, Suits.CLUBS));
        casino.addCard(new Card(Ranks.FIVE, Suits.CLUBS));
        //then
        Assertions.assertEquals(casino, game.getWinner(gamer, casino));

    }
}
