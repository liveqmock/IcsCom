package com.gdbocom.util.communication;

/**
 * 拼包时，所用的值的来源
 * 
 * @author qm
 * 
 */
public class FieldSource {
    private String name;
    public static final FieldSource VAR = new FieldSource("1");
    public static final FieldSource PROPS = new FieldSource("2");

    public FieldSource(String value) {
        this.name = value;
    }

    public boolean equals(FieldSource s) {
        if (this.name.equals(s.name)) {
            return true;
        }
        return false;
    }
}
