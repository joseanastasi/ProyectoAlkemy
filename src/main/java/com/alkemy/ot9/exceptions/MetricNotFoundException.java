package com.alkemy.ot9.exceptions;

public class MetricNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MetricNotFoundException() {
        super("Operation not found");
    }
}
