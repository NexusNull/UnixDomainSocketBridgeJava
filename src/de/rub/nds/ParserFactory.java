package de.rub.nds;

import net.automatalib.words.Word;

public class ParserFactory {
    UnixSocketServer server;
    public ParserFactory() {
        new InstanceManager("/home/nexus/remote/albot/home/pwellerhaus/chromium/src/out/Default/hello_world", 1);
        UnixSocketServer server = new UnixSocketServer("/tmp/asd");
    }

    public HTMLTokenizer<Character, Word<Character>> getHTMLTokenizer() {
        return new HTMLTokenizer<Character, Word<Character>>(this.server.getSocket(0));
    }
}
