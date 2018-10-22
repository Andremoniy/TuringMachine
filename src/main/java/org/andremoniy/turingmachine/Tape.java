package org.andremoniy.turingmachine;

import java.util.*;
import java.util.stream.Collectors;

public class Tape {

    private static final Character EMPTY = ' ';
    private final Map<Integer, Character> strip = new HashMap<>();

    Tape(final String string) {
        for (int position = 0; position < string.length(); position++) {
            strip.put(position, string.charAt(position));
        }
    }

    @Override
    public String toString() {
        return strip.keySet().stream().sorted()
                .map(position -> strip.get(position).toString())
                .collect(Collectors.joining());
    }

    Character read(final int headPosition) {
        return Optional.ofNullable(strip.get(headPosition)).orElse(EMPTY);
    }

    void write(final int headPosition, final char c) {
        strip.put(headPosition, c);
    }
}
