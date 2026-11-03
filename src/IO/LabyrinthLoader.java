/**
 * Nome: Maria Leonor Ferreira Araujo
 * Número: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Número: 8240332
 * Turma: LEI2T4
 */
package IO;

import Challenges.*;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

import Labyrinth.Labyrinth;
import Labyrinth.Chamber;
import Labyrinth.Hall;
import Labyrinth.IChamber;
import Labyrinth.ChamberType;

/**
 * Utility class responsible for loading and constructing the Labyrinth structure
 * from a JSON file configuration.
 */
public class LabyrinthLoader {

    /**
     * Represents the root structure of the JSON file containing arrays of chambers and halls.
     * Used for Gson deserialization.
     */
    private static class JsonLabyrinth {
        JsonChamber[] chambers;
        JsonHalls[] halls;
    }

    /**
     * Represents the JSON structure for a single chamber, including ID, description, and types.
     */
    private static class JsonChamber {
        int id;
        String description;
        ChamberType type;
        ChallengeType challengeType;
    }

    /**
     * Represents the JSON structure for connections (halls) between chambers.
     */
    private static class JsonHalls {
        int from;
        int to;
        boolean hasEvent;
    }

    /**
     * Parses the specified JSON file to construct the Labyrinth graph, initializing
     * chambers, challenges, and connecting halls.
     *
     * @param filePath The absolute or relative path to the JSON configuration file.
     * @return A fully constructed {@code Labyrinth} instance, or {@code null} if the file cannot be read or the data is invalid.
     */
    public static Labyrinth loadLabyrinth(String filePath) {
        Gson gson = new Gson();
        Labyrinth labyrinth = new Labyrinth();

        try (FileReader reader = new FileReader(filePath)) {
            JsonLabyrinth data = gson.fromJson(reader, JsonLabyrinth.class);

            if (data == null) {
                System.err.println("❌ Error: Invalid file.");
                return null;
            }

            if (data.chambers == null) {
                System.err.println("❌ Error: Could not load chamber.");
                return null;
            }

            int maxId = 0;
            for (JsonChamber chamber : data.chambers) {
                if (chamber.id > maxId) {
                    maxId = chamber.id;
                }
            }

            IChamber[] loadedChambers = new IChamber[maxId + 1];

            for (JsonChamber jsonChamber : data.chambers) {

                IChallenge challenge = createChallenge(jsonChamber.challengeType, jsonChamber.id);

                Chamber chamber = new Chamber(jsonChamber.id, jsonChamber.description, challenge);

                if (jsonChamber.type == ChamberType.TREASURE) {
                    chamber.setTreasure(true);

                } else if (jsonChamber.type == ChamberType.ENTRANCE) {
                    chamber.setEntrance(true);
                }

                labyrinth.addVertex(chamber);

                loadedChambers[jsonChamber.id] = chamber;
            }

            if (data.halls != null) {
                int eventCounter = 1;

                for (JsonHalls jsonHalls : data.halls) {
                    if (jsonHalls.from >= loadedChambers.length || jsonHalls.to >= loadedChambers.length) continue;

                    IChamber start = loadedChambers[jsonHalls.from];
                    IChamber end = loadedChambers[jsonHalls.to];

                    if (start != null && end != null) {

                        IEvent event = null;
                        if (jsonHalls.hasEvent) {
                            event = new Event(eventCounter++, "Suprise Event");
                        }

                        Hall hall = new Hall(event);
                        labyrinth.addCorridor(start, end, hall);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            return null;
        }

        return labyrinth;
    }

    /**
     * Factory method that creates specific IChallenge instances based on the provided type.
     *
     * @param type   The type of challenge to create (e.g., RIDDLE, LEVER).
     * @param hallId The ID associated with the chamber/challenge location.
     * @return An instance of {@code IChallenge}, or {@code null} if the type is NONE.
     */
    private static IChallenge createChallenge(ChallengeType type, int hallId) {
        if (type == null || type == ChallengeType.NONE) {
            return null;
        }

        switch (type) {
            case RIDDLE:
                return new Riddle(hallId, "Enigma");

            case LEVER:
                return new Lever(hallId, "Lever");

            default:
                return null;
        }
    }
}
