package com.jeroenj.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class CustomModel {
    public ArrayList<Vector3f> vertices;
    public ArrayList<Integer> colors;

    public CustomModel() {
        this.vertices = new ArrayList<>();
        this.colors = new ArrayList<>();

        String name;
        ArrayList<Vector3f> vertices = new ArrayList<>();
        ArrayList<Vector3i> faces = new ArrayList<>();

//        Path filePath = Path.of("C:/Users/jeroe/Downloads/cube_triangulated.obj");
        Path filePath = Path.of("C:/Users/jeroe/Downloads/cat_triangulated.obj");

        try (var reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("o ")) {
                    name = line.substring(2);
                } else if (line.startsWith("v ")) {
                    vertices.add(handleObjV(line));
                } else if (line.startsWith("f ")) {
                    faces.add(handleObjF(line));
                }
            }
        } catch (IOException e) {
            //
        }

        Random random = new Random();
        for (Vector3i face : faces) {
            this.vertices.add(new Vector3f(vertices.get(face.x - 1).x, vertices.get(face.x - 1).z, vertices.get(face.x - 1).y));
            this.vertices.add(new Vector3f(vertices.get(face.y - 1).x, vertices.get(face.y - 1).z, vertices.get(face.y - 1).y));
            this.vertices.add(new Vector3f(vertices.get(face.z - 1).x, vertices.get(face.z - 1).z, vertices.get(face.z - 1).y));
            colors.add(0xFF000000 | random.nextInt(0x00FFFFFF + 1));
        }
    }

    public CustomModel(List<Vector3f> vertices, List<Vector3i> faces) {
        this.vertices = new ArrayList<>();
        this.colors = new ArrayList<>();

        Random random = new Random();

        for (Vector3i face : faces) {
            this.vertices.add(new Vector3f(vertices.get(face.x - 1).x, vertices.get(face.x - 1).z, vertices.get(face.x - 1).y));
            this.vertices.add(new Vector3f(vertices.get(face.y - 1).x, vertices.get(face.y - 1).z, vertices.get(face.y - 1).y));
            this.vertices.add(new Vector3f(vertices.get(face.z - 1).x, vertices.get(face.z - 1).z, vertices.get(face.z - 1).y));
            colors.add(0xFF000000 | random.nextInt(0x00FFFFFF + 1));
        }
    }

    private Vector3f handleObjV(String v) {
        String[] parts = v.split(" ");
        return new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
    }

    private Vector3i handleObjF(String f) {
        String[] parts = f.split(" ");
        return new Vector3i(
                Integer.parseInt(parts[1].split("/")[0]),
                Integer.parseInt(parts[2].split("/")[0]),
                Integer.parseInt(parts[3].split("/")[0])
        );
    }

    public void render(MatrixStack matrices) {
        Tessellator tessellator = Tessellator.getInstance();

        matrices.push();

        // Rotation
//        matrices.scale(0.5f, 0.5f, 0.5f);
//        matrices.translate(0.0f, 1.5f, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f));

        // Vertices
        Matrix4f transformationMatrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
        for (int i = vertices.size() - 1; i >= 0; i--) {
            Vector3f vertex = vertices.get(i);
            buffer.vertex(transformationMatrix, vertex.x, vertex.y, vertex.z).color(colors.get((int)(i / 3.0f)));
        }

        matrices.pop();

        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
}
