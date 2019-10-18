package ecobeeguangdewang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * 2018-04-04
 * 
 * requires java version 8
 * for ecobee interview
 * 
 * to run this class:
 *  first mvn packge
 *  then use command java -jar target/ecobeeguangdewang-1.0-SNAPSHOT.jar 
 * 
 * this class uses stdin for inputing data and query sections
 * and stdout for outputting results.
 * 
 * when doing input: 
 *  input an empty line to start the query section.
 *  use control-D to mark the end of your input.
 * 
 * @author Guangde Wang
 */
public class OwnerRating {

    private static String FIELD_DELIMITER = "\" ";

    private Map<String, Double> ownerR = new HashMap<>();
    private List<LocationAndR> locationR = new ArrayList<>();
    private List<String> queries = new ArrayList<>();

    /**
     * to run this class:
     *  first mvn packge
     *  then use command java -jar target/ecobeeguangdewang-1.0-SNAPSHOT.jar 
     * 
     * this class uses stdin for inputing data and query sections
     * and stdout for outputting results.
     * 
     * when doing input: 
     *  input an empty line to start the query section.
     *  use control-D to mark the end of your input.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OwnerRating ownerRating = new OwnerRating();
        ownerRating.scanStdIn();
        for (String query : ownerRating.queries) {
            ownerRating.doQuery(query);
        }
    }

    private void doQuery(String query) {
        String[] pair = parseQuery(query);
        String queryOwner = pair[0];
        final String qLocation = pair[1];
        if (!ownerR.containsKey(queryOwner)) {
            System.out.println(query + " \"unknown\"");
            return;
        }
        final double thresholdR = ownerR.get(queryOwner);
        final AtomicLong numBetterHomes = new AtomicLong(0);

        Stream<LocationAndR> stream = locationR.parallelStream();
        long numTotalHomes = stream.filter(e -> {
                String lowercaseLocation = e.getLocation().toLowerCase();
                return lowercaseLocation.equals(qLocation + "\"")
                    || lowercaseLocation.startsWith(qLocation + "/");
                })
            .peek(e -> {
                    if (e.getR() > thresholdR) {
                        numBetterHomes.addAndGet(1);
                    }
                })
            .count();
        stream.close();

        if (numTotalHomes > 0) {
            double percentage = 100.0d * numBetterHomes.get()/numTotalHomes;
            int rating = getRating(percentage);
            System.out.println(query + " " + rating);
        } else {
            System.out.println(query + " \"unknown\"");
        }
    }

    private void scanStdIn() {
        boolean isDataSection = true;
        Scanner stdIn = new Scanner(System.in);
        while(stdIn.hasNextLine()) {
            String line = stdIn.nextLine();
            if (line == null || line.isEmpty()  ) {
                isDataSection = false;
                continue;
            }
            if (isDataSection) {
                populateData(line);
            } else {
                populateQuery(line);
            }
        }
    }

    private void populateData(String line) {
        String[] ownerLocationR = line.split(FIELD_DELIMITER);
        String owner = ownerLocationR[0] + "\"";
        String location = ownerLocationR[1] + "\"";
        String rs = ownerLocationR[2];
        double r = Double.parseDouble(rs);
        ownerR.put(owner, r);
        locationR.add(new LocationAndR(location, r));
    }

    private void populateQuery(String line) {
        queries.add(line);
    }
    private String[] parseQuery(String query) {
        String[] ownerAndLocation = query.split(FIELD_DELIMITER);
        String owner = ownerAndLocation[0] + "\"";
        String location = ownerAndLocation[1];
        if (location.endsWith("\"")) {
            location = location.substring(0, location.length() - 1);
        }
        return new String[]{owner, location.toLowerCase()};
    }
 
    private int getRating(double percentage) {
        if (percentage < 10.0d) {
            return 10;
        } else if (percentage < 20.0d) {
            return 9;
        } else if (percentage < 30.0d) {
            return 8;
        } else if (percentage < 40.0d) {
            return 7;
        } else if (percentage < 50.0d) {
            return 6;
        } else if (percentage < 60.0d) {
            return 5;
        } else if (percentage < 70.0d) {
            return 4;
        } else if (percentage < 80.0d) {
            return 3;
        } else if (percentage < 90.0d) {
            return 2;
        }
        return 1;
    }
}
