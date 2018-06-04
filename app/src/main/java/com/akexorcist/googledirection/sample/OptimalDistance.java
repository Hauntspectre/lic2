package com.akexorcist.googledirection.sample;

import java.util.ArrayList;

/**
 * Created by octavian on 5/27/2018.
 */

public class OptimalDistance {
    double dist;

    /* Uses Pythagorean theorem to find the distance between two sets of coordinates */
    public double distance(City one, City two) {
        double a = Math.abs((two.getLatitude() - one.getLatitude()));
        double b = Math.abs((two.getLongitude() - one.getLongitude()));
        double distance = Math.sqrt((a * a) + (b * b));
        //System.out.println("distance:"+Double.toString(distance));
        return distance;
    }

    /* Calculate the distance of a tour */
    public double computeTourCost(ArrayList<City> tour) {
        double cost = 0.0;
        for(int i=0; i < tour.size() - 1; i++) {
            cost += distance(tour.get(i), tour.get(i+1));
        }
        return cost;
    }

    /* Copies one ArrayList over another */
    public void copyArrayListOverAnother(ArrayList<City> overwriteThis, ArrayList<City> copyThis) {
        overwriteThis.clear();
        for(int i=0; i < copyThis.size(); i++) {
            overwriteThis.add(copyThis.get(i));
        }
    }


    /* Parent method to the recursive computeOptimalTour method */
    ArrayList<City> computeOptimalTour(ArrayList<City> islands, double maxDistance) {
        OptimalDistance optimalDistance = new OptimalDistance();
        optimalDistance.dist = Double.MAX_VALUE;
        int initialSize = islands.size();
        ArrayList<City> workingTour = new ArrayList<City>(initialSize);
        ArrayList<City> optimalTour = new ArrayList<City>(initialSize);
        computeOptimalTour(workingTour, islands, maxDistance, optimalTour, optimalDistance);
        System.out.println("optimal distance: "+optimalDistance.dist);
        return optimalTour;
    }

    /* Recursive Breadth-First Search Method */
    public void computeOptimalTour(ArrayList<City> tourSoFar, ArrayList<City> remaining, double maxDistance, ArrayList<City> optimalTour, OptimalDistance optimalDistance) {

        if(remaining.isEmpty()) {
            tourSoFar.add(tourSoFar.get(0));
            double totalCost = computeTourCost(tourSoFar);
            if(totalCost < optimalDistance.dist) {
                copyArrayListOverAnother(optimalTour, tourSoFar);
                optimalDistance.dist = totalCost;
            }
            tourSoFar.remove(tourSoFar.size() - 1);
            return;
        }

        for(int i = 0; i < remaining.size(); i++) {
            City next = (City) remaining.get(i);
            remaining.remove(i);
            tourSoFar.add(next);
            computeOptimalTour(tourSoFar, remaining, maxDistance, optimalTour, optimalDistance);
            tourSoFar.remove(tourSoFar.size() - 1);
            remaining.add(i, next);
        }

    }

}
