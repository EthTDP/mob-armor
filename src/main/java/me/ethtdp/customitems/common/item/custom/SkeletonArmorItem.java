package me.ethtdp.customitems.common.item.custom;

import me.ethtdp.customitems.common.item.ModArmorMaterials;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class SkeletonArmorItem extends GeoArmorItem implements IAnimatable{

        private AnimationFactory factory = new AnimationFactory(this);

        public static boolean isShooting;
        public static boolean isKeepingBow = false;

        public SkeletonArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
            super(material, slot, settings);
        }

    @Override
        public void registerControllers(AnimationData data) {
            data.addAnimationController(new AnimationController<SkeletonArmorItem>(this, "controller",
                    20, this::predicate));
        }

        @Override
        public AnimationFactory getFactory() {
            return this.factory;
        }

        private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
            return PlayState.CONTINUE;
        }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
    }

        private static boolean hasFullSuitOfArmorOn(Player player) {
            ItemStack boots = player.getInventory().getArmor(0);
            ItemStack leggings = player.getInventory().getArmor(1);
            ItemStack breastplate = player.getInventory().getArmor(2);
            ItemStack helmet = player.getInventory().getArmor(3);

            return !helmet.isEmpty() && !breastplate.isEmpty()
                    && !leggings.isEmpty() && !boots.isEmpty();
        }

        public static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
            for (ItemStack armorStack : player.getInventory().armor) {
                if (!(armorStack.getItem() instanceof ArmorItem)) {
                    return false;
                }
            }

            if (hasFullSuitOfArmorOn(player)) {
                ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
                ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
                ArmorItem breastplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
                ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

                return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                        leggings.getMaterial() == material && boots.getMaterial() == material;
            }
            return false;
        }
    }
