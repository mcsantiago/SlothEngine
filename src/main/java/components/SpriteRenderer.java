package components;

import org.joml.Vector4f;
import slothengine.Component;

public class SpriteRenderer extends Component {

    private Vector4f color;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update(float deltaTime) {
    }

    public Vector4f getColor() {
        return color;
    }
}
