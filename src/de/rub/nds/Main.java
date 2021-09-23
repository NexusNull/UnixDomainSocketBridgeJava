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
        new InstanceManager("/home/nexus/Development/CLionProjects/UnixDomainSocketBridgeCpp/cmake-build-debug/UnixDomainSocketBridgeCpp", 5);

        UnixSocket socket = null;
        while (socket == null) {
            socket = server.getSocket(0);
            Thread.sleep(100);
        }
        HTMLTokenizer<Character, Word<Character>> tokenizer = new HTMLTokenizer<Character, Word<Character>>(socket);
        ArrayList<Character> chars = new ArrayList<>();
        chars.add('<');
        chars.add('>');
        chars.add('/');
        chars.add('a');
        chars.add('b');
        Alphabet<Character> alph = new ListAlphabet<>(chars);
        MealyDHC adtLearner = new MealyDHC<Character, Word<Character>>(alph, tokenizer);
        adtLearner.startLearning();
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        Main main = new Main();
        main.run();
    }
}
