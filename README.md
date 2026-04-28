# Sistema de Facturación - Fidecompro

Este proyecto corresponde al desarrollo de un sistema de facturación realizado en Java como parte del curso Programación Cliente/Servidor Concurrente. El sistema permite gestionar clientes, productos, facturas y consultar las facturas registradas mediante una interfaz gráfica desarrollada con Java Swing.

El proyecto está organizado por capas, separando la interfaz gráfica, la lógica del sistema y el acceso a datos mediante MySQL.

## Acceso al sistema

Usuario: admin  
Contraseña: 1234

## Funcionalidades principales

- Inicio de sesión de usuario
- Gestión de clientes
- Gestión de productos
- Registro, actualización y eliminación de productos
- Creación de facturas
- Registro del detalle de factura
- Descuento automático de stock al facturar
- Consulta de facturas registradas
- Visualización del detalle de cada factura
- Conexión con base de datos MySQL

## Base de datos

El sistema utiliza una base de datos MySQL llamada:

fidecompro_db

Para crear la base de datos y sus tablas, se debe ejecutar el archivo:

fidecompro_db.sql

Este archivo contiene la estructura necesaria para crear las siguientes tablas:

- clientes
- productos
- facturas
- detalle_factura

## Configuración de conexión

La conexión a la base de datos se encuentra en la clase:

src/datos/ConexionBD.java

Antes de ejecutar el proyecto, se debe verificar que los datos de conexión coincidan con la configuración local de MySQL:

- Host: localhost o 127.0.0.1
- Puerto: 3306
- Usuario: root
- Contraseña: según la configuración del equipo
- Base de datos: fidecompro_db

## Cómo ejecutar el proyecto

1. Abrir MySQL Workbench.
2. Ejecutar el archivo fidecompro_db.sql.
3. Abrir el proyecto en NetBeans.
4. Verificar la contraseña en la clase ConexionBD.java.
5. Ejecutar Main.java.
6. Iniciar sesión con las credenciales indicadas.

## Estructura del proyecto

### Interfaz

Contiene las ventanas del sistema desarrolladas con Java Swing.

- VentanaLogin
- VentanaMenu
- VentanaClientes
- VentanaProductos
- VentanaFacturacion
- VentanaVerFacturas

### logica

Contiene las clases que representan las entidades principales del sistema.

- Usuario
- Cliente
- Producto
- ProductoTecnologia
- ProductoOficina
- Factura
- DetalleFactura
- SistemaFacturacion

### datos

Contiene las clases encargadas de la conexión y operaciones con la base de datos.

- ConexionBD
- ClienteDB
- ProductoDB
- FacturaDB

## Tecnologías utilizadas

- Java
- Java Swing
- MySQL
- JDBC
- NetBeans
- Programación Orientada a Objetos

## Autor

Proyecto académico desarrollado para el curso Programación Cliente/Servidor Concurrente, Fidélitas Virtual 2026.
