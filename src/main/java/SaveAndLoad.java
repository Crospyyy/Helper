import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SaveAndLoad {

    public static void saveIntArr(int[] layerLengths, String filePath) throws IOException {
        FileWriter myWriter = new FileWriter(filePath);

        myWriter.write(Arrays.toString(layerLengths));
        myWriter.close();
    }

    public static void saveListDoubleArr2D(List<double[][]> listOfArrays, String filepath) throws IOException {
        String[] strArr = new String[listOfArrays.size()];

        for (int i = 0; i < listOfArrays.size(); i++) {
            strArr[i] = Arrays.deepToString(listOfArrays.get(i));
        }


        FileWriter myWriter = new FileWriter(filepath);

        myWriter.write(Arrays.toString(strArr));
        myWriter.close();
    }

    public static void saveListDoubleArr(List<double[]> listOfArrays, String filepath) throws IOException {
        String[] strArr = new String[listOfArrays.size()];

        for (int i = 0; i < listOfArrays.size(); i++) {
            strArr[i] = Arrays.toString(listOfArrays.get(i));
        }


        FileWriter myWriter = new FileWriter(filepath);

        myWriter.write(Arrays.toString(strArr));
        myWriter.close();
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static List<Object> stringToList(String listStr) {

        // Idee von Qwynt

        List<Object> outputList = new ArrayList<>();
        String elementsToRemove = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

//        while (listStr.charAt(listStr.length() - 1) == ' ') {
//        listStr.substring()
        for (int i = 0; i < elementsToRemove.length(); i++) {
            char c = elementsToRemove.charAt(i);
            listStr = listStr.replaceAll(String.valueOf(c), "");
        }


        String newString = listStr.substring(1, listStr.length() - 1);
        if (newString.isBlank()) {
            return new ArrayList<>();
        }

        newString = newString + "e";

        String addingValue;

        for (int repeat = 0; newString.length() != 0; repeat++) {


            int layer = 0;
            int i = 0;

            while (!(newString.charAt(i) == ',' && layer == 0 || newString.charAt(i) == 'e')) {
                if (newString.charAt(i) == '[') {
                    layer++;
                }
                if (newString.charAt(i) == ']') {
                    layer--;
                }
                i++;
            }

            int rest = 0;

            if (i < newString.length() - 1) {
                while (newString.substring(i + rest + 1, i + rest + 2).isBlank()) {
                    rest++;
                }

            }
            addingValue = newString.substring(0, i);
//            System.out.println("adding sliderValue: (" + addingValue + ")");
            if (addingValue.charAt(0) == '[') {
//                System.out.println("array");
                outputList.add(stringToList(addingValue));
            } else {
//                System.out.println("number");
                outputList.add(Double.parseDouble(addingValue));
            }

//            System.out.println("removing a comma and " + rest + " spaces");
            newString = newString.substring(i + rest + 1);
//            System.out.println("new sliderValue: <" + newString + ">");

//            if (sliderValue.charAt(0) == '[') {
//                outputList.add(stringToList(sliderValue.substring(0, i)));
//            } else if (!sliderValue.isBlank()) {
//                outputList.add(Double.parseDouble(sliderValue));
//            }

//            if (i < newString.length()) {
//
        }

        return outputList;
    }

    public static String loadString(String filepath) throws FileNotFoundException {
        File myObj = new File(filepath);

        Scanner myReader;
        myReader = new Scanner(myObj);


        StringBuilder arrString = new StringBuilder();
        while (myReader.hasNextLine()) {
            arrString.append(myReader.nextLine());
        }


        return arrString.toString();
    }

    public static int[] loadIntArr(String filepath) throws FileNotFoundException {
        String listStr = SaveAndLoad.loadString(filepath);
        if (listStr.equals("")) {
            throw new IllegalArgumentException(filepath);
        }
        return Arr.listToIntArray(SaveAndLoad.stringToList(listStr));
    }

    public static List<double[][]> loadListDoubleArrArr(String filepath) throws FileNotFoundException {
        String listStr = SaveAndLoad.loadString(filepath);
        if (listStr.equals("")) {
            throw new IllegalArgumentException(filepath);
        }
        return listToListOfDoubleArrArr(SaveAndLoad.stringToList(listStr));
    }

    public static List<double[]> loadListDoubleArr(String filepath) throws FileNotFoundException {
        String listStr = SaveAndLoad.loadString(filepath);
        if (listStr.equals("")) {
            throw new IllegalArgumentException(filepath);
        }
        return listToListOfDoubleArr(SaveAndLoad.stringToList(listStr));
    }

    @SuppressWarnings("unchecked")
    public static List<double[][]> listToListOfDoubleArrArr(List<Object> list) {
        List<double[][]> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(list2DToArray((List<List<Double>>) list.get(i)));
        }
        return newList;
    }

    @SuppressWarnings("unchecked")
    public static List<double[]> listToListOfDoubleArr(List<Object> list) {
        List<double[]> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(list1DToArray((List<Double>) list.get(i)));
        }
        return newList;
    }

    public static double[][] list2DToArray(List<List<Double>> list) {
        double[][] arr = new double[list.size()][list.get(0).size()];
        for (int i1 = 0; i1 < arr.length; i1++) {
            for (int i2 = 0; i2 < arr[0].length; i2++) {
                arr[i1][i2] = list.get(i1).get(i2);
            }
        }
        return arr;
    }

    public static double[] list1DToArray(List<Double> list) {
        double[] arr = new double[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

//    static int @NotNull [] loadLayerLengths() throws FileNotFoundException {
//        return fileToIntArr(NetworkFolderData.layerLengthsFile);
//    }

//    @NotNull
//    static List<double[]> loadLayerBiases() throws FileNotFoundException {
//        return fileToListDoubleArr(NetworkFolderData.layerBiasFile);
//    }

//    @NotNull
//    static List<double[][]> loadLayerWeights() throws FileNotFoundException {
//        return fileToListDoubleArrArr(NetworkFolderData.layerWeightFile);
//    }
}
