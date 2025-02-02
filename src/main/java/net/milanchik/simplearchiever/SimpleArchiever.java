package net.milanchik.simplearchiever;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;

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
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("archive").then(CommandManager.argument("pathToFile", StringArgumentType.string()).then(CommandManager.argument("pathToZIPFile", StringArgumentType.string()) // Второй аргумент
                    .requires(source -> true).executes(context -> {
                        String pathFile = StringArgumentType.getString(context, "pathToFile");
                        String pathZIP = StringArgumentType.getString(context, "pathToZIPFile");
                        ServerCommandSource source = context.getSource();
                        try {
                            Archiver.compressArchive(pathFile, pathZIP);
                            source.getEntity().sendMessage(Text.translatable("successmessage"));
                            return Command.SINGLE_SUCCESS;
                        } catch (IOException e) {
                            source.getEntity().sendMessage(Text.of(Text.translatable("errormessage") + e.getMessage()));
                            return 0;
                        }
                    }))));
        });
    }
}