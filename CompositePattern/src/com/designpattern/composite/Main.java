package com.designpattern.composite;

// Client code demonstrating the Composite Pattern
class Main {
    public static void main(String[] args) {
        // Create files
        FileSystemComponent file1 = new File("document.txt", 1024);
        FileSystemComponent file2 = new File("image.jpg", 2048);
        FileSystemComponent file3 = new File("video.mp4", 10240);

        // Create directories
        Directory rootDir = new Directory("root");
        Directory documentsDir = new Directory("documents");
        Directory mediaDir = new Directory("media");

        // Build hierarchy
        documentsDir.addComponent(file1);
        mediaDir.addComponent(file2);
        mediaDir.addComponent(file3);

        rootDir.addComponent(documentsDir);
        rootDir.addComponent(mediaDir);

        // Display structure and sizes
        System.out.println("File System Structure:");
        printStructure(rootDir, 0);

        System.out.println("\nTotal size of root directory: " + rootDir.getSize() + " bytes");
    }

    private static void printStructure(FileSystemComponent component, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(component.getName() + " (" + component.getSize() + " bytes)");
        if (component instanceof Directory) {
            Directory dir = (Directory) component;
            for (FileSystemComponent child : dir.getChildren()) {
                printStructure(child, depth + 1);
            }
        }
    }
}