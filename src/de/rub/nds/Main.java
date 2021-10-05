package de.rub.nds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.learnlib.algorithms.dhc.mealy.MealyDHC;
import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;
import net.automatalib.words.impl.ListAlphabet;

public class Main {

    public void run() throws InterruptedException, IOException {
        UnixSocketServer server = new UnixSocketServer("/tmp/asd");
        new InstanceManager("/home/nexus/remote/nds/home/pwellerhaus/chromium/src/out/Default/hello_world", 0);

        UnixSocket socket = null;
        while (socket == null) {
            socket = server.getSocket(0);
            Thread.sleep(100);
        }
        HTMLTokenizer tokenizer = new HTMLTokenizer<String, Word<Character>>(socket);
        ArrayList<String> tokens = new ArrayList<>();

        tokens.add(">");
        tokens.add("\"");
        tokens.add("=");
        tokens.add("/");
        tokens.add("div");
        tokens.add("<!DOCTYPE>");
        tokens.add("html");
        Alphabet<String> alph = new ListAlphabet<>(tokens);
        MealyDHC adtLearner = new MealyDHC<String, Word<Character>>(alph, tokenizer);
        adtLearner.startLearning();

    }

    public static void main(String args[]) throws IOException, InterruptedException {
        Main main = new Main();
        main.run();
    }
}
