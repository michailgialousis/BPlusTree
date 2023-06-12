public class Finder {
    private Node head;

    public Finder() {
        head = null;
    }

    // add a new node to the linked list
    public void add(String fileName, int position) {
        Node newNode = new Node(fileName, position);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // display the file names and positions in the linked list
    public void display() {
        Node current = this.head;
        while (current != null) {
            System.out.println("File Name: " + current.fileName + ", Position: " + current.position);
            current = current.next;
        }
    }
}
