package com.jeroenj.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class CustomFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private final CustomModel model;

    public CustomFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context) {
        super(context);
//        model = new CustomModel(new ArrayList<Vector3f>(List.of(
//                new Vector3f(0.500000f, 0.500000f, -0.500000f),
//                new Vector3f(0.500000f, -0.500000f, -0.500000f),
//                new Vector3f(0.500000f, 0.500000f, 0.500000f),
//                new Vector3f(0.500000f, -0.500000f, 0.500000f),
//                new Vector3f(-0.500000f, 0.500000f, -0.500000f),
//                new Vector3f(-0.500000f, -0.500000f, -0.500000f),
//                new Vector3f(-0.500000f, 0.500000f, 0.500000f),
//                new Vector3f(-0.500000f, -0.500000f, 0.500000f)
//        )), new ArrayList<Vector3i>(List.of(
//                new Vector3i(5, 3, 1),
//                new Vector3i(3, 8, 4),
//                new Vector3i(7, 6, 8),
//                new Vector3i(2, 8, 6),
//                new Vector3i(1, 4, 2),
//                new Vector3i(5, 2, 6),
//                new Vector3i(5, 7, 3),
//                new Vector3i(3, 7, 8),
//                new Vector3i(7, 5, 6),
//                new Vector3i(2, 4, 8),
//                new Vector3i(1, 3, 4),
//                new Vector3i(5, 1, 2)
//        )));

        model = new CustomModel();
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        // Get the transformation matrix from the matrix stack, alongside the tessellator instance and a new buffer builder.
//        Matrix4f transformationMatrix = matrices.peek().getPositionMatrix();
//        Tessellator tessellator = Tessellator.getInstance();

        // Rotation
//        matrices.push();
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(MathHelper.RADIANS_PER_DEGREE * 45.0f));

//        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

        model.render(matrices);

//        // Front face | red
//        buffer.vertex(transformationMatrix, 0, 0, 0).color(0xFFFF0000);
//        buffer.vertex(transformationMatrix, 0, 1, 0).color(0xFFFF0000);
//        buffer.vertex(transformationMatrix, 1, 1, 0).color(0xFFFF0000);
//
//        buffer.vertex(transformationMatrix, 1, 1, 0).color(0xFFFF0000);
//        buffer.vertex(transformationMatrix, 1, 0, 0).color(0xFFFF0000);
//        buffer.vertex(transformationMatrix, 0, 0, 0).color(0xFFFF0000);
//
//        // Back face | green
//        buffer.vertex(transformationMatrix, 1, 0, 1).color(0xFF00FF00);
//        buffer.vertex(transformationMatrix, 1, 1, 1).color(0xFF00FF00);
//        buffer.vertex(transformationMatrix, 0, 1, 1).color(0xFF00FF00);
//
//        buffer.vertex(transformationMatrix, 0, 1, 1).color(0xFF00FF00);
//        buffer.vertex(transformationMatrix, 0, 0, 1).color(0xFF00FF00);
//        buffer.vertex(transformationMatrix, 1, 0, 1).color(0xFF00FF00);
//
//        // Left face | blue
//        buffer.vertex(transformationMatrix, 0, 0, 1).color(0xFF0000FF);
//        buffer.vertex(transformationMatrix, 0, 1, 1).color(0xFF0000FF);
//        buffer.vertex(transformationMatrix, 0, 1, 0).color(0xFF0000FF);
//
//        buffer.vertex(transformationMatrix, 0, 1, 0).color(0xFF0000FF);
//        buffer.vertex(transformationMatrix, 0, 0, 0).color(0xFF0000FF);
//        buffer.vertex(transformationMatrix, 0, 0, 1).color(0xFF0000FF);
//
//        // Right face | yellow
//        buffer.vertex(transformationMatrix, 1, 0, 0).color(0xFFFFFF00);
//        buffer.vertex(transformationMatrix, 1, 1, 0).color(0xFFFFFF00);
//        buffer.vertex(transformationMatrix, 1, 1, 1).color(0xFFFFFF00);
//
//        buffer.vertex(transformationMatrix, 1, 1, 1).color(0xFFFFFF00);
//        buffer.vertex(transformationMatrix, 1, 0, 1).color(0xFFFFFF00);
//        buffer.vertex(transformationMatrix, 1, 0, 0).color(0xFFFFFF00);
//
//        // Top face | cyan
//        buffer.vertex(transformationMatrix, 0, 1, 0).color(0xFF00FFFF);
//        buffer.vertex(transformationMatrix, 0, 1, 1).color(0xFF00FFFF);
//        buffer.vertex(transformationMatrix, 1, 1, 1).color(0xFF00FFFF);
//
//        buffer.vertex(transformationMatrix, 1, 1, 1).color(0xFF00FFFF);
//        buffer.vertex(transformationMatrix, 1, 1, 0).color(0xFF00FFFF);
//        buffer.vertex(transformationMatrix, 0, 1, 0).color(0xFF00FFFF);
//
//        // Bottom face | magenta
//        buffer.vertex(transformationMatrix, 0, 0, 0).color(0xFFFF00FF);
//        buffer.vertex(transformationMatrix, 1, 0, 0).color(0xFFFF00FF);
//        buffer.vertex(transformationMatrix, 1, 0, 1).color(0xFFFF00FF);
//
//        buffer.vertex(transformationMatrix, 1, 0, 1).color(0xFFFF00FF);
//        buffer.vertex(transformationMatrix, 0, 0, 1).color(0xFFFF00FF);
//        buffer.vertex(transformationMatrix, 0, 0, 0).color(0xFFFF00FF);

//        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
}
