package org.plantuml.idea.plantuml;

import net.sourceforge.plantuml.*;
import net.sourceforge.plantuml.core.Diagram;
//import org.plantuml.idea.lang.settings.PlantUmlSettings;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eugene Steinberg
 */
public class PlantUml {
    public static final String TESTDOT = "@startuml\ntestdot\n@enduml";
    public static final String UMLSTART = "@start";

    static {
        // Make sure settings are loaded and applied before we start rendering.
  //      PlantUmlSettings.getInstance();
    }

    public enum ImageFormat {
        PNG {
            @Override
            FileFormat getFormat() {
                return FileFormat.PNG;
            }
        },
        SVG {
            @Override
            FileFormat getFormat() {
                return FileFormat.SVG;
            }
        },
        EPS {
            @Override
            FileFormat getFormat() {
                return FileFormat.EPS;
            }
        };

        abstract FileFormat getFormat();
    }

    public static PlantUmlResult render(String source) {
        return render(source, ImageFormat.PNG, 0);
    }

    public static PlantUmlResult render(String source, File baseDir, int page) {
        return render(source, baseDir, ImageFormat.PNG, page);
    }

    /**
     * Renders file with support of plantUML include ange paging features, setting base dir and page for plantUML
     * to provided values
     *
     * @param source  plantUML source code
     * @param baseDir base dir to set
     * @param format  desired image format
     * @param page    page to render
     * @return rendering result
     */
    public static PlantUmlResult render(String source, File baseDir, ImageFormat format, int page) {
        PlantUmlResult render;
        File origDir = FileSystem.getInstance().getCurrentDir();

        if (baseDir != null)
            FileSystem.getInstance().setCurrentDir(baseDir);

        try {
            render = render(source, format, page);
        } finally {
            if (origDir != null)
                FileSystem.getInstance().setCurrentDir(origDir);
        }

        return render;
    }


    /**
     * Renders source code and saves diagram images to files according to provided naming scheme
     * and image format.
     *
     * @param source         source code to be rendered
     * @param baseDir        base dir to set for "include" functionality
     * @param format         image format
     * @param fileName       fileName to use with first file
     * @param fileNameFormat file naming scheme for further files
     * @throws IOException in case of rendering or saving fails
     */
    public static void renderAndSave(String source, File baseDir, ImageFormat format, String fileName, String fileNameFormat)
            throws IOException {
        File origDir = FileSystem.getInstance().getCurrentDir();
        FileOutputStream outputStream = null;
        if (baseDir != null)
            FileSystem.getInstance().setCurrentDir(baseDir);
        try {
            SourceStringReader reader = new SourceStringReader(source);

            List<BlockUml> blocks = reader.getBlocks();
            int imageСounter = 0;
            for (BlockUml block : blocks) {
                Diagram diagram = block.getDiagram();
                int pages = diagram.getNbImages();
                for (int page = 0; page < pages; ++page) {
                    String fName = imageСounter == 0 ? fileName : String.format(fileNameFormat, imageСounter);
                    outputStream = new FileOutputStream(fName);
                    reader.generateImage(outputStream, imageСounter++, new FileFormatOption(format.getFormat()));
                    outputStream.close();
                }
            }
        } finally {
            if (outputStream != null)
                outputStream.close();
            if (origDir != null)
                FileSystem.getInstance().setCurrentDir(origDir);
        }

    }

    /**
     * Renders given source code into diagram
     *
     * @param source plantUml source code
     * @param format desired image format
     * @return rendering result
     */

    public static PlantUmlResult render(String source, ImageFormat format, int page) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String desc = null;
        String error = null;
        int pages = 1;

        try {
            // image generation.
            SourceStringReader reader = new SourceStringReader(source);

            List<BlockUml> blocks = reader.getBlocks();
            if (blocks.size() > 0) {
                Diagram system = blocks.get(0).getDiagram();
                if (system != null) {
                    pages = system.getNbImages();
                }
            }
            // Write the image to "os"
            desc = reader.generateImage(os, page, new FileFormatOption(format.getFormat()));
        } catch (Throwable e) {
            error = e.getMessage();
        }
        return new PlantUmlResult(os.toByteArray(), desc, error, pages);
    }

    public static final String SOURCE_TYPE_PATTERN = "uml|dot|jcckit|ditaa|salt";
    private static Pattern sourcePattern =
            Pattern.compile("(?:(@start(?:" + SOURCE_TYPE_PATTERN + ")(?s).*?(?:@end(?:" + SOURCE_TYPE_PATTERN + ")|$))(?s).*?)+");

    /**
     * Extracts all
     * @param text
     * @return
     */
    public static Map<Integer, String> extractSources(String text) {
        LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();

        if (text.contains(UMLSTART)) {

            Matcher matcher = sourcePattern.matcher(text);

            while (matcher.find()) {
                result.put(matcher.start(), matcher.group());
            }
        }
        return result;
    }

    /**
     * Extracts plantUML diagram source code from the given string starting from given offset
     * <ul>
     * <li>Relies on having @startuml and @enduml tags (puml tags) wrapping plantuml sourcePattern code</li>
     * <li>If offset happens to be inside puml tags, returns corresponding sourcePattern code.</li>
     * <li>If offset happens to be outside puml tags, returns empty string </li>
     * </ul>
     *
     * @param text   source code containing multiple plantuml sources
     * @param offset offset in the text from which plantuml sourcePattern is extracted
     * @return extracted plantUml code, including @startuml and @enduml tags or empty string if
     * no valid sourcePattern code was found
     */
    public static String extractSource(String text, int offset) {
        Map<Integer, String> sources = extractSources(text);
        String source = "";
        for (Map.Entry<Integer, String> sourceData : sources.entrySet()) {
            Integer sourceOffset = sourceData.getKey();
            if (sourceOffset <= offset && offset <= sourceOffset + sourceData.getValue().length()) {
                source = stripComments(sourceData.getValue());
                break;
            }
        }
        return source;
    }

    private static Pattern sourceCommentPattern =
            Pattern.compile("^\\s*\\*\\s", Pattern.MULTILINE);

    private static String stripComments(String source) {
        Matcher matcher = sourceCommentPattern.matcher(source);
        return matcher.replaceAll("");
    }

}
