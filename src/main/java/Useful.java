import java.io.File;
import java.util.Arrays;

public class Useful {
    public static String[] filesInDirectory(String path) {
        File directoryPath = new File(path);
        String[] contents = directoryPath.list();
        assert contents != null;
        Arrays.sort(contents);
        return contents;
    }

    // method to delete all files in a directory
    public static void deleteFilesInDirectory(String path) {
        File directoryPath = new File(path);
        String[] contents = directoryPath.list();
        assert contents != null;
        for (String filePath : contents) {
            File file = new File(path + "/" + filePath);
            file.delete();
        }
    }


}
