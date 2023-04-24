package firemuffin303.muffinssongkran.registry;

import firemuffin303.muffinssongkran.Muffinssongkran;
import firemuffin303.muffinssongkran.common.item.WaterGunItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> WATER_GUNS = new ArrayList<>();

    public static final Item WHITE_WATER_GUN = registerWaterGun("white_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item ORANGE_WATER_GUN = registerWaterGun("orange_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item MAGENTA_WATER_GUN = registerWaterGun("magenta_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item LIGHT_BLUE_WATER_GUN = registerWaterGun("light_blue_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item YELLOW_WATER_GUN = registerWaterGun("yellow_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item LIME_WATER_GUN = registerWaterGun("lime_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item PINK_WATER_GUN = registerWaterGun("pink_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item GRAY_WATER_GUN = registerWaterGun("gray_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item LIGHT_GRAY_WATER_GUN = registerWaterGun("light_gray_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item CYAN_WATER_GUN = registerWaterGun("cyan_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item PURPLE_WATER_GUN = registerWaterGun("purple_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item BLUE_WATER_GUN = registerWaterGun("blue_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item BROWN_WATER_GUN = registerWaterGun("brown_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item GREEN_WATER_GUN = registerWaterGun("green_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item RED_WATER_GUN = registerWaterGun("red_water_gun",new WaterGunItem(new Item.Settings()));
    public static final Item BLACK_WATER_GUN = registerWaterGun("black_water_gun",new WaterGunItem(new Item.Settings()));

    public static final Item THAI_GARLAND = register("thai_garland",new Item(new Item.Settings()));


    public static void init(){

    }

    private static Item register(String id,Item item){
        Item item2 = Registry.register(Registries.ITEM,new Identifier(Muffinssongkran.MODID,id),item);
        ItemGroupEvents.modifyEntriesEvent(Muffinssongkran.MUFFINSSONGKRAN).register(entries -> entries.add(item2));
        return item2;
    }

    public static Item registerWaterGun(String id,Item item){
        WATER_GUNS.add(item);
        return register(id,item);
    }

}
