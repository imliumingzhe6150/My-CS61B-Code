import org.junit.jupiter.api.Test;
import wordnet.Graph;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/** 测试自建的 Graph 类（不碰任何数据文件）。 */
public class TestGraph {

    @Test
    public void testEmptyGraph() {
        Graph g = new Graph();
        assertThat(g.getNodes()).isEmpty();
    }

    @Test
    public void testAddNode() {
        Graph g = new Graph();
        g.addNode(1);
        assertThat(g.getNodes()).containsExactly(1);
        // 孤立节点：邻居集合应为空
        assertThat(g.neighbours(1)).isEmpty();
    }

    @Test
    public void testAddEdgeDirection() {
        Graph g = new Graph();
        g.addNode(1);
        g.addNode(2);
        g.addEdge(1, 2);

        assertThat(g.neighbours(1)).containsExactly(2);
        // 有向：2 不该指回 1
        assertThat(g.neighbours(2)).isEmpty();
    }

    @Test
    public void testMultipleEdges() {
        Graph g = new Graph();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);

        // neighbours 返回的 List 顺序由 HashSet 决定，转成 Set 再比较
        assertThat(new HashSet<>(g.neighbours(1))).isEqualTo(Set.of(2, 3));
        assertThat(g.getNodes()).isEqualTo(Set.of(1, 2, 3));
    }
}
