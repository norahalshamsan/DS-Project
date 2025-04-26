public class PhotoManager {
    
     LinkedList<Photo> photos;

     // Constructor
    public PhotoManager()
    {
            photos = new LinkedList<Photo>();
    }
    
    // Add a photo
    public void addPhoto(Photo p)
    {
           if (! IsPhototAvailable(p.getPath(), photos) )     
               photos.insert(p);
    }
    
    private boolean IsPhototAvailable(String p, LinkedList<Photo> List)
    {
        if (List.empty()) 
           return false;

        List.findFirst();
        while ( !List.last())
        {
           if (List.retrieve().getPath().compareToIgnoreCase(p)== 0)
               return true;

           List.findNext();
        }

        if (List.retrieve().getPath().compareToIgnoreCase(p)== 0)
           return true;

        return false;
    }

    // Delete a photo
    public void deletePhoto(String path) {
    if (photos.empty()) return;

    photos.findFirst();
    while (!photos.last()) {
        if (photos.retrieve().getPath().equalsIgnoreCase(path)) {
            photos.remove();
            return;
        }
        photos.findNext();
    }

    if (photos.retrieve().getPath().equalsIgnoreCase(path)) {
        photos.remove();
    }
}


    // Return all managed photos
    public LinkedList<Photo>  getPhotos()
    {
        return photos;
    }
}