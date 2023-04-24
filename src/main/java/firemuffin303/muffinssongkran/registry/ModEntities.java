package firemuffin303.muffinssongkran.registry;

import firemuffin303.muffinssongkran.Muffinssongkran;
import firemuffin303.muffinssongkran.common.entity.FluidSprayEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModEntities {
    public static final EntityType<FluidSprayEntity> FLUID_PROJECTILE = register("fluid_spray",EntityType.Builder.create((EntityType<FluidSprayEntity> entityType, World world) -> new FluidSprayEntity(world), SpawnGroup.MISC).setDimensions(0.5F,0.5F).maxTrackingRange(8));

    public static void init(){}

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> entityType){
        return Registry.register(Registries.ENTITY_TYPE,new Identifier(Muffinssongkran.MODID,id),entityType.build(id));

    }
}
