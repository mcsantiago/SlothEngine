package components;

import slothengine.Component;

public class SpriteRenderer extends Component {
    private boolean firstTime = false;

    @Override
    public void start() {
        super.start();
        System.out.println("I am starting");
    }

    @Override
    public void update(float deltaTime) {
        if (!firstTime) {
            System.out.println("I am updating");
            firstTime = true;
        }
    }
}
