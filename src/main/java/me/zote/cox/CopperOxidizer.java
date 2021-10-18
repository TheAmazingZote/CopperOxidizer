package me.zote.cox;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class CopperOxidizer extends JavaPlugin implements Listener {

    private static final List<Material> COPPER_BLOCKS = List.of(Material.COPPER_BLOCK, Material.EXPOSED_COPPER, Material.WEATHERED_COPPER, Material.OXIDIZED_COPPER);
    private static final List<Material> CUT_COPPER_BLOCKS = List.of(Material.CUT_COPPER, Material.EXPOSED_CUT_COPPER, Material.WEATHERED_CUT_COPPER, Material.OXIDIZED_CUT_COPPER);
    private static final List<Material> SLAB_COPPER_BLOCKS = List.of(Material.CUT_COPPER_SLAB, Material.EXPOSED_CUT_COPPER_SLAB, Material.WEATHERED_CUT_COPPER_SLAB, Material.OXIDIZED_CUT_COPPER_SLAB);
    private static final List<Material> STAIR_COPPER_BLOCKS = List.of(Material.CUT_COPPER_STAIRS, Material.EXPOSED_CUT_COPPER_STAIRS, Material.WEATHERED_CUT_COPPER_STAIRS, Material.OXIDIZED_CUT_COPPER_STAIRS);

    // WAX
    private static final List<Material> WAX_COPPER_BLOCKS = List.of(Material.WAXED_COPPER_BLOCK, Material.WAXED_EXPOSED_COPPER, Material.WAXED_WEATHERED_COPPER, Material.WAXED_OXIDIZED_COPPER);
    private static final List<Material> WAX_CUT_COPPER_BLOCKS = List.of(Material.WAXED_CUT_COPPER, Material.WAXED_EXPOSED_CUT_COPPER, Material.WAXED_WEATHERED_CUT_COPPER, Material.WAXED_OXIDIZED_CUT_COPPER);
    private static final List<Material> WAX_SLAB_COPPER_BLOCKS = List.of(Material.WAXED_CUT_COPPER_SLAB, Material.WAXED_EXPOSED_CUT_COPPER_SLAB, Material.WAXED_WEATHERED_CUT_COPPER_SLAB, Material.WAXED_OXIDIZED_CUT_COPPER_SLAB);
    private static final List<Material> WAX_STAIR_COPPER_BLOCKS = List.of(Material.WAXED_CUT_COPPER_STAIRS, Material.WAXED_EXPOSED_CUT_COPPER_STAIRS, Material.WAXED_WEATHERED_CUT_COPPER_STAIRS, Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block clicked = event.getClickedBlock();
        EquipmentSlot slot = event.getHand();

        if (player.isSneaking())
            return;

        if (clicked == null)
            return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (slot != EquipmentSlot.HAND)
            return;

        ItemStack hand = event.getItem();

        if (hand == null || hand.getType() != Material.WATER_BUCKET)
            return;

        List<Material> blocks = getList(clicked.getType());

        if (blocks == null)
            return;

        int index = blocks.indexOf(clicked.getType());

        if (index == 3)
            return;

        Material next = blocks.get(index + 1);
        Location location = player.getLocation();

        if (player.getGameMode() != GameMode.CREATIVE)
            player.getInventory().setItem(slot, new ItemStack(Material.BUCKET));
        
        location.getWorld().spawnParticle(Particle.SCRAPE, clicked.getLocation().clone().add(0.5, 0.5, 0.5), 40, 0.25, 0.25, 0.25);
        player.playSound(location, Sound.ITEM_BUCKET_EMPTY, 1F, 1F);

        BlockData currentData = clicked.getBlockData();
        String stringData = currentData.getAsString();

        int dataIndex = stringData.indexOf('[');

        if (dataIndex != -1)
            stringData = stringData.substring(dataIndex);

        clicked.setType(next, false);

        if (dataIndex != -1) {
            BlockData newData = next.createBlockData(stringData);
            clicked.setBlockData(newData, false);
        }

    }

    private List<Material> getList(Material material) {
        if (COPPER_BLOCKS.contains(material))
            return COPPER_BLOCKS;

        if (CUT_COPPER_BLOCKS.contains(material))
            return CUT_COPPER_BLOCKS;

        if (SLAB_COPPER_BLOCKS.contains(material))
            return SLAB_COPPER_BLOCKS;

        if (STAIR_COPPER_BLOCKS.contains(material))
            return STAIR_COPPER_BLOCKS;

        if (WAX_COPPER_BLOCKS.contains(material))
            return WAX_COPPER_BLOCKS;

        if (WAX_CUT_COPPER_BLOCKS.contains(material))
            return WAX_CUT_COPPER_BLOCKS;

        if (WAX_SLAB_COPPER_BLOCKS.contains(material))
            return WAX_SLAB_COPPER_BLOCKS;

        if (WAX_STAIR_COPPER_BLOCKS.contains(material))
            return WAX_STAIR_COPPER_BLOCKS;

        return null;
    }

}
