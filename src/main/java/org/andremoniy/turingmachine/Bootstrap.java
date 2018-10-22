package org.andremoniy.turingmachine;

public class Bootstrap {

    public static void main(final String[] args) {
        if (args.length != 1) {
            System.err.println("Please specify path to the table of rules");
            System.exit(1);
        }

        final String transitionTablePath = args[0];


    }

}
