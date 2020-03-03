/**
1353. Maximum Number of Events That Can Be Attended
Medium

Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.

You can attend an event i at any day d where startTimei <= d <= endTimei. Notice that you can only attend one event at any time d.

Return the maximum number of events you can attend.


Example 1:

Input: events = [[1,2],[2,3],[3,4]]
Output: 3
Explanation: You can attend all the three events.
One way to attend them all is as shown.
Attend the first event on day 1.
Attend the second event on day 2.
Attend the third event on day 3.

Example 2:
Input: events= [[1,2],[2,3],[3,4],[1,2]]
Output: 4

Example 3:
Input: events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
Output: 4

Example 4:
Input: events = [[1,100000]]
Output: 1

Example 5:
Input: events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
Output: 7

Example 6:
Input: events = [[1,2],[1,2],[3,3],[1,5],[1,5]]
Output: 5

Constraints:

1 <= events.length <= 10^5
events[i].length == 2
1 <= events[i][0] <= events[i][1] <= 10^5 

 */
import java.util.Arrays;
import java.util.PriorityQueue;

public class MaxNumEvents {

    public static void main(String[] args) {
        int[][] events = new int[][]{
            //{1,5},{1,5},{1,5},{2,3},{2,3}
            {1,4},{4,4},{2,2},{3,4},{1,1}
        };
        System.out.printf("[INPUT] events=[");
        for (int i = 0; i < events.length; i++) {
            if (i != 0) System.out.printf(", ");
            System.out.printf("%s", Arrays.toString(events[i]));
        }
        System.out.printf("]%n");
        MaxNumEvents mne = new MaxNumEvents();
        int ans = mne.maxEvents(events);
        System.out.printf("ans = %s%n", ans);
    }

    /**
     * copyright: based on solution by LeetCode user "lee215", 
     *    who posted this solution in the discussions section. 
     *    Idea: select the soonest ending event among events that starts with a
     *          day for all the days starting from the first to the last.
     *          This is a greedy algorithm, which means that greedily seeking a 
     *          local optimum at each step happily results in a global optimum.
     *          Greedy algorithms only works for certain problems.
     *    Steps:
     *    1) sort the events by start day.
     *    2) create a priority queue to hold the end days of 
     *        events using the natural ordering of the integer.
     *    3) starting with the first event and day 0,
     *    4) loop if there are 
     *          events remaining to be added to the queue 
     *       or the priority is not empty.
     *      4.1) if the queue is empty, jump to the start day of
     *           the current event because there are no events between
     *           the given day and the day to jump to (exclusive).
     *      4.2) if there are events remaining,
     *            push all the end day of events starting with
     *            this day to the priority queue, using the end day's
     *            natural ordering as the priority. 
     *      4.3) poll the priority queue to remove the top of the queue,
     *            which is the end day of the event to be attended for the day;
     *            increase the count of answer.
     *      4.4) increment the day;
     *      4.5) while the queue is not empty and there are end days in the queue
     *           that is before the day, remove them because they cannot be attended.
     *    5) return the answer
     *
     * Time complexity: O(nlogn) -- sorting O(nlogn) + PriorityQueue and looping O(nlogn)
     * Space complexity: O(n) -- PriorityQueue O(n)
     */
    public int maxEvents(int[][] events) {
        if (events == null) return 0;
        int n = events.length;
        if (n <= 1) return n;
        
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));
        
        int i = 0;
        int d = 0;
        int ans = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        while (!pq.isEmpty() || i < n) {
            if (pq.isEmpty()) d = events[i][0];   
            while (i < n && events[i][0] == d) {
                pq.offer(events[i++][1]); 
            }
            pq.poll();
            ans++;
            d++;
            while (!pq.isEmpty() && pq.peek() < d) {
                pq.poll(); 
            }
        }
        
        return ans;
    }

}
