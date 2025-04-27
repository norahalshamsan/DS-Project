public class Test {
    public static void main(String[] args) {
        InvIndexPhotoManager invmanager = new InvIndexPhotoManager();
        PhotoManager manager = new PhotoManager();

        Photo photo1 = new Photo("hedgehog.jpg", toTagsLinkedList("animal, hedgehog, apple, grass, green"));
        Photo photo2 = new Photo("bear.jpg", toTagsLinkedList("animal, bear, cab, grass, wind"));
        Photo photo3 = new Photo("orange-butterfly.jpg", toTagsLinkedList("insect, butterfly, flower, color"));

        invmanager.addPhoto(photo1);
        manager.addPhoto(photo1);
        invmanager.addPhoto(photo2);
        manager.addPhoto(photo2);
        invmanager.addPhoto(photo3);
        manager.addPhoto(photo3);

        System.out.println("\nContents of PhotoManager after additions:");
        printPhotoManagerContents(manager);

        System.out.println("\nContents of InvIndexPhotoManager after additions:");
        printInvIndexPhotoManagerContents(invmanager);

        Album album1 = new Album("Album1", "bear", manager, invmanager);
        Album album2 = new Album("Album2", "animal AND grass", manager, invmanager);
        Album album3 = new Album("Album3", "", manager, invmanager);

        System.out.println("\nGet album1 details:");
        printAlbum(album1);

        System.out.println("\nGet album2 details:");
        printAlbum(album2);

        System.out.println("\nGet album3 details:");
        printAlbum(album3);

        System.out.println("\nDelete the photo 'bear.jpg':");
        manager.deletePhoto("bear.jpg");
        invmanager.deletePhoto("bear.jpg");

        System.out.println("\nContents of PhotoManager after deletion:");
        printPhotoManagerContents(manager);

        System.out.println("\nContents of InvIndexPhotoManager after deletion:");
        printInvIndexPhotoManagerContents(invmanager);

        System.out.println("\nUpdated album3 details:");
        album3 = new Album("Album3", "", manager, invmanager);
        printAlbum(album3);
    }

    private static LinkedList<String> toTagsLinkedList(String tags) {
        LinkedList<String> result = new LinkedList<>();
        String[] tagsArray = tags.split("\\s*,\\s*");
        for (String tag : tagsArray) {
            result.insert(tag);
        }
        return result;
    }

    private static void printLL(LinkedList<String> list) {
        list.findFirst();
        if (list.empty()) return;
        while (true) {
            System.out.print(list.retrieve() + " ");
            if (list.last()) return;
            list.findNext();
        }
    }

    private static void printLLPhoto(LinkedList<Photo> list) {
        list.findFirst();
        if (list.empty()) return;
        while (true) {
            System.out.println(list.retrieve().getPath());
            if (list.last()) return;
            list.findNext();
        }
    }

    private static void printPhotoManagerContents(PhotoManager manager) {
        LinkedList<Photo> photos = manager.getPhotos();
        printLLPhoto(photos);
    }

    private static void printInvIndexPhotoManagerContents(InvIndexPhotoManager invManager) {
        BST<LinkedList<Photo>> bst = invManager.getPhotos();
        String allTags = bst.inOrder();
        if (allTags.length() == 0) {
            System.out.println("No photos.");
            return;
        }
        String[] tags = allTags.split(" AND ");
        for (String tag : tags) {
            if (bst.findkey(tag)) {
                LinkedList<Photo> photos = bst.retrieve();
                photos.findFirst();
                while (!photos.last()) {
                    System.out.println(photos.retrieve().getPath());
                    photos.findNext();
                }
                System.out.println(photos.retrieve().getPath());
            }
        }
    }

    private static void printAlbum(Album album) {
        System.out.println("Album name: " + album.getName());
        System.out.println("Album condition: " + album.getCondition());
        LinkedList<Photo> photos = album.getPhotos();
        System.out.println("Photos in album:");
        printLLPhoto(photos);
        System.out.printf("Number of comparisons of condition \"%s\" is %d\n", album.getCondition(), album.getNbComps());
    }
}
