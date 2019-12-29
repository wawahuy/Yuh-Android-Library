package ml.huytools.lib.Exceptions;

public class DoNotCircleShapeException extends RuntimeException {
    public DoNotCircleShapeException() {
        super("Không phải đối tượng Drawable.");
    }
}
