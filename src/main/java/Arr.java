

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SuppressWarnings("unused")
public class Arr {

    public static double @NotNull [] blur(double @NotNull [] arr, int imgWidth, int blur_size) {
        int imgHeight = arr.length / imgWidth;
        double[][] convertedArr = new double[imgWidth][imgHeight];

        int p = 0;
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                convertedArr[x][y] = arr[p];
                p++;
            }
        }
        double[][] blurredArr = new double[imgWidth][imgHeight];
        for (int pixelX = 0; pixelX < imgWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imgHeight; pixelY++) {

                List<Double> blurPixels = new ArrayList<>();
                for (int x = -blur_size; x <= blur_size; x++) {
                    for (int y = -blur_size; y <= blur_size; y++) {
                        if (pixelX + x >= 0 && pixelX + x < imgWidth && pixelY + y >= 0 && pixelY + y < imgHeight) {
                            blurPixels.add(convertedArr[pixelX + x][pixelY + y]);
                        }
                    }
                }
                double average = Arr.average(blurPixels);
                blurredArr[pixelX][pixelY] = average;

            }
        }

        double[] blurredArrReconverted = new double[imgWidth * imgHeight];
        p = 0;
        for (int x = 0; x < blurredArr.length; x++) {
            for (int y = 0; y < blurredArr[0].length; y++) {
                blurredArrReconverted[p++] = blurredArr[x][y];
            }
        }
        return blurredArrReconverted;

    }

    public static @NotNull List<Double> arrayToList(double @NotNull [] arr) {
        List<Double> list = new ArrayList<>();
        for (double value : arr) {
            list.add(value);
        }
        return list;
    }

    public static int @NotNull [] listToIntArray(@NotNull List<Object> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = ((Double) list.get(i)).intValue();
        }
        return arr;
    }

    public static double @NotNull [] listToDoubleArray(@NotNull List<Double> list) {
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static double @NotNull [] difference(double @NotNull [] array1, double[] array2) {
        double[] outputArray = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            outputArray[i] = Math.abs(array1[i] - array2[i]);
        }
        return outputArray;
    }

    public static double @NotNull [] difference(double @NotNull [] array1, double num) {
        double[] outputArray = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            outputArray[i] = Math.abs(array1[i] - num);
        }
        return outputArray;
    }

    public static double @NotNull [] fromTo(Double @NotNull [] inputArr, int from, int to) {
        int length = to - from + 1;
        double[] newArr = new double[length];
        System.arraycopy(inputArr, from, newArr, 0, length);
        return newArr;
    }

    public static int indexOfMinNumber(double @NotNull [] arr) {
        double min = arr[0];
        int min_index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= min) {
                min = arr[i];
                min_index = i;
            }
        }
        return min_index;
    }

    public static double min(double... arr) {
        double min = arr[0];
        int min_index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= min) {
                min = arr[i];
                min_index = i;
            }
        }
        return arr[min_index];
    }

    public static int min(@NotNull List<Object> list) {
        double min = (double) list.get(0);
        int min_index = 0;
        for (int i = 0; i < list.size(); i++) {
            if ((double) list.get(i) <= min) {
                min = (double) list.get(i);
                min_index = i;
            }
        }
        return min_index;
    }

    public static double[][] batchNormalization(double[][] inputsArr) {
        double[] averagesPerPx = new double[inputsArr[0].length];
        double[] avrDeviationPerPx = new double[averagesPerPx.length];
        double[][] rotatedArr = rotateArr(inputsArr);
        for (int i = 0; i < averagesPerPx.length; i++) {
            averagesPerPx[i] = average(rotatedArr[i]);
        }
        for (int pixPos = 0; pixPos < rotatedArr.length; pixPos++) {
            avrDeviationPerPx[pixPos] = average(difference(rotatedArr[pixPos], averagesPerPx[pixPos]));
        }

        double[][] normalized = createEmpty(inputsArr);
        for (int imgNum = 0; imgNum < inputsArr.length; imgNum++) {
            for (int pixPos = 0; pixPos < inputsArr[0].length; pixPos++) {
                normalized[imgNum] = divide(subtract(inputsArr[imgNum], averagesPerPx), avrDeviationPerPx);
            }
        }
        return normalized;
    }

    public static double[][] listDoubleArrToDoubleArrArr(List<double[]> list) {
        double[][] arr = new double[list.size()][list.get(0).length];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static List<double[]> doubleArrArrToListDoubleArr(double[][] arr) {
        List<double[]> list = new ArrayList<>();
        Collections.addAll(list, arr);
        return list;
    }

    public static double[][] rotateArr(double[]... array) {
        double[][] arr2 = new double[array[0].length][array.length];
        for (int a = 0; a < array.length; a++) {
            for (int b = 0; b < array[0].length; b++) {
                arr2[b][a] = array[a][b];
            }
        }
        return arr2;
    }


    public static int indexOfMaxNumber(double @NotNull [] arr) {
        double max = arr[0];
        int max_index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= max) {
                max = arr[i];
                max_index = i;
            }
        }
        return max_index;
    }

    public static double max(double @NotNull ... arr) {
        assert false : "arr empty";
        double max = arr[0];
        int max_index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= max) {
                max = arr[i];
                max_index = i;
            }
        }
        return arr[max_index];
    }

    public static double max(@NotNull List<Double> arr) {
        double max = arr.get(0);
        int max_index = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) >= max) {
                max = arr.get(i);
                max_index = i;
            }
        }
        return arr.get(max_index);
    }

    public static int maxIndex(@NotNull List<Double> list) {
        double max = list.get(0);
        int max_index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) >= max) {
                max = list.get(i);
                max_index = i;
            }
        }
        return max_index;
    }

    public static double average(double... arr) {
        return sum(arr) / arr.length;
    }

    public static double average(@NotNull List<Double> list) {
        return sum((list)) / list.size();
    }

    public static double sum(double @NotNull [] arr) {
        double sum;
        int i;
        for (sum = 0.0, i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static double sum(@NotNull List<Double> arr) {
        double sum = 0.0;

        for (double aDouble : arr) {
            sum += aDouble;
        }
        return sum;
    }

    public static double[] multiply(double @NotNull [] array1, double @NotNull [] array2) {
        if (array1.length != array2.length) {
            System.out.println("array1 length:" + array1.length + " is not the same as array2 length:" + array2.length);
            new Exception().printStackTrace();
            System.exit(0);
        }
        double[] newArr = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            newArr[i] = array1[i] * array2[i];
        }
        return newArr;
    }


    public static double[] multiply(double @NotNull [] array, double multiplier) {
        double[] newArr = createEmpty(array);
        for (int i = 0; i < array.length; i++) {
            newArr[i] = array[i] * multiplier;
        }
        return newArr;
    }

    public static double[][] multiply(double @NotNull [][] array, double multiplier) {
        double[][] newArr = createEmpty(array);
        for (int i = 0; i < array.length; i++) {
            newArr[i] = multiply(array[i], multiplier);
        }
        return newArr;
    }

    public static double[] createRandArr(int length, double min, double max) {
        double[] arr = new double[length];
        for (int i = 0; i < length; i++) {
            arr[i] = Rand.randRange(min, max);

        }
        return arr;
    }

    public static double[][] createRandArr(int len1, int len2, double min, double max) {
        double[][] arr = new double[len1][len2];
        for (int i1 = 0; i1 < len1; i1++) {
            arr[i1] = createRandArr(len2, min, max);
        }
        return arr;
    }

    public static double[][][] createRandArr(int len1, int len2, int len3, double min, double max) {
        double[][][] arr = new double[len1][len2][len3];
        for (int i = 0; i < len1; i++) {
            arr[i] = createRandArr(len2, len3, min, max);

        }
        return arr;
    }

    public static double[][][][] createRandArr(int len1, int len2, int len3, int len4, double min, double max) {
        double[][][][] arr = new double[len1][len2][len3][len4];
        for (int i = 0; i < len1; i++) {
            arr[i] = createRandArr(len2, len3, len4, min, max);

        }
        return arr;
    }


    public static double[] cloneArr(double @NotNull [] srcArr) {
        double[] dstArr = new double[srcArr.length];
        System.arraycopy(srcArr, 0, dstArr, 0, srcArr.length);

        return dstArr;
    }

    public static int[] cloneArr(int @NotNull [] srcArr) {
        int[] dstArr = new int[srcArr.length];
        for (int i = 0; i < srcArr.length; i++) {
            dstArr[i] = srcArr[i];
        }
        return dstArr;
    }

    public static int[] doubleArrToIntArr(double @NotNull [] arr) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = (int) ((arr[i] + 1) / 2 * 255);
        }
        return newArr;
    }

    public static int minOverOneIndex(@NotNull List<Double> list) {
        double minValueOver1 = 2;
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) >= 1 && list.get(i) < minValueOver1) {
                minValueOver1 = list.get(i);
                index = i;
            }
        }
        if (index == -1) {
//            System.out.println("switch");
            index = maxIndex(list);
        }
        return index;
    }

    public static void ensureSameLength(double @NotNull [] arr1, double @NotNull [] arr2, String arr1Name, String arr2Name) {
        if (arr1.length != arr2.length) {
            throw new IllegalArgumentException("The length of " + arr1Name + " (" + arr1.length + ") " + "must match the length of " + arr2Name + " (" + arr2.length + ").");
        }
    }

    public static void ensureSameLength(double @NotNull [] arr1, int length, String arr1Name, String arr2Name) {
        if (arr1.length != length) {
            throw new IllegalArgumentException("The length of " + arr1Name + " (" + arr1.length + ") " + "must match the length of " + arr2Name + " (" + length + ").");
        }
    }

    public static void ensureSameLength(@NotNull List<double[]> list1, @NotNull List<double[]> list2, String arr1Name, String arr2Name) {
        if (list1.size() != list2.size()) {
            System.out.println(list1.size());
            System.out.println(list2.size());
            new Exception("The length of " + arr1Name + " (" + list1.size() + ") " + "must match the length of " + arr2Name + " (" + list2.size() + ").").printStackTrace();
        }
    }

    public static @NotNull List<double[]> cloneListOfDoubleArr(@NotNull List<double[]> listToCopy) {
        List<double[]> listOfArr = new ArrayList<>();
        for (double[] doubles : listToCopy) {
            listOfArr.add(cloneArr(doubles));
        }
        return listOfArr;
    }

    public static @NotNull List<double[][]> cloneListOfDoubleArrArr(@NotNull List<double[][]> listToCopy) {
        List<double[][]> listOfArr = new ArrayList<>();
        for (double[][] doubles : listToCopy) {
            listOfArr.add(cloneArr(doubles));
        }
        return listOfArr;
    }

    public static double[] averagesOfArrays(double[] @NotNull ... arrays) {
        double[] average = new double[arrays[0].length];
        for (int i = 0; i < arrays[0].length; i++) {
            double sum = 0;
            for (double[] arr : arrays) {
                sum += arr[i];
            }
            sum /= arrays.length;
            average[i] = sum;
        }
        return average;
    }

    public static double[] add(double @NotNull [] array, double number) {
        double[] newArr = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            newArr[i] = array[i] + number;
        }
        return newArr;

    }

    public static double[] add(double @NotNull [] array1, double @NotNull [] array2) {
        if (array1.length != array2.length) {
            System.out.println("array1 length:" + array1.length + " is not the same as array2 length:" + array2.length);
            new Exception().printStackTrace();
            System.exit(0);
        }
        double[] newArr = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            newArr[i] = array1[i] + array2[i];
        }
        return newArr;
    }

    public static double[][] add(double @NotNull [][] array1, double @NotNull [][] array2) {
        if (array1.length != array2.length) {
            System.out.println("array1 length:" + array1.length + " is not the same as array2 length:" + array2.length);
            new Exception().printStackTrace();
            System.exit(0);
        }
        double[][] newArr = new double[array1.length][array1[0].length];
        for (int i = 0; i < array1.length; i++) {
            newArr[i] = Arr.add(array1[i], array2[i]);
        }
        return newArr;
    }


    public static double[] abs(double @NotNull [] array) {
        double[] newArr = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            newArr[i] = Math.abs(array[i]);
        }
        return newArr;
    }

    public static double[] subtract(double @NotNull [] array1, double @NotNull [] array2) {
        if (array1.length != array2.length) {
            System.out.println("array1 length:" + array1.length + " is not the same as array2 length:" + array2.length);
            System.exit(0);
        }
        double[] newArr = new double[array1.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = array1[i] - array2[i];
        }
        return newArr;
    }

    public static double[] subtract(double @NotNull [] array1, double num) {
        double[] newArr = new double[array1.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = array1[i] - num;
        }
        return newArr;
    }

    public static double[][] subtract(double @NotNull [][] array1, double @NotNull [][] array2) {
        if (array1.length != array2.length) {
            System.out.println("array1 length:" + array1.length + " is not the same as array2 length:" + array2.length);
            System.exit(0);
        }
        double[][] newArr = new double[array1.length][array1[0].length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = subtract(array1[i], array2[i]);
        }
        return newArr;
    }

    public static double[] divide(double @NotNull [] arr, double[] divider) {
        double[] newArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i] / divider[i];
        }
        return newArr;
    }

    public static double[] limitOverwrite(double @NotNull [] arr, double minimum, double maximum) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.max(arr[i], minimum);
            arr[i] = Math.min(arr[i], maximum);
        }
        return arr;
    }

    public static double[] combineArrays(double @NotNull [] a, double @NotNull [] b) {
        double[] c = new double[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static double[] createEmpty(double[] arr) {
        return new double[arr.length];
    }

    public static double[][] createEmpty(double[][] arr) {
        return new double[arr.length][arr[0].length];
    }

    public static double[][][] createEmpty(double[][][] arr) {
        return new double[arr.length][arr[0].length][arr[0][0].length];
    }

    public static double[][][][] createEmpty(double[][][][] arr) {
        return new double[arr.length][arr[0].length][arr[0][0].length][arr[0][0][0].length];
    }

    @NotNull
    public static List<double[]> scrambleListDoubleArr(List<double[]> list) {
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            indexList.add(Rand.randInt(i + 1), i);
        }
        List<double[]> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(list.get(indexList.get(i)));
        }
        return newList;
    }

    @NotNull
    public static List<Integer> getScrambledOrder(int listLength) {
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < listLength; i++) {
            indexList.add(Rand.randInt(i + 1), i);
        }
        return indexList;
    }

    @NotNull
    public static List<double[]> applyScrambling(List<Integer> indexList, List<double[]> list) {
        List<double[]> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(list.get(indexList.get(i)));
        }
        return newList;
    }

    public static double[] max(double[] arr1, double[] arr2) {
        double[] arr3 = new double[arr1.length];
        for (int i = 0; i < arr3.length; i++) {
            arr3[i] = max(arr1[i], arr2[i]);
        }
        return arr3;
    }

    public static double[] min(double[] arr1, double[] arr2) {
        double[] arr3 = new double[arr1.length];
        for (int i = 0; i < arr3.length; i++) {
            arr3[i] = min(arr1[i], arr2[i]);
        }
        return arr3;
    }

    public static List<Double> listObjectToListDouble(List<Object> objects) {
        List<Double> list = new ArrayList<>(objects.size());
        for (Object object : objects) {
            list.add((Double) object);
        }
        return list;
    }

    public static int last(int[] arr) {
        return arr[arr.length - 1];
    }

    public static double[][] getSquare(double[][] inputs, int startX, int startY, int width) {
        double[][] square = new double[width][width];
        for (int x = 0; x < width; x++) {
            System.arraycopy(inputs[startX + x], startY, square[x], 0, width);
        }
        return square;
    }

    public static double[][] getSquareAutoCut(double[][] inputs, int startX, int startY, int width) {
        double[][] square = new double[(int) Arr.min(width, inputs.length - startX)][(int) Arr.min(
                width,
                inputs[0].length - startY
        )];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                int inputX = startX + x;
                int inputY = startY + y;
                if (inputX < inputs.length && inputY < inputs[0].length) square[x][y] = inputs[inputX][inputY];
            }
        }
        return square;
    }

    public static double[][][] getCube(double[][][] inputs, int startX, int startY, int width) {
        double[][][] cube = new double[inputs.length][width][width];
        for (int z = 0; z < inputs.length; z++) {
            cube[z] = getSquare(inputs[z], startX, startY, width);
        }
        return cube;

    }

    public static void replaceSquare(double[][] inputs, double[][] replacementSquare, int startX, int startY, int width) {
        assertArrBigger(inputs, replacementSquare);
        assertSquareMatchesWidth(replacementSquare, width);
        for (int x = 0; x < width; x++) {
            System.arraycopy(replacementSquare[x], 0, inputs[startX + x], startY, width);
        }
    }

    public static void replaceCube(double[][][] inputs, double[][][] replacementCube, int startX, int startY, int width) {
        assertSameZDepth(inputs, replacementCube);
        for (int z = 0; z < width; z++) {
            replaceSquare(inputs[z], replacementCube[z], startX, startY, width);
        }
    }

    public static void addSquare(double[][] inputs, double[][] addingSquare, int startX, int startY, int width) {
        assertArrBigger(inputs, addingSquare);
        assertSquareMatchesWidth(addingSquare, width);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                inputs[startX + x][startY + y] += addingSquare[x][y];
            }
        }
    }

    public static void addCubeOverwrite(double[][][] inputs, double[][][] addingCube, int startX, int startY, int width) {
        assertSameZDepth(inputs, addingCube);
        for (int z = 0; z < inputs.length; z++) {
            addSquare(inputs[z], addingCube[z], startX, startY, width);
        }
    }

    public static void assertSquareMatchesWidth(double[][] square, int width) {
        if (width != square.length || width != square[0].length)
            throw new RuntimeException("replacement square[" + square.length + "][" + square[0].length + "] doesn't match the width (" + width + ")");
    }

    public static void assertArrBigger(double[][] biggerArr, double[][] smallerArr) {
        if (biggerArr.length < smallerArr.length || biggerArr[0].length < smallerArr[0].length)
            throw new RuntimeException("arr1[" + biggerArr.length + "][" + biggerArr[0].length + "] smaller than replacement square[" + smallerArr.length + "][" + smallerArr[0].length + "]");
    }

    public static void assertSameZDepth(double[][][] inputs, double[][][] replacementCube) {
        if (inputs.length != replacementCube.length)
            throw new RuntimeException("inputs z depth (" + inputs.length + ") doesn't match replacementCube z depth (" + replacementCube.length + ")");
    }

    public static double[][][] multiply(double[][][] processingInp, double[][][] weights) {
        int zLen = processingInp.length;
        int xLen = processingInp[0].length;
        int yLen = processingInp[0][0].length;
        double[][][] outputs = new double[zLen][xLen][yLen];
        for (int z = 0; z < zLen; z++) {
            for (int x = 0; x < xLen; x++) {
                for (int y = 0; y < yLen; y++) {
                    outputs[z][x][y] = processingInp[z][x][y] * weights[z][x][y];
                }
            }
        }
        return outputs;
    }

    public static double sum(double[][][] multiplied) {
        int zLen = multiplied.length;
        int xLen = multiplied[0].length;
        int yLen = multiplied[0][0].length;
        double sum = 0;
        for (int z = 0; z < zLen; z++)
            for (int x = 0; x < xLen; x++)
                for (int y = 0; y < yLen; y++) sum += multiplied[z][x][y];
        return sum;
    }

    public static double[][][] add(double[][][] doubles, double[][][] add) {
        double[][][] added = createEmpty(doubles);
        for (int i = 0; i < added.length; i++) {
            added[i] = add(doubles[i], add[i]);
        }
        return added;
    }

    public static void addOverwrite(double[][][] doubles, double[][][] add) {
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = add(doubles[i], add[i]);
        }
    }

    public static double[][][] multiply(double[][][] arr, double multiply) {
        double[][][] multiplied = createEmpty(arr);
        for (int i = 0; i < multiplied.length; i++) {
            multiplied[i] = Arr.multiply(arr[i], multiply);
        }
        return multiplied;
    }

    public static double[][] lastElements(List<double[]> list, int numElements) {
        // Todo create min() for int
        numElements = (int) min(numElements, list.size());
        double[][] arr = new double[numElements][list.get(0).length];
        for (int i = 0; i < numElements; i++) {
            arr[i] = Arr.cloneArr(list.get(list.size() - 1 - i));
        }
        return arr;
    }

    public static double @NotNull [] sums(double[][] values) {
        double[] sums = new double[values.length];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = sum(values[i]);
        }
        return sums;
    }

    public static double[][][] cloneArr(double[][][] arr) {
        double[][][] arr2 = createEmpty(arr);
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = cloneArr(arr[i]);
        }
        return arr2;
    }

    public static double[][][][] cloneArr(double[][][][] arr) {
        double[][][][] arr2 = createEmpty(arr);
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = cloneArr(arr[i]);
        }
        return arr2;

    }

    public static double[][] cloneArr(double[] @NotNull [] srcArr) {
        double[][] dstArr = new double[srcArr.length][srcArr[0].length];
        for (int i = 0; i < dstArr.length; i++) {
            System.arraycopy(srcArr[i], 0, dstArr[i], 0, srcArr[i].length);
        }

        return dstArr;
    }

    public static double[][] to2d(double[] flattened, int len1, int len2) {
        double[][] output = new double[len1][len2];
        int index = 0;
        for (int a = 0; a < len1; a++) {
            for (int b = 0; b < len2; b++) {
                output[a][b] = flattened[index];
                index++;
            }
        }
        return output;
    }

    public static double[][][] to3d(double[] data1d, int len1, int len2, int len3) {
        double[][][] data3d = new double[len1][len2][len3];
        int index = 0;
        for (int a = 0; a < len1; a++) {
            for (int b = 0; b < len2; b++) {
                for (int c = 0; c < len3; c++) {
                    data3d[a][b][c] = data1d[index];
                    index++;
                }
            }
        }
        return data3d;
    }

    public static void addOverwrite(double[][][][] filterWeights, double[][][][] weight) {
        for (int i = 0; i < filterWeights.length; i++) {
            filterWeights[i] = add(filterWeights[i], weight[i]);
        }
    }

    public static void addOverwrite(double[][] arr1, double[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = Arr.add(arr1[i], arr2[i]);
        }
    }

    public static void addOverwrite(double[] arr1, double[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] += arr2[i];
        }
    }

    public static double[][][][] multiply(double[][][][] arr, double multiplier) {
        double[][][][] multiplied = createEmpty(arr);
        for (int i = 0; i < multiplied.length; i++) {
            multiplied[i] = Arr.multiply(arr[i], multiplier);
        }
        return multiplied;
    }

    public static double max(double[][] square) {
        double[] maxes = new double[square.length];
        for (int i = 0; i < maxes.length; i++) {
            maxes[i] = max(square[i]);
        }
        return max(maxes);
    }

    public static int[] maxIndex(double[][] square) {
        int[] indexes = new int[2];
        double max = 0;
        boolean numIn = false;
        for (int x = 0; x < square.length; x++) {
            for (int y = 0; y < square[0].length; y++) {
                if (!numIn || square[x][y] > max) {
                    numIn = true;
                    max = square[x][y];
                    indexes = new int[]{x, y};
                }

            }
        }
        return indexes;
    }

    public static double[] flatten(double[][] arr) {
        double[] flattened = new double[arr.length * arr[0].length];
        int index = 0;
        for (int a = 0; a < arr.length; a++) {
            for (int b = 0; b < arr[0].length; b++) {
                flattened[index] = arr[a][b];
                index++;
            }
        }
        return flattened;

    }

    public static double[] flatten(double[][][] arr) {
        double[] flattened = new double[arr.length * arr[0].length * arr[0][0].length];
        int index = 0;
        for (int a = 0; a < arr.length; a++) {
            for (int b = 0; b < arr[0].length; b++) {
                for (int c = 0; c < arr[0][0].length; c++) {
                    flattened[index] = arr[a][b][c];
                    index++;
                }
            }
        }
        return flattened;
    }

    public static double[] flatten(double[][][][] arr) {
        double[] flattened = new double[arr.length * arr[0].length * arr[0][0].length * arr[0][0][0].length];
        int index = 0;
        for (int a = 0; a < arr.length; a++) {
            for (int b = 0; b < arr[0].length; b++) {
                for (int c = 0; c < arr[0][0].length; c++) {
                    for (int d = 0; d < arr[0][0][0].length; d++) {
                        flattened[index] = arr[a][b][c][d];
                        index++;
                    }
                }
            }
        }
        return flattened;
    }

    public static double[][][][] to4d(double[] flattened, int len1, int len2, int len3, int len4) {
        double[][][][] output = new double[len1][len2][len3][len4];
        int index = 0;
        for (int a = 0; a < len1; a++) {
            for (int b = 0; b < len2; b++) {
                for (int c = 0; c < len3; c++) {
                    for (int d = 0; d < len4; d++) {
                        output[a][b][c][d] = flattened[index];
                        index++;
                    }
                }
            }
        }
        return output;
    }

    public static double[] sums(List<double[]> list) {
        double[] sums = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            sums[i] = sum(list.get(i));
        }
        return sums;
    }

    public static double hypotenuse(double p1x, double p1y, double p2x, double p2y) {
        return hypotenuse(p2x - p1x, p2y - p1y);
    }

    public static double hypotenuse(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double hypotenuse(double x, double y, double z) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }
}
