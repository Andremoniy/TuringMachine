package org.andremoniy.turingmachine;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Bootstrap {

    public static void main(final String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Please specify the path to the table of rules and the initial tape value");
            System.exit(1);
        }

        final String transitionTablePath = args[0];
        final List<String> rules = FileUtils.readLines(new java.io.File(transitionTablePath), StandardCharsets.UTF_8);

        final TuringMachine turingMachine = new TuringMachine(rules);

        final String initialTape = args[1];
        turingMachine.process(new Tape(initialTape));
    }

}
