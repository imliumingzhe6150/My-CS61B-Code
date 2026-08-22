package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import wordnet.WordNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler {

    private NGramMap ngm;
    private WordNet wn;

    public HyponymsHandler(NGramMap ngm, WordNet wn) {
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> hyponyms = wn.hyponyms(q.words());

        if (q.k() == 0) {
            return hyponyms.toString();
        }
        return filterByFrequency(hyponyms, q.startYear(), q.endYear(), q.k());
    }

    /** 按 [startYear, endYear] 区间内的总词频，选出最热门的 k 个下位词（字母序输出）。 */
    private String filterByFrequency(Set<String> hyponyms, int startYear, int endYear, int k) {
        Map<String, Double> counts = new HashMap<>();
        for (String word : hyponyms) {
            counts.put(word, totalCount(word, startYear, endYear));
        }

        // 只保留词频 > 0 的词
        List<String> candidates = new ArrayList<>();
        for (String word : hyponyms) {
            if (counts.get(word) > 0) {
                candidates.add(word);
            }
        }

        // 按词频降序；词频相同按字母序（保证结果确定）
        candidates.sort((a, b) -> {
            int cmp = Double.compare(counts.get(b), counts.get(a));
            if (cmp != 0) {
                return cmp;
            }
            return a.compareTo(b);
        });

        // 取前 k 个
        List<String> topK = new ArrayList<>();
        for (int i = 0; i < k && i < candidates.size(); i++) {
            topK.add(candidates.get(i));
        }

        // 最终按字母序输出
        Collections.sort(topK);
        return topK.toString();
    }

    /** 词在 [startYear, endYear] 区间内的出现总次数。 */
    private double totalCount(String word, int startYear, int endYear) {
        double total = 0;
        for (double count : ngm.countHistory(word, startYear, endYear).values()) {
            total += count;
        }
        return total;
    }
}
