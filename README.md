# Biblioteca Methaporce

Este repositorio contiene el código fuente para un sistema de gestión de bibliotecas, diseñado para administrar el catálogo de libros y la información de los usuarios registrados.

## Características Actuales

El sistema actualmente soporta las siguientes operaciones básicas (CRUD):

-   **Gestión de Libros:**
    -   Crear nuevos libros.
    -   Consultar la lista de todos los libros.
    -   Obtener detalles de un libro específico por su ID.
    -   Actualizar la información de un libro.
    -   Eliminar un libro del catálogo.
-   **Gestión de Usuarios:**
    -   Registrar nuevos usuarios.
    -   Consultar la lista de todos los usuarios.
    -   Obtener detalles de un usuario específico por su ID.
    -   Actualizar la información de un usuario.
    -   Eliminar un usuario del sistema.

## Tecnologías
-   **Lenguaje:** Java 17
-   **Framework:** Spring Boot 3
-   **Base de Datos:** H2 (en memoria) / PostgreSQL
-   **Gestor de Dependencias:** Maven

## Prerrequisitos

Tener instalado:

-   JDK 17 o superior.
-   Maven 3.8 o superior.

## Instalación y Ejecución

1.  **Clona el repositorio:**
    ```bash
    git clone <URL-DEL-REPOSITORIO>
    cd BIBLIOTECA_METHAPORCE
    ```

2.  **(Opcional) Configura la base de datos:**
    -   Si no usas la base de datos en memoria, ve a `src/main/resources/application.properties`.
    -   Modifica las propiedades `spring.datasource` con tus credenciales.

3.  **Ejecuta el proyecto con Maven:**
    ```bash
    mvn spring-boot:run
    ```

Una vez iniciada, la aplicación estará disponible en `http://localhost:8080`.

## Endpoints de la API (Ejemplo)

<!-- *Si es una API REST, puedes documentar los endpoints aquí. Ejemplo:*
-   `GET /api/libros`: Obtiene todos los libros.
-   `POST /api/usuarios`: Crea un nuevo usuario. -->
