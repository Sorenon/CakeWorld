package net.sorenon.cake_world.compat;

import org.objectweb.asm.tree.ClassNode;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class SodiumPlugin implements IMixinConfigPlugin {
	private boolean sodiumInstalled = false;

	@Override
	public void onLoad(String mixinPackage) {
		sodiumInstalled = QuiltLoader.isModLoaded("sodium");
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return sodiumInstalled;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName,
						 ClassNode targetClass,
						 String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName,
						  ClassNode targetClass,
						  String mixinClassName,
						  IMixinInfo mixinInfo) {

	}
}
