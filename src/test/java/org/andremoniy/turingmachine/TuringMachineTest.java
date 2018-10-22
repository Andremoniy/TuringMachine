package org.andremoniy.turingmachine;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TuringMachineTest {

    private static final List<String> RULES_abbb = Arrays.asList(
            // begin reading abbb... we look for next same sequence
            "0;<;<;R;0",
            "0;a;a;R;18",
            "18;b;b;R;18",
            "18;a;a;L;19",
            "19;b;b;L;19",
            "19;a;a;R;20",
            "20;b;c;R;21",
            "21;b;b;R;21",
            "21;a;a;R;22",
            "22;d;d;R;22",
            "22;b;d;L;23",
            "23;d;d;L;23",
            "23;a;a;L;23",
            "23;b;b;L;23",
            "23;c;c;R;20",
            "20;a;a;L;24",
            "24;c;c;L;24",
            "24;a;a;R;25",
            "25;c;b;R;25",
            "25;a;a;R;25",
            "25;d;b;R;25",
            "25;b;b;R;25",
            "25;>;>;R;2",
            "2;a;a;R;2",
            // when we found empty place, write there a symbol
            "2; ;a;L;3",
            // and go back to erase the read abbb...
            "3;>;>;L;3",
            "3;b;b;L;3",
            "3;a;a;L;3",
            "3;<;<;S;4",
            "4;<; ;R;4",
            "4;a; ;R;5",
            "5;b; ;R;5",
            // we met next "a", so we do step back and write <
            "5;a;a;L;17",
            "17; ;<;R;0",
            // if the read abb... was the last, we switch to another state
            "18;>;>;L;7",
            "7;b;b;L;7",
            "7;a;a;L;7",
            "7;<;<;S;8",
            "8;<; ;R;8",
            // erase a and switch to next rule
            "8;a;<;R;9",
            "9;b;b;R;10",
            "10;b;b;R;10",
            "10;>;>;R;10",
            // move until we met last symbol in the stack, but check that it is not empty
            "10;a;a;R;13",
            // new state - 13 - means that we met at least one symbol in the stack
            "13;a;a;R;13",
            "13; ; ;L;11",
            "11;a; ;L;12",
            "12;a;a;L;12",
            "12;>;>;L;12",
            "12;b;b;L;12",
            // prepare to delete one b
            "12;<;<;S;17",
            "17;<; ;R;17",
            // back to the same rule
            "17;b;<;R;9",
            // this case means there is no symbols in the stack
            // then we should check that there is only one "b" in the string:
            "10; ; ;L;14",
            "14;>;>;L;14",
            "14;b;b;L;15",
            // next state should be start string marker and here we can stop
            "15;<;<;H;16"
    );

    @Test
    void shouldProperlyMoveOnTheTape() {
        // Given
        final Tape tape = new Tape("<abbbabbbabbb>");

        // s/c/c/m/s

        final TuringMachine turingMachine = new TuringMachine(RULES_abbb);

        // When
        turingMachine.process(tape);

        // Then
        assertEquals("<b>", tape.toString().trim());
    }

    @Test
    void shouldProperlyMoveOnTheTape2() {
        // Given
        final Tape tape = new Tape("<ab>");

        // s/c/c/m/s

        final TuringMachine turingMachine = new TuringMachine(RULES_abbb);

        // When
        turingMachine.process(tape);

        // Then
        assertEquals("<b>", tape.toString().trim());
    }

    @Test
    void shouldProperlyMoveOnTheTape3() {
        // Given
        final Tape tape = new Tape("<abbabb>");

        // s/c/c/m/s

        final TuringMachine turingMachine = new TuringMachine(RULES_abbb);

        // When
        turingMachine.process(tape);

        // Then
        assertEquals("<b>", tape.toString().trim());
    }

    @Test
    void shouldFailOnTheWrongInput() {
        // Given
        final Tape tape = new Tape("<abbbabbbbabbb>");

        // s/c/c/m/s

        final TuringMachine turingMachine = new TuringMachine(RULES_abbb);

        // When
        try {
            turingMachine.process(tape);
            fail("Should fail on processing");
        } catch (NullPointerException e) {
            assertEquals("Cannot find rule for state '22' and char '>'. Current tape: '    <accccaddd>a'", e.getMessage());
        }
    }

    @Test
    void shouldFailOnTheWrongInput2() {
        // Given
        final Tape tape = new Tape("<abbbabbb>");

        // s/c/c/m/s

        final TuringMachine turingMachine = new TuringMachine(RULES_abbb);

        // When
        try {
            turingMachine.process(tape);
            fail("Should fail on processing");
        } catch (NullPointerException e) {
            assertEquals("Cannot find rule for state '15' and char 'b'. Current tape: '      <bb>  '", e.getMessage());
        }
    }

}