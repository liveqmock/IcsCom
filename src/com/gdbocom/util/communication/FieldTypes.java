package com.gdbocom.util.communication;

/**
 * 拆包时，对应值的属性。
 * 
 * @author qm
 * 
 */
public class FieldTypes {
    private String name = null;
    public static final FieldTypes STATIC = new FieldTypes("1");
    public static final FieldTypes VARIABLELENGTH = new FieldTypes("5");
    public static final FieldTypes LOOPSTART = new FieldTypes("2");
    public static final FieldTypes LOOPEND = new FieldTypes("3");
    public static final FieldTypes LOOPING = new FieldTypes("4");

    public FieldTypes(String value) {
        this.name = value;
    }

    public boolean equals(FieldTypes f) {
        if (this.name.equals(f.name)) {
            return true;
        }
        return false;
    }
}
