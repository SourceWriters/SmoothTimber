package net.sourcewriters.smoothtimber.api.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public final class ExtensionHelper {

    private ExtensionHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Optional<Field> getField(final Class<?> clazz, final String field) {
        try {
            return Optional.of(clazz.getField(field));
        } catch (NoSuchFieldException | SecurityException e) {
            // Ignore because we've to check declared fields
        }
        try {
            return Optional.of(clazz.getDeclaredField(field));
        } catch (NoSuchFieldException | SecurityException e) {
            // Ignore because we don't care about if the field doesn't exist
        }
        return Optional.empty();
    }

    public static Optional<Method> getMethod(final Class<?> clazz, final String method, final Class<?>... arguments) {
        try {
            return Optional.of(clazz.getMethod(method, arguments));
        } catch (NoSuchMethodException | SecurityException e) {
            // Ignore because we've to check declared methods
        }
        try {
            return Optional.of(clazz.getDeclaredMethod(method, arguments));
        } catch (NoSuchMethodException | SecurityException e) {
            // Ignore because we don't care about if the method doesn't exist
        }
        return Optional.empty();
    }

    public static <A extends Annotation> Optional<A> getAnnotation(final AnnotatedElement element, final Class<A> annotation) {
        return Optional.ofNullable(element.getAnnotation(annotation))
            .or(() -> Optional.ofNullable(element.getDeclaredAnnotation(annotation)));
    }

    public static <A extends Annotation> Optional<A> getAnnotationOfField(final Class<A> annotation, final Class<?> clazz,
        final String fieldName) {
        return getField(clazz, fieldName).flatMap(method -> getAnnotation(method, annotation));
    }

    public static <A extends Annotation> Optional<A> getAnnotationOfMethod(final Class<A> annotation, final Class<?> clazz,
        final String methodName, final Class<?>... arguments) {
        return getMethod(clazz, methodName, arguments).flatMap(method -> getAnnotation(method, annotation));
    }

}
