```
                                                 +------------+
        ________________________________________\|   Rental   |/____________________________
       |                                        /|            |\                            |
       |                                         +------------+                             |
       |                                              / \                                   |
      / \                                              |                                    |
      \ /                 +-------------------+--------+---------+-------------------+     / \
+------------+            |                   |                  |                   |     \ /          
|    User    |    +--------------+    +--------------+   +--------------+   +------------------+
|            |    | RentalByDay  |    | RentalByHour |   | RentalByWeek |   | RentalPromotion  |
+------------+    +--------------+    +--------------+   +--------------+   +------------------+  
     / \
      |
      |
     / \
     \ /
+------------+
| Principal  |
|            |
+------------+
```

El anterior es diagrama UML describe el modelo de clases utilizado.

Se comenzó por la definición de casos de prueba y se continuó con el desarrollo una vez se tuvieron los casos posibles de comportamiento.

Existen 4 tipos diferentes de Renta:
Por día, hora, semana y un tipo especial que es la Promoción. Este último tiene una lista de cualquiera de los anteriores (Command).
Igualmente existen 2 tipos de usuarios uno normal y otro principal que vendría a ser como un titular de la cuenta que puede tener una lista de usuarios típicos.

El proyecto se creó utilizando Maven para que se puedan obtener fácilemente las dependencias necesarias como JUnit.
Los datos como precio por tipo de Renta y descuentos se agregan desde un archivo de propiedaes. En un proyecto real estos datos deberían ser obtenidos desde una Base de datos o consumiendo un WebService.

Los tests creados incluyen:
- Obtención de datos desde las Properties
- Creación de todos los tipos de Renta
- Creación de todos los tipos de usuario
- Asociación de rentas a los diferentes tipos de usuarios.
- Obtención de costos parciales(Partial) y finales(Invoice).
- Manejo de excepciones en casos de falta de inicialización de la renta, finalización de la renta  cuando se pida algún costo acumulado.
- Inclusión de mas de 5 tipos de renta para una promoción.
- Calculo de los valores por cada tipo de renta.

Para ejecutar los tests:
- Manualmente usando maven:
	- En el directorio raíz del proyecto ejecutar
```
  mvn test
```
- Importando el proyecto en eclipse y ejecutar los tests directamente
```
  Run As -> JUnit
```		
