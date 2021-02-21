package components;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import renderer.Texture;

public class Sprite {

    @Getter @Setter private Texture texture = null;

    @Getter @Setter
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    @Getter @Setter private float width;
    @Getter @Setter private float height;

    public int getTexId() {
        return texture == null ? -1 : texture.getTexID();
    }
}
