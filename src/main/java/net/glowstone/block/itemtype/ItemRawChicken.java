package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ItemRawChicken extends ItemFood {

    public ItemRawChicken() {
        super(2, 1.2f);
    }

    @Override
    public boolean eat(GlowPlayer player, ItemStack item) {
        if (!super.eat(player, item)) return false;

        if (Math.random() < 0.3) {
            player.addPotionEffect(PotionEffectType.HUNGER.createEffect(30 * 20, 1), true);
        }
        return true;
    }
}
