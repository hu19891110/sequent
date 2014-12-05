package org.plantuml.idea.lang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author imamontov
 */
public class PlantUmlTypeFactory extends FileTypeFactory {

    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(
                PlantUmlFileType.PLANTUML_FILE_TYPE,
                PlantUmlFileType.PLANTUML_EXT);
    }
}