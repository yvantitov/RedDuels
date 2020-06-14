package com.github.radagastthered.redduels.redduels.object;

import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

public class DuelType {
    public String name;
    public Permission permission = null;
    public String worldName;
    public String description;
    public int[] player1SpawnLocation;
    public int[] player2SpawnLocation;
    public ItemStack[] items;
    public ItemStack[] armor;
}
