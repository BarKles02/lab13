import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
//Bartłomiej Kleszczewski 87058 L3 

//to co nowego z lab13 zaczyna się w linijce 227

class Sorter {
    int[] array;
    private boolean debug = true;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Sorter(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            readFromFile(fileName);
        }
    }

    public Sorter(int numberOfElements) {
        if (numberOfElements > 0) {
            generateTestFile("test.txt", numberOfElements);
            readFromFile("test.txt");
        }
    }

    public void doBubbleSort() {
        int n = array.length;
        int tmp = 0;
        long startTime = System.nanoTime();
        int comparisons = 0, assignments = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                comparisons++;
                if (array[j - 1] > array[j]) {
                    tmp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = tmp;

                    assignments++;
                }
            }
        }
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime);

        if (n < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("BubbleSort / %d / %d %s / %d comparisons/ %d assignments%n", n, elapsedTime,
                    (n < 1000) ? "ns" : "ms", comparisons, assignments);
        }
    }

    public void doBubbleSortG() {
        int n = array.length;
        boolean swapped;
        int tmp;
        long startTime = System.nanoTime();
        int comparisons = 0, assignments = 0;

        for (int i = 0; i < n; i++) {
            swapped = false;
            for (int j = 1; j < (n - i); j++) {
                comparisons++;
                if (array[j - 1] > array[j]) {
                    tmp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = tmp;

                    assignments++;

                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = (n < 1000) ? (endTime - startTime) : (endTime - startTime) / 1000000;

        if (debug) {
            System.out.printf("BubbleSort with Sentinel / %d / %d %s / %d comparisons/ %d assignments%n", n,
                    elapsedTime, (n < 1000) ? "ns" : "ms", comparisons, assignments);
        }
    }

    public void doCocktailSort() {
        boolean swapped = true;
        int n = array.length;
        int start = 0;
        int end = array.length;
        int comparisons = 0, assignments = 0;
        long startTime = System.nanoTime();

        while (swapped) {
            swapped = false;

            for (int i = start; i < end - 1; ++i) {
                comparisons++;
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    assignments++;
                    swapped = true;
                }
            }

            if (!swapped)
                break;

            swapped = false;

            --end;

            for (int i = end - 1; i >= start; --i) {
                comparisons++;
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    assignments++;
                    swapped = true;
                }
            }

            ++start;
        }

        long endTime = System.nanoTime();
        long elapsedTime;

        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("Coctail Sort / %d / %d %s / %d comparisons/ %d assignments%n", n, elapsedTime,
                    (n < 1000) ? "ns" : "ms", comparisons, assignments);
        }
    }

    public void doInsertionSort() {
        int n = array.length;
        int comparisons = 0, assignments = 0;
        long startTime = System.nanoTime();

        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i] > key)) {
                array[i + 1] = array[i];
                i--;
                comparisons++;
                assignments++;
            }
            array[i + 1] = key;
            assignments++;
        }

        long endTime = System.nanoTime();
        long elapsedTime;

        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("Insertion Sort / %d / %d %s / %d comparisons/ %d assignments%n", n, elapsedTime,
                    (n < 1000) ? "ns" : "ms", comparisons, assignments);
        }

    }

    public void doSelectionSort() {
        int min;
        int n = array.length;
        int comparisons = 0, assignments = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            min = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j] < array[min])
                    min = j;
            }

            if (min != i) {
                int tmp = array[min];
                array[min] = array[i];
                array[i] = tmp;
                assignments++;
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime;

        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("SelectionSort / %d / %d %s / %d comparisons/ %d assignments%n", n, elapsedTime,
                    (n < 1000) ? "ns" : "ms", comparisons, assignments);
        }
    }
    // Lab13 kolejne algorytmy sortowania

    public void doQuickSort() {
        long startTime = System.nanoTime();
        quickSort(0, array.length - 1);
        long endTime = System.nanoTime();
        long elapsedTime;
        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }
        if (debug) {
            System.out.printf("QuickSort / %d / %d %s%n", array.length, elapsedTime,
                    (array.length < 1000) ? "ns" : "ms");
        }
    }

    private void quickSort(int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            int pi = partiotion(lowIndex, highIndex);
            quickSort(lowIndex, pi - 1);
            quickSort(pi + 1, highIndex);
        }
    }

    private int partiotion(int lowIndex, int highIndex) {
        int pivot = array[highIndex];
        int i = lowIndex - 1;

        for (int j = lowIndex; j < highIndex; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, highIndex);
        return i + 1;
    }

    public void doJavaSort() {
        long startTime = System.nanoTime();
        Arrays.sort(array);
        long endTime = System.nanoTime();
        long elapsedTime;

        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("JavaSort / %d / %d %s %n", array.length, elapsedTime,
                    (array.length < 1000) ? "ns" : "ms");

        }
    }

    public void doMergeSort() {
        long startTime = System.nanoTime();
        mergeSort(0, array.length - 1);
        long endTime = System.nanoTime();
        long elapsedTime;
        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("MergeSort / %d / %d ms%n", array.length, elapsedTime,
                    (array.length < 1000) ? "ns" : "ms");
        }
    }

    private void mergeSort(int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(l, m);
            mergeSort(m + 1, r);
            merge(l, m, r);
        }
    }

    private void merge(int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = array[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = array[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    public void doCountingSort() {
        int n = array.length;
        long startTime = System.nanoTime();
        int max = Arrays.stream(array).max().getAsInt();

        int[] count = new int[max + 1];

        for (int i = 0; i < n; i++) {
            count[array[i]]++;
        }

        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        int[] output = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }

        System.arraycopy(output, 0, array, 0, n);
        long endTime = System.nanoTime();
        long elapsedTime;
        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("Counting Sort / %d/ time: %s %s,%n", array.length, elapsedTime,
                    (array.length < 1000) ? "ns" : "ms");
        }
    }

    public void doPriorityQueueSort() {
        long startTime = System.nanoTime();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int value : array) {
            pq.add(value);
        }
        int index = 0;
        while (!pq.isEmpty()) {
            array[index++] = pq.remove();
        }
        long endTime = System.nanoTime();
        long elapsedTime;
        if (array.length < 1000) {
            elapsedTime = endTime - startTime;
        } else {
            elapsedTime = (endTime - startTime) / 1000000;
        }

        if (debug) {
            System.out.printf("PriorityQueueSort / %d, elapsed time: %d %s ,%n", array.length, elapsedTime,
                    (array.length < 1000) ? "ns" : "ms");
        }
    }

    public void saveAs(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            if (array.length != 0) {
                for (int i : array) {
                    writer.println(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            ArrayList<Integer> tmpList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                tmpList.add(Integer.parseInt(line));
            }
            array = tmpList.stream().mapToInt(Integer::intValue).toArray();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void generateTestFile(String fileName, int n) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int i = 0; i < n; i++) {
                int randomNumber = (int) (Math.random() * 100);
                writer.println(randomNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void swap(int arr[], int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}

public class Main {
    public static void main(String[] args) {
        Sorter tab1 = new Sorter(10);
        Sorter tab2 = new Sorter(100);
        Sorter tab3 = new Sorter(100000);
        Sorter tab4 = new Sorter(1000);
        Sorter tab5 = new Sorter("liczby.txt");

        tab1.doQuickSort();
        tab1.saveAs("tab1.txt");

        tab2.doJavaSort();
        tab2.saveAs("tab2.txt");

        tab3.doMergeSort();
        tab3.saveAs("tab3.txt");

        tab4.doCountingSort();
        tab4.saveAs("tab4.txt");

        tab5.doPriorityQueueSort();
        tab5.saveAs("tab5.txt");

    }
}
