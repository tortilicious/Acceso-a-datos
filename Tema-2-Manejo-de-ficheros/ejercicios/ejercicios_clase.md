# Ejercicios Propuestos - Tema 2: Manejo de Ficheros

## Ciclo DAM - 2º Curso
**Módulo:** Acceso a Datos  
**Tema:** Manejo de Ficheros

---

## 📋 Índice de Ejercicios

1. [Ficheros de Acceso Aleatorio](#ejercicio-1-ficheros-de-acceso-aleatorio)
2. [Listar Propiedades de Ficheros](#ejercicio-2-listar-propiedades-de-ficheros-y-carpetas)
3. [Aplicar Filtro](#ejercicio-3-aplicar-filtro)
4. [API DOM - Leer XML](#ejercicio-4-api-dom-leer-fichero-xml)
5. [API SAX - Leer XML](#ejercicio-5-api-sax-leer-fichero-xml)
6. [DataInputStream](#ejercicio-6-datainputstream)
7. [Serialización](#ejercicio-7-serialización)

---

## Ejercicio 1: Ficheros de Acceso Aleatorio

### 📌 Descripción

Crea un fichero aleatorio `modulosdam.dat` donde escribirás los datos de los módulos del ciclo DAM. Los datos están en tres arrays que debes procesar usando `RandomAccessFile`.

### 📊 Datos de Entrada

```
nombre[]   = {"Acceso a datos", "Bases de datos", ...}
horas[]    = {200, 160, ...}
curso[]    = {"2º", "1º", ...}
```

### 🎯 Requisitos

- Usar **`RandomAccessFile`** para escribir y leer datos
- Escribir los datos de forma secuencial
- **Mostrar el contenido del fichero** antes de finalizar el programa
- Usar un tamaño de registro fijo para poder acceder aleatoriamente

### 💡 Pistas

- Usa `RandomAccessFile` con modo `"rw"` (lectura/escritura)
- Define un tamaño fijo para cada registro (ej: 100 bytes)
- Para strings, usa `writeUTF()` y `readUTF()`
- Usa `raf.seek()` para moverte a diferentes posiciones

### 📝 Salida Esperada

```
=== Lectura del fichero modulosdam.dat ===
Módulo 1: Acceso a datos | Horas: 200 | Curso: 2º
Módulo 2: Bases de datos | Horas: 160 | Curso: 1º
...
```

---

## Ejercicio 2: Listar Propiedades de Ficheros y Carpetas

### 📌 Descripción

Crea un programa que liste el contenido del directorio de trabajo. Para cada fichero, muestra sus propiedades principales.

### 🎯 Requisitos

Para cada fichero mostrar:
- **Nombre del fichero**
- **Se puede leer** (true/false)
- **Se puede escribir** (true/false)
- **Tamaño en bytes**
- **Ruta absoluta**

### 💡 Pistas

- Usa la clase **`File`**
- El directorio de trabajo es `.` (punto)
- Usa `File.listFiles()` para obtener el contenido
- Métodos útiles: `canRead()`, `canWrite()`, `length()`, `getAbsolutePath()`

### 📝 Salida Esperada

```
=== Contenido del Directorio: /home/usuario/proyecto ===

Nombre: archivo1.txt
  ¿Se puede leer? true
  ¿Se puede escribir? true
  Tamaño: 1245 bytes
  Ruta absoluta: /home/usuario/proyecto/archivo1.txt

Nombre: imagen.jpg
  ¿Se puede leer? true
  ¿Se puede escribir? false
  Tamaño: 45632 bytes
  Ruta absoluta: /home/usuario/proyecto/imagen.jpg

...
```

---

## Ejercicio 3: Aplicar Filtro

### 📌 Descripción

Crea un programa que liste únicamente los ficheros del directorio de trabajo que tengan extensión `.txt`.

### 🎯 Requisitos

- Listar solo ficheros con extensión `.txt`
- Mostrar nombre, tamaño y ruta absoluta
- Indicar el total de ficheros `.txt` encontrados

### 💡 Pistas

- Usa **`File.listFiles(FileFilter)`** o implementa tu propio filtro
- Comprueba la extensión con `endsWith(".txt")`
- Usa `isFile()` para asegurarte que es un fichero (no una carpeta)

### 📝 Salida Esperada

```
=== Ficheros .txt en el Directorio ===

1. notas.txt (2KB) - /home/usuario/proyecto/notas.txt
2. apuntes.txt (15KB) - /home/usuario/proyecto/apuntes.txt
3. todo.txt (512B) - /home/usuario/proyecto/todo.txt

Total: 3 ficheros .txt encontrados
```

---

## Ejercicio 4: API DOM - Leer Fichero XML

### 📌 Descripción

Partiendo del fichero `menus.xml` (disponible en la carpeta `xml_ejercicios`), crea un programa que liste los menús con su contenido.

### 🎯 Requisitos

Para cada menú mostrar:
- **Número de menú**
- **Cada plato** con:
    - Nombre del plato
    - Tipo (primero, segundo o postre)
    - Lista de ingredientes (solo nombres, sin cantidades)

### 💡 Pistas

- Usa **`DocumentBuilder`** para parsear el XML
- Usa **`getElementsByTagName()`** para navegar
- La estructura XML es jerárquica: MENU → PLATO → INGREDIENTES → INGREDIENTE
- Usa un método auxiliar para obtener texto de elementos

### 📝 Estructura XML de Referencia

```xml
<?xml version="1.0" encoding="UTF-8"?>
<menucard>
    <menu numero="1">
        <plato tipo="primero">
            <nombre>Sopa de cebolla</nombre>
            <ingredientes>
                <ingrediente cantidad="1">Cebolla</ingrediente>
                <ingrediente cantidad="2">Caldo</ingrediente>
            </ingredientes>
        </plato>
        <plato tipo="segundo">
            <nombre>Lomo al horno</nombre>
            <ingredientes>
                <ingrediente cantidad="1">Lomo</ingrediente>
                <ingrediente cantidad="1">Ajo</ingrediente>
            </ingredientes>
        </plato>
    </menu>
</menucard>
```

### 📝 Salida Esperada

```
=== MENÚ 1 ===

PRIMERO: Sopa de cebolla
  Ingredientes: Cebolla, Caldo

SEGUNDO: Lomo al horno
  Ingredientes: Lomo, Ajo

POSTRE: Helado de vainilla
  Ingredientes: Leche, Azúcar, Vainilla

...
```

---

## Ejercicio 5: API SAX - Leer Fichero XML

### 📌 Descripción

Muestra el contenido del fichero `books.xml` usando la librería **SAX** (Simple API for XML).

### 🎯 Requisitos

Mostrar la estructura del XML con el formato especificado:
- Elementos con nombre
- Atributos con su valor
- Contenido de texto

**Orden de presentación:**
1. Elemento (nombre)
2. Atributos (si existen)
3. Contenido (si existe)
4. Subelementos

### 💡 Pistas

- Implementa un **`DefaultHandler`** personalizado
- Sobrescribe los métodos: `startElement()`, `endElement()`, `characters()`
- Usa `attributes.getQName(i)` y `attributes.getValue(i)` para obtener atributos
- Controla indentación con un contador de profundidad

### 📝 Estructura XML de Referencia

```xml
<?xml version="1.0" encoding="UTF-8"?>
<bookstore>
    <book category="COOKING">
        <title lang="en">Everyday Italian</title>
        <author>Giada De Laurentiis</author>
        <year>2005</year>
        <price>30.00</price>
    </book>
    <book category="CHILDREN">
        <title lang="en">Harry Potter</title>
        <author>J.K. Rowling</author>
        <year>2005</year>
        <price>29.99</price>
    </book>
</bookstore>
```

### 📝 Salida Esperada

```
Elemento: bookstore
  Elemento: book
    Atributo: category = COOKING
    Elemento: title
      Atributo: lang = en
      Contenido: Everyday Italian
    Elemento: author
      Contenido: Giada De Laurentiis
    Elemento: year
      Contenido: 2005
    Elemento: price
      Contenido: 30.00
  Elemento: book
    Atributo: category = CHILDREN
    Elemento: title
      Atributo: lang = en
      Contenido: Harry Potter
    Elemento: author
      Contenido: J.K. Rowling
    Elemento: year
      Contenido: 2005
    Elemento: price
      Contenido: 29.99
```

---

## Ejercicio 6: DataInputStream

### 📌 Descripción

Crea una aplicación que almacene datos de vehículos usando `DataInputStream` y `DataOutputStream`. Los datos se pedirán por teclado y se añadirán al fichero sin sobrescribir datos anteriores.

### 🎯 Requisitos

**Datos de cada vehículo (en este orden):**
1. Matrícula (String)
2. Marca (String)
3. Tamaño del depósito (double)
4. Modelo (String)

**Funcionalidad:**
- Pedir datos por teclado con `JOptionPane.showInputDialog()`
- Añadir al fichero sin sobrescribir (append mode)
- El fichero siempre será el mismo: `vehiculos.dat`
- Al finalizar, mostrar todos los datos en cuadros de diálogo

### 💡 Pistas

- Usa `DataOutputStream` para escribir tipos primitivos y strings
- Usa `DataInputStream` para leer en el mismo orden
- Para append mode en `DataOutputStream`, necesitas capturar excepciones de EOF
- Usa `FileOutputStream` con `true` para modo append
- Cada ejecución añade un vehículo nuevo

### 📝 Estructura del Programa

```
Inicio
  ├─ ¿Leer fichero existente? (si existe)
  ├─ Pedir datos del nuevo vehículo
  ├─ Escribir en fichero (append)
  └─ Mostrar todos los vehículos en diálogos
Fin
```

### 📝 Salida Esperada (Diálogo 1)

```
Vehículo 1
---------
Matrícula: 1234ABC
Marca: Toyota
Depósito: 65.5 litros
Modelo: Corolla 2020
```

### 📝 Salida Esperada (Diálogo 2)

```
Vehículo 2
---------
Matrícula: 5678DEF
Marca: Ford
Depósito: 72.0 litros
Modelo: Fiesta 2021
```

---

## Ejercicio 7: Serialización

### 📌 Descripción

Realiza el **mismo ejercicio que el anterior (Ejercicio 6)**, pero usando **serialización de objetos** en lugar de `DataInputStream`.

### 🎯 Requisitos

**Crear una clase `Vehiculo` con:**
- Atributos: matrícula, marca, depósito, modelo
- Constructor con todos los parámetros
- Métodos getter y setter
- **Implementar `Serializable`**

**Funcionalidad:**
- Serializar objetos `Vehiculo` completos (no datos individuales)
- Usar `ObjectOutputStream` para escribir
- Usar `ObjectInputStream` para leer
- Append mode con `ObjectOutputStream` personalizado (necesario para múltiples objetos)
- El fichero será: `vehiculos_ser.dat`

### 💡 Pistas

**Clase Vehiculo:**
```java
public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String matricula;
    private String marca;
    private double deposito;
    private String modelo;
    
    // Constructor, getters, setters...
}
```

**ObjectOutputStream personalizado para append:**
```java
public class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    
    @Override
    protected void writeStreamHeader() throws IOException {
        // No escribir cabecera si el fichero ya tiene datos
    }
}
```

### 📝 Estructura del Programa

```
Inicio
  ├─ Pedir datos del nuevo vehículo
  ├─ Crear objeto Vehiculo
  ├─ Serializar en fichero (append)
  ├─ Leer todos los Vehiculos serializados
  └─ Mostrar en diálogos
Fin
```

### 📝 Salida Esperada

```
Vehículo 1
---------
Matrícula: 1234ABC
Marca: Toyota
Depósito: 65.5 litros
Modelo: Corolla 2020

[Siguiente Diálogo]

Vehículo 2
---------
Matrícula: 5678DEF
Marca: Ford
Depósito: 72.0 litros
Modelo: Fiesta 2021
```

### ⚠️ Diferencia con Ejercicio 6

| Aspecto | Ejercicio 6 | Ejercicio 7 |
|--------|-----------|-----------|
| **Estructura** | Datos individuales | Objetos completos |
| **Escritura** | `writeUTF()`, `writeDouble()` | `writeObject()` |
| **Lectura** | `readUTF()`, `readDouble()` | `readObject()` |
| **Orden** | Crítico (debe coincidir) | Automático (orden de objetos) |
| **Flexibilidad** | Baja | Alta (cambiar propiedades es fácil) |
| **Fichero** | `vehiculos.dat` | `vehiculos_ser.dat` |

---

## 🎓 Resumen de Conceptos

| Ejercicio | Concepto Principal | API/Clase |
|-----------|-------------------|-----------|
| 1 | Acceso aleatorio a ficheros | `RandomAccessFile` |
| 2 | Propiedades de ficheros | `File`, métodos de información |
| 3 | Filtrado de ficheros | `FileFilter`, predicados |
| 4 | Parsing XML jerárquico | DOM (Document Object Model) |
| 5 | Parsing XML secuencial | SAX (Simple API for XML) |
| 6 | Almacenamiento de tipos | `DataInputStream/OutputStream` |
| 7 | Persistencia de objetos | Serialización (`Serializable`) |

---

## ✅ Criterios de Evaluación

- ✓ El código funciona correctamente
- ✓ Se usan las APIs/clases especificadas
- ✓ El manejo de excepciones es correcto
- ✓ El código está bien comentado y es legible
- ✓ Se sigue la estructura de directorios del proyecto
- ✓ Los ficheros se generan en las rutas correctas
- ✓ La salida es clara y bien formateada

---

## 📚 Referencias Útiles

- [Java File API Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html)
- [Java DOM Parser Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.xml/javax/xml/parsers/DocumentBuilder.html)
- [Java SAX Parser Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.xml/org/xml/sax/ContentHandler.html)
- [Java Serialization Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/Serializable.html)

---

**Última actualización:** 2024-2025  
**Grado:** Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Curso:** 2º