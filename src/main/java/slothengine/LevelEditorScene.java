package slothengine;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
  private float[] vertexArray = {
    // position                   // color                // UV
    100.5f,   0.5f, 0.0f,        1.0f, 0.0f, 0.0f, 1.0f,   1, 1,  // bottom right
      0.5f, 100.5f, 0.0f,        0.0f, 1.0f, 0.0f, 1.0f,   0, 0,  // Top left
    100.5f, 100.5f, 0.0f,        0.0f, 0.0f, 1.0f, 1.0f,   1, 0,  // Top right
      0.5f,   0.5f, 0.0f,        1.0f, 0.0f, 1.0f, 1.0f,   0, 1,  // Bottom left
  };

  // Must be in counter clockwise order!
  private int[] elementArray = {
    2, 1, 0, // Top-right triangle
    0, 1, 3  // Bottom left triangle
  };

  private int vaoID, vboID, eboID;

  private Shader defaultShader;
  private Texture testTexture;

  public LevelEditorScene() {
  }

  @Override
  public void init() {
    super.init();
    this.camera = new Camera(new Vector2f(-500.0f, -50.0f));

    defaultShader = new Shader("assets/shaders/default.glsl");
    defaultShader.compileAndLink();
    System.out.println(defaultShader);

    testTexture = new Texture("assets/images/mio-chan.jpg");

    // ==============================================================
    // Generate VAO, VBO, and EBO buffer objects, and send to GPU
    // ==============================================================
    vaoID = glGenVertexArrays();
    glBindVertexArray(vaoID);

    // Create a float buffer of vertices
    FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
    vertexBuffer.put(vertexArray).flip();

    // Create VBO upload the vertex buffer
    vboID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

    // Create the indices and upload
    IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
    elementBuffer.put(elementArray).flip();

    eboID = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

    // Add the vertex attribute pointers
    int positionSize = 3;
    int colorSize = 4;
    int uvSize = 2;
    int vertexSizeBytes = (positionSize + colorSize + uvSize) * Float.BYTES;

    glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
    glEnableVertexAttribArray(1);

    glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize) * Float.BYTES);
    glEnableVertexAttribArray(2);
  }

  @Override
  public void update(float deltaTime) {
    camera.position.x -= deltaTime * 50;
    camera.position.y -= deltaTime * 20;

    defaultShader.use();

    // Upload texture to shader
    defaultShader.uploadTexture("TEX_SAMPLER", 0);
    glActiveTexture(GL_TEXTURE0);
    testTexture.bind();

    defaultShader.uploadMat4("uProjection", camera.getProjectionMatrix());
    defaultShader.uploadMat4("uView", camera.getViewMatrix());
    defaultShader.uploadFloat("uTime", Time.getTime());

    // Bind the VAO
    glBindVertexArray(vaoID);

    // Enable the vertex attribute pointers
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

    // Unbind
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);

    glBindVertexArray(0);

    defaultShader.detatch();
    testTexture.unbind();
  }
}
