package entities.airoport;

import entities.planes.ExperimentalPlane;
import models.ClassificationLevel;
import models.MilitaryType;
import entities.planes.MilitaryPlane;
import entities.planes.PassengerPlane;
import entities.planes.Plane;

import java.util.*;

public class Airport {
    private final List<? extends Plane> planes;

    public Airport(List<? extends Plane> planes) {
        this.planes = planes;
    }


    public List<? extends Plane> getPlanes() {
        return planes;
    }

    private <T extends Plane> List<T> getPlanesOfType(Class<T> planeType) {
        List<T> planesOfType = new ArrayList<>();

        for (Plane plane : this.planes) {
            if (planeType.isInstance(plane)) {
                planesOfType.add(planeType.cast(plane));
            }
        }

        return planesOfType;
    }

    public List<PassengerPlane> getPassengerPlanes() {
        return getPlanesOfType(PassengerPlane.class);
    }

    public List<MilitaryPlane> getMilitaryPlanes() {
        return getPlanesOfType(MilitaryPlane.class);
    }

    public List<ExperimentalPlane> getExperimentalPlanes() {
        return getPlanesOfType(ExperimentalPlane.class);
    }

    public List<ClassificationLevel> getClassificationLevelsInExperimentalPlanes() {
        List<ClassificationLevel> classificationLevels = new ArrayList<>();
        List<ExperimentalPlane> experimentalPlanes = getExperimentalPlanes();

        for (ExperimentalPlane experimentalPlane : experimentalPlanes) {
            classificationLevels.add(experimentalPlane.getClassificationLevel());
        }

        return classificationLevels;
    }

    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() {
        List<PassengerPlane> passengerPlanes = getPassengerPlanes();
        PassengerPlane planeWithMaxCapacity = passengerPlanes.get(0);

        for (PassengerPlane passengerPlane : passengerPlanes) {
            if (passengerPlane.getPassengersCapacity() > planeWithMaxCapacity.getPassengersCapacity()) {
                planeWithMaxCapacity = passengerPlane;
            }
        }

        return planeWithMaxCapacity;
    }

    public List<MilitaryPlane> getMilitaryPlanesOfType(MilitaryType militaryPlaneType) {
        List<MilitaryPlane> militaryPlanesOfType = new ArrayList<>();
        List<MilitaryPlane> militaryPlanes = getMilitaryPlanes();

        for (MilitaryPlane plane : militaryPlanes) {
            if (plane.getType() == militaryPlaneType) {
                militaryPlanesOfType.add(plane);
            }
        }

        return militaryPlanesOfType;
    }

    public Airport sortByMaxFlightDistance() {
        planes.sort(Comparator.comparingInt(Plane::getMaxFlightDistance));
        return this;
    }

    public Airport sortByMaxSpeed() {
        planes.sort(Comparator.comparingInt(Plane::getMaxSpeed));
        return this;
    }

    public Airport sortByMaxLoadCapacity() {
        planes.sort(Comparator.comparingInt(Plane::getMaxLoadCapacity));
        return this;
    }

    @Override
    public String toString() {
        return "airoport.Airport{" +
                "Planes=" + planes.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(planes, airport.planes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planes);
    }
}
