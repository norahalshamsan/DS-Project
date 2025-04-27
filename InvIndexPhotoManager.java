public class InvIndexPhotoManager {
    BST<LinkedList<Photo>> InvertedIndex;

    // Constructor
    public InvIndexPhotoManager() {
        InvertedIndex = new BST<LinkedList<Photo>>();
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
        if (InvertedIndex.findkey(tag)) {
            LinkedList<Photo> photos_inverted = InvertedIndex.retrieve();
            photos_inverted.insert(p);
            InvertedIndex.update(tag, photos_inverted);
        } else {
            LinkedList<Photo> photos_inverted = new LinkedList<Photo>();
            photos_inverted.insert(p);
            InvertedIndex.insert(tag, photos_inverted);
        }
    }

    // Delete a photo
    public void deletePhoto(String path) {
        String allTags = InvertedIndex.inOrder();
        if (allTags.length() == 0) return; // Nothing to delete

        String[] tags = allTags.split(" AND ");

        for (String tag : tags) {
            if (!InvertedIndex.findkey(tag)) continue; // Skip if tag not found

            LinkedList<Photo> photos = InvertedIndex.retrieve();
            boolean remove = false;

            if (!photos.empty()) {
                photos.findFirst();
                while (true) {
                    if (photos.retrieve().getPath().equalsIgnoreCase(path)) {
                        photos.remove();
                        remove = true;
                        if (photos.empty()) break;
                    } else if (photos.last()) {
                        break;
                    } else {
                        photos.findNext();
                    }
                }
            }

            if (photos.empty()) {
                InvertedIndex.removeKey(tag); // Remove tag if no photos left
            } else if (remove) {
                InvertedIndex.update(tag, photos); // Update tag if list modified
            }
        }
    }
    // Return the inverted index of all managed photos
    public BST<LinkedList<Photo>> getPhotos() {
        return InvertedIndex;
    }
}
