# Ejercicios Prácticos - Procesamiento de Ficheros

## Basados en la Teoría de `Procesamiento Ficheros.md`

Estos ejercicios están diseñados para practicar los conceptos teóricos del documento de referencia, progresando desde lo básico hasta lo avanzado.

---

## 📊 Mapa de Ejercicios

| Nivel | Tema | Ejercicio | API Principal |
|-------|------|-----------|----------------|
| 🟢 **Básico** | Clase File | 1 | `File` |
| 🟢 **Básico** | Streams | 2 | `FileInputStream/OutputStream` |
| 🟡 **Intermedio** | Lectura Secuencial | 3 | `BufferedReader` |
| 🟡 **Intermedio** | Escritura Secuencial | 4 | `BufferedWriter` |
| 🟡 **Intermedio** | Ficheros Secuenciales | 5 | `BufferedReader/Writer` |
| 🔴 **Avanzado** | Acceso Aleatorio | 6 | `RandomAccessFile` |
| 🔴 **Avanzado** | Acceso Aleatorio | 7 | `RandomAccessFile` |
| 🟡 **Intermedio** | Scanner | 8 | `Scanner` |
| 🟡 **Intermedio** | PrintWriter | 9 | `PrintWriter` |
| 🔴 **Avanzado** | Integración | 10 | Múltiples APIs |

---

## 🟢 NIVEL BÁSICO

---

## Ejercicio 1: Explorador de Ficheros con la Clase File

### 📌 Objetivo

Dominar la clase `File` y sus métodos básicos para obtener información de ficheros y carpetas.

### 🎯 Requisitos

Crea un programa `Explorador.java` que:

1. **Pida la ruta de un directorio** por teclado (Scanner)
2. **Valide que existe** y que es un directorio
3. **Liste todos los elementos** mostrando:
    - Nombre
    - Tipo (fichero o carpeta)
    - Tamaño en bytes (0 si es carpeta)
    - Ruta absoluta
    - Si se puede leer/escribir
4. **Al final**, mostrar estadísticas:
    - Total de ficheros
    - Total de carpetas
    - Tamaño total en bytes
    - Tamaño total en MB

### 💡 Pistas

```java
File directorio = new File(rutaIntroducida);

if (directorio.exists() && directorio.isDirectory()) {
    File[] contenido = directorio.listFiles();
    // Iterar sobre contenido...
}
```

- Usa `isFile()` e `isDirectory()` para distinguir
- Usa `canRead()` y `canWrite()` para permisos
- Convierte bytes a MB dividiendo entre 1024*1024

### 📝 Salida Esperada

```
=== Explorador de Ficheros ===
Directorio: /home/usuario/Documentos

📁 carpeta_personal (Carpeta)
   Tamaño: 0 bytes | Lectura: ✓ | Escritura: ✓
   Ruta: /home/usuario/Documentos/carpeta_personal

📄 resumen.txt (Fichero)
   Tamaño: 2,456 bytes | Lectura: ✓ | Escritura: ✓
   Ruta: /home/usuario/Documentos/resumen.txt

---
ESTADÍSTICAS:
Total ficheros: 5
Total carpetas: 2
Tamaño total: 125.34 MB
```

### ✅ Conceptos Practicados

- Creación de objetos `File`
- Métodos de información (`exists()`, `isFile()`, `isDirectory()`)
- Obtención de propiedades (`getName()`, `getAbsolutePath()`, `length()`)
- Permisos (`canRead()`, `canWrite()`)

---

## Ejercicio 2: Copiar Fichero Binario con Streams

### 📌 Objetivo

Practicar lectura y escritura de ficheros binarios usando `FileInputStream` y `FileOutputStream`.

### 🎯 Requisitos

Crea un programa `CopiadorBinario.java` que:

1. **Pida dos rutas** (origen y destino) por teclado
2. **Valide que el fichero origen existe**
3. **Copie el contenido** usando un buffer de **1024 bytes**
4. **Muestre progreso:**
    - Bytes copiados
    - Porcentaje completado
    - Velocidad (KB/s)
5. **Al finalizar:**
    - Confirmar que se copió correctamente
    - Mostrar tamaño original vs tamaño copiado

### 💡 Pistas

```java
try (FileInputStream fis = new FileInputStream(origen);
     FileOutputStream fos = new FileOutputStream(destino)) {
    
    byte[] buffer = new byte[1024];
    int bytesLeidos;
    
    while ((bytesLeidos = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesLeidos);
        // Actualizar progreso...
    }
}
```

- Usa `long tiempoInicio = System.currentTimeMillis()` para medir tiempo
- Calcula velocidad: `(bytesTotales / (tiempoTranscurrido / 1000.0))` en bytes/segundo

### 📝 Salida Esperada

```
=== Copiador de Ficheros Binarios ===
Origen: /home/usuario/imagen.jpg
Destino: /home/usuario/imagen_copia.jpg

Copiando...
[████████████████████░░░░░░░░░░░░░░░░░░] 65%
Bytes: 6.5 MB / 10 MB
Velocidad: 25.3 MB/s

✓ Copia completada exitosamente
Tamaño original: 10,485,760 bytes
Tamaño copiado: 10,485,760 bytes
Tiempo: 0.41 segundos
```

### ✅ Conceptos Practicados

- `FileInputStream` y `FileOutputStream`
- Lectura en bloques (buffers)
- Try-with-resources
- Manejo de bytes

---

## 🟡 NIVEL INTERMEDIO

---

## Ejercicio 3: Lector de Fichero Grande Línea a Línea

### 📌 Objetivo

Practicar lectura secuencial eficiente usando `BufferedReader`.

### 🎯 Requisitos

Crea un programa `LectorFicheroGrande.java` que:

1. **Pida la ruta de un fichero de texto** por teclado
2. **Lea el fichero línea por línea** mostrando:
    - Número de línea
    - Contenido de la línea
    - Longitud de la línea
3. **Proporcione opciones de búsqueda:**
    - Buscar palabras clave (case-insensitive)
    - Mostrar solo líneas que contienen la palabra
    - Contar coincidencias
4. **Mostrar estadísticas finales:**
    - Total de líneas
    - Longitud máxima de línea
    - Longitud promedio
    - Línea más corta y más larga

### 💡 Pistas

```java
try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
    String linea;
    int numeroLinea = 1;
    
    while ((linea = br.readLine()) != null) {
        // Procesar línea...
        numeroLinea++;
    }
}
```

- Usa `linea.toLowerCase().contains(busqueda)` para búsqueda case-insensitive
- Almacena longitudes en un `List<Integer>` para calcular máximo/mínimo

### 📝 Salida Esperada

```
=== Lector de Fichero Grande ===
Fichero: /home/usuario/texto.txt

1: En un lugar de la Mancha (41 caracteres)
2: de cuyo nombre no quiero acordarme (36 caracteres)
3: (0 caracteres - línea vacía)
4: Un hidalgo de los de lanza en astillero (42 caracteres)
...

¿Buscar palabra? (s/n): s
Palabra a buscar: Mancha

Coincidencias:
Línea 1: En un lugar de la Mancha (encontrada)

Total de coincidencias: 1

---
ESTADÍSTICAS:
Total de líneas: 42
Longitud máxima: 85 caracteres
Longitud promedio: 45.2 caracteres
Línea más larga (Línea 23): "En aquella época vivían unos personajes..."
Línea más corta (Línea 5): ""
```

### ✅ Conceptos Practicados

- `BufferedReader` para lectura eficiente
- Lectura línea a línea con `readLine()`
- Procesamiento de strings
- Búsqueda y filtrado

---

## Ejercicio 4: Generador de Fichero de Configuración

### 📌 Objetivo

Practicar escritura eficiente de ficheros usando `BufferedWriter` y `PrintWriter`.

### 🎯 Requisitos

Crea un programa `GeneradorConfiguracion.java` que:

1. **Cree un fichero `aplicacion.conf`** con configuración de una aplicación
2. **Solicite datos al usuario:**
    - Nombre de la aplicación
    - Versión
    - Puerto del servidor
    - Usuario administrador
    - Fecha de creación
3. **Escriba en el fichero:**
    - Cabecera con timestamp
    - Sección `[APLICACION]`
    - Sección `[SERVIDOR]`
    - Sección `[ADMIN]`
    - Sección `[DIRECTORIOS]`
4. **Permita actualizar la configuración** (append mode):
    - Si el fichero existe, añada cambios al final
    - Mantega un log de modificaciones

### 💡 Pistas

```java
try (PrintWriter pw = new PrintWriter(
        new FileWriter("aplicacion.conf", true)  // Append mode
)) {
    pw.printf("# Configuración creada: %s\n", LocalDateTime.now());
    pw.println("[APLICACION]");
    pw.printf("nombre=%s\n", nombre);
}
```

- Usa `PrintWriter` con `printf()` para formato
- Primera ejecución: `true` para append, pero crea el fichero si no existe
- Usa `java.time.LocalDateTime` para timestamp

### 📝 Salida Esperada

**Fichero `aplicacion.conf`:**
```
# Configuración creada: 2024-12-15T14:30:45.123456
# ===================================================

[APLICACION]
nombre=Mi Aplicación
version=1.0.0
fecha_creacion=2024-12-15

[SERVIDOR]
puerto=8080
host=localhost
timeout=30000

[ADMIN]
usuario=admin
email=admin@ejemplo.com

[DIRECTORIOS]
home=/home/usuario/miapp
logs=./logs
data=./data

# ===================================================
# Modificación: 2024-12-15T14:35:22.654321
# Usuario cambió: puerto=8080 -> puerto=9090

[SERVIDOR]
puerto=9090
```

### ✅ Conceptos Practicados

- `BufferedWriter` para escritura eficiente
- `PrintWriter` con `printf()` para formateo
- Append mode (`FileWriter` con `true`)
- Gestión de ficheros de configuración

---

## Ejercicio 5: Procesador de Ficheros CSV

### 📌 Objetivo

Practicar lectura y escritura secuencial con procesamiento de datos formateados.

### 🎯 Requisitos

Crea un programa `ProcesadorCSV.java` que:

1. **Lea un fichero CSV** con formato:
   ```
   nombre,apellido,edad,salario
   Juan,García,28,2500
   María,López,32,3000
   ```

2. **Valide los datos:**
    - Edad entre 18 y 70
    - Salario positivo
    - Campos no vacíos

3. **Genere dos ficheros de salida:**
    - `validos.csv` - registros correctos
    - `errores.log` - registros inválidos con razón del error

4. **Mostrar estadísticas:**
    - Total registros
    - Registros válidos
    - Registros inválidos
    - Salario promedio
    - Edad promedio
    - Persona con mayor salario

### 💡 Pistas

```java
try (Scanner scanner = new Scanner(new File("empleados.csv"));
     PrintWriter pwValidos = new PrintWriter(new FileWriter("validos.csv"));
     PrintWriter pwErrores = new PrintWriter(new FileWriter("errores.log"))) {
    
    String linea;
    while (scanner.hasNextLine()) {
        linea = scanner.nextLine();
        String[] campos = linea.split(",");
        // Validar y procesar...
    }
}
```

- Usa `Scanner` para lectura
- Usa `PrintWriter` para escritura
- Parsea números con `Integer.parseInt()` y `Double.parseDouble()`
- Maneja `NumberFormatException` para datos inválidos

### 📝 Salida Esperada

**Entrada: `empleados.csv`**
```
nombre,apellido,edad,salario
Juan,García,28,2500
María,López,32,3000
Pedro,Martínez,25,-500
Ana,Rodríguez,abc,2800
Luis,Fernández,35,2200
```

**Salida: `validos.csv`**
```
nombre,apellido,edad,salario
Juan,García,28,2500
María,López,32,3000
Luis,Fernández,35,2200
```

**Salida: `errores.log`**
```
[ERROR] Línea 4: Pedro,Martínez,25,-500
  Razón: El salario debe ser positivo (-500)

[ERROR] Línea 5: Ana,Rodríguez,abc,2800
  Razón: La edad no es un número válido (abc)
```

**Consola:**
```
=== Procesador CSV ===
Fichero: empleados.csv

Procesando...

✓ Registros válidos: 3
✗ Registros inválidos: 2
─────────────────────
Total: 5

---
ESTADÍSTICAS:
Salario promedio: 2.566,67 €
Edad promedio: 31,67 años
Mayor salario: María López (3.000 €)
```

### ✅ Conceptos Practicados

- `Scanner` para lectura formateada
- Procesamiento de CSV
- Validación de datos
- Múltiples escritores simultáneos
- Manejo de excepciones

---

## 🔴 NIVEL AVANZADO

---

## Ejercicio 6: Base de Datos Simple con RandomAccessFile

### 📌 Objetivo

Dominar `RandomAccessFile` para acceso aleatorio eficiente a datos estructurados.

### 🎯 Requisitos

Crea un programa `BaseDatosEstudiantes.java` que:

1. **Gestione registros de estudiantes** en un fichero aleatorio:
    - Número de expediente (String, 10 caracteres)
    - Nombre (String, 30 caracteres)
    - Nota (double)
    - Presencia (int, porcentaje)

2. **Implemente operaciones CRUD:**
    - **C**reate: Añadir nuevo estudiante
    - **R**ead: Leer estudiante específico por número de expediente
    - **U**pdate: Actualizar nota o presencia
    - **D**elete: Marcar como eliminado

3. **Proporcione consultas:**
    - Buscar por número de expediente (acceso directo)
    - Listar todos los estudiantes
    - Estudiantes con nota >= 5.0
    - Estudiantes con presencia >= 80%

4. **Menú interactivo** con opciones numeradas

### 💡 Estructura de Registro

**Tamaño fijo por estudiante: ~52 bytes**
```
expediente: 10 bytes (String UTF)
nombre: 30 bytes (String UTF)
nota: 8 bytes (double)
presencia: 4 bytes (int)
Total: ~52 bytes
```

### 💡 Pistas

```java
try (RandomAccessFile raf = new RandomAccessFile("estudiantes.db", "rw")) {
    // Guardar en posición específica
    long posicion = numeroEstudiante * REGISTRO_SIZE;
    raf.seek(posicion);
    raf.writeUTF(expediente);
    raf.writeUTF(nombre);
    raf.writeDouble(nota);
    raf.writeInt(presencia);
}
```

- Define constante: `private static final int REGISTRO_SIZE = 52;`
- Usa `seek()` para acceso directo
- Mantén un índice de estudiantes válidos

### 📝 Salida Esperada

```
=== GESTOR DE ESTUDIANTES ===

1. Añadir estudiante
2. Buscar por expediente
3. Actualizar nota
4. Actualizar presencia
5. Listar todos
6. Estudiantes aprobados (>=5.0)
7. Presencia alta (>=80%)
8. Salir

Opción: 1
Expediente: 2024001
Nombre: Juan García López
Nota: 7.5
Presencia: 85
✓ Estudiante añadido

Opción: 2
Expediente a buscar: 2024001
─────────────────────────────
Expediente: 2024001
Nombre: Juan García López
Nota: 7.5
Presencia: 85%

Opción: 5
─────────────────────────────
1. Expediente: 2024001 | Juan García López | Nota: 7.5 | Presencia: 85%
2. Expediente: 2024002 | María López Ruiz | Nota: 8.2 | Presencia: 92%
3. Expediente: 2024003 | Pedro Martínez | Nota: 4.8 | Presencia: 70%
...
```

### ✅ Conceptos Practicados

- `RandomAccessFile` para acceso directo
- Registros de tamaño fijo
- Operaciones CRUD
- Búsqueda directa vs secuencial
- Indexación

---

## Ejercicio 7: Editor de Fichero Binario Avanzado

### 📌 Objetivo

Practicar manipulación avanzada de ficheros binarios con visualización hexadecimal.

### 🎯 Requisitos

Crea un programa `EditorBinario.java` que:

1. **Cargue un fichero binario** (cualquier tipo)
2. **Muestre en formato hexadecimal:**
    - Desplazamiento (offset)
    - Bytes en hexadecimal (16 por línea)
    - Representación ASCII
3. **Permita navegar:**
    - Ir a posición específica
    - Primera/última página
    - Página anterior/siguiente
4. **Permita modificar:**
    - Cambiar bytes específicos por desplazamiento
    - Insertar/eliminar bytes
5. **Proporcione análisis:**
    - Estadísticas del fichero
    - Búsqueda de secuencias de bytes
    - Diferencia entre dos ficheros

### 💡 Ejemplo de Visualización

```
=== EDITOR BINARIO ===
Fichero: imagen.jpg

Desplazamiento | Bytes Hexadecimales           | ASCII
─────────────────────────────────────────────────────
0x00000000     | FF D8 FF E0 00 10 4A 46 49 46 | ÿØÿà..JFIF
0x00000010     | 00 01 01 00 00 01 00 01 00 00 | ........
0x00000020     | 11 00 48 00 00 FF DB 00 43 00 | ..H..ÿÛ.C.
0x00000030     | 08 06 06 07 06 05 08 07 07 07 | ........

Página 1 de 456 (Tamaño: 465,920 bytes)
Navegación: [P]anterior [S]iguiente [I]r a [Q]salir
```

### 💡 Pistas

- Crea un método `mostrarLinea()` que formatea 16 bytes
- Usa `String.format("%02X ", byte)` para hexadecimal
- Usa `Character.isLetterOrDigit()` para ASCII válido
- Tamaño de página: 256 bytes (16 líneas × 16 bytes)

### 📝 Funcionalidades Esperadas

```
Opción: I
Desplazamiento (en hex o decimal): 0x1000
✓ Saltando a posición 0x1000

Opción: C
Desplazamiento: 0x50
Nuevo valor (hex): 4B
Antiguo valor: 48
✓ Byte modificado

Opción: B
Secuencia a buscar (hex): FF D8 FF
Encontrado en desplazamiento: 0x00000000
Siguiente coincidencia: 0x00045230
```

### ✅ Conceptos Practicados

- Lectura y escritura de binarios
- Formateo hexadecimal
- Navegación en ficheros
- Búsqueda de patrones
- Estadísticas de ficheros

---

## Ejercicio 8: Lector de Fichero con Scanner y Delimitadores

### 📌 Objetivo

Practicar `Scanner` con delimitadores personalizados para parsear datos formateados.

### 🎯 Requisitos

Crea un programa `ParserDatos.java` que:

1. **Lea un fichero con formato personalizado:**
   ```
   PERSONA|Juan García|28|juan@ejemplo.com|Madrid
   PERSONA|María López|32|maria@ejemplo.com|Barcelona
   TELEFONO|123456789|Juan García|Móvil
   ```

2. **Use Scanner con delimitadores:**
    - `|` para separar campos
    - `;` para fin de registro
    - `\n` para nueva línea

3. **Extraiga y procese datos:**
    - Valide formato de email
    - Valide número de teléfono
    - Agrupe datos por persona

4. **Genere reportes:**
    - Lista de personas con sus datos
    - Lista de teléfonos por persona
    - Personas sin email
    - Números de teléfono inválidos

### 💡 Pistas

```java
try (Scanner scanner = new Scanner(new File("datos.txt"))) {
    scanner.useDelimiter("\\|");  // Delimitador: |
    
    while (scanner.hasNext()) {
        String tipo = scanner.next();
        // Procesar según tipo...
    }
}
```

- Regex para delimitadores: `\\|` (| escapado)
- Valida email con regex: `^[A-Za-z0-9+_.-]+@(.+)$`
- Valida teléfono: `^\d{9,}$`

### 📝 Salida Esperada

```
=== PARSER DE DATOS ===

=== PERSONAS ===
1. Juan García
   Edad: 28
   Email: juan@ejemplo.com
   Ciudad: Madrid
   Teléfonos: 123456789 (Móvil)

2. María López
   Edad: 32
   Email: maria@ejemplo.com
   Ciudad: Barcelona
   Teléfonos: 987654321 (Trabajo)

---
=== VALIDACIÓN ===
✓ Emails válidos: 2
✓ Números de teléfono válidos: 2
✗ Emails inválidos: 0
✗ Números inválidos: 0
```

### ✅ Conceptos Practicados

- `Scanner` con delimitadores personalizados
- Expresiones regulares básicas
- Validación de datos
- Procesamiento de ficheros con formato

---

## Ejercicio 9: Generador de Reporte con PrintWriter

### 📌 Objetivo

Practicar escritura formateada con `PrintWriter` y `printf()`.

### 🎯 Requisitos

Crea un programa `GeneradorReporte.java` que:

1. **Lea datos de ventas** (fichero CSV):
   ```
   producto,cantidad,precio_unitario,vendedor
   Laptop,5,1200.50,Juan
   Mouse,150,25.99,María
   ```

2. **Genere un reporte profesional** en HTML con:
    - Cabecera con fecha/hora
    - Tabla de datos formateada
    - Totales y subtotales
    - Pie de página con resumen

3. **Calcule:**
    - Subtotal por vendedor
    - Total de ventas
    - Promedio de venta
    - Producto más vendido

4. **Guarde en fichero HTML** y **también en TXT** con diferente formato

### 💡 Pistas

```java
try (PrintWriter pw = new PrintWriter(new FileWriter("reporte.html"))) {
    pw.println("<!DOCTYPE html>");
    pw.println("<html>");
    pw.printf("<title>Reporte de Ventas - %s</title>\n", LocalDate.now());
    pw.printf("<h1>Total Ventas: %.2f €</h1>\n", total);
}
```

- Usa `printf()` para alineación: `%15s` (String 15 caracteres), `%10.2f` (Float 10 caracteres, 2 decimales)
- Usa `LocalDateTime.now()` para timestamp
- Genera HTML con estilos básicos

### 📝 Salida Esperada (HTML)

```html
<!DOCTYPE html>
<html>
<head>
    <title>Reporte de Ventas - 2024-12-15</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <h1>Reporte de Ventas</h1>
    <p>Generado: 2024-12-15 14:35:22</p>
    
    <table>
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio Unit.</th>
            <th>Vendedor</th>
            <th>Total</th>
        </tr>
        <tr>
            <td>Laptop</td>
            <td>5</td>
            <td>1.200,50 €</td>
            <td>Juan</td>
            <td>6.002,50 €</td>
        </tr>
        ...
    </table>
    
    <h2>Resumen</h2>
    <p>Total de Ventas: 12.345,67 €</p>
    <p>Número de Productos Vendidos: 156</p>
    <p>Vendedor con Mayor Volumen: María (8.500,00 €)</p>
</body>
</html>
```

**Salida Esperada (TXT)**
```
═══════════════════════════════════════════════════════════════
                    REPORTE DE VENTAS
═══════════════════════════════════════════════════════════════
Generado: 2024-12-15 14:35:22

PRODUCTO         CANTIDAD  PRECIO UNIT.   VENDEDOR      TOTAL
───────────────────────────────────────────────────────────────
Laptop                 5      1.200,50 €       Juan    6.002,50 €
Mouse                150         25,99 €       María    3.898,50 €
...
───────────────────────────────────────────────────────────────
TOTAL VENTAS:                                         12.345,67 €
PROMEDIO POR VENTA:                                       79,14 €
PRODUCTO MÁS VENDIDO: Mouse (150 unidades)
VENDEDOR TOP: María (8.500,00 €)
═══════════════════════════════════════════════════════════════
```

### ✅ Conceptos Practicados

- `PrintWriter` con `printf()`
- Formateo de datos numéricos
- Generación de reportes
- HTML básico
- Múltiples formatos de salida

---

## 🔴 NIVEL EXPERTO

---

## Ejercicio 10: Gestor de Ficheros Integrado (Proyecto Final)

### 📌 Objetivo

Integrar todos los conceptos anteriores en una aplicación completa.

### 🎯 Requisitos

Crea un programa `GestorFicheros.java` con menú principal que permita:

#### 1. **Explorador (Ejercicio 1)**
- Navegar por directorios
- Ver propiedades de ficheros

#### 2. **Copiar Ficheros (Ejercicio 2)**
- Copiar ficheros con indicador de progreso
- Soporte para ficheros binarios y texto

#### 3. **Procesador de Texto**
- Buscar y reemplazar (usando `BufferedReader/Writer`)
- Contar palabras, líneas, caracteres
- Convertir mayúsculas/minúsculas

#### 4. **Procesador CSV (Ejercicio 5)**
- Importar/exportar CSV
- Validar datos
- Generar reportes

#### 5. **Base de Datos Simple (Ejercicio 6)**
- CRUD de registros
- Búsqueda eficiente con `RandomAccessFile`

#### 6. **Analizador Binario (Ejercicio 7)**
- Visualizar ficheros en hex
- Buscar patrones

### 💡 Estructura del Programa

```java
public class GestorFicheros {
    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.mostrar();
    }
}

public class MenuPrincipal {
    public void mostrar() {
        // Menú inter