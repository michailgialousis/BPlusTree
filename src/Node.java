public class Node {
    String fileName;
    int position;
    Node next;

    public Node(String fileName, int position) {
        this.fileName = fileName;
        this.position = position;
        this.next = null;
    }
}