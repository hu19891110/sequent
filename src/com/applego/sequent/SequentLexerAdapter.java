package com.applego.sequent;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * Created by pin on 9.1.15.
 */
public class SequentLexerAdapter extends FlexAdapter {
    public SequentLexerAdapter() {
        super(new _SequentLexer((Reader) null));
    }
}

