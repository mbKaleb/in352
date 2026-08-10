import java.util.Random;

public class Coordinate {
    

    public Coordinate(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public String display(){
        return "Latitude: " + this.latitude + "\nLongitude: " + this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public void add(double lat, double lon){
        this.latitude += lat;
        this.longitude += lon;
    }

    public Coordinate pingLocation(){
        double a = this.latitude +getRandomDelta();
        double b = this.longitude +getRandomDelta();
        return new Coordinate(a, b);
    }

    private double getRandomDelta(){
        int delta1 = randy.nextInt(5) -2;
        return (double) delta1 * 0.0001;
    }
    private double latitude;
    private double longitude;
    private Random randy = new Random();
}