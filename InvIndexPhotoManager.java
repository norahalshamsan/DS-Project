public class InvIndexPhotoManager {
    BST<LinkedList<Photo>> Inverted_Index;

    // Constructor
    public InvIndexPhotoManager() {
        Inverted_Index = new BST<LinkedList<Photo>>();
    }

    // Add a photo
    public void addPhoto(Photo p) {
        addPhotoToTag(" ", p); // Add under default " " tag

        LinkedList<String> tags = p.getTags();
        if (!tags.empty()) {
            tags.findFirst();
            while (true) {
                String currentTag = tags.retrieve();
                addPhotoToTag(currentTag, p);
                if (tags.last()) break;
                tags.findNext();
            }
        }
    }

    // Helper method to avoid repeated logic
    private void addPhotoToTag(String tag, Photo p) {
        if (Inverted_Index.findkey(tag)) {
            LinkedList<Photo> photos_inverted = Inverted_Index.retrieve();
            photos_inverted.insert(p);
            Inverted_Index.update(tag, photos_inverted);
        } else {
            LinkedList<Photo> photos_inverted = new LinkedList<Photo>();
            photos_inverted.insert(p);
            Inverted_Index.insert(tag, photos_inverted);
        }
    }

    // Delete a photo
    public void deletePhoto(String path) {
        String allTags = Inverted_Index.inOrder();
        if (allTags.length() == 0)
            allTags += " ";
        else
            allTags += " AND " + " ";

        String[] tags = allTags.split(" AND ");

        for (String tag : tags) {
            if (!Inverted_Index.findkey(tag)) continue;

            LinkedList<Photo> photos_inverted = Inverted_Index.retrieve();
            photos_inverted.findFirst();

            while (true) {
                if (photos_inverted.retrieve().getPath().equalsIgnoreCase(path)) {
                    photos_inverted.remove();
                    break;
                }
                if (photos_inverted.last()) break;
                photos_inverted.findNext();
            }

            // Check again for last element after loop
            if (!photos_inverted.empty() && 
                photos_inverted.retrieve().getPath().equalsIgnoreCase(path)) {
                photos_inverted.remove();
            }

            if (photos_inverted.getSize() == 0) {
                Inverted_Index.removeKey(tag);
            } else {
                Inverted_Index.update(tag, photos_inverted);
            }
        }
    }

    // Return the inverted index of all managed photos
    public BST<LinkedList<Photo>> getPhotos() {
        return Inverted_Index;
    }
}