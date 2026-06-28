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
        len = Math.max(len, getMaxPrefixLen(parser.getArguments(), rb));
        len = Math.max(len, getMaxPrefixLen(parser.getOptions(), rb));

        // 2. Print (Delegated Logic)
        for (OptionHandler h : parser.getArguments())
            printOption(w, parser, h, len, rb, filter);
        for (OptionHandler h : parser.getOptions())
            printOption(w, parser, h, len, rb, filter);

        w.flush();
    }

    private static int getMaxPrefixLen(Iterable<OptionHandler> handlers, ResourceBundle rb) {
        int max = 0;
        for (OptionHandler h : handlers) {
            if (h.option.usage().length() > 0) {
                max = Math.max(max, h.getNameAndMeta(rb, null).length());
            }
        }
        return max;
    }

    // This method now calls the parser's protected printOption method
    private static void printOption(PrintWriter w, CmdLineParser parser, OptionHandler h, int len, ResourceBundle rb,
            OptionHandlerFilter filter) {
        // You would move the logic for printOption from CmdLineParser to here
        // Or keep it protected in CmdLineParser and call it from here.
    }
}