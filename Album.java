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
                if (!allAvilable(photo.allTags, Array))
                    Rphotos.remove();
                else
                    Rphotos.findNext();
            }

            Photo photo11 = Rphotos.retrieve();
            if (!allAvilable(photo11.allTags, Array))
                Rphotos.remove();
            else
                Rphotos.findNext();
        }

        return Rphotos;
    }

    private boolean allAvilable(LinkedList<String> AllTags, String[] Array) {
        boolean continue1 = true;

        if (AllTags.empty()) {
            continue1 = false;
        } else {
            for (int i = 0; i < Array.length && continue1; i++) {
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
                    continue1 = false;
            }
        }

        return continue1;
    }

    private LinkedList<Photo> getPhotosBST() {
        BST<LinkedList<Photo>> photosBST = invmanager.getPhotos();
        LinkedList<Photo> Rphotos = new LinkedList<Photo>();
        NbComps = 0;
        String[] tags;

        if (condition.equals("")) {
            if (photosBST.findkey(" "))
                Rphotos = photosBST.retrieve();
        } else {
            tags = condition.split(" AND ");

            for (int i = 0; i < tags.length; i++) {
                if (photosBST.findkey(tags[i])) {
                    if (i == 0) {
                        LinkedList<Photo> miniTag = photosBST.retrieve();
                        miniTag.findFirst();
                        while (!miniTag.last()) {
                            Rphotos.insert(miniTag.retrieve());
                            miniTag.findNext();
                        }
                        Rphotos.insert(miniTag.retrieve());
                    } else {
                        Rphotos = intersectPhotos(Rphotos, photosBST.retrieve());
                    }
                } else {
                    Rphotos = new LinkedList<Photo>();
                    break;
                }
            }
        }

        return Rphotos;
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

    private int menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Linked List");
        System.out.println("2. BST");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        return choice;
    }
}