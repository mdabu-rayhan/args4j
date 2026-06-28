package org.kohsuke.args4j;

/**
 * Encapsulates a command-line token to prevent passing raw strings
 * throughout the parsing logic.
 */
public class CliToken {
    private final String value;
    private final boolean isFlag;

    public CliToken(String value) {
        this.value = value;
        // Logic previously hidden in CmdLineParser is now encapsulated here
        this.isFlag = value.startsWith("-");
    }

    public String getValue() {
        return value;
    }

    public boolean isFlag() {
        return isFlag;
    }

    @Override
    public String toString() {
        return value;
    }
}