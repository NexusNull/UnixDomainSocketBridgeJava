package de.rub.nds;

import de.learnlib.api.query.Query;
import net.automatalib.words.Word;

public class HTMLTokenizerQuery extends Query<Character, Word<Character>> {
    Word<Character> prefix;
    Word<Character> suffix;

    HTMLTokenizerQuery(Word<Character> prefix, Word<Character> suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public void answer(Word<Character> output) {

    }

    @Override
    public Word<Character> getPrefix() {
        return prefix;
    }

    @Override
    public Word<Character> getSuffix() {
        return suffix;
    }
}
