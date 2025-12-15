# Acceso a Datos - 2º DAM

**Repositorio:** [https://github.com/tortilicious/Acceso-a-datos](https://github.com/tortilicious/Acceso-a-datos)

Repositorio para el estudio y desarrollo de la asignatura de **Acceso a Datos** del grado superior DAM.

## 📋 Descripción del Módulo

Este módulo forma parte de la formación del Ciclo de Desarrollo de Aplicaciones Multiplataforma y enseña a gestionar la persistencia de datos en aplicaciones multiplataforma a través de diferentes tecnologías y patrones.

## 🎯 Objetivos del Módulo

### Competencias Generales

- Interpretar el diseño lógico de bases de datos, analizando y cumpliendo especificaciones para gestionar bases de datos
- Seleccionar y emplear lenguajes, herramientas y librerías para desarrollar aplicaciones multiplataforma con acceso a bases de datos
- Gestionar la información almacenada, planificando e implementando sistemas de formularios e informes
- Valorar y emplear herramientas específicas para crear tutoriales, manuales y documentación de aplicaciones
- Seleccionar y emplear lenguajes y herramientas para desarrollar componentes personalizados en sistemas ERP-CRM
- Verificar los componentes software, analizando especificaciones para completar un plan de pruebas

### Resultados de Aprendizaje

| Bloque | Descripción |
|--------|-------------|
| **Persistencia en Ficheros** | Desarrollar aplicaciones que gestionan información en ficheros (XML con APIs DOM, SAX, JAXP) |
| **Persistencia en BDR-BDOR** | Gestionar datos en bases de datos relacionales y objeto-relacionales, implementar ORM |
| **Persistencia en BD Documentales** | Trabajar con bases de datos nativas XML y JSON |
| **Componentes de Acceso a Datos** | Programar componentes reutilizables identificando sus características y usando herramientas de desarrollo |

## 📚 Estructura del Repositorio

```
Acceso-a-datos/
├── README.md
│
├── Tema-2-Manejo-de-ficheros/
│   ├── teoria/
│   │   └── apuntes.md                   # Apuntes teóricos del tema
│   ├── ejercicios/
│   │   └── src/
│   │       └── com/dam/tema2/           # Código Java
│   └── entregas/                        # Entregas formales del tema
│
├── Tema-3-Manejo-de-conectores/
│   ├── teoria/
│   │   └── apuntes.md
│   ├── ejercicios/
│   │   └── src/
│   │       └── com/dam/tema3/
│   └── entregas/
│
└── Tema-4-Mapeo-objeto-relacional/
    ├── teoria/
    │   └── apuntes.md
    ├── ejercicios/
    │   └── src/
    │       └── com/dam/tema4/
    └── entregas/
```

## 🛠️ Tecnologías Principales

- **Lenguaje:** Java 11
- **IDE:** IntelliJ IDEA
- **Bases de Datos:** SQL (MySQL), XML, JSON
- **ORM:** Hibernate, JPA
- **APIs:** DOM, SAX, JAXP
- **Testing:** JUnit 4
- **Logging:** SLF4J
- **Serialización:** Gson, JAXB

## 🚀 Configuración Inicial

### Requisitos

- JDK 11 o superior
- IntelliJ IDEA

### Pasos para configurar

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/tortilicious/Acceso-a-datos.git
   cd Acceso-a-datos
   ```

2. **Abrir en IntelliJ:**
    - File → Open
    - Selecciona la carpeta raíz `Acceso-a-datos/`

3. **Configurar dependencias (si es necesario):**
    - En IntelliJ, File → Project Structure → Libraries
    - Añade las dependencias necesarias para cada tema

## 📖 Cómo Usar Este Repositorio

### Para cada tema:

1. **Leer Teoría:** Abre `Tema-X/teoria/apuntes.md`
2. **Practicar:** Resuelve los ejercicios en `Tema-X/ejercicios/src/`
3. **Entregar:** Completa las entregas formales en `Tema-X/entregas/`

## 📊 Estado del Repositorio

- ✅ Tema 2 - Manejo de ficheros
- ✅ Tema 3 - Manejo de conectores
- ✅ Tema 4 - Mapeo objeto relacional
- ⏳ Temas adicionales (pendientes de visibilidad en el calendario escolar)

## 🤝 Estudio en Colaboración

Este repositorio forma parte de una estrategia integral de estudio en colaboración con Claude (IA), quien puede acceder a este contenido para:
- Explicar conceptos teóricos con contexto
- Revisar tu código y proporcionar feedback
- Ayudarte a resolver ejercicios paso a paso
- Prepararte para evaluaciones

**Cómo compartir con Claude:**
- Comparte el enlace del repositorio: `https://github.com/tortilicious/Acceso-a-datos`
- O copia/pega archivos específicos cuando necesites ayuda
- Claude tendrá contexto completo de tu estructura y avance

## 📝 Convenciones del Proyecto

- **Nombres de paquetes:** `com.dam.tema{numero}`
- **Nombres de clases:** CamelCase (ej: `GestorFicheros`, `ConectorBD`)
- **Nombres de métodos:** camelCase (ej: `cargarFichero()`, `conectarBD()`)
- **Comentarios:** En español para claridad durante el aprendizaje

---

**Curso:** 2º DAM | **Año:** 2024-2025 | **Lenguaje:** Java