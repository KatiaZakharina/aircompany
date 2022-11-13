import entities.airoport.Airport;
import entities.planes.ExperimentalPlane;
import models.ClassificationLevel;
import models.ExperimentalType;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import entities.planes.MilitaryPlane;
import entities.planes.PassengerPlane;
import entities.planes.Plane;

import java.util.Arrays;
import java.util.List;

public class AirportTest {
    private static final PassengerPlane PASSENGER_PLANE_WITH_MAX_PASSENGER_CAPACITY = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);
    private static final MilitaryPlane MILITARY_TRANSPORT_PLANE = new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT);

    private static final List<Plane> planes = Arrays.asList(
            PASSENGER_PLANE_WITH_MAX_PASSENGER_CAPACITY,
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            MILITARY_TRANSPORT_PLANE,
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalType.VTOL, ClassificationLevel.TOP_SECRET)
    );

    @Test
    public void testHasPlanes() {
        Assert.assertFalse(new Airport(planes).getPlanes().isEmpty());
    }

    @Test
    public void testHasTransportMilitaryPlanes() {
         Assert.assertTrue(new Airport(planes).getMilitaryPlanesOfType(MilitaryType.TRANSPORT).contains(MILITARY_TRANSPORT_PLANE));
    }

    @Test
    public void testHasFighterMilitaryPlanes() {
        Assert.assertFalse(new Airport(planes).getMilitaryPlanesOfType(MilitaryType.FIGHTER).isEmpty());
    }

    @Test
    public void testHasBomberMilitaryPlanes() {
        Assert.assertFalse(new Airport(planes).getMilitaryPlanesOfType(MilitaryType.BOMBER).isEmpty());
    }

    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        Assert.assertEquals(new Airport(planes).getPassengerPlaneWithMaxPassengersCapacity(), PASSENGER_PLANE_WITH_MAX_PASSENGER_CAPACITY);
    }

    @Test
    public void testSortByMaxLoadCapacity() {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();
        Assert.assertTrue(planesSortedByMaxLoadCapacity.get(0).getMaxLoadCapacity() >= planesSortedByMaxLoadCapacity.get(1).getMaxLoadCapacity());
    }

    @Test
    public void testSortExperimentalPlanesByMaxDistance() {
        Airport airport = new Airport(planes);
        airport.sortByMaxFlightDistance();
        List<? extends Plane> planesSortedByMaxFlightDistance = airport.getExperimentalPlanes();
        Assert.assertTrue(planesSortedByMaxFlightDistance.get(0).getMaxFlightDistance() <= planesSortedByMaxFlightDistance.get(1).getMaxFlightDistance());
    }

    @Test
    public void testSortExperimentalPlanesByMaxSpeed() {
        Airport airport = new Airport(planes);
        airport.sortByMaxSpeed();
        List<? extends Plane> planesSortedByByMaxSpeed = airport.getExperimentalPlanes();
        Assert.assertTrue(planesSortedByByMaxSpeed.get(0).getMaxSpeed() <= planesSortedByByMaxSpeed.get(1).getMaxSpeed());
    }

    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() {
        Assert.assertFalse(new Airport(planes).getMilitaryPlanesOfType(MilitaryType.BOMBER).isEmpty());
    }

    @Test
    public void testHasNotExperimentalPlanesWithClassificationLevelTypeWithConfidentialType(){
        Assert.assertFalse(new Airport(planes).getClassificationLevelsInExperimentalPlanes().contains(ClassificationLevel.CONFIDENTIAL));
    }

    @Test
    public void testExperimentalPlanesHasOnlyClassificationLevelHigherThanUnclassified(){
        Assert.assertFalse(new Airport(planes).getClassificationLevelsInExperimentalPlanes().contains(ClassificationLevel.UNCLASSIFIED));
    }
}
