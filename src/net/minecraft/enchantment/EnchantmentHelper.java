package net.minecraft.enchantment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;
import pisi.unitedmeows.minecraft.Settings;

public class EnchantmentHelper {
	/** Is the random seed of enchantment effects. */
	private static final Random enchantmentRand = new Random();
	/**
	 * Used to calculate the extra armor of enchantments on armors equipped on player.
	 */
	private static final ModifierDamage enchantmentModifierDamage = new ModifierDamage();
	/**
	 * Used to calculate the (magic) extra damage done by enchantments on current equipped item of
	 * player.
	 */
	private static final ModifierLiving enchantmentModifierLiving = new ModifierLiving();
	private static final HurtIterator ENCHANTMENT_ITERATOR_HURT = new HurtIterator();
	private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator();

	/**
	 * Returns the level of enchantment on the ItemStack passed.
	 */
	public static int getEnchantmentLevel(final int enchID, final ItemStack stack) {
		if (stack == null) return 0;
		else {
			final NBTTagList nbttaglist = stack.getEnchantmentTagList();
			if (nbttaglist == null) return 0;
			else {
				for (int i = 0; i < nbttaglist.tagCount(); ++i) { final int j = nbttaglist.getCompoundTagAt(i).getShort("id"); final int k = nbttaglist.getCompoundTagAt(i).getShort("lvl"); if (j == enchID) return k; }
				return 0;
			}
		}
	}

	public static Map<Integer, Integer> getEnchantments(final ItemStack stack) {
		final Map<Integer, Integer> map = Maps.<Integer, Integer>newLinkedHashMap();
		final NBTTagList nbttaglist = stack.getItem() == Items.enchanted_book ? Items.enchanted_book.getEnchantments(stack) : stack.getEnchantmentTagList();
		if (nbttaglist != null) for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			final int j = nbttaglist.getCompoundTagAt(i).getShort("id");
			final int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
			map.put(j, k);
		}
		return map;
	}

	/**
	 * Set the enchantments for the specified stack.
	 */
	public static void setEnchantments(final Map<Integer, Integer> enchMap, final ItemStack stack) {
		final NBTTagList nbttaglist = new NBTTagList();
		final Iterator iterator = enchMap.keySet().iterator();
		while (iterator.hasNext()) {
			final int i = ((Integer) iterator.next());
			final Enchantment enchantment = Enchantment.getEnchantmentById(i);
			if (enchantment != null) {
				final NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("id", (short) i);
				nbttagcompound.setShort("lvl", enchMap.get(Integer.valueOf(i)).shortValue());
				nbttaglist.appendTag(nbttagcompound);
				if (stack.getItem() == Items.enchanted_book) Items.enchanted_book.addEnchantment(stack, new EnchantmentData(enchantment, enchMap.get(Integer.valueOf(i))));
			}
		}
		if (nbttaglist.tagCount() > 0) {
			if (stack.getItem() != Items.enchanted_book) stack.setTagInfo("ench", nbttaglist);
		} else if (stack.hasTagCompound()) stack.getTagCompound().removeTag("ench");
	}

	/**
	 * Returns the biggest level of the enchantment on the array of ItemStack passed.
	 */
	public static int getMaxEnchantmentLevel(final int enchID, final ItemStack[] stacks) {
		if (stacks == null) return 0;
		else {
			int i = 0;
			for (final ItemStack itemstack : stacks) { final int j = getEnchantmentLevel(enchID, itemstack); if (j > i) i = j;
			}
			return i;
		}
	}

	/**
	 * Executes the enchantment modifier on the ItemStack passed.
	 */
	private static void applyEnchantmentModifier(final IModifier modifier, final ItemStack stack) {
		if (stack != null) {
			final NBTTagList nbttaglist = stack.getEnchantmentTagList();
			if (nbttaglist != null) for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				final int j = nbttaglist.getCompoundTagAt(i).getShort("id");
				final int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
				if (Enchantment.getEnchantmentById(j) != null) modifier.calculateModifier(Enchantment.getEnchantmentById(j), k);
			}
		}
	}

	/**
	 * Executes the enchantment modifier on the array of ItemStack passed.
	 */
	private static void applyEnchantmentModifierArray(final IModifier modifier, final ItemStack[] stacks) { for (final ItemStack itemstack : stacks) applyEnchantmentModifier(modifier, itemstack); }

	/**
	 * Returns the modifier of protection enchantments on armors equipped on player.
	 */
	public static int getEnchantmentModifierDamage(final ItemStack[] stacks, final DamageSource source) {
		if (Settings.ENCHANTMENT_HELPER_MEMORY_LEAK_FIX) {
			final ModifierDamage enchantmentModifierDamage = new ModifierDamage();
			enchantmentModifierDamage.damageModifier = 0;
			enchantmentModifierDamage.source = source;
			applyEnchantmentModifierArray(enchantmentModifierDamage, stacks);
			return enchantmentModifierDamage.damageModifier;
		} else {
			enchantmentModifierDamage.damageModifier = 0;
			enchantmentModifierDamage.source = source;
			applyEnchantmentModifierArray(enchantmentModifierDamage, stacks);
			if (enchantmentModifierDamage.damageModifier > 25) enchantmentModifierDamage.damageModifier = 25;
			else if (enchantmentModifierDamage.damageModifier < 0) enchantmentModifierDamage.damageModifier = 0;
			return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
		}
	}

	public static float getModifierForCreature(final ItemStack p_152377_0_, final EnumCreatureAttribute p_152377_1_) {
		if (Settings.ENCHANTMENT_HELPER_MEMORY_LEAK_FIX) {
			final ModifierLiving enchantmentModifierLiving = new ModifierLiving();
			enchantmentModifierLiving.livingModifier = 0.0F;
			enchantmentModifierLiving.entityLiving = p_152377_1_;
			applyEnchantmentModifier(enchantmentModifierLiving, p_152377_0_);
			return enchantmentModifierLiving.livingModifier;
		} else {
			enchantmentModifierLiving.livingModifier = 0.0F;
			enchantmentModifierLiving.entityLiving = p_152377_1_;
			applyEnchantmentModifier(enchantmentModifierLiving, p_152377_0_);
			return enchantmentModifierLiving.livingModifier;
		}
	}

	public static void applyThornEnchantments(final EntityLivingBase p_151384_0_, final Entity p_151384_1_) {
		if (Settings.ENCHANTMENT_HELPER_MEMORY_LEAK_FIX) {
			final HurtIterator enchantmentIteratorHurt = new HurtIterator();
			enchantmentIteratorHurt.attacker = p_151384_1_;
			enchantmentIteratorHurt.user = p_151384_0_;
			if (p_151384_0_ != null) applyEnchantmentModifierArray(enchantmentIteratorHurt, p_151384_0_.getInventory());
			if (p_151384_1_ instanceof EntityPlayer) applyEnchantmentModifier(enchantmentIteratorHurt, p_151384_0_.getHeldItem());
		} else {
			ENCHANTMENT_ITERATOR_HURT.attacker = p_151384_1_;
			ENCHANTMENT_ITERATOR_HURT.user = p_151384_0_;
			if (p_151384_0_ != null) applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getInventory());
			if (p_151384_1_ instanceof EntityPlayer) applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getHeldItem());
		}
	}

	public static void applyArthropodEnchantments(final EntityLivingBase p_151385_0_, final Entity p_151385_1_) {
		if (Settings.ENCHANTMENT_HELPER_MEMORY_LEAK_FIX) {
			final DamageIterator enchantmentIteratorDamage = new DamageIterator();
			enchantmentIteratorDamage.user = p_151385_0_;
			enchantmentIteratorDamage.target = p_151385_1_;
			if (p_151385_0_ != null) applyEnchantmentModifierArray(enchantmentIteratorDamage, p_151385_0_.getInventory());
			if (p_151385_0_ instanceof EntityPlayer) applyEnchantmentModifier(enchantmentIteratorDamage, p_151385_0_.getHeldItem());
		} else {
			ENCHANTMENT_ITERATOR_DAMAGE.user = p_151385_0_;
			ENCHANTMENT_ITERATOR_DAMAGE.target = p_151385_1_;
			if (p_151385_0_ != null) applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getInventory());
			if (p_151385_0_ instanceof EntityPlayer) applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getHeldItem());
		}
	}

	/**
	 * Returns the Knockback modifier of the enchantment on the players held item.
	 */
	public static int getKnockbackModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.knockback.effectId, player.getHeldItem()); }

	/**
	 * Returns the fire aspect modifier of the players held item.
	 */
	public static int getFireAspectModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.fireAspect.effectId, player.getHeldItem()); }

	/**
	 * Returns the 'Water Breathing' modifier of enchantments on player equipped armors.
	 */
	public static int getRespiration(final Entity player) { return getMaxEnchantmentLevel(Enchantment.respiration.effectId, player.getInventory()); }

	/**
	 * Returns the level of the Depth Strider enchantment.
	 */
	public static int getDepthStriderModifier(final Entity player) { return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, player.getInventory()); }

	/**
	 * Return the extra efficiency of tools based on enchantments on equipped player item.
	 */
	public static int getEfficiencyModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.efficiency.effectId, player.getHeldItem()); }

	/**
	 * Returns the silk touch status of enchantments on current equipped item of player.
	 */
	public static boolean getSilkTouchModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.silkTouch.effectId, player.getHeldItem()) > 0; }

	/**
	 * Returns the fortune enchantment modifier of the current equipped item of player.
	 */
	public static int getFortuneModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.fortune.effectId, player.getHeldItem()); }

	/**
	 * Returns the level of the 'Luck Of The Sea' enchantment.
	 */
	public static int getLuckOfSeaModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, player.getHeldItem()); }

	/**
	 * Returns the level of the 'Lure' enchantment on the players held item.
	 */
	public static int getLureModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.lure.effectId, player.getHeldItem()); }

	/**
	 * Returns the looting enchantment modifier of the current equipped item of player.
	 */
	public static int getLootingModifier(final EntityLivingBase player) { return getEnchantmentLevel(Enchantment.looting.effectId, player.getHeldItem()); }

	/**
	 * Returns the aqua affinity status of enchantments on current equipped item of player.
	 */
	public static boolean getAquaAffinityModifier(final EntityLivingBase player) { return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, player.getInventory()) > 0; }

	public static ItemStack getEnchantedItem(final Enchantment p_92099_0_, final EntityLivingBase p_92099_1_) {
		for (final ItemStack itemstack : p_92099_1_.getInventory()) if (itemstack != null && getEnchantmentLevel(p_92099_0_.effectId, itemstack) > 0) return itemstack;
		return null;
	}

	/**
	 * Returns the enchantability of itemstack, using a separate calculation for each enchantNum (0, 1
	 * or 2), cutting to the max enchantability power of the table, which is locked to a max of 15.
	 */
	public static int calcItemStackEnchantability(final Random rand, final int enchantNum, int power, final ItemStack stack) {
		final Item item = stack.getItem();
		final int i = item.getItemEnchantability();
		if (i <= 0) return 0;
		else {
			if (power > 15) power = 15;
			final int j = rand.nextInt(8) + 1 + (power >> 1) + rand.nextInt(power + 1);
			return enchantNum == 0 ? Math.max(j / 3, 1) : (enchantNum == 1 ? j * 2 / 3 + 1 : Math.max(j, power * 2));
		}
	}

	/**
	 * Adds a random enchantment to the specified item. Args: random, itemStack, enchantabilityLevel
	 */
	public static ItemStack addRandomEnchantment(final Random p_77504_0_, final ItemStack p_77504_1_, final int p_77504_2_) {
		final List<EnchantmentData> list = buildEnchantmentList(p_77504_0_, p_77504_1_, p_77504_2_);
		final boolean flag = p_77504_1_.getItem() == Items.book;
		if (flag) p_77504_1_.setItem(Items.enchanted_book);
		if (list != null) for (final EnchantmentData enchantmentdata : list) if (flag) Items.enchanted_book.addEnchantment(p_77504_1_, enchantmentdata);
		else p_77504_1_.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
		return p_77504_1_;
	}

	public static List<EnchantmentData> buildEnchantmentList(final Random randomIn, final ItemStack itemStackIn, final int p_77513_2_) {
		final Item item = itemStackIn.getItem();
		int i = item.getItemEnchantability();
		if (i <= 0) return null;
		else {
			i = i / 2;
			i = 1 + randomIn.nextInt((i >> 1) + 1) + randomIn.nextInt((i >> 1) + 1);
			final int j = i + p_77513_2_;
			final float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
			int k = (int) (j * (1.0F + f) + 0.5F);
			if (k < 1) k = 1;
			List<EnchantmentData> list = null;
			final Map<Integer, EnchantmentData> map = mapEnchantmentData(k, itemStackIn);
			if (map != null && !map.isEmpty()) {
				final EnchantmentData enchantmentdata = WeightedRandom.getRandomItem(randomIn, map.values());
				if (enchantmentdata != null) {
					list = Lists.<EnchantmentData>newArrayList();
					list.add(enchantmentdata);
					for (int l = k; randomIn.nextInt(50) <= l; l >>= 1) {
						final Iterator<Integer> iterator = map.keySet().iterator();
						while (iterator.hasNext()) {
							final Integer integer = iterator.next();
							boolean flag = true;
							for (final EnchantmentData enchantmentdata1 : list) if (!enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(integer))) {
								flag = false;
								break;
							}
							if (!flag) iterator.remove();
						}
						if (!map.isEmpty()) {
							final EnchantmentData enchantmentdata2 = WeightedRandom.getRandomItem(randomIn, map.values());
							list.add(enchantmentdata2);
						}
					}
				}
			}
			return list;
		}
	}

	public static Map<Integer, EnchantmentData> mapEnchantmentData(final int p_77505_0_, final ItemStack p_77505_1_) {
		final Item item = p_77505_1_.getItem();
		Map<Integer, EnchantmentData> map = null;
		final boolean flag = p_77505_1_.getItem() == Items.book;
		for (final Enchantment enchantment : Enchantment.enchantmentsBookList) if (enchantment != null && (enchantment.type.canEnchantItem(item) || flag)) for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) if (p_77505_0_ >= enchantment.getMinEnchantability(i) && p_77505_0_ <= enchantment.getMaxEnchantability(i)) {
			if (map == null) map = Maps.<Integer, EnchantmentData>newHashMap();
			map.put(enchantment.effectId, new EnchantmentData(enchantment, i));
		}
		return map;
	}

	static final class DamageIterator implements IModifier {
		public EntityLivingBase user;
		public Entity target;

		private DamageIterator()
		{}

		@Override
		public void calculateModifier(final Enchantment enchantmentIn, final int enchantmentLevel) { enchantmentIn.onEntityDamaged(this.user, this.target, enchantmentLevel); }
	}

	static final class HurtIterator implements IModifier {
		public EntityLivingBase user;
		public Entity attacker;

		private HurtIterator()
		{}

		@Override
		public void calculateModifier(final Enchantment enchantmentIn, final int enchantmentLevel) { enchantmentIn.onUserHurt(this.user, this.attacker, enchantmentLevel); }
	}

	interface IModifier { void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel); }

	static final class ModifierDamage implements IModifier {
		public int damageModifier;
		public DamageSource source;

		private ModifierDamage()
		{}

		@Override
		public void calculateModifier(final Enchantment enchantmentIn, final int enchantmentLevel) { this.damageModifier += enchantmentIn.calcModifierDamage(enchantmentLevel, this.source); }
	}

	static final class ModifierLiving implements IModifier {
		public float livingModifier;
		public EnumCreatureAttribute entityLiving;

		private ModifierLiving()
		{}

		@Override
		public void calculateModifier(final Enchantment enchantmentIn, final int enchantmentLevel) { this.livingModifier += enchantmentIn.calcDamageByCreature(enchantmentLevel, this.entityLiving); }
	}
}
