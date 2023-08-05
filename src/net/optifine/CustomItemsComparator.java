package net.optifine;

import java.util.Comparator;

import net.minecraft.src.Config;

public class CustomItemsComparator implements Comparator {
	@Override
	public int compare(final Object o1, final Object o2) {
		final CustomItemProperties customitemproperties = (CustomItemProperties) o1;
		final CustomItemProperties customitemproperties1 = (CustomItemProperties) o2;
		return customitemproperties.weight != customitemproperties1.weight ? customitemproperties1.weight - customitemproperties.weight
				: !Config.equals(customitemproperties.basePath, customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name);
	}
}
