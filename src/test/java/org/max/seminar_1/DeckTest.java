package org.max.seminar_1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.seminar_1.exception.DeckEmptyException;

import java.util.ArrayList;
import java.util.List;

public class DeckTest {

    @Test
    void testDeckException() throws DeckEmptyException {
        //given
//        //Создание колоды с заполнением вручную
//        List<Card> cards = new ArrayList<>();
//        Deck deck = new Deck(cards);
        DeckService deckService = new DeckService();
        Deck deck = deckService.getNewDeck();
        //when
        for(int i = 0; i < 52; i++) {
            deck.getCard();
        }
        //then
        Assertions.assertThrows(DeckEmptyException.class, () -> deck.getCard());
    }
}
