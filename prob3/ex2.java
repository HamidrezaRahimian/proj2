package prob3;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

public class ex2 {
    public static void main(String[] args) {
        Library library = new Library();

        // these threads gonna read and write the "book"
        BookReader reader1 = new BookReader(library, "Reader A");
        BookReader reader2 = new BookReader(library, "Reader B");
        BookEditor writer = new BookEditor(library, "Writer X");

        reader1.start();
        reader2.start();
        writer.start();
    }
}

// SHARED CLASS -------------------------------------------------------------------

class Library {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private String book = "Original Book";

    public void read(String readerName) {
        lock.readLock().lock(); // yo lemme in to read
        try {
            System.out.println(readerName + ":  Reading the book -> " + book);
            Thread.sleep(1000); // chill time
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock(); // done reading bruh
            System.out.println(readerName + ":  Done reading");
        }
    }

    public void write(String writerName) {
        lock.writeLock().lock(); // no one else allowed, I'm editing
        try {
            System.out.println(writerName + ":  Writing a new version of the book...");
            Thread.sleep(2000); // writer being dramatic
            book = " New Edition by " + writerName;
            System.out.println(writerName + ":  Done writing!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock(); // let the others in
            System.out.println(writerName + ":  Released the write lock");
        }
    }
}

// THREADS -------------------------------------------------------------------

class BookReader extends Thread {
    private final Library library;
    private final String readerName;

    public BookReader(Library library, String readerName) {
        this.library = library;
        this.readerName = readerName;
    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            library.read(readerName);
            try {
                Thread.sleep(1500); // cooldown time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BookEditor extends Thread {
    private final Library library;
    private final String writerName;

    public BookEditor(Library library, String writerName) {
        this.library = library;
        this.writerName = writerName;
    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            library.write(writerName);
            try {
                Thread.sleep(3000); // chill before writing again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
