package components;

import lombok.Setter;

public class Animation extends Component {
    @Setter private int spriteIndexStart;
    @Setter private int spriteIndexEnd;
    @Setter private float spriteFlipTime;
    @Setter private Spritesheet sprites;

    @Setter private int currentSpriteIndex;
    @Setter private float spriteFlipTimeLeft;

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
