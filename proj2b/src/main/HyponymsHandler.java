package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import wordnet.WordNet;

public class HyponymsHandler extends NgordnetQueryHandler {

    private NGramMap ngm;
    private WordNet wn;

    public HyponymsHandler(NGramMap ngm, WordNet wn) {
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        String word = q.words().get(0);
        return wn.hyponyms(word).toString();
    }
}
