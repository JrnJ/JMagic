package com.jeroenj.attachments;


import com.jeroenj.JMagic;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class JMagicAttachmentTypes {
    public static final AttachmentType<JMagicManaAttachment> PLAYER_MANA = AttachmentRegistry.create(JMagic.id("mana"),
            builder -> builder
                    .initializer(() -> new JMagicManaAttachment(0))
                    .persistent(JMagicManaAttachment.CODEC)
                    .syncWith(
                            JMagicManaAttachment.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static void initialize() {

    }
}
