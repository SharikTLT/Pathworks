package com.oliveshark.pathworks.framework.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuadTreeTest {

    /**
     * Check split quadTree if capacity reached
     */
    @Test
    void checkSplit() {
        QuadTree<String> tree = new QuadTree<>(new Rectangle(-100, -100, 200, 200), 4);

        tree.insert(new Vector2(-50, 50), "nw");
        assertEquals(1, tree.getItems().size());
        assertNull(tree.getSubTrees());

        tree.insert(new Vector2(50, 50), "ne");
        assertEquals(2, tree.getItems().size());
        assertNull(tree.getSubTrees());

        tree.insert(new Vector2(50, -50), "se");
        assertEquals(3, tree.getItems().size());
        assertNull(tree.getSubTrees());

        tree.insert(new Vector2(-50, -50), "sw");
        assertEquals(0, tree.getItems().size());
        assertEquals(4, tree.getSubTrees().length);
        assertEquals(4, tree.size());
    }

    /**
     * Insert objects into tree and check what desired elements found
     */
    @Test
    void checkQuery() {
        QuadTree<String> tree = new QuadTree<>(new Rectangle(-100, -100, 200, 200), 4);
        Rectangle searchArea = new Rectangle(-55, 10, 80, 30);
        Set<String> desiredElements = new HashSet<>();

        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                float x1 = 1f * x - 100;
                float y1 = 1f * y - 100;
                String key = String.format("%f,%f", x1, y1);
                Vector2 pos = new Vector2(x1, y1);
                if (searchArea.contains(pos)) {
                    desiredElements.add(key);
                }
                assertTrue(tree.insert(pos, key));
            }
        }

        List<String> query = tree.query(searchArea);

        Set<String> foundedElements = new HashSet<>(query);

        assertEquals(desiredElements.size(), query.size());
        assertEquals(desiredElements.size(), foundedElements.size());
        assertTrue(foundedElements.containsAll(desiredElements));

        assertEquals(9900, tree.query(new Rectangle(0, 0, 100, 100)).size());
        assertEquals(10100, tree.query(new Rectangle(-100, 0, 100, 100)).size());
        assertEquals(40_000, tree.size());
        for (int i = 0; i < 100; i++) {
            assertFalse(tree.insert(new Vector2(201 + i, 201 + i), "not in bound"));
        }
        assertEquals(40_000, tree.size());
    }
}