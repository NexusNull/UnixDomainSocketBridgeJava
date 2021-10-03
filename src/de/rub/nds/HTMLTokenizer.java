package de.rub.nds;

import de.learnlib.api.oracle.MembershipOracle;
import de.learnlib.api.query.Query;
import net.automatalib.words.Word;

import java.io.IOException;
import java.util.Collection;

public class HTMLTokenizer<I, D> implements MembershipOracle.MealyMembershipOracle<I, D> {
    UnixSocket socket;

    HTMLTokenizer(UnixSocket socket) {
        this.socket = socket;
    }

    @Override
    public void processQueries(Collection<? extends Query<I, Word<D>>> queries) {
        for (Query<I, Word<D>> query : queries) {
            try {
                String result = this.socket.send(query.getInput().toString());
                System.out.println(result);
                query.answer((Word<D>) Word.fromString(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
