# 💻 Sistema de Facturación Java - MySQL

Bienvenido a mi **proyecto profesional de sistema de facturación y gestión comercial**, desarrollado en **Java** con **MySQL**.  
Este sistema está diseñado para pequeñas empresas y startups que buscan un control completo de inventario, clientes, proveedores y facturación.

---

## ✨ Funcionalidades destacadas

- 🛒 **Gestión de artículos, proveedores, clientes y empleados**  
- 🧾 **Facturación completa** con control de inventario y posibilidad de cancelar facturas  
- 📊 **Reportes en HTML** para revisión rápida de ventas y stock  
- 🏗️ **Arquitectura MVC** + **patrón DAO** para código modular y mantenible  
- 💡 Base sólida y extensible, lista para mejoras o integraciones futuras

---

## 🛠️ Tecnologías utilizadas

- **Java** (NetBeans)  
- **Swing** (Interfaces gráficas)  
- **MySQL** (Base de datos)  
- **MVC + DAO** (Patrones de diseño y separación de responsabilidades)

---
ok, esa version que me das unificala a esta # SistemaFacturacionJava-MYSQL

Este proyecto es un sistema de facturación y gestión comercial desarrollado en Java, usando MySQL como base de datos.

## Funcionalidades principales

- Gestión de artículos, proveedores, clientes y empleados.
- Facturación con control de inventario y cancelación de facturas.
- Reportes en formato HTML.
- Arquitectura MVC y patrón DAO.

## Tecnologías usadas

- Java (NetBeans)
- MySQL

## Descripción

Sistema desarrollado para gestionar productos, proveedores, clientes, empleados y facturación completa con lógica de negocio real. Base sólida para pequeñas empresas y startups.

---

## Nota importante

Para ejecutar el proyecto es necesario configurar la conexión a la base de datos en el archivo `ConexionDB.java`.

## 🗂️ Estructura del proyecto

```text
app/
 └── Main.java               → Punto de entrada de la aplicación

config/
 └── ConexionBD.java         → Configuración de la conexión a la base de datos

models/
 ├── Articulo.java
 ├── Cliente.java
 ├── DetalleFactura.java
 ├── Empleado.java
 ├── Factura.java
 └── Proveedor.java           → Clases del dominio (modelo de datos)

dao/
 ├── ArticuloDAO.java
 ├── ClienteDAO.java
 ├── EmpleadoDAO.java
 ├── FacturaDAO.java
 └── ProveedorDAO.java        → Acceso a datos y operaciones CRUD

vistas/
 ├── FrmArticulos.java
 ├── FrmClientes.java
 ├── FrmEmpleados.java
 ├── FrmFacturas.java
 └── FrmPrincipal.java       → Interfaces gráficas con Swing

+-------------------+
|       app         |
|-------------------|
| Main.java         |
+-------------------+
         |
         v
+-------------------+
|      vistas       |
|-------------------|
| FrmPrincipal      |
| FrmArticulos      |
| FrmClientes       |
| FrmEmpleados      |
| FrmFacturas       |
+-------------------+
         |
         v
+-------------------+      +-------------------+
|      models       | ---> |        dao        |
|-------------------|      |-------------------|
| Articulo.java      |      | ArticuloDAO.java |
| Cliente.java       |      | ClienteDAO.java  |
| Empleado.java      |      | EmpleadoDAO.java |
| Factura.java       |      | FacturaDAO.java  |
| DetalleFactura.java|      | ProveedorDAO.java|
| Proveedor.java     |      +------------------+
+-------------------+
         |
         v
+-------------------+
|      config       |
|-------------------|
| ConexionBD.java   |
+-------------------+
