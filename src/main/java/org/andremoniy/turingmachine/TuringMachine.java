package org.andremoniy.turingmachine;

import java.util.*;

public class TuringMachine {

    private final Map<Integer, Map<Character, Triple<Character, Movement, Integer>>> rulesTable;

    public TuringMachine(final List<String> rules) {
        final Map<Integer, Map<Character, Triple<Character, Movement, Integer>>> rulesTable = new HashMap<>();
        for (String rule : rules) {
            if (rule.startsWith("//")) {
                continue;
            }
            final String[] parts = rule.split(";");
            final Map<Character, Triple<Character, Movement, Integer>> symbolMap = rulesTable.computeIfAbsent(Integer.parseInt(parts[0]), integer -> new HashMap<>());
            symbolMap.put(parts[1].charAt(0), new Triple<>(parts[2].charAt(0), Movement.valueOf(parts[3]), parts.length > 4 ? Integer.parseInt(parts[4]) : null));

        }
        this.rulesTable = Collections.unmodifiableMap(rulesTable);
    }

    public void process(final Tape tape) {
        int headPosition = 0;
        int state = 0;
        do {
            final Character character = tape.read(headPosition);
            final Map<Character, Triple<Character, Movement, Integer>> characterTripleMap = rulesTable.get(state);
            Objects.requireNonNull(characterTripleMap, "Cannot find rules for state '" + state + "'");

            final Triple<Character, Movement, Integer> rule = characterTripleMap.get(character);
            Objects.requireNonNull(rule, "Cannot find rule for state '" + state + "' and char '" + character + "'. Current tape: '" + tape + "'");

            tape.write(headPosition, rule.a);
            headPosition = rule.b.move(headPosition);

            if (rule.b == Movement.H) {
                break;
            }

            state = rule.c;

            System.out.println(rule);
            System.out.println(tape);
            System.out.println(" ".repeat(headPosition)+"^");
            System.out.println();
        } while (true);
    }

    private class Triple<A, B, C> {
        final A a;
        final B b;
        final C c;

        Triple(final A a, final B b, final C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return a + ";" + b + ";" + c;
        }
    }

    enum Movement {
        L(-1), R(1), H(0), S(0);

        private final int shift;

        Movement(final int shift) {
            this.shift = shift;
        }

        public int move(final int headPosition) {
            return headPosition + shift;
        }
    }
}
