package br.unb.cic0197.copa2026.util;

import br.unb.cic0197.copa2026.model.Jogador;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtil {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass() == Jogador.class && "selecao".equals(f.getName());
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    public static Path dataPath(String fileName) {
        return Paths.get("data", fileName);
    }

    public static void ensureStorageDirectory(Path storagePath) throws IOException {
        Path parent = storagePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
