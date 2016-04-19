package no.ntnu.iie.stud.cateringstorm.Maps;

/**
 * Created by Chris on 14.04.2016.
 */

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import no.ntnu.iie.stud.cateringstorm.gui.util.Coordinate;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class MapBackend {
    private static final Coordinate HOME_LOCATION =  addressToPoint("Elgesetergate 1, Trondheim, Norway");

    public static void main(String[] args) {

        ArrayList<Coordinate> addressList = new ArrayList<>();
        ArrayList<Coordinate> pointList = new ArrayList<>();

        Coordinate midtByen = new Coordinate(10.38980484008789, 63.4289094478486);
        Coordinate saksvik = new Coordinate(10.612106323242188, 63.43380650358365);
        Coordinate valgrind = new Coordinate(10.425596237182617, 63.41586854601292);
        Coordinate noot = new Coordinate(10.353240966796875, 63.41774218303736);
        Coordinate audun = new Coordinate(10.421905517578125, 63.394534542417105);
        Coordinate plass1 = new Coordinate(10.42564343636363, 63.403443636363336);
        Coordinate plass2 = new Coordinate(10.213435353535323, 63.44456663434353);

        addressList.add(addressToPoint("Stibakken 2, Malvik, Norway"));
        addressList.add(addressToPoint("Valgrindvegen 12, Trondheim, Norway"));
        addressList.add(addressToPoint("AndersBanan tvereggensveg 2, Trondheim, Norway"));

        pointList.add(midtByen);
        pointList.add(saksvik);
        pointList.add(valgrind);
        pointList.add(noot);
        pointList.add(audun);
        pointList.add(plass1);
        pointList.add(plass2);

        /*for (double[] ayy : pointList){
            System.out.println(ayy[1] + " " + ayy[0] + "     ");
        }*/

        /*for (double[] ayy : createFastestRoute(pointList)){
            System.out.println(ayy[1] + " " + ayy[0] + "     ");
        }*/

        /*for (double[] ayy : getRandomList(pointList)) {
            System.out.println(ayy[1] + " " + ayy[0] + "     ");
        }*/

        //System.out.println(getTotalDistanceList(getRandomList(pointList)));

        for (Coordinate ayy : getShortestRoute(addressList)) {
            System.out.println(ayy);
        }
    }

    public static Coordinate addressToPoint(String address) {

        Geocoder geocoder = new Geocoder();
        GeocoderRequest geoRequest = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
        GeocodeResponse geocodeResponse = new GeocodeResponse();

        try {
            geocodeResponse = geocoder.geocode(geoRequest);
        } catch (IOException ioe) {
            System.out.println("error");
        }

        List<GeocoderResult> results = geocodeResponse.getResults();

        if(geocodeResponse.getStatus() != GeocoderStatus.OK) {
            System.err.println("Geocoder failed: " + geocodeResponse.getStatus());
            return null;
        }

        double latitude = results.get(0).getGeometry().getLocation().getLat().doubleValue();
        double longitude = results.get(0).getGeometry().getLocation().getLng().doubleValue();

        //System.out.println("Place: " + address + " At:   Latitude: " + latitude + " Longitude: " + longitude);

        return new Coordinate(latitude, longitude);
    }


    //fra nettet
    public static double distFrom(Coordinate from, Coordinate to) {
        double earthRadius = 6371.0; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLng = Math.toRadians(to.getLongitude() - from.getLongitude());
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude()));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }

    public static boolean isFirstAddressCloserToLast(Coordinate fromAddress1, Coordinate fromAddress2, Coordinate toAddress) {

        if (distFrom(fromAddress1, toAddress) > distFrom(fromAddress2, toAddress)) {
            return false;
        } else if (distFrom(fromAddress1, toAddress) < distFrom(fromAddress2, toAddress)) {
            return true;
        } else {
            return new Boolean(null);
        }
    }

    public static ArrayList<Coordinate> createFastestRoute(ArrayList<Coordinate> route) {

        //init Arrays
        ArrayList<Coordinate> sortedList = new ArrayList<>();
        ArrayList<Coordinate> copy = new ArrayList<>(route);

        //First point
        Coordinate startPoint = route.get(0);
        sortedList.add(startPoint);

        sortedList.add(sortLoop(route, sortedList.get(0)));
        sortedList.add(sortLoop(route, sortedList.get(1)));
        sortedList.add(sortLoop(route, sortedList.get(2)));
        sortedList.add(sortLoop(route, sortedList.get(3)));
        sortedList.add(copy.get(0));

        return sortedList;

    }

    public static ArrayList<Coordinate> getShortestRoute(ArrayList<Coordinate> route){

        ArrayList<Coordinate> lowestList = new ArrayList<>();
        ArrayList<Coordinate> tempList;
        double lowest = 100000;
        double temp;

        for (int i = 0; i < 1000; i++) {
            tempList = getRandomList(route);
            temp = getTotalDistanceList(tempList);
            if (temp < lowest){
                lowest = temp;
                lowestList = tempList;
            }
        }
        return lowestList;
    }

    public static double getTotalDistanceList(ArrayList<Coordinate> route) {
        double totalDistance = 0.0;

        for (int i = 0; i+1 < route.size(); i++){
            totalDistance += distFrom(route.get(i), route.get(i+1));
        }
        totalDistance += distFrom(route.get(route.size()-1), route.get(0));
        return totalDistance;
    }

    public static ArrayList<Coordinate> getRandomList(ArrayList<Coordinate> route) {

        ArrayList<Coordinate> copy = new ArrayList<>(route);
        ArrayList<Coordinate> randomList = new ArrayList<>();

        Random generateRandomInteger = new Random();
        int random = 0;

        randomList.add(HOME_LOCATION);


        for (int i = 0; i < route.size(); i++){

            random = generateRandomInteger.nextInt(copy.size());

            randomList.add(copy.get(random));
            copy.remove(random);

        }

        randomList.add(HOME_LOCATION);

        return randomList;
    }

    public static Coordinate sortLoop(ArrayList<Coordinate> route, Coordinate previousPoint) {

        ArrayList<Double> distanceTable = new ArrayList<>();
        Coordinate point = null;

        for (int i = 0; i < route.size(); i++) {
            if (distFrom(previousPoint, route.get(i)) != 0.0) {
                distanceTable.add(distFrom(previousPoint, route.get(i)));
            }
        }
        Collections.sort(distanceTable);
        for (int i = 0; i < route.size(); i++) {
            if (distanceTable.get(0) == distFrom(previousPoint, route.get(i))) {
                point = route.get(i);
                //Remove second point. This is to make sure the route continues.
                route.remove(previousPoint);
            }
        }
        return point;
    }
}