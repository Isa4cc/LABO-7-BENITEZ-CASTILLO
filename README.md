# Laboratorio: Triángulo de Sierpinski 

## ¿Qué es?

El programa dibuja un fractal llamado Triángulo de Sierpinski.
La idea es simple: tomas un triángulo, lo divides en tres triángulos más
pequeños, y repites lo mismo con cada uno de esos. Y luego con los de adentro.
Y luego con los de adentro de los de adentro.

El código está dividido en 5 archivos, cada uno con su propio trabajo:

| Archivo | Qué hace |
|---|---|
| `SierpinskiLab.java` | Arma la ventana y pone todo en su lugar |
| `PanelDibujo.java` | El lienzo donde aparece el fractal |
| `PanelControles.java` | El campo donde escribes el nivel y el botón Dibujar |
| `AlgoritmoSierpinski.java` | El cerebro: aquí vive la recursión |
| `Tema.java` | Los colores de toda la app |

---

## Cómo funciona el algoritmo

Imagina que le pides a alguien que dibuje un triángulo de nivel 3.
Esa persona dice: "yo no sé dibujar un triángulo de nivel 3, pero sí sé
dividirlo en tres partes y pedirle a otras tres personas que cada una dibuje
un triángulo de nivel 2". Cada una de esas personas hace lo mismo: divide y
le pasa el trabajo a tres más, pidiendo nivel 1. Y así hasta que alguien
recibe nivel 0 y dice "ok, este sí lo dibujo yo directamente".

Eso es exactamente lo que hace el método `sierpinski()` en el código:

1. Si el nivel es 0 → dibuja el triángulo y ya, se acabó.
2. Si el nivel es mayor → calcula los puntos medios de los lados,
   marca el hueco del centro, y llama a `sierpinski()` tres veces
   con nivel - 1.

Los colores que ves en el fractal no son decoración: cada color representa
qué tan profundo está ese triángulo en la cadena de llamadas. El naranja
es el más profundo (nivel 0), el cian es un nivel arriba, y así.

---

## Preguntas de Reflexión

### ¿Por qué este problema es naturalmente recursivo?

Porque el fractal está hecho de copias de sí mismo. Un triángulo de Sierpinski
de nivel 5 contiene exactamente tres triángulos de Sierpinski de nivel 4
adentro. Y cada uno de esos tiene tres de nivel 3. Y así para siempre.

Cuando un problema tiene esa forma "el todo está hecho de partes que son
iguales al todo pero más chicas", la recursión lo describe de manera natural
y directa. Intentar resolverlo de otra forma sería como explicar con palabras
algo que con un dibujo se entiende en dos segundos.

### ¿Qué ocurre si no existe caso base?

El programa nunca para. Cada llamada genera tres más, que generan tres más,
infinitamente. La memoria del sistema se llena de llamadas apiladas hasta
que Java lanza un `StackOverflowError` y el programa se cae.

Es como si le dijeras a alguien "divídelo y pásaselo a tres personas" pero
nunca le dijeras cuándo parar. Esa persona le diría lo mismo a otras tres,
que se lo dirían a otras tres, hasta que se acaba la gente o el espacio.
El caso base es la condición de salida, sin ella no hay final posible.

### ¿Cómo crece el número de llamadas?

Cada nivel multiplica por 3 el trabajo del nivel anterior:

| Nivel | Triángulos dibujados | Total de llamadas |
|---|---|---|
| 0 | 1 | 1 |
| 1 | 3 | 4 |
| 2 | 9 | 13 |
| 3 | 27 | 40 |
| 4 | 81 | 121 |
| 5 | 243 | 364 |
| 10 | 59,049 | 88,573 |
| 15 | 14,348,907 | 21,523,360 |

En notación Big O esto se expresa como **O(3^N)**: el trabajo crece
proporcionalmente a 3 elevado al nivel. Es una de las complejidades más
costosas que existen — para comparar, un algoritmo O(N) con N=15 hace
15 operaciones, mientras que uno O(3^N) hace más de 14 millones.

### ¿Qué relación tiene con estructuras tipo árbol?

El árbol de llamadas del algoritmo es literalmente un árbol. La primera
llamada es la raíz. Cada llamada tiene exactamente tres hijos (las tres
llamadas recursivas). Y las hojas son los casos base, donde se dibuja
el triángulo final.

La pila del sistema (stack) va bajando rama por rama, exactamente como
recorres un árbol en profundidad: entra por la izquierda, llega hasta
la hoja, sube, baja por la rama del medio, y así. Los colores del fractal
muestran visualmente ese recorrido: los triángulos del mismo color están
al mismo nivel del árbol.
