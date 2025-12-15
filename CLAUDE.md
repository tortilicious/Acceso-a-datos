# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This is a learning repository for the "Acceso a Datos" (Data Access) module of the second year of the DAM (Desarrollo de Aplicaciones Multiplataforma) degree program. The repository contains theory, exercises, and formal assignments organized by topic.

**Repository URL**: https://github.com/tortilicious/Acceso-a-datos

## Project Structure

The repository follows a topic-based structure:

```
AccesoDatos/
├── Tema-2-Manejo-de-ficheros/
│   ├── teoria/                    # Theory documents (markdown)
│   ├── ejercicios/                # Practice exercises
│   └── entregas/                  # Formal submissions/assignments
│       └── Tarea_ AD02/
│           ├── src/
│           │   ├── Ejercicio_1/   # DOM-based XML reading
│           │   └── Ejercicio_2/   # DOM to JAXB conversion
│           │       └── JAXB/      # JAXB annotated classes
│           └── lib/               # External libraries (JAXB JARs)
│
├── Tema-3-Manejo-de-conectores/   # Database connectors (planned)
└── Tema-4-Mapeo-objeto-relacional/ # ORM mapping (planned)
```

## Key Technologies

- **Language**: Java 11+
- **IDE**: IntelliJ IDEA
- **XML Processing**:
  - DOM (Document Object Model) for tree-based XML navigation
  - STAX (Streaming API for XML) for sequential processing
  - JAXB (Java Architecture for XML Binding) for object mapping
- **Libraries**: Jakarta XML Binding API 3.0 (JAXB implementation)

## Development Commands

### Project Setup

This is an IntelliJ IDEA project. To work with it:

1. Open the project in IntelliJ IDEA (the `.iml` and `.idea/` files configure the project)
2. Ensure JDK 11+ is configured
3. External libraries are stored in `lib/` directories within each assignment

### Running Code

Since this is a learning repository with multiple independent exercises:

```bash
# Navigate to the specific source directory
cd "Tema-2-Manejo-de-ficheros/entregas/Tarea_ AD02/src"

# Compile with classpath to JAXB libraries
javac -cp "../lib/*" Ejercicio_1/*.java
javac -cp "../lib/*" Ejercicio_2/*.java Ejercicio_2/JAXB/*.java

# Run specific exercises
java -cp ".:../lib/*" Ejercicio_1.Main
java -cp ".:../lib/*" Ejercicio_2.Main
```

Note: Adjust path separators for Windows (use `;` instead of `:` for classpath)

## Architecture Notes

### XML Processing Strategy Selection

The codebase demonstrates three distinct XML processing approaches, each with specific use cases:

**DOM (Document Object Model)**
- Used in: `Ejercicio_1/Main.java`
- Loads entire XML document into memory as a tree structure
- Best for: Small to medium XML files requiring random access and navigation
- Pattern: Parse once, query multiple times with `getElementsByTagName()`

**STAX (Streaming API)**
- Covered in theory but not yet implemented in exercises
- Processes XML sequentially, event by event
- Best for: Large XML files (GB+), memory-constrained environments
- Pattern: Forward-only cursor or event iterator

**JAXB (XML Binding)**
- Used in: `Ejercicio_2/` with annotated classes in `JAXB/` package
- Automatically maps XML ↔ Java objects using annotations
- Best for: Type-safe object-oriented XML handling, API integration
- Pattern: Define annotated POJOs, use Marshaller/Unmarshaller

### DOM to JAXB Conversion Pattern

The `Ejercicio_2/Main.java` demonstrates a hybrid approach:

1. Read XML using DOM for flexibility (`DocumentBuilder`)
2. Extract data from DOM tree structure
3. Create JAXB-annotated objects from extracted data
4. Marshal objects to new XML file with JAXB

This pattern is useful when:
- Source XML structure differs from desired output structure
- Need to transform or filter data during conversion
- Want type-safe output but have unstructured input

### JAXB Annotation Patterns

Classes in `Ejercicio_2/JAXB/` follow these conventions:

```java
@XmlRootElement(name = "elementName")    // Root element
@XmlAccessorType(XmlAccessType.FIELD)    // Field-based access
public class Entity {
    @XmlAttribute                         // XML attribute
    private String id;

    @XmlElement                           // XML element
    private String name;

    @XmlElement(name = "xmlName")         // Custom XML name
    private String javaFieldName;

    @XmlElementWrapper(name = "wrapper")  // List wrapper element
    @XmlElement(name = "item")
    private List<Item> items;
}
```

## File Path Conventions

- Relative paths in code are relative to the project root
- XML data files are typically in the same directory as source code or `src/`
- Example: `"src/DepartamentosEmpleados.xml"` in `Ejercicio_2/Main.java`

## Naming Conventions

- **Packages**: `com.dam.tema{numero}` or simple names like `Ejercicio_1`, `Ejercicio_2`
- **Classes**: PascalCase (e.g., `LecturaDOM`, `Departamento`, `Empresa`)
- **Methods**: camelCase (e.g., `obtenerTexto()`, `setNombre()`)
- **Variables**: camelCase
- **Comments**: Spanish for educational clarity

## Common Utilities

### Helper Method Pattern

Exercises use a common utility pattern for extracting XML text content:

```java
private static String obtenerTexto(Element elemento, String tagName) {
    return elemento.getElementsByTagName(tagName)
            .item(0)
            .getTextContent();
}
```

This simplifies DOM navigation by encapsulating the verbose `getElementsByTagName().item(0).getTextContent()` pattern.

## JAXB Dependencies

External libraries required for JAXB (located in `lib/` directories):

- `jakarta.xml.bind-api-3.0.0.jar` - JAXB API
- `jakarta.activation-api-2.0.1.jar` - Activation framework
- `jaxb-core-3.0.0.jar` - JAXB core
- `jaxb-runtime-3.0.0.jar` - JAXB runtime
- `istack-commons-runtime-3.0.12.jar` - Supporting utilities

These must be in the classpath when compiling and running JAXB code.

## Theory Documents

Extensive theory is documented in markdown files in `teoria/` directories:

- `Procesamiento Ficheros.md` - Comprehensive guide to Java I/O, streams, file handling
- `Procesamiento XML.md` - Complete coverage of DOM, STAX, and JAXB with examples

These documents provide context and reference for all exercises.

## Educational Context

This is a learning repository where:

- Comments are in Spanish for clarity during learning
- Code prioritizes educational clarity over production optimization
- Each exercise demonstrates specific concepts in isolation
- Formal submissions are in `entregas/` directories
- The student is collaborating with Claude AI as a study tool
- Para el curso voy a usar JDK 23