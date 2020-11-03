package components;

import slothengine.Component;

public class FontRenderer extends Component {

    @Override
    public void start() {
        super.start();
        if (gameObject.getComponent(SpriteRenderer.class) != null) {
            System.out.println("Found Font Renderer!");
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
