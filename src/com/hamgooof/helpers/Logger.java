package com.hamgooof.helpers;


import java.io.PrintStream;

public class Logger {
    private static Logger _logger;
    private final PrintStream _output;

    private Logger() {
        _output = System.out;
    }

    public static Logger getLogger() {
        if (_logger == null)
            _logger = new Logger();
        return _logger;
    }

    public void writeln(String output) {
        _output.println(output);
    }

    public void writeln(Object output) {
        writeln(output.toString());
    }

    public void blankln(int count) {
        for (int i = 0; i < count; i++) {
            writeln("");
        }
    }

    public void blankln() {
        writeln("");

    }

}
