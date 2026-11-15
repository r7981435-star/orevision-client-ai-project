package dev.orevision;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class OreVisionClient implements ClientModInitializer {
    private static boolean enabled = false;
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = KeyBinding.Category.create(
                Identifier.of("orevision", "main")
        );

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.orevision.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                enabled = !enabled;
                refreshWorld(client);
            }
        });
    }

    public static boolean isEnabled() {
        return enabled;
    }

    private static void refreshWorld(MinecraftClient client) {
        if (client.worldRenderer != null) {
            client.worldRenderer.reload();
        }
    }
}
