package org.kohsuke.args4j;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ResourceBundle;
import org.kohsuke.args4j.spi.OptionHandler;

/**
 * Handles the visual presentation of usage information,
 * decoupling UI logic from the core parsing engine.
 */
class UsagePrinter {

    public static void print(CmdLineParser parser, Writer out, ResourceBundle rb, OptionHandlerFilter filter) {
        PrintWriter w = new PrintWriter(out);

        // 1. Calculate max length (Centralized Logic)
        int len = 0;
        // FIX: Passing the parser object down to the helper method
        len = Math.max(len, getMaxPrefixLen(parser, parser.getArguments(), rb));
        len = Math.max(len, getMaxPrefixLen(parser, parser.getOptions(), rb));

        // 2. Print (Delegated Logic)
        for (OptionHandler h : parser.getArguments())
            printOption(w, parser, h, len, rb, filter);
        for (OptionHandler h : parser.getOptions())
            printOption(w, parser, h, len, rb, filter);

        w.flush();
    }

    // FIX: Added CmdLineParser as a parameter so we can get its properties
    private static int getMaxPrefixLen(CmdLineParser parser, Iterable<OptionHandler> handlers, ResourceBundle rb) {
        int max = 0;
        for (OptionHandler h : handlers) {
            if (h.option.usage().length() > 0) {
                // FIX: Pass parser.getProperties() instead of null
                max = Math.max(max, h.getNameAndMeta(rb, parser.getProperties()).length());
            }
        }
        return max;
    }

    // This delegates the printing back to the parser's protected method
    private static void printOption(PrintWriter w, CmdLineParser parser, OptionHandler h, int len, ResourceBundle rb,
            OptionHandlerFilter filter) {
        parser.printOption(w, h, len, rb, filter);
    }
}