package org.plantuml.idea.lang.settings;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import net.sourceforge.plantuml.OptionFlags;
import org.jetbrains.annotations.Nullable;

/**
 * @author Max Gorbunov
 * @author Eugene Steinberg
 */
@State(name = "PlantUmlSettings", storages = {@Storage(file = StoragePathMacros.APP_CONFIG + "/plantuml.cfg")})
public class PlantUmlSettings implements PersistentStateComponent<PlantUmlSettings> {
    private String dotExecutable = null;
    private boolean errorAnnotationEnabled = true;

    public static PlantUmlSettings getInstance() {
        return ServiceManager.getService(PlantUmlSettings.class);
    }

    public String getDotExecutable() {
        return dotExecutable;
    }

    public void setDotExecutable(String dotExecutable) {
        this.dotExecutable = dotExecutable;
        if ("".equals(dotExecutable)) {
            this.dotExecutable = null;
        }
        OptionFlags.getInstance().setDotExecutable(this.dotExecutable);
    }

    public boolean isErrorAnnotationEnabled() {
        return errorAnnotationEnabled;
    }

    public void setErrorAnnotationEnabled(boolean errorAnnotationEnabled) {
        this.errorAnnotationEnabled = errorAnnotationEnabled;
    }

    @Nullable
    @Override
    public PlantUmlSettings getState() {
        return this;
    }

    @Override
    public void loadState(PlantUmlSettings state) {
        XmlSerializerUtil.copyBean(state, this);
        OptionFlags.getInstance().setDotExecutable(dotExecutable);
    }
}
