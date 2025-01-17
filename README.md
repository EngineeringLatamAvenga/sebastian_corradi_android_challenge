# Code Challenge - Sebastian Corradi
SebastianCorradi@gmail.com

Requerimientos disponibles en https://github.com/EngineeringLatamAvenga/sebastian_corradi_android_challenge/blob/main/Mobile%20Challenge%20-%20v0.5.pdf

# Librerías utilizadas
- Retrofit
- Hilt
- Room
- Google Maps

# Solución
La solución presentada implementa una arquitectura CLEAN, que implica la separación de capas para una mejor encapsulación y separación de responsabilidades en conjunto con MVVM, que es un patrón de programación reactiva que usa como base al patron observable. También sigue los lineamientos de los principios SOLID.
Utiliza "MAPPERS" para separar las entidades que recibe del backend de las que usa la vista y las que usa la BD, proveyendo una mayor robustez y escalabilidad
Al iniciar la aplicación se consulta la BD para detectar si ya tiene ciudades cargadas, en caso contrario (aun no se cargaron) envía una solicitud al backend y luego las almacena en la BD local, para ser consultadas desde ahi en el futuro. Luego las modificaciones (si se agregan como favoritos) y las consultas como consecuencia de los filtros se lee desde la BD local (usa un patron REPOSITORY)
NO usa el componente de navegación, sino que lo resuelve "artesanalmente". Consideré usarlo en un comienzo pero para 2 pantallas lo podia resolver artesanalmente (originalmente eran 3 pantallas, pero no encontré documentación de la 3er pantalla así que por ahora se muestra vacía)
No había restricciones ni lineamientos sobre la UI así que no incurrí en tiempo para mejorarla
Para agilizar el filtrado se guarda una copia de la lista de ciudades dentro del viewmodel, entiendo que la cantidad de ciudades es demasiado extensa y esto podría traer problemas de performance y del uso de memoria, pero es una alternativa y no había restricciones al respecto.
El mapa se muestra vacío hasta que el usuario haga click en alguna ciudad, pero muestra un mensaje amigable.

# Notas
La resolución me tomo alrededor de 20 horas, repartidas en 8 días aproximadamente.
 