public class Photo {
    String path;
    LinkedList<String> allTags = new LinkedList<>();

    // Constructor
    public Photo(String path, LinkedList<String> tags) {
        this.path = path;
        if (!tags.empty()) {
            tags.findFirst();
            while (!tags.last()) {
                allTags.insert(tags.retrieve());
                tags.findNext();
            }
            allTags.insert(tags.retrieve());  // insert the last element
        }
    }

    // Return the path (full file name) of the photo.
    public String getPath() {
        return path;
    }

    // Return a fresh copy of allTags
    public LinkedList<String> getTags() {
        LinkedList<String> copy = new LinkedList<>();
        if (!allTags.empty()) {
            allTags.findFirst();
            while (!allTags.last()) {
                copy.insert(allTags.retrieve());
                allTags.findNext();
            }
            copy.insert(allTags.retrieve());
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Photo{path=")
            .append(path)
            .append(", allTags=");
        if (!allTags.empty()) {
            allTags.findFirst();
            while (!allTags.last()) {
                sb.append(allTags.retrieve()).append("; ");
                allTags.findNext();
            }
            sb.append(allTags.retrieve());
        }
        sb.append("}");
        return sb.toString();
    }
}