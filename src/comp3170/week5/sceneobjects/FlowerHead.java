package comp3170.week5.sceneobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
import static org.lwjgl.opengl.GL41.*;
import static comp3170.Math.TAU;

public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;
	public static int numEdgePoints = 10;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;
	private int[] indices;
	private int indexBuffer;
	

	public FlowerHead(int nPetals, Vector3f colour) {
		
		// TODO: Create the flower head. (TASK 1)
		// Consider the best way to draw the mesh with the nPetals input. 
		// Note that this may involve moving some code OUT of this class!
		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
		
		petalColour = colour;
		
		vertices = new Vector4f[numEdgePoints + 1];
		vertices[0] = new Vector4f(0, 0, 0, 1);
		
//		float innerRadius = 0.2f;
//        float outerRadius = 0.5f;
//		
//        float x = (float) (innerRadius * Math.cos(angle));
//        float y = (float) (outerRadius * Math.sin(angle));
        
//        vertices[i + 1] = new Vector4f(x, y, 0, 1);
        
        Matrix4f rotate = new Matrix4f();
        
        for (int i = 0; i < numEdgePoints; i++) {
        	float angle = i * TAU / numEdgePoints;
        	
        	rotate.rotationZ(angle);
        	vertices[i + 1]= new Vector4f(1, 0, 0, 1);
        	vertices[i + 1].mul(rotate);

        }
        vertexBuffer = GLBuffers.createBuffer(vertices);
        
        indices = new int[numEdgePoints * 3];
        
        for (int i = 0; i < numEdgePoints; i++) {
        	 indices[i * 3] = 0;
             indices[i * 3 + 1] = i + 1; 
             indices[i * 3 + 2] = (i + 4) % numEdgePoints + 1;
        }
        
        indexBuffer = GLBuffers.createIndexBuffer(indices);
		
	}

	public void update(float dt) {
		// TODO: Make the flower head rotate. (TASK 5)
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		// TODO: Add any appropriate draw code. (TASK 1)
		shader.enable();
        shader.setUniform("u_mvpMatrix", mvpMatrix);
        shader.setAttribute("a_position", vertexBuffer);
        shader.setUniform("u_colour", petalColour);            
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0); 
		
	}
}
