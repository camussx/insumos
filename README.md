**Objetivo del algoritmo**
Llevar el control diario de vencimientos, que permita registrar con facilidad los insumos de la tienda, asegurando la calidad y cumplimiento de los estándares de la marca, así como reducir multas por parte de los municipios por trabajar con productos perecibles.

**Funcionamiento del programa**
El programa será divido en 3 partes o clases principales:

**Main**: Para el flujo principal de la aplicación e interacción con el usuario.
Este archivo contiene el flujo principal de la aplicación y maneja la interacción con el usuario. Sus principales funcionalidades son:

-	Menú principal: Presenta opciones al usuario para registrar nuevos productos, generar un reporte o salir del programa.
-	Registro de nuevos productos: Permite al usuario seleccionar un producto de una lista predefinida o ingresar un nuevo producto, registrar su fecha de vencimiento y tipo de conservación.
-	Generación de reportes: Genera y muestra un reporte de todos los productos, incluyendo el estado (vencido, próximo a vencer, en buen estado) y exporta este reporte a un archivo.
-	Guardado de productos: Al salir, guarda los productos actuales en un archivo para persistencia de datos.

**Producto**: Este archivo define la clase Producto, que representa un producto individual. Sus principales funcionalidades son:

-	Atributos del producto: Almacena información sobre el nombre, código, fecha de vencimiento y tipo de conservación del producto.
-	Cálculo de días para vencer: Proporciona un método para calcular los días restantes hasta la fecha de vencimiento del producto.
-	Determinación del estado del producto: Proporciona un método para determinar si el producto está vencido, próximo a vencer o en buen estado.

**Cafetería**: Este archivo define la clase Cafeteria, que gestiona una colección de productos. Sus principales funcionalidades son:

-	Gestión de la lista de productos: Permite agregar nuevos productos a la lista y obtener la lista completa de productos.
-	Ordenamiento de productos: Proporciona un método para ordenar los productos por fecha de vencimiento.
-	Exportación de reportes: Proporciona un método para exportar un reporte detallado de los productos a un archivo de texto, incluyendo información sobre el estado de cada producto.
