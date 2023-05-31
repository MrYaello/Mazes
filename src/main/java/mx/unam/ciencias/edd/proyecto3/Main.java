package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main extends Flags {

  private final String USE = "Para resolver un laberinto (.mze) se debe enviar por entrada estandar ej.:" + "\n" +
          "a) java -jar target/proyecto3.jar < ejemplo.mze > solucion.svg"  + "\n" +
          "b) cat ejemplo.mze | java -jar target/proyecto3.jar > solucion.svg"  + "\n" +
          "Para generar un laberinto se debe invocar de la siguiente forma ej.:" + "\n" +
          "a) java -jar target/proyecto3.jar -g -s <Semilla> -w <Ancho> -h <Alto>"  + "\n" +
          "-) -g           --- Indica que hay que generar un laberinto."  + "\n" +
          "-) -s <Semilla> --- (Opcional) La semilla para generar el laberinto."  + "\n" +
          "-) -w <Ancho>   --- Número de columnas del laberinto."  + "\n" +
          "-) -h <Alto>    --- Número de renglones del laberinto."  + "\n";
  private int width;
  private int height;
  private int[][] matrix;
  public Maze maze;

  public Main() {
    maze = new Maze();
  }

  public void start(String[] args) {
    try {
      flagsChecker(args);
    } catch (Exception e) {
      error(USE);
    }

    if (args.length == 0 && !generate()) {
      read();
      maze.build(matrix);
      maze.solve();
      System.out.println(maze.drawMaze(true));
    }
    else maze.generate();
  }

  public void read() {
    BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));
    try {
      int ch = -1;
      int M = reader.read();
      int A = reader.read();
      int Z = reader.read();
      int E = reader.read();
      height = reader.read();
      width =  reader.read();
      if (M != 77 || A != 65 || Z != 90 || E != 69) throw new Exception();
      if ((height < 2 || height > 255) || (width < 2 || width > 255)) throw new Exception();
      matrix = new int[height][width];
      for (int i = 0; i < height; i++)
        for (int j = 0; j < width; j++)
          if ((ch = reader.read()) == -1 || ch > 255) throw new Exception();
          else matrix[i][j] = ch;
      if (reader.read() != -1) throw new Exception();
    } catch (Exception e) {
      error("El laberinto es inválido.");
    } finally {
      try {
        reader.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void error(String err) {
    System.err.println(err);
    System.exit(-1);
  }
}