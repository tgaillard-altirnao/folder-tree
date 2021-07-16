package com.aodocs.foldertree;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FolderTree {

    public Optional<Folder> buildTree(List<String> readableFolders, List<String> writableFolders) {
        List<Folder> folders = writableFolders.stream()
                .filter((folderName) -> isReachable(readableFolders, folderName))
                .map(Folder::new)
                .collect(Collectors.toList());

        return findRoot(buildIntermediateFolders(folders));
    }

    private List<Folder> buildIntermediateFolders(List<Folder> folders) {
        List<Folder> completeItems = new LinkedList<>(folders);
        folders.forEach((folder) -> buildParent(folder, completeItems));
        return completeItems;
    }

    private void buildParent(Folder item, List<Folder> items) {
        getParentName(item.getName()).ifPresent((parentName) -> {
            Optional<Folder> parentOpt = findFolderByName(items, parentName);
            parentOpt.orElseGet(() -> {
                Folder createdParent = new Folder(parentName);
                createdParent.setChilds(new LinkedList<>());
                buildParent(createdParent, items);
                items.add(createdParent);
                return createdParent;
            }).getChilds().add(item);
        });

    }

    private Optional<Folder> findFolderByName(List<Folder> items, String folderName) {
        return items.stream().filter((folder) -> folder.getName().equals(folderName)).findFirst();
    }

    private boolean isReachable(List<String> readableFolders, String target) {
        return getParentName(target).map(readableFolders::contains).orElse(true);
    }

    private Optional<Folder> findRoot(Collection<Folder> folders) {
        return folders.stream().filter((folder) -> "/".equals(folder.getName())).findFirst();
    }

    private Optional<String> getParentName(String folderName) {
        return Optional.ofNullable(new File(folderName).getParent());
    }
}
