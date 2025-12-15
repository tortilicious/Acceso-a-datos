# Procesamiento de XML en Java - Guía Completa

## 0. Conceptos Fundamentales Previos

### 0.1 ¿Qué es XML?

XML (eXtensible Markup Language) es un formato para almacenar datos estructurados usando **etiquetas personalizadas**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biblioteca>
    <libro>
        <titulo>1984</titulo>
        <autor>George Orwell</autor>
        <año>1949</año>
    </libro>
    <libro>
        <titulo>Don Quijote</titulo>
        <autor>Cervantes</autor>
        <año>1605</año>
    </libro>
</biblioteca>
```

**¿Cuándo usamos XML?** Cuando necesitamos guardar datos jerárquicos (estructurados en árbol) de forma legible y estándar. Muy común en:
- Configuración de aplicaciones (`web.xml`, `pom.xml`)
- Intercambio de datos entre sistemas
- Documentos estructurados
- APIs SOAP (antes de que dominara JSON)

### 0.2 La Estructura de un Documento XML

```
Documento XML = Árbol jerárquico

                    <biblioteca>
                    /            \
                <libro>          <libro>
               /   |   \        /   |   \
          <titulo> <autor> <año> <titulo> ...
          1984     Orwell   1949  Don Quijote
```

### 0.3 Conceptos Clave

- **Elemento**: Una etiqueta y su contenido (`<titulo>1984</titulo>`)
- **Atributo**: Propiedad de un elemento (`<libro id="1">`)
- **Nodo**: Cada parte del árbol (elemento, atributo, texto, etc.)
- **Raíz**: El elemento que contiene todo (`<biblioteca>`)
- **Hijo**: Elemento dentro de otro (`<titulo>` es hijo de `<libro>`)

---

## 1. Las Tres Estrategias: Cuándo Usar Cada Una

Antes de aprender cada API, es crucial entender **cuándo** usarlas. Todas procesan XML, pero de formas muy diferentes.

### 1.1 Comparativa Visual

```
DOCUMENTO XML EN MEMORIA:

                    <biblioteca>
                    /            \
                <libro>          <libro>
               /   |   \        /   |   \
          <titulo> <autor> <año> ...

DOM:     Carga TODO en memoria como árbol
         ✅ Puedes navegar hacia arriba/abajo
         ❌ Lento para ficheros gigantes
         [Analógico: Tener todo el libro en la mesa]

STAX:    Lee secuencialmente (como leer línea por línea)
         ✅ Muy eficiente en memoria
         ✅ Rápido
         ❌ Solo hacia adelante
         [Analógico: Leer página por página, no puedes volver atrás]

JAXB:    Convierte XML ↔ Objetos Java automáticamente
         ✅ Muy limpio y OOP
         ✅ Type-safe
         ❌ Necesitas clases Java definidas
         [Analógico: Traducir documentos a otro idioma automáticamente]
```

### 1.2 Tabla de Decisión

| Escenario | Mejor Opción |
|-----------|--------------|
| **Fichero XML pequeño, necesitas navegar libremente** | DOM |
| **Fichero XML gigante, procesar secuencialmente** | STAX |
| **Convertir XML ↔ Objetos Java automáticamente** | JAXB |
| **Modificar XML completamente** | DOM |
| **Extraer datos específicos de XML grande** | STAX |
| **Enviar/recibir XML de una API** | JAXB |
| **Parsear configuración (web.xml, etc.)** | DOM o JAXB |

---

## 2. DOM: Document Object Model

### 2.1 El Concepto: El Árbol Completo en Memoria

DOM carga **TODO** el documento XML en memoria como una **estructura de árbol**.

Tienes acceso a todos los nodos y puedes navegar libremente:
- De padres a hijos
- De hijos a padres
- Entre hermanos

```
Proceso de DOM:

1. Lee COMPLETO el fichero XML
2. Construye un árbol en memoria
3. Ahora puedes hacer queries como:
   - "Dame el primer <libro>"
   - "Dame todos los <autor>"
   - "Busca el elemento con id=5"
```

### 2.2 Estructura de Clases DOM

```
Document (el documento completo)
  └─ Element root (el elemento raíz)
       ├─ Element hijo1
       ├─ Element hijo2
       │   └─ Text (contenido de texto)
       └─ Attribute (atributos)
```

### 2.3 Parsear XML con DOM

**Fichero de ejemplo: `biblioteca.xml`**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biblioteca>
    <libro id="1">
        <titulo>1984</titulo>
        <autor>George Orwell</autor>
        <año>1949</año>
    </libro>
    <libro id="2">
        <titulo>Don Quijote</titulo>
        <autor>Cervantes</autor>
        <año>1605</año>
    </libro>
</biblioteca>
```

**Código Java:**

```java
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

public class ParseadorDOM {
    public static void main(String[] args) {
        try {
            // 1. Crear un DocumentBuilder (el analizador)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // 2. Parsear el fichero XML
            Document documento = builder.parse(new File("biblioteca.xml"));
            
            // 3. Obtener la raíz del documento
            Element raiz = documento.getDocumentElement();
            System.out.println("Elemento raíz: " + raiz.getTagName()); // "biblioteca"
            
            // 4. Obtener todos los elementos <libro>
            NodeList libros = documento.getElementsByTagName("libro");
            
            // 5. Iterar sobre cada libro
            for (int i = 0; i < libros.getLength(); i++) {
                Element libro = (Element) libros.item(i);
                
                // Obtener atributo
                String id = libro.getAttribute("id");
                
                // Obtener contenido de elementos hijos
                String titulo = libro.getElementsByTagName("titulo")
                                    .item(0)
                                    .getTextContent();
                String autor = libro.getElementsByTagName("autor")
                                   .item(0)
                                   .getTextContent();
                String año = libro.getElementsByTagName("año")
                                 .item(0)
                                 .getTextContent();
                
                System.out.println("ID: " + id);
                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("Año: " + año);
                System.out.println("---");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**Salida:**
```
Elemento raíz: biblioteca
ID: 1
Título: 1984
Autor: George Orwell
Año: 1949
---
ID: 2
Título: Don Quijote
Autor: Cervantes
Año: 1605
---
```

### 2.4 Puntos Clave de DOM

**Obtener elementos:**

```java
// Por nombre de etiqueta (devuelve todos)
NodeList elementos = documento.getElementsByTagName("libro");

// El elemento raíz
Element raiz = documento.getDocumentElement();

// Hijos directos
NodeList hijos = elemento.getChildNodes();

// Solo elementos hijo (no nodos de texto)
NodeList elementosHijo = elemento.getElementsByTagName("*");
```

**Obtener contenido:**

```java
// Contenido de texto (⚠️ NO usar toString())
String texto = elemento.getTextContent();  // ✅ CORRECTO

// Obtener atributo
String valor = elemento.getAttribute("id");

// Verificar si tiene atributo
if (elemento.hasAttribute("id")) {
    // ...
}
```

**Navegar el árbol:**

```java
Element elemento = ...;

// Padre
Element padre = (Element) elemento.getParentNode();

// Primer hijo
Node primerHijo = elemento.getFirstChild();

// Siguiente hermano
Node hermano = elemento.getNextSibling();

// Elemento anterior
Node anterior = elemento.getPreviousSibling();
```

### 2.5 Crear y Modificar XML con DOM

DOM también permite **crear** XML desde cero:

```java
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();

// Crear documento vacío
Document doc = builder.newDocument();

// Crear elemento raíz
Element raiz = doc.createElement("biblioteca");
doc.appendChild(raiz);

// Crear un libro
Element libro = doc.createElement("libro");
libro.setAttribute("id", "3");
raiz.appendChild(libro);

// Crear elementos hijos
Element titulo = doc.createElement("titulo");
titulo.setTextContent("Fundación");
libro.appendChild(titulo);

Element autor = doc.createElement("autor");
autor.setTextContent("Isaac Asimov");
libro.appendChild(autor);

// Guardar a fichero
TransformerFactory transformerFactory = TransformerFactory.newInstance();
Transformer transformer = transformerFactory.newTransformer();
DOMSource source = new DOMSource(doc);
StreamResult result = new StreamResult(new File("salida.xml"));
transformer.transform(source, result);

System.out.println("Fichero XML creado");
```

### 2.6 Ventajas y Desventajas de DOM

**Ventajas:**
- ✅ Acceso total al árbol (arriba, abajo, lado a lado)
- ✅ Fácil de entender
- ✅ Puedes modificar y crear XML fácilmente
- ✅ Ideal para ficheros pequeños/medianos

**Desventajas:**
- ❌ Carga TODO en memoria (lento con ficheros gigantes)
- ❌ Alto consumo de memoria
- ❌ No ideal para procesar ficheros de GB

---

## 3. STAX: Streaming API for XML

### 3.1 El Concepto: Lectura Secuencial como un Stream

STAX es lo opuesto a DOM. En lugar de cargar todo en memoria, **lee el XML secuencialmente**, evento por evento:

```
DOM (árbol completo):
┌────────────────────────────────┐
│ DOCUMENTO COMPLETO EN MEMORIA  │
│  <biblioteca>                  │
│    <libro>...</libro>          │
│    <libro>...</libro>          │
│  </biblioteca>                 │
└────────────────────────────────┘

STAX (stream):
        LECTOR SECUENCIAL
        ↓    ↓    ↓    ↓    ↓
    Evento1, Evento2, Evento3, ...
    
    Ejemplo de eventos:
    - START_ELEMENT: <libro>
    - ATTRIBUTE: id="1"
    - CHARACTERS: George Orwell
    - END_ELEMENT: </libro>
```

**Analogía:** DOM es como tener un libro completo en la mesa. STAX es como leer el libro página por página y olvidar las páginas anteriores.

### 3.2 Dos Interfaces de STAX

STAX ofrece **dos formas** de leer XML:

#### 3.2.1 API Cursor (XMLStreamReader)

Usas un **cursor** que apunta al evento actual y se mueve hacia adelante:

```java
while (reader.hasNext()) {
    int evento = reader.next();  // Obtén el siguiente evento
    // El cursor se movió, el evento anterior se perdió
}
```

Es como **picar** línea por línea de un documento. El cursor se mueve y lo que ya pasaste desaparece.

#### 3.2.2 API Iterador (XMLEventReader)

Obtienes **eventos** uno a uno que puedes procesar:

```java
while (reader.hasNext()) {
    XMLEvent evento = reader.nextEvent();
    if (evento.isStartElement()) {
        // El evento permanece, puedes consultarlo
    }
}
```

Es como **obtener notas** de lo que leíste. Cada nota es un objeto que puedes inspeccionar.

### 3.3 API Cursor: XMLStreamReader

```java
import javax.xml.stream.*;
import java.io.FileInputStream;

public class ParseadorSTAXCursor {
    public static void main(String[] args) {
        try {
            // 1. Crear un XMLInputFactory
            XMLInputFactory factory = XMLInputFactory.newInstance();
            
            // 2. Crear un XMLStreamReader (el cursor)
            XMLStreamReader reader = factory.createXMLStreamReader(
                new FileInputStream("biblioteca.xml")
            );
            
            // 3. Leer evento por evento
            while (reader.hasNext()) {
                int evento = reader.next();
                
                // Procesar según el tipo de evento
                switch (evento) {
                    case XMLStreamConstants.START_ELEMENT:
                        String nombreElemento = reader.getLocalName();
                        System.out.println("Abriendo: <" + nombreElemento + ">");
                        
                        // Obtener atributos (si los hay)
                        for (int i = 0; i < reader.getAttributeCount(); i++) {
                            String atributo = reader.getAttributeName(i).toString();
                            String valor = reader.getAttributeValue(i);
                            System.out.println("  @" + atributo + "=" + valor);
                        }
                        break;
                        
                    case XMLStreamConstants.CHARACTERS:
                        String texto = reader.getText().trim();
                        if (!texto.isEmpty()) {
                            System.out.println("  Texto: " + texto);
                        }
                        break;
                        
                    case XMLStreamConstants.END_ELEMENT:
                        String nombre = reader.getLocalName();
                        System.out.println("Cerrando: </" + nombre + ">");
                        break;
                }
            }
            
            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**Salida:**
```
Abriendo: <biblioteca>
Abriendo: <libro>
  @id=1
Abriendo: <titulo>
  Texto: 1984
Cerrando: </titulo>
Abriendo: <autor>
  Texto: George Orwell
Cerrando: </autor>
Abriendo: <año>
  Texto: 1949
Cerrando: </año>
Cerrando: </libro>
Abriendo: <libro>
  @id=2
...
```

### 3.4 API Iterador: XMLEventReader

```java
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;

public class ParseadorSTAXIterador {
    public static void main(String[] args) {
        try {
            // 1. Crear el factory
            XMLInputFactory factory = XMLInputFactory.newInstance();
            
            // 2. Crear un XMLEventReader (iterador de eventos)
            XMLEventReader reader = factory.createXMLEventReader(
                new FileInputStream("biblioteca.xml")
            );
            
            // 3. Iterar sobre eventos
            while (reader.hasNext()) {
                XMLEvent evento = reader.nextEvent();
                
                // Comprobar el tipo de evento
                if (evento.isStartElement()) {
                    StartElement start = evento.asStartElement();
                    String nombre = start.getName().getLocalPart();
                    System.out.println("Abriendo: <" + nombre + ">");
                    
                    // Obtener atributos
                    var atributos = start.getAttributes();
                    while (atributos.hasNext()) {
                        Attribute attr = (Attribute) atributos.next();
                        System.out.println("  @" + attr.getName() + "=" + attr.getValue());
                    }
                }
                
                else if (evento.isCharacters()) {
                    Characters chars = evento.asCharacters();
                    String texto = chars.getData().trim();
                    if (!texto.isEmpty()) {
                        System.out.println("  Texto: " + texto);
                    }
                }
                
                else if (evento.isEndElement()) {
                    EndElement end = evento.asEndElement();
                    String nombre = end.getName().getLocalPart();
                    System.out.println("Cerrando: </" + nombre + ">");
                }
            }
            
            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**La salida es idéntica al cursor**, pero el código es más orientado a objetos (eventos como objetos).

### 3.5 Comparativa: Cursor vs Iterador

| Aspecto | Cursor | Iterador |
|---------|--------|----------|
| **Clase** | XMLStreamReader | XMLEventReader |
| **Obtener evento** | `int evento = reader.next()` | `XMLEvent evento = reader.nextEvent()` |
| **Información** | Métodos como `getLocalName()` | Objetos event con métodos |
| **Código** | Switch/if sobre integers | Más orientado a objetos |
| **Memoria** | Ligeramente menos | Ligeramente más |
| **Facilidad** | Menos intuitiva | Más intuitiva |

**Analogía:**
- **Cursor**: Señalas "estoy aquí" en el documento y preguntas "¿qué hay en esta posición?"
- **Iterador**: Recibes una nota que dice "esto sucedió"

### 3.6 Ventajas y Desventajas de STAX

**Ventajas:**
- ✅ Muy eficiente en memoria (no carga todo)
- ✅ Muy rápido incluso con ficheros gigantes
- ✅ Bajo consumo de CPU
- ✅ Ideal para streaming/procesamiento en tiempo real

**Desventajas:**
- ❌ Solo puedes ir hacia adelante (no hacia atrás)
- ❌ No puedes "saltar" a un elemento específico fácilmente
- ❌ Más código que DOM
- ❌ No ideal si necesitas acceder a elementos múltiples veces

### 3.7 Caso de Uso: Procesar Fichero XML Gigante

```java
// Si tuvieras un fichero con 1 millón de libros,
// DOM crash (memoria agotada)
// STAX funciona perfectamente:

XMLInputFactory factory = XMLInputFactory.newInstance();
XMLStreamReader reader = factory.createXMLStreamReader(
    new FileInputStream("biblioteca_gigante.xml")
);

int contadorLibros = 0;

while (reader.hasNext()) {
    int evento = reader.next();
    
    if (evento == XMLStreamConstants.START_ELEMENT 
        && "libro".equals(reader.getLocalName())) {
        contadorLibros++;
        
        // Procesar este libro
        // La memoria se libera cuando pasas al siguiente
        // porque STAX no mantiene historial
    }
}

System.out.println("Total: " + contadorLibros + " libros");
reader.close();
```

---

## 4. JAXB: XML Binding

### 4.1 El Concepto: Mapeo Automático XML ↔ Objetos

JAXB es totalmente diferente. No te importa cómo está estructurado el XML internamente. Lo que haces es:

1. **Defines clases Java** que representen la estructura XML
2. **Anotas** esas clases para indicarle a JAXB cómo mapearlas
3. **JAXB automáticamente** convierte XML a objetos y viceversa

```
XML:                          JAXB:                    Java:

<libro>                  ┌─────────────────┐     public class Libro {
  <titulo>1984</titulo>  │ MAPEADOR JAXB   │       String titulo;
  <autor>Orwell</autor>  │   (el puente)   │       String autor;
</libro>                 └─────────────────┘     }

                              ↓ Marshal
                    (XML → Objeto)
                              ↑ Unmarshal
                    (Objeto → XML)
```

### 4.2 Definir Clases JAXB

**Paso 1: Crear la clase `Libro`**

```java
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "libro")  // Esta clase representa el elemento raíz
@XmlAccessorType(XmlAccessType.FIELD)
public class Libro {
    
    @XmlAttribute  // Este es un atributo XML
    private String id;
    
    @XmlElement    // Estos son elementos XML
    private String titulo;
    
    @XmlElement
    private String autor;
    
    @XmlElement(name = "año")  // El nombre en XML es diferente
    private int año;
    
    // Constructores
    public Libro() {}
    
    public Libro(String id, String titulo, String autor, int año) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    
    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", año=" + año +
                '}';
    }
}
```

**Paso 2: Crear la clase `Biblioteca` (contenedor)**

```java
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "biblioteca")
@XmlAccessorType(XmlAccessType.FIELD)
public class Biblioteca {
    
    @XmlElement(name = "libro")  // Lista de <libro>
    private List<Libro> libros;
    
    public Biblioteca() {}
    
    public Biblioteca(List<Libro> libros) {
        this.libros = libros;
    }
    
    public List<Libro> getLibros() {
        return libros;
    }
    
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
    
    @Override
    public String toString() {
        return "Biblioteca{" +
                "libros=" + libros +
                '}';
    }
}
```

### 4.3 Convertir XML a Objeto (Unmarshalling)

```java
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class LectorJAXB {
    public static void main(String[] args) {
        try {
            // 1. Crear un contexto JAXB para la clase Biblioteca
            JAXBContext context = JAXBContext.newInstance(Biblioteca.class);
            
            // 2. Crear un Unmarshaller (convierte XML a Java)
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            // 3. Convertir el fichero XML a objeto Java
            Biblioteca biblioteca = (Biblioteca) unmarshaller.unmarshal(
                new File("biblioteca.xml")
            );
            
            // 4. Usar los datos como objetos Java normales
            System.out.println("Biblioteca cargada con " + 
                             biblioteca.getLibros().size() + " libros");
            
            for (Libro libro : biblioteca.getLibros()) {
                System.out.println(libro);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**Salida:**
```
Biblioteca cargada con 2 libros
Libro{id='1', titulo='1984', autor='George Orwell', año=1949}
Libro{id='2', titulo='Don Quijote', autor='Cervantes', año=1605}
```

### 4.4 Convertir Objeto a XML (Marshalling)

```java
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Arrays;

public class EscritorJAXB {
    public static void main(String[] args) {
        try {
            // 1. Crear objetos Java
            Libro libro1 = new Libro("1", "1984", "George Orwell", 1949);
            Libro libro2 = new Libro("2", "Don Quijote", "Cervantes", 1605);
            
            Biblioteca biblioteca = new Biblioteca(
                Arrays.asList(libro1, libro2)
            );
            
            // 2. Crear contexto JAXB
            JAXBContext context = JAXBContext.newInstance(Biblioteca.class);
            
            // 3. Crear un Marshaller (convierte Java a XML)
            Marshaller marshaller = context.createMarshaller();
            
            // 4. Configurar para que el XML sea legible (con indentación)
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            // 5. Convertir a XML y guardar
            marshaller.marshal(biblioteca, new File("salida_jaxb.xml"));
            
            System.out.println("Fichero XML creado: salida_jaxb.xml");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**Fichero generado (`salida_jaxb.xml`):**
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<biblioteca>
    <libro id="1">
        <titulo>1984</titulo>
        <autor>George Orwell</autor>
        <año>1949</año>
    </libro>
    <libro id="2">
        <titulo>Don Quijote</titulo>
        <autor>Cervantes</autor>
        <año>1605</año>
    </libro>
</biblioteca>
```

### 4.5 Anotaciones JAXB Comunes

| Anotación | Propósito |
|-----------|-----------|
| `@XmlRootElement` | Marca el elemento raíz |
| `@XmlElement` | Campo que es un elemento XML |
| `@XmlAttribute` | Campo que es un atributo XML |
| `@XmlAccessorType` | Indica cómo acceder a los campos (FIELD, PROPERTY, etc.) |
| `@XmlElementWrapper` | Envuelve lista en elemento |
| `@XmlTransient` | Campo que NO se mapea a XML |
| `@XmlValue` | Campo cuyo valor es el texto del elemento |
| `@XmlList` | Campo que representa una lista de valores |

### 4.6 Ejemplo Avanzado: Elementos Anidados

**XML:**
```xml
<biblioteca>
    <libro id="1">
        <titulo>1984</titulo>
        <autor>
            <nombre>George</nombre>
            <apellido>Orwell</apellido>
        </autor>
    </libro>
</biblioteca>
```

**Clases Java:**

```java
import javax.xml.bind.annotation.*;

@XmlRootElement
public class Autor {
    @XmlElement
    private String nombre;
    
    @XmlElement
    private String apellido;
    
    // Constructores, getters, setters...
}

@XmlRootElement
public class Libro {
    @XmlAttribute
    private String id;
    
    @XmlElement
    private String titulo;
    
    @XmlElement
    private Autor autor;  // Anidamiento automático
    
    // Constructores, getters, setters...
}

@XmlRootElement
public class Biblioteca {
    @XmlElement(name = "libro")
    private List<Libro> libros;
    
    // Constructores, getters, setters...
}
```

JAXB automáticamente mapea los elementos anidados.

### 4.7 Ventajas y Desventajas de JAXB

**Ventajas:**
- ✅ Muy limpio y orientado a objetos
- ✅ Automático (no necesitas escribir código de conversión)
- ✅ Type-safe (el compilador te avisa de errores)
- ✅ Ideal para APIs REST/SOAP
- ✅ Código muy legible

**Desventajas:**
- ❌ Necesitas definir las clases Java por adelantado
- ❌ Si el XML es muy complejo, las anotaciones se complican
- ❌ Overhead de rendimiento (más lento que DOM/STAX)
- ❌ Si el XML varía mucho, es inflexible

---

## 5. Comparativa Completa de las Tres APIs

| Criterio | DOM | STAX | JAXB |
|----------|-----|------|------|
| **Filosofía** | Árbol completo | Stream secuencial | Mapeo automático |
| **Memoria** | Muy alta | Muy baja | Media |
| **Velocidad** | Lenta con ficheros grandes | Muy rápida | Media |
|