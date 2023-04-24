package firemuffin303.muffinssongkran.registry;

import firemuffin303.muffinssongkran.Muffinssongkran;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    public static final SoundEvent ITEM_WATER_GUN_FILLING = register("item.water_gun.filling");
    public static final SoundEvent ITEM_WATER_GUN_LOADED = register("item.water_gun.loaded");

    public static void init(){}


    private static SoundEvent register(String id) {
        return (SoundEvent) Registry.register(Registries.SOUND_EVENT, new Identifier(Muffinssongkran.MODID,id), SoundEvent.of(new Identifier(Muffinssongkran.MODID,id)));
    }
}
