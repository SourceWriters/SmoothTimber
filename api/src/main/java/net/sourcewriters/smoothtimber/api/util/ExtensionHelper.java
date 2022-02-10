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

    public static Optional<Field> getField(Class<?> clazz, String field) {
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

    public static Optional<Method> getMethod(Class<?> clazz, String method, Class<?>... arguments) {
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

    public static <A extends Annotation> Optional<A> getAnnotation(AnnotatedElement element, Class<A> annotation) {
        return Optional.ofNullable(element.getAnnotation(annotation))
            .or(() -> Optional.ofNullable(element.getDeclaredAnnotation(annotation)));
    }

    public static <A extends Annotation> Optional<A> getAnnotationOfField(Class<A> annotation, Class<?> clazz, String fieldName) {
        return getField(clazz, fieldName).flatMap(method -> getAnnotation(method, annotation));
    }

    public static <A extends Annotation> Optional<A> getAnnotationOfMethod(Class<A> annotation, Class<?> clazz, String methodName,
        Class<?>... arguments) {
        return getMethod(clazz, methodName, arguments).flatMap(method -> getAnnotation(method, annotation));
    }

}
