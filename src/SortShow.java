/**
 Author: Scott Avery, Sam Alvizo
 Scott and Sam worked on this assignment as a team
 */


//importing the libraries that will be needed in this program

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Random;

//The class that has all the sorts in it
public class SortShow extends JPanel {


    // An array to hold the lines_lengths to be sorted
    public int[] lines_lengths;
    //The amount of lines needed
    public final int total_number_of_lines = 256;
    // An array to holds the scrambled lines_lengths
    public int[] scramble_lines;
    //A temp Array that is used later for sorts
    public int[] tempArray;
    //the default constructor for the SortShow class
    public SortShow(){
        //assigning the size for the lines_lengths below
        lines_lengths = new int[total_number_of_lines];
        for(int i = 0; i < total_number_of_lines; i++)
            lines_lengths[i] =  i+5;

    }


    //A method that scrambles the lines
    public void scramble_the_lines(){
        //A random generator
        Random num = new Random();
        //Randomly switching the lines
        for(int i = 0; i < total_number_of_lines; i++){
            //getting a random number using the nextInt method (a number between 0 to i + 1)
            int j = num.nextInt(i + 1);
            //swapping The element at i and j
            swap(i, j);
        }
        //assigning the size for the scramble_lines below
        scramble_lines = new int[total_number_of_lines];
        //copying the now scrambled lines_lengths array into the scramble_lines array
        //to store for reuse for other sort methods
        //so that all sort methods will use the same scrambled lines for fair comparison
        for (int i = 0; i < total_number_of_lines; i++)
        {
            scramble_lines[i] = lines_lengths[i];
        }
        //Drawing the now scrambled lines_lengths
        paintComponent(this.getGraphics());
    }

    //Swapping method that swaps two elements in the lines_lengths array
    public void swap(int i, int j){
        //storing the i element in lines_lengths in temp
        int temp = lines_lengths[i];
        //giving i element in lines_lengths the value of j element in lines_lengths
        lines_lengths[i] = lines_lengths[j];
        //giving j element in lines_lengths the value of temp
        lines_lengths[j] = temp;
    }

    //The selectionSort method
    public void SelectionSort(){
        //getting the date and time when the selection sort starts
        Calendar start = Calendar.getInstance();
        //Using the selection sort to lines_lengths sort the array
        for (int index = 0 ; index < total_number_of_lines - 1 ; index++) {
            int indexOfNextSmallest = getIndexOfSmallest (index, total_number_of_lines - 1);
            swap (index, indexOfNextSmallest);
            paintComponent(this.getGraphics());
            delay(10);
        }
        Calendar end = Calendar.getInstance();
        //getting the time it took for the selection sort to execute
        //subtracting the end time with the start time
        SortGUI.selectionTime = end.getTime().getTime() - start.getTime().getTime();
    }

    //this method gets the smallest element in the array of lines_lengths
    public int getIndexOfSmallest(int first, int last){
        int min = lines_lengths[first];
        int indexOfMin = first;
        //Looping through the array to look for minimum value
        for (int index = first + 1; index <= last; index++) {
            if (lines_lengths[index] < min){
                min = lines_lengths[index];
                indexOfMin = index;
            }
        }
        //Returning the minimum value
        return indexOfMin;
    }

    ///////////////////////////////////////////////////////////////////////////////////

    //recursive merge sort method
    public void R_MergeSort(){
        //getting the date and time when the recursive merge sort starts
        Calendar start = Calendar.getInstance();
        //Calling Merge Sort function
        R_MergeSort(0, total_number_of_lines-1);
        //You need to complete this part.
        Calendar end = Calendar.getInstance();
        //getting the time it took for the iterative merge sort to execute
        //subtracting the end time with the start time
        SortGUI.rmergeTime = end.getTime().getTime() - start.getTime().getTime();

    }

    //recursive merge sort method
    public void R_MergeSort(int first, int last){
        //If first index is less than last index, start sort
        if(first < last){
            //Assigning middle index
            int mid = (first + last) / 2;
            R_MergeSort(first, mid);
            R_MergeSort(mid + 1, last);
            R_Merge(first, mid, last);
            //You need to complete this part.
            //Update graph
            paintComponent(this.getGraphics());
            //Causing a delay for 10ms
            delay(10);
        }
    }


    //recursive merge sort method
    public void R_Merge(int first, int mid, int last){
        //Getting size of first and second half of the array
        int firstSize = mid - first + 1;
        int lastSize = last - mid;
        //assigning the size for the tempArray below
        int firstHalf[] = new int[firstSize];
        int lastHalf[] = new int[lastSize];
        //Fill both arrays with elements in the original array
        for (int i = 0; i < firstSize; ++i)
            firstHalf[i] = lines_lengths[first + i];
        for (int j = 0; j < lastSize; ++j)
            lastHalf[j] = lines_lengths[mid + 1 + j];

        int i = 0, j = 0;
        int k = first;
        //Loop through both arrays
        while (i < firstSize && j < lastSize) {
            //Swap elements when firstHalf index is less than the secondHalf index
            if (firstHalf[i] <= lastHalf[j]) {
                lines_lengths[k] = firstHalf[i];
                i++;
            }
            else {
                lines_lengths[k] = lastHalf[j];
                j++;
            }
            k++;
        }
        //Fill original array with new arranged firstHalf
        while (i < firstSize) {
            lines_lengths[k] = firstHalf[i];
            i++;
            k++;
        }
        //Fill original array with new arranged lastHalf
        while (j < lastSize) {
            lines_lengths[k] = lastHalf[j];
            j++;
            k++;
        }
    }
    //You need to complete this part.
    //////////////////////////////////////////////////////////////////////////////////////////

    //iterative merge sort method
    public void I_MergeSort()
    {
        //getting the date and time when the iterative merge sort starts
        Calendar start = Calendar.getInstance();
        //assigning the size for the tempArray below
        tempArray = new int[total_number_of_lines];
        //saving the value of total_number_of_lines
        int beginLeftovers = total_number_of_lines;


        for (int segmentLength = 1; segmentLength <= total_number_of_lines/2; segmentLength = 2*segmentLength)
        {
            beginLeftovers = I_MergeSegmentPairs(total_number_of_lines, segmentLength);
            int endSegment = beginLeftovers + segmentLength - 1;
            if (endSegment < total_number_of_lines - 1)
            {
                I_Merge(beginLeftovers, endSegment, total_number_of_lines - 1);
            }
        }

        // merge the sorted leftovers with the rest of the sorted array
        if (beginLeftovers < total_number_of_lines) {
            I_Merge(0, beginLeftovers-1, total_number_of_lines - 1);
        }
        //getting the date and time when the iterative merge sort ends
        Calendar end = Calendar.getInstance();
        //getting the time it took for the iterative merge sort to execute
        //subtracting the end time with the start time
        SortGUI.imergeTime = end.getTime().getTime() - start.getTime().getTime();
    }

    // Merges segments pairs (certain length) within an array
    public int I_MergeSegmentPairs(int l, int segmentLength)
    {
        //The length of the two merged segments

        //You suppose  to complete this part (Given).
        int mergedPairLength = 2 * segmentLength;
        int numberOfPairs = l / mergedPairLength;

        int beginSegment1 = 0;
        for (int count = 1; count <= numberOfPairs; count++)
        {
            int endSegment1 = beginSegment1 + segmentLength - 1;

            int beginSegment2 = endSegment1 + 1;
            int endSegment2 = beginSegment2 + segmentLength - 1;
            I_Merge(beginSegment1, endSegment1, endSegment2);

            beginSegment1 = endSegment2 + 1;
            //redrawing the lines_lengths
            paintComponent(this.getGraphics());
            //Causing a delay for 10ms
            delay(10);
        }
        // Returns index of last merged pair
        return beginSegment1;
        //return 1;//modify this line
    }

    public void I_Merge(int first, int mid, int last)
    {
        //You suppose  to complete this part (Given).
        // Two adjacent sub-arrays
        int beginHalf1 = first;
        int endHalf1 = mid;
        int beginHalf2 = mid + 1;
        int endHalf2 = last;

        // While both sub-arrays are not empty, copy the
        // smaller item into the temporary array
        int index = beginHalf1; // Next available location in tempArray
        for (; (beginHalf1 <= endHalf1) && (beginHalf2 <= endHalf2); index++)
        {
            // Invariant: tempArray[beginHalf1..index-1] is in order
            if (lines_lengths[beginHalf1] < lines_lengths[beginHalf2])
            {
                tempArray[index] = lines_lengths[beginHalf1];
                beginHalf1++;
            }
            else
            {
                tempArray[index] = lines_lengths[beginHalf2];
                beginHalf2++;
            }
        }
        //redrawing the lines_lengths
        paintComponent(this.getGraphics());

        // Finish off the nonempty sub-array

        // Finish off the first sub-array, if necessary
        for (; beginHalf1 <= endHalf1; beginHalf1++, index++)
            // Invariant: tempArray[beginHalf1..index-1] is in order
            tempArray[index] = lines_lengths[beginHalf1];

        // Finish off the second sub-array, if necessary
        for (; beginHalf2 <= endHalf2; beginHalf2++, index++)
            // Invariant: tempa[beginHalf1..index-1] is in order
            tempArray[index] = lines_lengths[beginHalf2];

        // Copy the result back into the original array
        for (index = first; index <= last; index++)
            lines_lengths[index] = tempArray[index];
    }

    //////////////////////////////////////////////////////////////////////
    void incrementalInsertionSort(int first, int last, int space){
        int unsorted, index, t;
        //Setting and looping through first and last indices of the array
        for (unsorted=first+space; unsorted <= last; unsorted = unsorted+space) {
            t = lines_lengths[unsorted];
            //Space between indices of elements for sorting
            for (index = unsorted - space ; (index >= first) &&
                    (t-(lines_lengths[index]) < 0); index = index - space) {
                lines_lengths[index + space] = lines_lengths[index];
            }
            lines_lengths[index + space] = t;
        }
        //Updating graph
        paintComponent(this.getGraphics());
        //Delay for 10ms
        delay(10);
        // end for
    }
    void shellSort(int first, int last){
        int n = last - first+1;
        //Splitting array by 2 each time
        for(int space = n/2; space > 0; space = space / 2){
            //Looping through array
            for(int begin = first; begin < first + space; begin++){
                incrementalInsertionSort(begin, last, space);
            }
        }
    }
    public void shell()	{

        //getting the date and time when the shell sort starts
        Calendar start = Calendar.getInstance();
        shellSort(0, total_number_of_lines-1);
        //getting the date and time when the iterative merge sort ends
        Calendar end = Calendar.getInstance();
        //getting the time it took for the iterative merge sort to execute
        //subtracting the end time with the start time
        SortGUI.shellTime = end.getTime().getTime() - start.getTime().getTime();
    }
    //////////////////////////////////////////////////////////////////////
    //This method resets the window to the scrambled lines display
    public int partition(int left, int right)	{
        int n = lines_lengths[left];

        while(left <= right)	{
            // searching number that is greater than n
            while(lines_lengths[left] < n)	{
                left++;
            }
            // searching number that is less than n
            while(lines_lengths[right] > n)	{
                right--;
            }
            // swap values
            if(left <= right)	{
                int temp = lines_lengths[left];
                lines_lengths[left] = lines_lengths[right];
                lines_lengths[right] = temp;

                left++;
                right--;
            }
        }
        return left;
    }
    public void recursiveQuick(int start, int end)	{
        int n = partition(start, end);
        // call quicksort with left part of the array
        if(start < n - 1)	{
            recursiveQuick(start, n - 1);
        }
        // call quick sort with right part of the array
        if(end > n)	{
            recursiveQuick(n, end);
        }
        //Update graph
        delay(10);
        paintComponent(this.getGraphics());
    }
    public void quickSort()	{
        //getting the date and time quick sort starts
        Calendar start = Calendar.getInstance();

        // finish this ---------------------------------------------------------------------
        recursiveQuick(0, lines_lengths.length-1);

        //getting the date and time when the quick sort ends
        Calendar end = Calendar.getInstance();
        //getting the time it took for the quick sort took to execute
        //subtracting the end time with the start time
        SortGUI.quickTime = end.getTime().getTime() - start.getTime().getTime();
    }
    //////////////////////////////////////////////////////////////////////
    public void bubbleSort()	{
        //getting the date and time bubble sort starts
        Calendar start = Calendar.getInstance();
        int n = lines_lengths.length;
        //Loop through array
        for(int i = 0; i < n - 1; i++)	{
            for(int j = 0; j < n - i - 1; j++)	{
                //Swap values when next element is greater than last
                if(lines_lengths[j] > lines_lengths[j + 1])	{
                    int temp = lines_lengths[j];
                    lines_lengths[j] = lines_lengths[j + 1];
                    lines_lengths[j + 1] = temp;
                }
            }
            //Update graph
            delay(10);
            paintComponent(this.getGraphics());
        }
        //getting the date and time when the bubble sort ends
        Calendar end = Calendar.getInstance();
        //getting the time it took for the bubble sort took to execute
        //subtracting the end time with the start time
        SortGUI.bubbleTime = end.getTime().getTime() - start.getTime().getTime();
    }
    //////////////////////////////////////////////////////////////////////
    public void reset(){
        if(scramble_lines != null)
        {
            //copying the old scrambled lines into lines_lengths
            for (int i = 0; i < total_number_of_lines; i++)
            {
                lines_lengths[i] = scramble_lines[i] ;
            }
            //Drawing the now scrambled lines_lengths
            paintComponent(this.getGraphics());
        }
    }

    public void Insertion_Sort() {
        Calendar start = Calendar.getInstance();
        //Loop through array
        for (int i = 1; i < total_number_of_lines; ++i) {
            int key = lines_lengths[i];
            int j = i - 1;
            //Go through array to check if array at i is greater than the key
            while (j >= 0 && lines_lengths[j] > key) {
                //Index will be pushed one past its current position
                lines_lengths[j + 1] = lines_lengths[j];
                j = j - 1;
            }
            //Assign new key
            lines_lengths[j + 1] = key;
            //Update Graph
            paintComponent(this.getGraphics());
            delay(10);
        }
        Calendar end = Calendar.getInstance();
        SortGUI.insertionTime = end.getTime().getTime() - start.getTime().getTime();
    }

    //This method colours the lines and prints the lines
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //A loop to assign a colour to each line
        for(int i = 0; i < total_number_of_lines; i++){
            //using eight colours for the lines
            if(i % 8 == 0){
                g.setColor(Color.green);
            } else if(i % 8 == 1){
                g.setColor(Color.blue);
            } else if(i % 8 == 2){
                g.setColor(Color.yellow);
            } else if(i%8 == 3){
                g.setColor(Color.red);
            } else if(i%8 == 4){
                g.setColor(Color.black);
            } else if(i%8 == 5){
                g.setColor(Color.orange);
            } else if(i%8 == 6){
                g.setColor(Color.magenta);
            } else
                g.setColor(Color.gray);

            //Drawing the lines using the x and y-components
            g.drawLine(4*i + 25, 300, 4*i + 25, 300 - lines_lengths[i]);
        }

    }

    //A delay method that pauses the execution for the milliseconds time given as a parameter
    public void delay(int time){
        try{
            Thread.sleep(time);
        }catch(InterruptedException ie){
            Thread.currentThread().interrupt();
        }
    }

}

