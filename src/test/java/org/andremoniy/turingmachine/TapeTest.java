package org.andremoniy.turingmachine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TapeTest {

    @Test
    void shouldReturnProperToString() {
        // Given
        final Tape tape = new Tape("abbb");

        // When
        final String tapeToString = tape.toString();

        // Then
        assertEquals("abbb", tapeToString);
    }

}