package com.aodocs.foldertree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FolderTreeTest {

    private FolderTree folderTree;

    @BeforeEach
    public void setUp() {
        folderTree = new FolderTree();
    }
    @Test
    public void test1() {
        Folder root = folderTree.buildTree(List.of("/"), List.of("/a")).get();

        assertThat(root.getName()).isEqualTo("/");
        assertThat(root.getChilds().get(0).getName()).isEqualTo("/a");
    }

    @Test
    public void test2() {
        Folder root = folderTree.buildTree(List.of("/"), List.of("/a", "/b")).get();

        assertThat(root.getName()).isEqualTo("/");
        assertThat(root.getChilds().get(0).getName()).isEqualTo("/a");
        assertThat(root.getChilds().get(1).getName()).isEqualTo("/b");
    }

    @Test
    public void test3() {
        assertThat(folderTree.buildTree(List.of("/"), List.of("/a/b"))).isEmpty();
    }

    @Test
    public void test4() {
        Folder root = folderTree.buildTree(List.of("/", "/b"), List.of("/a/b", "/b/b")).get();

        assertThat(root.getName()).isEqualTo("/");
        assertThat(root.getChilds()).hasSize(1);
        assertThat(root.getChilds().get(0).getName()).isEqualTo("/b");
        assertThat(root.getChilds().get(0).getChilds().get(0).getName()).isEqualTo("/b/b");
    }

    @Test
    public void test5() {
        Folder root = folderTree.buildTree(List.of("/", "/b", "/c", "/d", "/d/e", "/d/e/f"), List.of("/a/b", "/b/b", "/d/e/f")).get();

        Folder nodeD = getChildByName(root, "d");
        assertThat(nodeD.getName()).isEqualTo("/d");
        Folder nodeDE = getChildByName(nodeD, "e");
        assertThat(nodeDE.getName()).isEqualTo("/d/e");
        Folder nodeDEF = getChildByName(nodeDE, "f");
        assertThat(nodeDEF.getName()).isEqualTo("/d/e/f");
    }

    @Test
    public void test6() {
        Folder root = folderTree.buildTree(List.of("/", "/a", "/a/b", "/a/c", "/a/d"), List.of("/a/b", "/a/c", "/a/d")).get();

        Folder nodeA = getChildByName(root, "a");
        assertThat(nodeA.getName()).isEqualTo("/a");

        Folder nodeAB = getChildByName(nodeA, "b");
        assertThat(nodeAB.getName()).isEqualTo("/a/b");

        Folder nodeAC = getChildByName(nodeA, "c");
        assertThat(nodeAC.getName()).isEqualTo("/a/c");

        Folder nodeAD = getChildByName(nodeA, "d");
        assertThat(nodeAD.getName()).isEqualTo("/a/d");
    }

    private Folder getChildByName(Folder parent, String childName) {
        String searchedName = parent.getName().equals("/") ? "/" + childName : parent.getName() + "/" + childName;
        return parent.getChilds().stream()
                .filter((child) -> child.getName().equals(searchedName))
                .findFirst()
                .orElseThrow();
    }
}
