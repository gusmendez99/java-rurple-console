package com.gusmendez;

import com.gusmendez.model.PileCoin;
import com.gusmendez.model.Robot;
import com.gusmendez.model.Wall;
import com.gusmendez.util.ReadUtil;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.gusmendez.model.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

//For File Choose in console
import javax.swing.JFileChooser;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.print("Ingrese la ruta del archivo del mapa: ");
        String mapPath = chooseAndGetFilePath("mapa");
        if(mapPath != null){
            Map map = getMap(mapPath);
            System.out.print(map);

            System.out.print("Ingrese la ruta del archivo de istrucciones: ");
            String instructionsPath = chooseAndGetFilePath("instrucciones");

            if(instructionsPath != null){
                List<String> instructionsList = getInstructions(instructionsPath);
                int step = 0;
                boolean hasErrorInstructions = false;
                System.out.println("Ejecutando...");
                do {
                    hasErrorInstructions = map.placeInstruction(instructionsList.get(step));
                    if(!hasErrorInstructions){
                        System.out.println(map);   //Overriding toString in Map class
                        step++;
                        try {
                            TimeUnit.SECONDS.sleep((long) 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("ERROR AL EJECUTAR COMANDO, REVISE SUS INSTRUCCIONES...");
                    }
                } while (step < instructionsList.size() && !hasErrorInstructions && !map.hasRobotPickAllCoins());

                if(map.hasRobotPickAllCoins()){
                    System.out.println("Felicidades, el robot recogio todas las monedas del mapa ingresado");
                } else {
                    System.out.println("Wow! el robot no recogio todas las monedas del mapa ingresado");
                }
                System.exit(0); //Doesnt finish after use a FileChooser, its necessary a exit method
            } else {
                System.out.println("Ocurrio un error al cargar la ruta de instrucciones, intente de nuevo...");
            }
        } else {
            System.out.println("Ocurrio un error al cargar la ruta del mapa, intente de nuevo...");
        }
    }

    private static String chooseAndGetFilePath(String fileName){
        FileDialog dialog = new FileDialog((Frame)null, "Seleccione " + fileName + " a abrir");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = (dialog.getDirectory() + dialog.getFile()).replace("\\", "/");
        System.out.println(file + " seleccionado.");
        return file;

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
                                        break;
                                    case ">":
                                        map.setRobot(new Robot(row, column, 1));
                                        break;
                                    case "v":
                                        map.setRobot(new Robot(row, column, 2));
                                        break;
                                    case "<":
                                        map.setRobot(new Robot(row, column, 3));
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
                        }
                    }
                }
            });
            //Get width because the previous stream has been closed
            Stream<String> linesMap = Files.lines(
                    Paths.get(mapPath),
                    StandardCharsets.UTF_8
            );

            //Set Dimensions
            map.setWidth(linesMap.findFirst().get().length());
            map.setHeight(atomicInteger.get());
        } catch (IOException exception) {
            System.out.println("Error al generar mapa!");
        } finally {
            System.out.println("Mapa cargado exitosamente");
        }
        return map;
    }

}
