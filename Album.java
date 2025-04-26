import java.util.Scanner;

public class Album {
    private String name;
    private String condition;
    private PhotoManager manager;
    private InvIndexPhotoManager invmanager;
    private int NbComps;

    public Album(String name, String condition, PhotoManager manager) {
        this.name = name;
        this.condition = condition;
        this.manager = manager;
        NbComps = 0;
    }
    
        public Album(String name, String condition, PhotoManager manager ,InvIndexPhotoManager invmanager) {
        this.name = name;
        this.condition = condition;
        this.manager = manager;
        NbComps = 0;
        this.invmanager = invmanager;
    }

    

    public LinkedList<Photo> getPhotos() {
        int choice = menu();

        LinkedList<Photo> result = new LinkedList<Photo>();
        if (choice == 1) {
            result = getPhotosLiS();
        } else {
            result = getPhotosBST();
        }

        return result;
    }

    private LinkedList<Photo> getPhotosLiS() {
        LinkedList<Photo> Rphotos = new LinkedList<Photo>();
        LinkedList<Photo> photos1 = manager.getPhotos();
        if (!photos1.empty()) {
            photos1.findFirst();
            while (!photos1.last()) {
                Rphotos.insert(new Photo(photos1.retrieve().getPath(), photos1.retrieve().getTags()));
                photos1.findNext();
            }
            Rphotos.insert(new Photo(photos1.retrieve().getPath(), photos1.retrieve().getTags()));
        }

        NbComps = 0;

        if (!condition.equals("")) {
            String[] Array = condition.split(" AND ");
            Rphotos.findFirst();
            while (!Rphotos.last()) {
                Photo photo = Rphotos.retrieve();
                if (!allAvilable(photo.TagsA, Array))
                    Rphotos.remove();
                else
                    Rphotos.findNext();
            }

            Photo photo11 = Rphotos.retrieve();
            if (!allAvilable(photo11.TagsA, Array))
                Rphotos.remove();
            else
                Rphotos.findNext();
        }

        return Rphotos;
    }

    private boolean allAvilable(LinkedList<String> AllTags, String[] Array) {
        boolean continuego = true;

        if (AllTags.empty()) {
            continuego = false;
        } else {
            for (int i = 0; i < Array.length && continuego; i++) {
                boolean found_in_tags = false;
                AllTags.findFirst();

                while (!AllTags.last()) {
                    this.NbComps++;
                    if (AllTags.retrieve().equalsIgnoreCase(Array[i])) {
                        found_in_tags = true;
                        break;
                    }
                    AllTags.findNext();
                }

                if (!found_in_tags) {
                    this.NbComps++;
                    if (AllTags.retrieve().equalsIgnoreCase(Array[i]))
                        found_in_tags = true;
                }

                if (!found_in_tags)
                    continuego = false;
            }
        }

        return continuego;
    }

    private LinkedList<Photo> getPhotosBST() {
        BST<LinkedList<Photo>> photosBST = invmanager.getPhotos();
        LinkedList<Photo> Bstphotos = new LinkedList<Photo>();
        NbComps = 0;
        String[] tags;

        if (condition.equals("")) {
            if (photosBST.findkey(" "))
                Bstphotos = photosBST.retrieve();
        } else {
            tags = condition.split(" AND ");

            for (int i = 0; i < tags.length; i++) {
                if (photosBST.findkey(tags[i])) {
                    if (i == 0) {
                        LinkedList<Photo> mTag = photosBST.retrieve();
                        mTag.findFirst();
                        while (!mTag.last()) {
                           Bstphotos.insert(mTag.retrieve());
                            mTag.findNext();
                        }
                        Bstphotos.insert(mTag.retrieve());
                    } else {
                        Bstphotos = intersectPhotos(Bstphotos, photosBST.retrieve());
                    }
                } else {
                    Bstphotos = new LinkedList<Photo>();
                    break;
                }
            }
        }

        return  Bstphotos;
    }

    private LinkedList<Photo> intersectPhotos(LinkedList<Photo> list1, LinkedList<Photo> list2) {
        LinkedList<Photo> result = new LinkedList<Photo>();

        if (list1.empty()) return result;
        if (list2.empty()) return list1;

        list2.findFirst();
        while (!list2.last()) {
            boolean found = false;
            list1.findFirst();
            while (!list1.last() && !found) {
                NbComps++;
                if (list2.retrieve().getPath().equalsIgnoreCase(list1.retrieve().getPath()))
                    found = true;
                list1.findNext();
            }

            if (!found) {
                NbComps++;
                if (list2.retrieve().getPath().equalsIgnoreCase(list1.retrieve().getPath()))
                    found = true;
            }

            if (found)
                result.insert(list2.retrieve());

            list2.findNext();
        }

        // Handle the last element
        boolean found = false;
        list1.findFirst();
        while (!list1.last() && !found) {
            NbComps++;
            if (list2.retrieve().getPath().equalsIgnoreCase(list1.retrieve().getPath()))
                found = true;
            list1.findNext();
        }

        if (!found) {
            NbComps++;
            if (list2.retrieve().getPath().equalsIgnoreCase(list1.retrieve().getPath()))
                found = true;
        }

        if (found)
            result.insert(list2.retrieve());

        return result;
    }
    
    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public PhotoManager getManager() {
        return manager;
    }

    public InvIndexPhotoManager getInvManager() {
        return invmanager;
    }

    public int getNbComps() {
        return NbComps;
    }

    private int menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Linked List");
        System.out.println("2. BST");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        return choice;
    }
}
