package components;

import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import slothengine.Component;
import slothengine.Transform;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;
    private boolean isDirty = true;

    private Transform lastTransform;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite ;
        this.color = new Vector4f(1, 1, 1, 1);

    }

    @Override
    public void start() {
        super.start();
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float deltaTime) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            this.isDirty = true;
        }
    }

    public Vector4f getColor() {
        return color;
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color = color;
            this.isDirty = true;
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setClean() {
        isDirty = false;
    }
}
