package com.syntaxphoenix.spigot.smoothtimber.utilities;

import org.bukkit.ChatColor;

import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

public final class Centering {

    private Centering() {}

    public static String center(final String input) {
        return center(input, "\n");
    }

    public static String center(final String input, final String split) {
        final String[] sentences = input.split(split);
        final int length = identifyLength(sentences);
        final StringBuilder builder = new StringBuilder();
        for (final String sentence : sentences) {
            apply(builder, length, sentence).append(split);
        }
        final String output = builder.toString();
        return output.substring(0, output.length() - 1);
    }

    public static String[] center(final String... input) {
        final int length = identifyLength(input);
        final String[] output = new String[input.length];
        final StringBuilder builder = new StringBuilder();
        int index = 0;
        for (final String sentence : input) {
            output[index++] = apply(builder, length, sentence).toStringClear();
        }
        return output;
    }

    private static StringBuilder apply(final StringBuilder builder, final int length, final String sentence) {
        final int size = ChatColor.stripColor(sentence).length();
        final int difference = length - size;
        final int spaces = difference / 2;
        for (int current = 0; current < spaces; current++) {
            builder.append(' ');
        }
        return builder.append(sentence);
    }

    private static int identifyLength(final String[] input) {
        int length = 0;
        for (final String sentence : input) {
            final int size = ChatColor.stripColor(sentence).length();
            if (size > length) {
                length = size;
            }
        }
        return length;
    }

}
