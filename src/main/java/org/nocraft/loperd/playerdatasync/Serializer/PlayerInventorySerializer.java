package org.nocraft.loperd.playerdatasync.Serializer;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.nocraft.loperd.playerdatasync.Inventory.SavedPlayerInventory;

import java.io.IOException;

public class PlayerInventorySerializer {

    private final Bs64InventorySerializer baseSerializer;

    public PlayerInventorySerializer(Bs64InventorySerializer serializer) {
        baseSerializer = serializer;
    }

    public JsonObject serializeInventory(SavedPlayerInventory inventory) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        String content = baseSerializer.serializeItemStacks(inventory.getStorage());
        String armor = baseSerializer.serializeItemStacks(inventory.getArmor());

        JsonObject result = new JsonObject();

        if (content != null && armor != null) {
            result.add("content", new JsonPrimitive(content));
            result.add("armor", new JsonPrimitive(armor));

            return result;
        }

        throw new IllegalStateException("Can not understand type of serialized value");
    }

    public PlayerInventory deserializeInventory(JsonObject serializedInventory) throws IOException {
        String contentsValue = serializedInventory.get("content").getAsString();
        String armorValue = serializedInventory.get("armor").getAsString();

        Inventory content = baseSerializer.deserializeInventory(contentsValue);
        ItemStack[] armor = baseSerializer.deserializeItemStack(armorValue);

        PlayerInventory inventory = (PlayerInventory) Bukkit.getServer().createInventory(null, InventoryType.PLAYER);

        inventory.setArmorContents(armor);
        inventory.setContents(content.getContents());

        return inventory;
    }

    public String serializeItemStack(ItemStack[] enderChest) {
        return baseSerializer.serializeItemStacks(enderChest);
    }
}
