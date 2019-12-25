package ml.huytools.lib.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ml.huytools.lib.MVP.Model;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JsonName {
    enum Type {
        Normal,
        Model,
        ModelManager
    };

    String value() default "";
    Type type() default Type.Normal;
    Class<?> clazz() default Model.class;
}
