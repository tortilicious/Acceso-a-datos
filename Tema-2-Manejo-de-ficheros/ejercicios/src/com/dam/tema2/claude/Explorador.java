package com.dam.tema2.claude;


import java.io.File;
import java.util.Scanner;

/*
    📌 Objetivo
    Dominar la clase File y sus métodos básicos para obtener información de ficheros y carpetas.

    🎯 Requisitos
    Crea un programa Explorador.java que:

    Pida la ruta de un directorio por teclado (Scanner)
    Valide que existe y que es un directorio
    Liste todos los elementos mostrando:
    Nombre
    Tipo (fichero o carpeta)
    Tamaño en bytes (0 si es carpeta)
    Ruta absoluta
    Si se puede leer/escribir
    Al final, mostrar estadísticas:
    Total de ficheros
    Total de carpetas
    Tamaño total en bytes
    Tamaño total en MB
 */
public class Explorador {

    // función recursiva para leer los directorios
    private static void listarDirectorios(File directorio, int nivel) {
        int totalDirectorios = 0;
        int totalFicheros = 0;
        long totalBytes = 0;
        String indentacion = "\t".repeat(nivel);

        for (File f : directorio.listFiles()) {
            if (f.isDirectory()) {
                ++totalDirectorios;
                System.out.printf("Leyendo ficheros de la carpeta: %s\n", f.getName());
                listarDirectorios(f, nivel + 1);
            } else {
                ++totalFicheros;
                totalBytes += f.length();
                System.out.printf("%sNombre del fichero: %s\n", indentacion, f.getName());
                System.out.printf("%sTamaño del fichero: %s bytes\n", indentacion, f.length());
                System.out.printf("%sRuta absoluta: %s\n", indentacion, f.getAbsolutePath());
                System.out.printf("%sPermiso de lectura: %s\n", indentacion, f.canRead());
                System.out.printf("%sPermiso de escritura: %s\n", indentacion, f.canWrite());
                System.out.println();
            }
        }

        double totalMB = totalBytes / (1024.0 * 1024.0);

        System.out.printf("""
                        
                        =============================
                         ESTATISTICAS DEL DIRECTORIO
                        =============================
                        
                        Total de ficheros: %d ficheros
                        Total de carpetas: %d carpetas
                        Tamaño total en bytes: %,d bytes
                        Tamaño total en MB: %.2f MB
                        """,
                totalFicheros, totalDirectorios, totalBytes, totalMB
        );
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Pide la ruta del directorio
        System.out.println("Introduce la ruta del directorio:");
        String rutaDirectorio = entrada.nextLine();

        // Crea un objeto File para validar la ruta
        File directorio = new File(rutaDirectorio);

        if (!directorio.exists()) {
            System.out.println("El directorio no existe");
        }

        // Valida que sea un directorio
        if (directorio.isDirectory()) {
            System.out.printf("El archivo '%s' es un directorio\n\n", directorio.getPath());

            // Lista todos los elementos del directorio
            listarDirectorios(directorio, 1);
        } else {
            System.out.println("El archivo no es un directorio");
        }
    }
}


