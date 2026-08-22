import org.junit.jupiter.api.Test;
import wordnet.WordNet;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/** 测试 WordNet 类（用最小的 synsets16 / hyponyms16 数据集）。 */
public class TestWordNet {

    private static final String SYNSETS = "data/wordnet/synsets16.txt";
    private static final String HYPONYMS = "data/wordnet/hyponyms16.txt";

    @Test
    public void testHyponymsChange() {
        // change 在 synset 2 和 8 两个节点 → 需要取并集遍历
        WordNet wn = new WordNet(SYNSETS, HYPONYMS);
        assertThat(wn.hyponyms("change")).isEqualTo(Set.of(
                "alteration", "change", "demotion", "increase", "jump", "leap",
                "modification", "saltation", "transition", "variation"));
    }

    @Test
    public void testHyponymsAct() {
        // act 只在一个节点（6）→ 沿 6→7→8→9,10 链式遍历
        WordNet wn = new WordNet(SYNSETS, HYPONYMS);
        assertThat(wn.hyponyms("act")).isEqualTo(Set.of(
                "act", "action", "change", "demotion",
                "human_action", "human_activity", "variation"));
    }

    @Test
    public void testHyponymsLeafNode() {
        // demotion 是叶子节点（没有下位词）→ 结果只含它自己
        WordNet wn = new WordNet(SYNSETS, HYPONYMS);
        assertThat(wn.hyponyms("demotion")).isEqualTo(Set.of("demotion"));
    }

    @Test
    public void testHyponymsNonexistentWord() {
        // 不存在的词 → 空集合（测试 null 检查）
        WordNet wn = new WordNet(SYNSETS, HYPONYMS);
        assertThat(wn.hyponyms("zzz")).isEmpty();
    }
}
