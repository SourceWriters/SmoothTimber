package net.sourcewriters.smoothtimber.core.util;

import java.awt.Color;

import com.syntaxphoenix.syntaxapi.logging.color.ColorProcessor;
import com.syntaxphoenix.syntaxapi.logging.color.LogType;

final class SafeColorType extends LogType {

    public static final SafeColorType DEFAULT = new SafeColorType("info", "INFO", '7');

    public static final ColorProcessor PROCESSOR = (flag, type) -> type.asColorString(flag);

    /*
     * 
     */

    private char color;

    /*
     * 
     * 
     * 
     */

    public SafeColorType(final String id, final char color) {
        super(id, id.toUpperCase());
        this.color = color;
    }

    public SafeColorType(final String id, final String name, final char color) {
        super(id, name);
        this.color = color;
    }

    /*
     * 
     * 
     * 
     */

    public void setColor(final char color) {
        this.color = color;
    }

    /*
     * 
     * 
     * 
     */

    @Override
    public ColorProcessor getColorProcessor() {
        return PROCESSOR;
    }

    @Override
    public Color asColor() {
        return null;
    }

    @Override
    public String asColorString() {
        return "\u00A7" + color;
    }

    @Override
    public String asColorString(final boolean stream) {
        return stream ? asColorString() : "";
    }

}
