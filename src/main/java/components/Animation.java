package components;

import slothengine.Component;

public class Animation extends Component {
    private int spriteIndexStart;
    private int currentSpriteIndex;
    private int spriteIndexEnd;
    private float spriteFlipTime;
    private float spriteFlipTimeLeft;
    private Spritesheet sprites;

    public Animation(int spriteIndexStart, int spriteIndexEnd, float spriteFlipTime, Spritesheet sprites) {
        this.spriteIndexStart = this.currentSpriteIndex = spriteIndexStart;
        this.spriteIndexEnd = spriteIndexEnd;
        this.spriteFlipTime = spriteFlipTime;
        this.spriteFlipTimeLeft = 0;
        this.sprites = sprites;
    }

    @Override
    public void update(float deltaTime) {
        if (this.gameObject.getComponent(SpriteRenderer.class) == null) {
            return;
        }

        spriteFlipTimeLeft -= deltaTime;

        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            currentSpriteIndex++;
            if (currentSpriteIndex > spriteIndexEnd) {
                currentSpriteIndex = spriteIndexStart;
            }
            this.gameObject.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(currentSpriteIndex));
        }
    }
}
