package net.milanchik.simplearchiever;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SimpleArchiever implements ModInitializer {
    public static final String MOD_ID = "simplearchiever";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("archive")
                    .then(ClientCommandManager.argument("pathToFile", StringArgumentType.string())
                            .then(ClientCommandManager.argument("pathToZIPFile", StringArgumentType.string())
                                    .requires(source -> true).executes(context -> {
                                        String pathFile = StringArgumentType.getString(context, "pathToFile");
                                        String pathZIP = StringArgumentType.getString(context, "pathToZIPFile");
                                        try {
                                            Archiver.compressArchive(pathFile, pathZIP);
                                            context.getSource().getEntity().sendMessage(Text.translatable("successmessage"));
                                            return Command.SINGLE_SUCCESS;
                                        } catch (IOException e) {
                                            context.getSource().getEntity().sendMessage(Text.of(Text.translatable("errormessage") + e.getMessage()));
                                            return 0;
                                        }
                                    }))));
        });
    }
}