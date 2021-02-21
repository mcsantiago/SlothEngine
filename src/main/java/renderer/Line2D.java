package renderer;

import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Line2D {
    @Getter private final Vector2f from;
    @Getter private final Vector2f to;
    @Getter private final Vector3f color;
    private int lifetime;

    public Line2D(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifetime = lifetime;
    }

    public int beginFrame() {
        this.lifetime--;
        return this.lifetime;
    }
}
