package net.sorenon.oh_nuts;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class DoughnutItem extends Item {

	public static final FoodProperties FOOD = new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build();

	public DoughnutItem() {
		super(new Properties().food(FOOD));
	}
}
