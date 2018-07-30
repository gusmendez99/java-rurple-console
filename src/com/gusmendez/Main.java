package com.gusmendez;

import com.gusmendez.model.PileCoin;
import com.gusmendez.model.Robot;
import com.gusmendez.model.Wall;
import com.gusmendez.util.ReadUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.gusmendez.model.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        System.out.print("Ingrese la ruta del archivo del mapa: ");
        String mapPath = ReadUtil.readString();
        Map map = getMap(mapPath);
        System.out.print(map);

        System.out.print("Ingrese la ruta del archivo de istrucciones: ");
        String instructionsPath = ReadUtil.readString();
        List<String> instructionsList = getInstructions(instructionsPath);

        int step = 0;
        boolean hasErrorInstructions = false;
        System.out.println("Ejecutando...");
        do {
            hasErrorInstructions = map.placeInstruction(instructionsList.get(step));
            if(!hasErrorInstructions){
                System.out.println(map);   //Overriding toString in Map class
                step++;
                System.out.print("-> Presiona enter: ");
                ReadUtil.readString();
            } else {
                System.out.println("ERROR AL EJECUTAR COMANDO, REVISE SUS INSTRUCCIONES...");
            }
        } while (step < instructionsList.size() && !hasErrorInstructions && !map.hasRobotPickAllCoins());

        if(map.hasRobotPickAllCoins()){
            System.out.println("Felicidades, el robot recogio todas las monedas del mapa ingresado");
        } else {
            System.out.println("Wow! el robot no recogio todas las monedas del mapa ingresado");
        }
    }

    private static List<String> getInstructions(String instructionsPath) {
        final List<String> instructionsList = new ArrayList<>();

        try {
            Stream<String> lines = Files.lines (
                    Paths.get(instructionsPath),
                    StandardCharsets.UTF_8
            );
            lines.forEach(line -> {
                try {
                    if(Map.isValidInstruction(line)){
                        instructionsList.add(line);
                    } else {
                        Exception e = new Exception("Existe una instruccion invalida, revise su archivo");
                        throw e;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        } catch (IOException exception) {
            System.out.println("Error al leer instrucciones!");
        }
        return instructionsList;

    }

    private static Map getMap(String mapPath){
        final Map map = new Map();

        try {
            Stream<String> lines = Files.lines(
                    Paths.get(mapPath),
                    StandardCharsets.UTF_8
            );

            map.setHeight(65);
            map.setWidth(65);
            AtomicInteger atomicInteger = new AtomicInteger();

            lines.forEach(line -> {
                int row = atomicInteger.getAndIncrement(); //Index of the line that its

                for(int column = 0; column < line.length(); column++){
                    String charToEvaluate = line.substring(column, column + 1);
                    if(!charToEvaluate.equals("0")){
                        if(!ReadUtil.isNumber(charToEvaluate)){
                            try {
                                switch (charToEvaluate){
                                    case "*":
                                        map.addWall(new Wall(row, column));
                                        break;
                                    case "^":
                                        map.setRobot(new Robot(row, column, 0));
                                        System.out.println("Robot Added");
                                        break;
                                    case ">":
                                        map.setRobot(new Robot(row, column, 1));
                                        System.out.println("Robot Added");
                                        break;
                                    case "v":
                                        map.setRobot(new Robot(row, column, 2));
                                        System.out.println("Robot Added");
                                        break;
                                    case "<":
                                        map.setRobot(new Robot(row, column, 3));
                                        System.out.println("Robot Added");
                                        break;
                                    default:
                                        throw new Exception("Hay un error en el mapa, intente de nuevo...");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            int coins = Integer.parseInt(charToEvaluate);
                            map.addPileCoins(new PileCoin(row, column, coins));
                            System.out.println("Pile of Coins Added");
                        }
                    }
                }
            });

        } catch (IOException exception) {
            System.out.println("Error al generar mapa!");
        } finally {
            System.out.println("Mapa cargado exitosamente");
        }

        return map;
    }

}
