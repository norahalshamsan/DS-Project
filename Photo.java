// File: Photo.java

public class Photo {
    String path;
    LinkedList<String> TagsA = new LinkedList<>();

    // Constructor
    public Photo(String path, LinkedList<String> tags) {
        this.path = path;
        if (!tags.empty()) {
            tags.findFirst();
            while (!tags.last()) {
                TagsA.insert(tags.retrieve());
                tags.findNext();
            }
            TagsA.insert(tags.retrieve());
        }
    }

    // Return the path (full file name) of the photo.
    public String getPath() {
        return path;
    }

    // Return a fresh copy of TagsA
    public LinkedList<String> getTags() {
        LinkedList<String> copy = new LinkedList<>();
        if (!TagsA.empty()) {
            TagsA.findFirst();
            while (!TagsA.last()) {
                copy.insert(TagsA.retrieve());
                TagsA.findNext();
            }
            copy.insert(TagsA.retrieve());
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Photo{path=");
        sb = new StringBuilder(sb.toString() + path + ", allTags=");
        if (!TagsA.empty()) {
            TagsA.findFirst();
            while (!TagsA.last()) {
                sb = new StringBuilder(sb.toString() + TagsA.retrieve() + "; ");
                TagsA.findNext();
            }
            sb = new StringBuilder(sb.toString() + TagsA.retrieve());
        }
        sb = new StringBuilder(sb.toString() + "}");
        return sb.toString();
    }
}
