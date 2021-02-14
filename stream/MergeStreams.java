/**
 * Merge multiple streams in time order.
 *
 * There are n streams with endless Events coming.
 * Each stream of Events arrives in time order.
 * Please merge the n streams and output a stream with all the events in time order.
 * A stream can only be visited sequencially in time order; no random access;
 * 
 * Example:
 *  An event is expressed as a pair of long [eventValue, eventTime]
 *   stream 1: [1, 1], [1, 2], [1, 3], [1, 6]
 *   stream 2: [2, 1], [2, 3], [2, 5], [2, 6]
 *   stream 3: [3, 3], [3, 4], [3, 7]
 *
 *   output: [1,1][2,1][1,2][1,3][2,3][3,3][3,4][2,5][1,6][2,6][3,7]
 */

import java.util.*;

public class MergeStreams {
    
    public static void main(String[] args) {
        long[][] i1 = new long[][]{{1, 1}, {1, 2}, {1, 3}, {1, 6}};
        long[][] i2= new long[][]{{2, 1}, {2, 3}, {2, 5}, {2, 6}};
        long[][] i3= new long[][]{{3, 3}, {3, 4}, {3, 7}};

        Stream s1 = new QueueStream();
        Stream s2 = new QueueStream();
        Stream s3 = new QueueStream();

        MergeStreams ms = new MergeStreams();

        ms.buildStream(s1, i1);
        ms.buildStream(s2, i2);
        ms.buildStream(s3, i3);

        Stream ans = ms.merge(Arrays.asList(s1, s2, s3));
        System.out.println(ans);
    }

    public Stream merge(List<Stream> streams) {
        Stream out = new QueueStream();
        int i;
        while ((i = nextStreamToPoll(streams)) != -1) {
            out.add(streams.get(i).next()); 
        }
        return out;
    }

    private int nextStreamToPoll(List<Stream> streams) {
        long min = Long.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < streams.size(); i++) {
            if (streams.get(i).hasNext()) {
                long time = streams.get(i).peek().time;
                if (time < min) {
                    min = time;
                    minIndex = i;
                }
            }
        }
        return minIndex;
    }


    private void buildStream(Stream s, long[][] input) {
        for (long[] i : input) {
            s.add(new Event(Long.valueOf(i[0]), i[1]));
        }
    }

    public static interface Stream {
        boolean add(Event e);
        boolean hasNext();
        Event next();
        Event peek();
    }
    
    public static class QueueStream implements Stream {
        LinkedList<Event> q = new LinkedList<>();

        @Override
        public boolean add(Event e) {
            return q.offer(e);
        }

        @Override
        public boolean hasNext() {
            return !q.isEmpty();
        }

        @Override
        public Event next() {
            return q.poll();
        }

        @Override
        public Event peek() {
            return q.peek();
        }

        @Override
        public String toString() {
            return q.toString();
        }
    }

    public static class Event {
        Object value;
        long time;

        public Event(Object value, long time) {
            this.value = value;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("[%s, %d]", value.toString(), time);
        }
    }
}
