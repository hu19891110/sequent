package com.applego.sequent.parser.psi;

import com.applego.sequent.lang.PlantUmlLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by pin on 8.1.15.
 */
public class SequentTokenType extends IElementType {
    public SequentTokenType(@NotNull @NonNls String debugName) {
        super(debugName, PlantUmlLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "SimpleTokenType." + super.toString();
    }
}