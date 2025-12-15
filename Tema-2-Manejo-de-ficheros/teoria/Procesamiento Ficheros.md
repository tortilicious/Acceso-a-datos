# Manejo de Ficheros en Java - Guía Completa (Actualizada)

## 0. Conceptos Fundamentales Previos

### 0.1 ¿Por Qué Necesitamos Ficheros?

Imagina un programa contador que hiciste en Compose:

```kotlin
var contador by remember { mutableStateOf(5) }
```

**El problema:** Cuando cierras la app, `contador` desaparece. Si la vuelves a abrir, vuelve a ser 0.

**La solución:** Guardar los datos en un **fichero** en el almacenamiento del dispositivo, para que persistan.

### 0.2 ¿Qué es un Fichero?

Un **fichero** es un contenedor de datos en el disco duro que persiste incluso cuando apagas la computadora.

**Tipos principales:**

1. **Ficheros de texto:** Contenido legible (`datos.txt`, `config.log`)
2. **Ficheros binarios:** Contenido en bytes (`imagen.jpg`, `base_datos.db`)

### 0.3 Flujos (Streams) vs Ficheros Secuenciales vs Acceso Aleatorio

Hay **tres formas fundamentalmente diferentes** de trabajar con ficheros:

```
STREAMS (Flujos genéricos):
    Pueden conectarse a CUALQUIER fuente
    (fichero, red, memoria, etc.)
    
    Fichero ──┐
    Red ──────┼──→ InputStream / OutputStream
    Memoria ──┘
    
FICHEROS SECUENCIALES:
    Lees/escribes desde el inicio hasta el final
    como una película: paso a paso, sin poder volver atrás
    
    [Byte 0] → [Byte 1] → [Byte 2] → [Byte 3]
    Lectura lineal

FICHEROS DE ACCESO ALEATORIO (RandomAccessFile):
    Puedes saltar a CUALQUIER posición del fichero
    como un libro con índice: abres la página que quieres
    
    [Byte 0] [Byte 1] [Byte 2] [Byte 3] [Byte 4]
        ↓       ↓       ↓       ↓       ↓
    Puedes acceder directamente a cualquier byte
```

### 0.4 Tabla de Decisión Rápida

| Escenario | Mejor Opción |
|-----------|--------------|
| **Leer línea a línea un fichero de texto** | BufferedReader (secuencial) |
| **Escribir datos en un fichero de texto** | BufferedWriter (secuencial) |
| **Copiar ficheros binarios** | FileInputStream/OutputStream |
| **Buscar un dato específico en posición conocida** | RandomAccessFile |
| **Procesar datos de red o memoria** | InputStream/OutputStream genéricos |
| **Almacenar registros de tamaño fijo** | RandomAccessFile |
| **Leer carácter por carácter** | FileReader (secuencial, pero lento) |
| **Fichero gigante en streaming** | InputStream genérico |

---

## 1. Conceptos Clave: El Modelo de Java I/O

### 1.1 La Jerarquía de Clases

```
Toda comunicación de datos en Java sigue este patrón:

                    Fuente/Destino
                    (fichero, red, etc.)
                           |
                           ↓
        ┌──────────────────────────────┐
        │ Stream (Flujo)               │
        │ - InputStream (leer)         │
        │ - OutputStream (escribir)    │
        └──────────────────────────────┘
                           |
                           ↓
                    Tu código Java
                    (que procesa datos)
```

### 1.2 Tres Niveles de Abstracción

```
Nivel 1: STREAMS GENÉRICOS (bajo nivel)
    InputStream, OutputStream
    Trabajan con BYTES
    Muy primitivos pero universales

Nivel 2: WRAPPERS ESPECIALIZADOS (nivel medio)
    BufferedReader, FileReader, BufferedWriter, FileWriter
    Hacen que trabajar con texto sea fácil
    Añaden buffering (eficiencia)

Nivel 3: ABSTRACCIÓN COMPLETA (alto nivel)
    File (representar ficheros)
    RandomAccessFile (acceso aleatorio)
    Trabajan con ficheros específicos
```

### 1.3 Las Excepciones

Casi todas las operaciones de ficheros pueden fallar:
- El fichero no existe
- No tienes permisos
- El disco está lleno
- El fichero está siendo usado por otro programa

Por eso Java **obliga** a capturar `IOException`:

```java
try {
    // Código que usa ficheros
    fichero.createNewFile();
} catch (IOException e) {
    // Código si algo falla
    System.out.println("Error: " + e.getMessage());
}
```

---

## 2. La Clase File: Representar Ficheros y Carpetas

### 2.1 ¿Qué es File?

`File` es una clase que **representa** un fichero o carpeta. Es importante entender:

**`File` NO crea, lee ni elimina nada automáticamente.** Solo es una **referencia** a un fichero en el disco.

```java
import java.io.File;

public class EjemploFile {
    public static void main(String[] args) {
        // Crear una referencia a un fichero (no lo crea aún)
        File miArchivo = new File("datos.txt");
        
        // El fichero no existe todavía
        System.out.println("¿Existe? " + miArchivo.exists());  // false
        
        // Ahora lo creamos
        try {
            miArchivo.createNewFile();  // Aquí es cuando se crea
            System.out.println("¿Existe? " + miArchivo.exists());  // true
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 2.2 Crear Objetos File

**Opción 1: Ruta relativa (desde la carpeta del proyecto)**

```java
File fichero = new File("datos.txt");  // En la raíz del proyecto
File fichero = new File("src/data/archivo.txt");  // En subcarpeta
```

**Opción 2: Ruta absoluta (desde la raíz del sistema)**

```java
// Windows
File fichero = new File("C:\\Users\\Miguel\\Desktop\\datos.txt");

// Linux/macOS
File fichero = new File("/home/miguel/Desktop/datos.txt");
```

**Opción 3: Usando separador multiplataforma**

```java
// ✅ RECOMENDADO: Funciona en Windows, Linux y macOS
File fichero = new File("datos" + File.separator + "archivo.txt");
```

### 2.3 Métodos Útiles de File

```java
File fichero = new File("datos.txt");

// ¿Existe el fichero?
if (fichero.exists()) {
    System.out.println("Existe");
} else {
    System.out.println("No existe");
}

// ¿Es un fichero o carpeta?
if (fichero.isFile()) {
    System.out.println("Es un fichero");
}
if (fichero.isDirectory()) {
    System.out.println("Es una carpeta");
}

// Obtener información
System.out.println("Nombre: " + fichero.getName());           // "datos.txt"
System.out.println("Ruta: " + fichero.getPath());              // "datos.txt"
System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());
System.out.println("Tamaño: " + fichero.length() + " bytes");  // En bytes

// Crear un fichero vacío
try {
    if (fichero.createNewFile()) {
        System.out.println("Fichero creado");
    } else {
        System.out.println("Ya existía");
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}

// Eliminar un fichero
if (fichero.delete()) {
    System.out.println("Eliminado");
} else {
    System.out.println("No se pudo eliminar");
}

// Crear una carpeta
File carpeta = new File("miCarpeta");
if (carpeta.mkdir()) {
    System.out.println("Carpeta creada");
}

// Crear carpetas anidadas
File carpetaAnidada = new File("nivel1/nivel2/nivel3");
if (carpetaAnidada.mkdirs()) {  // mkdirs crea todas las carpetas
    System.out.println("Estructura de carpetas creada");
}

// Listar ficheros en una carpeta
File carpeta = new File(".");
File[] ficheros = carpeta.listFiles();
if (ficheros != null) {
    for (File f : ficheros) {
        System.out.println(f.getName());
    }
}
```

---

## 3. Streams: Los Bloques de Construcción

### 3.1 ¿Qué es un Stream?

Un **stream** (flujo) es un conducto de datos que conecta una **fuente** con un **destino**.

```
STREAM DE LECTURA (InputStream):

Fichero ──(InputStream)──→ Tu código Java
                           (lees bytes uno a uno)

STREAM DE ESCRITURA (OutputStream):

Tu código Java ──(OutputStream)──→ Fichero
                (escribes bytes uno a uno)
```

### 3.2 FileInputStream: Leer Bytes

`FileInputStream` lee bytes de un fichero, **uno a uno**. Es muy bajo nivel.

```java
import java.io.FileInputStream;
import java.io.IOException;

public class LectorBinario {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("datos.bin")) {
            
            int byte_leido;
            // read() devuelve un byte (0-255) o -1 si fin de fichero
            while ((byte_leido = fis.read()) != -1) {
                System.out.print(byte_leido + " ");
            }
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Problema:** Leer byte por byte es **muy lento**. Para un fichero de 1MB, harías 1 millón de operaciones.

### 3.3 Mejora: Leer en Bloques

```java
import java.io.FileInputStream;
import java.io.IOException;

public class LectorBloques {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("datos.bin")) {
            
            // Buffer de 1KB (1024 bytes)
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            
            // read(buffer) lee hasta 1024 bytes y los coloca en buffer
            // Devuelve cuántos bytes se leyeron realmente
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                System.out.println("Leí " + bytesLeidos + " bytes");
                
                // Procesar los bytes leídos
                for (int i = 0; i < bytesLeidos; i++) {
                    System.out.print(buffer[i] + " ");
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Ventaja:** Ahora en lugar de 1 millón de operaciones, solo 1000 (1MB / 1KB).

### 3.4 FileOutputStream: Escribir Bytes

```java
import java.io.FileOutputStream;
import java.io.IOException;

public class EscritorBinario {
    public static void main(String[] args) {
        // Crear datos de ejemplo
        byte[] datos = {72, 101, 108, 108, 111};  // "Hello" en ASCII
        
        try (FileOutputStream fos = new FileOutputStream("salida.bin")) {
            
            // Escribir todos los bytes
            fos.write(datos);
            
            System.out.println("Datos escritos");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 3.5 Try-with-resources: Cierre Automático

**Método antiguo (❌ peligroso):**

```java
FileInputStream fis = new FileInputStream("archivo.txt");
try {
    // ... código
} finally {
    fis.close();  // Si se olvida, el recurso nunca se cierra
}
```

**Método moderno (✅ seguro):**

```java
try (FileInputStream fis = new FileInputStream("archivo.txt")) {
    // ... código
}
// Se cierra automáticamente, incluso si ocurre excepción
```

**Ventajas:**
- Se cierra automáticamente
- Aunque haya excepción, se cierra
- Código más limpio

---

## 4. Ficheros Secuenciales: Lectura y Escritura de Texto

### 4.1 ¿Qué son Ficheros Secuenciales?

Ficheros que se leen/escriben desde el inicio hasta el final, **en orden**, sin poder saltar.

```
Fichero:    [Línea 1] [Línea 2] [Línea 3] [Línea 4] [Línea 5]
Lectura:      ↓         ↓         ↓         ↓         ↓
            Orden: 1 → 2 → 3 → 4 → 5
            
Si quieres la línea 4, DEBES leer 1, 2 y 3 primero
```

### 4.2 FileReader: El Lector Básico (Lento)

`FileReader` lee **carácter por carácter**. Es lento pero simple.

```java
import java.io.FileReader;
import java.io.IOException;

public class LectorCaracteres {
    public static void main(String[] args) {
        try (FileReader fr = new FileReader("archivo.txt")) {
            
            int caracter;
            while ((caracter = fr.read()) != -1) {
                System.out.print((char) caracter);
            }
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Problema:** Para un fichero de 1 millón de caracteres, hace 1 millón de operaciones. Muy lento.

### 4.3 BufferedReader: El Lector Eficiente (✅ Recomendado)

`BufferedReader` **envuelve** a `FileReader` y lee en bloques. Mucho más eficiente.

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorEficiente {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
            
            String linea;
            int numeroLinea = 1;
            
            // readLine() devuelve una línea completa (null si fin de fichero)
            while ((linea = br.readLine()) != null) {
                System.out.println(numeroLinea + ": " + linea);
                numeroLinea++;
            }
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Ventajas sobre FileReader:**
- Lee líneas completas (no carácter por carácter)
- Mantiene un buffer interno de 8KB
- Mucho más rápido

### 4.4 FileWriter y BufferedWriter: Escritura

**FileWriter (bajo nivel, lento):**

```java
import java.io.FileWriter;
import java.io.IOException;

public class EscritorBasico {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter("salida.txt")) {
            
            fw.write("Primera línea\n");
            fw.write("Segunda línea\n");
            fw.write("Tercera línea\n");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**BufferedWriter (recomendado):**

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscritorEficiente {
    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("salida.txt"))) {
            
            bw.write("Primera línea");
            bw.newLine();  // Salto de línea
            bw.write("Segunda línea");
            bw.newLine();
            bw.write("Tercera línea");
            bw.newLine();
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 4.5 Append Mode: Añadir sin Sobrescribir

**Sobrescribe (reemplaza contenido):**

```java
new FileWriter("archivo.txt")      // ← Sobrescribe
```

**Append (añade al final):**

```java
new FileWriter("archivo.txt", true)  // ← true = append mode
```

Ejemplo práctico:

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegistroEventos {
    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("log.txt", true)  // Append mode
        )) {
            
            // Esto se añade al final, no sobrescribe
            bw.write("[INFO] La aplicación se inició");
            bw.newLine();
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 4.6 Ejemplo Completo: Sistema de Notas

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SistemaNotas {
    private static final String FICHERO = "notas.txt";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n=== Sistema de Notas ===");
            System.out.println("1. Ver notas");
            System.out.println("2. Añadir nota");
            System.out.println("3. Salir");
            System.out.print("Elige opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer
            
            switch (opcion) {
                case 1 -> verNotas();
                case 2 -> annadirNota(scanner);
                case 3 -> continuar = false;
                default -> System.out.println("Opción inválida");
            }
        }
        
        scanner.close();
        System.out.println("Adiós");
    }
    
    private static void verNotas() {
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea;
            int numero = 1;
            System.out.println("\n=== Tus Notas ===");
            
            while ((linea = br.readLine()) != null) {
                System.out.println(numero + ". " + linea);
                numero++;
            }
            
        } catch (IOException e) {
            System.out.println("No hay notas guardadas aún");
        }
    }
    
    private static void annadirNota(Scanner scanner) {
        System.out.print("Escribe tu nota: ");
        String nota = scanner.nextLine();
        
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(FICHERO, true)  // Append
        )) {
            bw.write(nota);
            bw.newLine();
            System.out.println("Nota guardada");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Salida:**
```
=== Sistema de Notas ===
1. Ver notas
2. Añadir nota
3. Salir
Elige opción: 2
Escribe tu nota: Comprar leche
Nota guardada

=== Sistema de Notas ===
1. Ver notas
2. Añadir nota
3. Salir
Elige opción: 2
Escribe tu nota: Llamar al doctor
Nota guardada

=== Sistema de Notas ===
1. Ver notas
2. Añadir nota
3. Salir
Elige opción: 1

=== Tus Notas ===
1. Comprar leche
2. Llamar al doctor
```

### 4.7 Copiar Ficheros (Secuencial)

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopiadorFicheros {
    public static void main(String[] args) {
        String origen = "documento_original.txt";
        String destino = "documento_copia.txt";
        
        try (
            BufferedReader br = new BufferedReader(new FileReader(origen));
            BufferedWriter bw = new BufferedWriter(new FileWriter(destino))
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Fichero copiado correctamente");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

---

## 5. RandomAccessFile: Acceso Aleatorio

### 5.1 El Concepto: Saltar a Cualquier Posición

`RandomAccessFile` permite leer/escribir en **cualquier posición** del fichero sin pasar por las anteriores.

```
SECUENCIAL (BufferedReader):
Fichero:    [Byte 0] [Byte 1] [Byte 2] ... [Byte 999]
Lectura:      ↓    →  ↓    →   ↓    →  ...
            LINEAL: debes pasar por cada uno

ALEATORIO (RandomAccessFile):
Fichero:    [Byte 0] [Byte 1] [Byte 2] ... [Byte 999]
                           ↑
                    Salta directamente aquí
                    sin pasar por los anteriores
```

**Analogía:** 
- BufferedReader es como una película: debes verla en orden
- RandomAccessFile es como un libro: puedes abrir cualquier página directamente

### 5.2 El Puntero (Pointer)

`RandomAccessFile` mantiene un **puntero** que indica en qué posición estás.

```
Fichero:    [B0] [B1] [B2] [B3] [B4] [B5] [B6] [B7]
                                  ↑
                            Puntero aquí
                          (posición = 3)

Cuando lees, el puntero avanza:
    Leer en posición 3 → puntero se mueve a 4
    Leer en posición 4 → puntero se mueve a 5
```

### 5.3 Crear un RandomAccessFile

```java
import java.io.RandomAccessFile;
import java.io.IOException;

public class EjemploRandomAccess {
    public static void main(String[] args) {
        try {
            // Modos: "r" (lectura), "rw" (lectura/escritura)
            RandomAccessFile raf = new RandomAccessFile("datos.bin", "rw");
            
            // Ahora puedes hacer operaciones aleatorias
            
            raf.close();
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Modos disponibles:**

| Modo | Significado |
|------|------------|
| `"r"` | Solo lectura |
| `"rw"` | Lectura y escritura |
| `"rws"` | Lectura, escritura y sincronización con disco |
| `"rwd"` | Lectura, escritura y sincronización de metadatos |

### 5.4 Métodos para Controlar el Puntero

```java
RandomAccessFile raf = new RandomAccessFile("datos.bin", "rw");

// Obtener la posición actual
long posicion = raf.getFilePointer();
System.out.println("Estoy en posición: " + posicion);

// Mover a una posición específica (en bytes)
raf.seek(100);  // Salta a byte 100

// Obtener el tamaño total del fichero
long tamaño = raf.length();
System.out.println("Fichero tiene " + tamaño + " bytes");

// Mover al final
raf.seek(raf.length());

raf.close();
```

### 5.5 Lectura con RandomAccessFile

```java
import java.io.RandomAccessFile;
import java.io.IOException;

public class LectorAleatorio {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("datos.bin", "r")) {
            
            // Leer un byte
            raf.seek(0);
            byte unByte = raf.readByte();
            System.out.println("Byte en posición 0: " + unByte);
            
            // Leer un entero (4 bytes)
            raf.seek(10);
            int numero = raf.readInt();
            System.out.println("Entero en posición 10: " + numero);
            
            // Leer un long (8 bytes)
            raf.seek(20);
            long numeroGrande = raf.readLong();
            System.out.println("Long en posición 20: " + numeroGrande);
            
            // Leer un texto (UTF)
            raf.seek(100);
            String texto = raf.readUTF();
            System.out.println("Texto en posición 100: " + texto);
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 5.6 Escritura con RandomAccessFile

```java
import java.io.RandomAccessFile;
import java.io.IOException;

public class EscritorAleatorio {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("salida.bin", "rw")) {
            
            // Escribir en secuencia
            raf.writeByte(65);              // 1 byte
            raf.writeInt(12345);            // 4 bytes
            raf.writeLong(9876543210L);     // 8 bytes
            raf.writeUTF("Hola mundo");     // Variable
            
            // Ir a posición 2 y sobrescribir
            raf.seek(2);
            raf.writeByte(70);
            
            System.out.println("Datos escritos correctamente");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 5.7 Caso Práctico: Base de Datos Simple de Estudiantes

```java
import java.io.RandomAccessFile;
import java.io.IOException;

public class BaseDatosEstudiantes {
    private static final String FICHERO = "estudiantes.db";
    private static final int REGISTRO_SIZE = 50;  // Bytes por registro
    
    public static void main(String[] args) {
        try {
            // Guardar estudiantes
            guardarEstudiante(0, "Juan");
            guardarEstudiante(1, "María");
            guardarEstudiante(2, "Pedro");
            
            // Leer estudiante específico (sin leer los anteriores)
            System.out.println("Estudiante 0: " + leerEstudiante(0));
            System.out.println("Estudiante 2: " + leerEstudiante(2));  // Acceso directo
            System.out.println("Estudiante 1: " + leerEstudiante(1));
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Guardar en posición específica
    private static void guardarEstudiante(int numero, String nombre) 
            throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "rw")) {
            // Cada estudiante ocupa REGISTRO_SIZE bytes
            // Estudiante 0: bytes 0-49
            // Estudiante 1: bytes 50-99
            // Estudiante 2: bytes 100-149
            
            long posicion = (long) numero * REGISTRO_SIZE;
            raf.seek(posicion);
            raf.writeUTF(nombre);
        }
    }
    
    // Leer de posición específica (acceso directo)
    private static String leerEstudiante(int numero) 
            throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(FICHERO, "r")) {
            long posicion = (long) numero * REGISTRO_SIZE;
            raf.seek(posicion);  // ← Salta directamente
            
            return raf.readUTF();
        }
    }
}
```

**Salida:**
```
Estudiante 0: Juan
Estudiante 2: Pedro
Estudiante 1: María
```

**¿Por qué es eficiente?** Acceder al estudiante 2 fue DIRECTO. Con BufferedReader habrías tenido que leer los estudiantes 0 y 1 primero.

### 5.8 Comparativa: Secuencial vs Aleatorio

```
ESCENARIO: 10,000 registros, quieres el registro 9,999

SECUENCIAL (BufferedReader):
┌─────────────────────────────────┐
│ Lee registro 0 → 1 → 2 → ... 9,999
│ Total: 10,000 operaciones
│ Tiempo: LENTO
└─────────────────────────────────┘

ALEATORIO (RandomAccessFile):
┌─────────────────────────────────┐
│ Salta directamente a 9,999
│ Total: 1 operación
│ Tiempo: RÁPIDO
└─────────────────────────────────┘
```

### 5.9 Métodos Comunes de RandomAccessFile

| Método | Función | Bytes |
|--------|---------|-------|
| `readByte()` | Leer 1 byte | 1 |
| `writeByte(int)` | Escribir 1 byte | 1 |
| `readInt()` | Leer entero | 4 |
| `writeInt(int)` | Escribir entero | 4 |
| `readLong()` | Leer largo | 8 |
| `writeLong(long)` | Escribir largo | 8 |
| `readFloat()` | Leer decimal | 4 |
| `writeFloat(float)` | Escribir decimal | 4 |
| `readDouble()` | Leer double | 8 |
| `writeDouble(double)` | Escribir double | 8 |
| `readUTF()` | Leer String UTF | Variable |
| `writeUTF(String)` | Escribir String UTF | Variable |
| `readBoolean()` | Leer boolean | 1 |
| `writeBoolean(boolean)` | Escribir boolean | 1 |
| `seek(long)` | Mover puntero | - |
| `getFilePointer()` | Obtener posición | - |
| `length()` | Obtener tamaño | - |
| `setLength(long)` | Cambiar tamaño | - |

### 5.10 Ventajas y Desventajas

**Ventajas:**
- ✅ Acceso muy rápido a datos específicos
- ✅ Puedes sobrescribir en posiciones exactas
- ✅ Ideal para bases de datos simples
- ✅ Leer y escribir con el mismo objeto
- ✅ No necesitas cargar todo en memoria

**Desventajas:**
- ❌ Más complejo que lectura secuencial
- ❌ Debes saber exactamente dónde están los datos
- ❌ No es ideal para ficheros muy grandes sin estructura
- ❌ Requiere cuidado con posiciones y tamaños

---

## 6. PrintWriter y PrintStream: Escritura Formateada

### 6.1 ¿Cuándo Usarlos?

`PrintWriter` es muy útil cuando quieres **escribir datos formateados** (números, booleanos, objetos) de forma fácil.

```java
// BufferedWriter (bajo nivel)
bw.write("Número: " + numero);
bw.write("Booleano: " + verdadero);

// PrintWriter (más fácil)
pw.println("Número: " + numero);
pw.println("Booleano: " + verdadero);
pw.printf("Formato: %d, %s, %.2f\n", 42, "hola", 3.14159);
```

### 6.2 Usando PrintWriter

```java
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscritorFormateado {
    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("datos.txt"))) {
            
            // Escribir diferentes tipos de datos
            pw.println("=== Datos del Usuario ===");
            pw.println("Nombre: Juan García");
            pw.println("Edad: 25");
            pw.println("Altura: 1.75 metros");
            pw.println("Activo: true");
            
            // Usar printf para formato específico
            pw.printf("Saldo: %.2f€\n", 123.456);
            pw.printf("Código: %05d\n", 42);  // Con ceros a la izquierda
            
            System.out.println("Fichero escrito");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Fichero resultante (`datos.txt`):**
```
=== Datos del Usuario ===
Nombre: Juan García
Edad: 25
Altura: 1.75 metros
Activo: true
Saldo: 123.46€
Código: 00042
```

### 6.3 Diferencia: println() vs print()

```java
PrintWriter pw = ...;

// println(): Escribe y añade salto de línea
pw.println("Primera línea");
pw.println("Segunda línea");

// Resultado:
// Primera línea
// Segunda línea

// print(): Solo escribe, sin salto
pw.print("Parte 1 ");
pw.print("Parte 2 ");
pw.print("Parte 3");

// Resultado:
// Parte 1 Parte 2 Parte 3
```

### 6.4 PrintStream (para flujos de bytes)

`PrintStream` es similar a `PrintWriter` pero funciona con bytes en lugar de caracteres.

```java
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EscritorStream {
    public static void main(String[] args) {
        try (PrintStream ps = new PrintStream(
                new FileOutputStream("salida_binaria.bin")
        )) {
            
            ps.println("Número: " + 42);
            ps.printf("Pi: %.2f\n", 3.14159);
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

---

## 7. Scanner: Lectura Formateada

### 7.1 ¿Qué es Scanner?

`Scanner` es lo opuesto a `PrintWriter`. Mientras que `PrintWriter` **escribe** datos formateados, `Scanner` **lee** datos formateados.

```
PrintWriter:  Java → Fichero (escribir formateado)
Scanner:      Fichero → Java (leer formateado)
```

### 7.2 Leer Fichero con Scanner

```java
import java.io.Scanner;
import java.io.File;
import java.io.IOException;

public class LectorFormateado {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("datos.txt"))) {
            
            // Leer línea por línea
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println("Leída: " + linea);
            }
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 7.3 Leer Diferentes Tipos de Datos

```java
import java.io.Scanner;
import java.io.File;

public class LectorTipos {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("datos.txt"))) {
            
            // Leer diferentes tipos
            if (scanner.hasNextInt()) {
                int numero = scanner.nextInt();
                System.out.println("Entero: " + numero);
            }
            
            if (scanner.hasNextDouble()) {
                double decimal = scanner.nextDouble();
                System.out.println("Decimal: " + decimal);
            }
            
            if (scanner.hasNextBoolean()) {
                boolean valor = scanner.nextBoolean();
                System.out.println("Booleano: " + valor);
            }
            
            if (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println("Texto: " + linea);
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 7.4 Usar Delimitadores Personalizados

Por defecto, `Scanner` usa espacios y saltos de línea como separadores. Puedes cambiar esto:

```java
import java.io.Scanner;
import java.io.File;

public class LectorCSV {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("datos.csv"))) {
            
            // Usar coma como separador
            scanner.useDelimiter(",");
            
            while (scanner.hasNext()) {
                String valor = scanner.next().trim();
                System.out.println("Campo: " + valor);
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Fichero CSV (`datos.csv`):**
```
Juan,25,Madrid
María,30,Barcelona
Pedro,28,Valencia
```

**Salida:**
```
Campo: Juan
Campo: 25
Campo: Madrid
Campo: María
...
```

---

## 8. Comparativa Completa: Cuándo Usar Cada Uno

### 8.1 Tabla de Decisión Definitiva

| Operación | Clase Recomendada | Por Qué |
|-----------|-------------------|--------|
| **Leer texto línea a línea** | `BufferedReader` | Eficiente, simple |
| **Leer palabra/número a palabra** | `Scanner` | Parseado automático |
| **Escribir texto** | `BufferedWriter` | Eficiente, simple |
| **Escribir datos formateados** | `PrintWriter` | Más limpio con printf |
| **Leer caracteres individuales** | `FileReader` | Solo si necesitas char a char |
| **Leer bytes individuales** | `FileInputStream` | Solo si necesitas control total |
| **Leer bytes en bloques** | `FileInputStream` + buffer | Eficiente para binarios |
| **Copiar ficheros binarios** | `FileInputStream` + `FileOutputStream` | Estándar |
| **Acceso aleatorio a datos** | `RandomAccessFile` | Único con acceso directo |
| **Registros de tamaño fijo** | `RandomAccessFile` | Perfecta estructura |
| **Datos de red** | `InputStream` / `OutputStream` | Flujo genérico |

### 8.2 Flujo de Decisión Visual

```
¿Qué necesitas hacer?

    ├─ LEER
    │  ├─ ¿Necesitas acceso a datos específicos directamente?
    │  │  ├─ SÍ → RandomAccessFile
    │  │  └─ NO → ¿Línea completa?
    │  │     ├─ SÍ → BufferedReader
    │  │     └─ NO → ¿Palabra/número?
    │  │        ├─ SÍ → Scanner
    │  │        └─ NO → FileReader/FileInputStream
    │
    └─ ESCRIBIR
       ├─ ¿Necesitas formatear (printf)?
       │  ├─ SÍ → PrintWriter
       │  └─ NO → ¿Línea completa?
       │     ├─ SÍ → BufferedWriter
       │     └─ NO → FileWriter/FileOutputStream
```

---

## 9. Patrones Comunes

### 9.1 Patrón 1: Copiar Fichero Texto

```java
try (BufferedReader br = new BufferedReader(new FileReader("origen.txt"));
     BufferedWriter bw = new BufferedWriter(new FileWriter("destino.txt"))) {
    
    String linea;
    while ((linea = br.readLine()) != null) {
        bw.write(linea);
        bw.newLine();
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### 9.2 Patrón 2: Copiar Fichero Binario

```java
try (FileInputStream fis = new FileInputStream("origen.bin");
     FileOutputStream fos = new FileOutputStream("destino.bin")) {
    
    byte[] buffer = new byte[1024];
    int bytesLeidos;
    
    while ((bytesLeidos = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesLeidos);
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### 9.3 Patrón 3: Procesar CSV

```java
try (Scanner scanner = new Scanner(new File("datos.csv"))) {
    scanner.useDelimiter(",|\\n");  // Coma o salto de línea
    
    while (scanner.hasNext()) {
        String campo = scanner.next().trim();
        System.out.println(campo);
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### 9.4 Patrón 4: Contar Líneas

```java
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    int lineas = 0;
    while (br.readLine() != null) {
        lineas++;
    }
    System.out.println("Total de líneas: " + lineas);
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### 9.5 Patrón 5: Buscar Texto en Fichero

```java
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    String linea;
    int numeroLinea = 1;
    String buscar = "ERROR";
    
    while ((linea = br.readLine()) != null) {
        if (linea.contains(buscar)) {
            System.out.println("Encontrado en línea " + numeroLinea + ": " + linea);
        }
        numeroLinea++;
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### 9.6 Patrón 6: Registrar en Log (Append)

```java
try (PrintWriter pw = new PrintWriter(
        new FileWriter("app.log", true)  // Append mode
)) {
    pw.println("[" + System.currentTimeMillis() + "] INFO: Evento registrado");
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### 9.7 Patrón 7: Leer Fichero Gigante Eficientemente

```java
try (BufferedReader br = new BufferedReader(new FileReader("gigante.txt"), 65536)) {
    // Buffer de 64KB en lugar de 8KB por defecto
    // Mucho más rápido para ficheros grandes
    
    String linea;
    while ((linea = br.readLine()) != null) {
        procesarLinea(linea);  // Procesar cada línea
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}

private static void procesarLinea(String linea) {
    // Tu lógica aquí
}
```

---

## 10. Manejo de Excepciones: try-catch vs try-with-resources

### 10.1 Método Antiguo (❌ No recomendado)

```java
BufferedReader br = null;
try {
    br = new BufferedReader(new FileReader("archivo.txt"));
    String linea;
    while ((linea = br.readLine()) != null) {
        System.out.println(linea);
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
} finally {
    // ⚠️ Fácil olvidar esto
    if (br != null) {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**Problemas:**
- Código muy verboso
- Fácil olvidar `close()`
- Si hay excepción, podría no cerrarse

### 10.2 Método Moderno (✅ Recomendado)

```java
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {
        System.out.println(linea);
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
// Se cierra automáticamente, incluso si hay excepción
```

**Ventajas:**
- Código limpio y simple
- Se cierra automáticamente
- Funciona incluso si hay excepción

### 10.3 Capturar Múltiples Excepciones (Java 7+)

```java
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {
        int numero = Integer.parseInt(linea);  // Podría lanzar NumberFormatException
        System.out.println(numero);
    }
} catch (IOException e) {
    System.out.println("Error de fichero: " + e.getMessage());
} catch (NumberFormatException e) {
    System.out.println("Error de formato: " + e.getMessage());
}
```

---

## 11. Ejemplo Completo: Gestor de Contactos

```java
import java.io.*;
import java.util.Scanner;

public class GestorContactos {
    private static final String FICHERO = "contactos.txt";
    
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n=== Gestor de Contactos ===");
            System.out.println("1. Ver contactos");
            System.out.println("2. Añadir contacto");
            System.out.println("3. Buscar contacto");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            
            int opcion = entrada.nextInt();
            entrada.nextLine();
            
            switch (opcion) {
                case 1 -> verContactos();
                case 2 -> annadirContacto(entrada);
                case 3 -> buscarContacto(entrada);
                case 4 -> continuar = false;
                default -> System.out.println("Opción inválida");
            }
        }
        
        entrada.close();
        System.out.println("Adiós");
    }
    
    private static void verContactos() {
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea;
            System.out.println("\n=== Contactos ===");
            
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("No hay contactos guardados");
        }
    }
    
    private static void annadirContacto(Scanner entrada) {
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Teléfono: ");
        String telefono = entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();
        
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(FICHERO, true)  // Append
        )) {
            pw.printf("%s | %s | %s\n", nombre, telefono, email);
            System.out.println("Contacto añadido");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void buscarContacto(Scanner entrada) {
        System.out.print("Nombre a buscar: ");
        String buscar = entrada.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea;
            boolean encontrado = false;
            
            System.out.println("\n=== Resultados ===");
            while ((linea = br.readLine()) != null) {
                if (linea.toLowerCase().contains(buscar.toLowerCase())) {
                    System.out.println(linea);
                    encontrado = true;
                }
            }
            
            if (!encontrado) {
                System.out.println("No se encontraron contactos");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Ejecución:**
```
=== Gestor de Contactos ===
1. Ver contactos
2. Añadir contacto
3. Buscar contacto
4. Salir
Opción: 2
Nombre: Juan García
Teléfono: 666123456
Email: juan@example.com
Contacto añadido

=== Gestor de Contactos ===
1. Ver contactos
2. Añadir contacto
3. Buscar contacto
4. Salir
Opción: 1

=== Contactos ===
Juan García | 666123456 | juan@example.com

=== Gestor de Contactos ===
1. Ver contactos
2. Añadir contacto
3. Buscar contacto
4. Salir
Opción: 3
Nombre a buscar: Juan
=== Resultados ===
Juan García | 666123456 | juan@example.com
```

---

## 12. Buenas Prácticas

1. **Siempre usa try-with-resources**
   ```java
   try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
       // Código
   } catch (IOException e) {
       // Manejo de error
   }
   ```

2. **Verifica si un fichero existe antes de leer**
   ```java
   File fichero = new File("datos.txt");
   if (fichero.exists() && fichero.isFile()) {
       // Leer
   }
   ```

3. **Usa rutas relativas cuando sea posible**
   ```java
   new File("datos.txt")  // Mejor que rutas absolutas
   ```

4. **Usa BufferedReader/Writer para texto**
   - Son mucho más eficientes que FileReader/Writer

5. **Usa FileInputStream/OutputStream para binarios**
   - Siempre con un buffer de bytes

6. **Usa RandomAccessFile solo cuando necesites acceso aleatorio**
   - Más complejo que secuencial

7. **Usa PrintWriter para escritura formateada**
   - Mucho más limpio que concatenar strings

8. **Usa Scanner para lectura formateada**
   - Parseado automático de tipos

9. **Maneja correctamente las excepciones**
   - No ignores IOException (hace que sea muy difícil debuggear)

10. **Documenta dónde se guardan los ficheros**
    ```java
    private static final String FICHERO = "datos.txt";  // En raíz del proyecto
    ```

---

## 13. Resumen Visual

```
JERARQUÍA DE STREAMS:

                      InputStream/OutputStream
                     (lectura/escritura de bytes)
                              |
                    ┌─────────┴─────────┐
                    |                   |
          FileInputStream          FileOutputStream
          (bytes del fichero)     (escribir bytes)
                    |                   |
          BufferedInputStream    BufferedOutputStream
          (lectura con buffer)    (escritura con buffer)
                    |
          ┌─────────┴──────────┐
          |                    |
        FileReader          InputStreamReader
      (caracteres)         (bytes → caracteres)
          |
    BufferedReader
    (líneas completas)
          |
       Scanner
    (parsing automático)

PARA ESCRIBIR:
FileWriter → BufferedWriter → PrintWriter
(bajo)         (medio)         (alto)

ACCESO ALEATORIO:
RandomAccessFile
(única opción para saltar posiciones)
```

---

## 14. Tabla Resumen Final

| Operación | Clase | Ventaja |
|-----------|-------|---------|
| Leer línea completa | `BufferedReader` | Eficiente, simple |
| Escribir línea completa | `BufferedWriter` | Eficiente, simple |
| Leer formateado | `Scanner` | Parseado automático |
| Escribir formateado | `PrintWriter` | printf, limpio |
| Leer bytes | `FileInputStream` | Universal |
| Escribir bytes | `FileOutputStream` | Universal |
| Acceso aleatorio | `RandomAccessFile` | Acceso directo |
| Representar fichero | `File` | Información y creación |
| Fichero de configuración | `Properties` | Clave-valor |
| XML | `DocumentBuilder` (DOM) | Árbol completo |
| JSON | `JSONObject` | Estructuras complejas |