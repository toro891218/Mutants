# mutants
 Challenge Mercadolibre Validación mutantes
 
#Required Setup
Java 11

#Database
DynamoDB
Crear tabla: mutants y mutants_verification ambas con: Clave de partición "id (String)"
Agregar en el archivo application.properties ID y Key de cuent de AWS para acceder al servicio de Base de datos. 

# Prueba

# Nivel 1:

Programa (en cualquier lenguaje de programación) que cumpla con el método pedido por Magneto.

# Nivel 2:

Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine, Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato:

POST → /mutant/ { “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] }

En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un 403-Forbidden

# Nivel 3:

Anexar una base de datos, la cual guarde los ADN’s verificados con la API. Solo 1 registro por ADN.

Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las verificaciones de ADN: {"count_mutant_dna" : 40, "count_human_dna" : 100, "ratio" : 0.4 } Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1 millón de peticiones por segundo). Test-Automáticos, Code coverage > 80%.

Entregar:
● Código Fuente (Para Nivel 2 y 3: En repositorio github): Se Entrega Codigo Fuente para punto 2 y 3
● Instrucciones de cómo ejecutar el programa o la API. (Para Nivel 2 y 3: En README de
github). : Se agrega Swagger con especificaciones de API
● URL de la API (Nivel 2 y 3).



