# MINIPROYECTO4

FREDDY ALEXANDER MELO BUITRAGO -- 202125498
VERONICA GRANADOS -- 2123263
JUAN DAVID SALAZAR -- 2344293

# MINIPROYECTO4

## 📌 Descripción del proyecto
**MINIPROYECTO4** es un proyecto desarrollado en **Java** usando **Maven** como sistema de construcción y gestión de dependencias. El proyecto maneja la lógica de un juego con persistencia de información mediante **serialización de objetos**, permitiendo guardar y recuperar el estado de ejecución usando archivos `.ser`.

En el repositorio se incluyen archivos serializados que pueden representar el **estado del juego** y/o un **tablero de prueba**, útiles para continuar partidas o validar comportamientos sin reiniciar desde cero.

---

## 🛠️ Tecnologías utilizadas
- **Java (JDK 17+)**
- **Apache Maven**
- **Serialización en Java**
- **Maven Wrapper (`mvnw`, `mvnw.cmd`)**
- **Git/GitHub** (opcional)

---

## 📂 Estructura del proyecto
```text
MINIPROYECTO4/
├── game_state.ser        # Estado serializado del juego
├── test_board.ser        # Tablero de prueba serializado
├── pom.xml               # Configuración del proyecto Maven
├── mvnw                  # Maven Wrapper (Linux / macOS)
├── mvnw.cmd              # Maven Wrapper (Windows)
└── README.md             # Documentación del proyecto

## 📂 Como compilar el proyecto
Windows mvnw.cmd clean compile
Linux ./mvnw clean compile
