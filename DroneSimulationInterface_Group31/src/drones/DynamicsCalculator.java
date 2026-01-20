package drones;

import java.util.ArrayList;
import java.util.List;

/**
 * This class collects speed data from drone dynamics, calculates the average speed,
 * and computes the total distance traveled.
 */
public class DynamicsCalculator {

    private static List<Double> speeds; // List to store drone speeds
    public static double totalDistance; // Total distance traveled in kilometers
    public static double avgSpeed; // Average speed of the drones

    /**
     * Constructor to initialize the speed list and total distance.
     */
    public DynamicsCalculator() {
        speeds = new ArrayList<>();
        totalDistance = 0.0;
    }

    /**
     * Adds a drone's speed to the list and updates the total distance.
     * 
     * @param speed The drone's speed in km/h.
     */
    public void addSpeed(double speed) {
        speeds.add(speed);
        // Since speed is in km/h and updates occur every second,
        // the speed in km/h also represents the distance traveled in km per hour.
        // To convert it to km per second, we divide the speed by 3600.
        totalDistance += speed / 3.6;
    }

    /**
     * Calculates the average speed of the drones.
     * 
     * @return The average speed in km/h.
     */
    public static double calculateAverageSpeed() {
        if (speeds.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double speed : speeds) {
            sum += speed;
        }
        return avgSpeed = sum / speeds.size();
    }

    /**
     * Returns the calculated average speed.
     * 
     * @return The average speed in km/h.
     */
    public static double getAvgSpeed() {
        return avgSpeed;
    }

    /**
     * Returns the total distance traveled by all drones.
     * 
     * @return The total distance in kilometers, rounded to two decimal places.
     */
    public static double getTotalDistance() {
        return Math.round(totalDistance * 100) / 100;
    }

    /**
     * Resets the speed list and total distance.
     */
    public static void reset() {
        speeds.clear();
        totalDistance = 0.0;
    }

    /**
     * Returns the list of collected speeds.
     * 
     * @return A list of collected speeds.
     */
    public List<Double> getSpeeds() {
        return speeds;
    }
}