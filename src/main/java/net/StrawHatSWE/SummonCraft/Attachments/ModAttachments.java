package net.StrawHatSWE.SummonCraft.Attachments;

import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SummonCraft.MOD_ID);

    public static final Supplier<AttachmentType<PlayerSummonData>> SUMMON_DATA =
            ATTACHMENT_TYPES.register("summon_data",
                    () -> AttachmentType.builder(PlayerSummonData::new).build());
}
