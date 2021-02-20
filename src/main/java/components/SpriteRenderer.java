package components;

import imgui.ImGui;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import slothengine.Component;
import slothengine.Transform;

public class SpriteRenderer extends Component {

    @Getter
    private Vector4f color = new Vector4f(1, 1, 1, 1);
    @Getter
    private Sprite sprite = new Sprite();
    private boolean isDirty;
    private Transform lastTransform;

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

    @Override
    public void imgui() {
        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            setColor(new Vector4f(imColor[0], imColor[1], imColor[2], imColor[3]));
        }
    }
}
