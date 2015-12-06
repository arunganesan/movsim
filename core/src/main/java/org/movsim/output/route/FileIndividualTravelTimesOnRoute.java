package org.movsim.output.route;

import org.movsim.input.ProjectMetaData;
import org.movsim.io.FileOutputBase;
import org.movsim.simulator.roadnetwork.routing.Route;
import org.movsim.simulator.vehicles.Vehicle;
import org.movsim.utilities.Units;

public class FileIndividualTravelTimesOnRoute extends FileOutputBase {

    private static final String extensionFormat = ".tt_individual.route_%s.csv";

    private static final String outputHeading = String.format("%s%9s, %10s, %10s, %10s, %10s, %10s %n", COMMENT_CHAR,
            "entryTime[s]", "exitTime[s]", "traveltime[s]", "meanSpeed[km/h]", "VehicleID", "VehicleLabel");
    private static final String outputFormat = "%10.2f, %10.2f, %10.2f, %10.2f, %d, %s %n";

    public FileIndividualTravelTimesOnRoute(Route route) {
        super(ProjectMetaData.getInstance().getOutputPath(), ProjectMetaData.getInstance().getProjectName());
        writer = createWriter(String.format(extensionFormat, route.getName()));
        writer.printf(outputHeading);
        writer.flush();
    }

    public void write(Vehicle vehicle, double entryTime, double exitTime, double routeLength) {
        double traveltime = exitTime - entryTime;
        double meanSpeed = (traveltime > 0) ? routeLength / traveltime : -1;
        write(outputFormat, entryTime, exitTime, traveltime, meanSpeed * Units.MS_TO_KMH, vehicle.getId(),
                vehicle.getLabel());
    }

}
