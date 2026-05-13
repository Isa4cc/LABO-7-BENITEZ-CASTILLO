import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 * AlgoritmoSierpinski.java
 * ─────────────────────────────────────────────────────────────
 * Contiene ÚNICAMENTE la lógica recursiva del fractal.
 * No sabe nada de ventanas, paneles ni controles.
 *
 * Responsabilidad única: dado un Graphics2D y un nivel,
 * dibuja el Triángulo de Sierpinski de forma recursiva.
 *
 * ── Estructura del algoritmo ─────────────────────────────────
 *
 *   CASO BASE (nivel == 0)
 *     → Dibuja el triángulo relleno y retorna.
 *        El stack se detiene aquí.
 *
 *   CASO RECURSIVO (nivel > 0)
 *     1. Calcular 3 puntos medios (M1, M2, M3).
 *     2. Dibujar el hueco central con color del nivel actual
 *        (permite visualizar la profundidad del stack LIFO).
 *     3. Hacer tres llamadas recursivas con nivel-1:
 *          - subtriángulo superior   (A,  M1, M3)
 *          - subtriángulo inf-izq    (M1, B,  M2)
 *          - subtriángulo inf-der    (M3, M2, C )
 * ─────────────────────────────────────────────────────────────
 */
public class AlgoritmoSierpinski {

    /**
     * Punto de entrada público. Configura el antialiasing
     * y lanza la primera llamada recursiva.
     *
     * @param g2    Contexto gráfico donde se dibuja
     * @param nivel Profundidad de recursión inicial
     * @param a     Vértice superior del triángulo
     * @param b     Vértice inferior-izquierdo
     * @param c     Vértice inferior-derecho
     */
    public void dibujar(Graphics2D g2, int nivel,
                        Point a, Point b, Point c) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        sierpinski(g2, nivel, a, b, c);
    }

    //  MÉTODO RECURSIVO PRIVADO
    private void sierpinski(Graphics2D g2, int nivel,
                            Point a, Point b, Point c) {

        // CASO BASE
        if (nivel == 0) {
            Color color = Tema.NIVEL_COLORES[0];   // naranja

            int[] xs = { a.x, b.x, c.x };
            int[] ys = { a.y, b.y, c.y };

            g2.setColor(color);
            g2.fillPolygon(xs, ys, 3);

            g2.setColor(color.brighter().darker());
            g2.setStroke(new BasicStroke(0.8f));
            g2.drawPolygon(xs, ys, 3);
            return;   // <── detiene la recursión
        }

        // CASO RECURSIVO

        // Paso 1: calcular los 3 puntos medios
        //   M1 = medio de A-B (lado izquierdo)
        //   M2 = medio de B-C (lado inferior)
        //   M3 = medio de C-A (lado derecho)
        Point m1 = puntoMedio(a, b);
        Point m2 = puntoMedio(b, c);
        Point m3 = puntoMedio(c, a);

        // Paso 2: marcar el hueco central con el color del nivel
        //         → el color revela la profundidad del stack LIFO
        dibujarHueco(g2, m1, m2, m3, nivel);

        // Paso 3: tres llamadas recursivas con nivel - 1
        sierpinski(g2, nivel - 1, a,  m1, m3);   // subtriángulo superior
        sierpinski(g2, nivel - 1, m1, b,  m2);   // subtriángulo inf-izquierdo
        sierpinski(g2, nivel - 1, m3, m2, c );   // subtriángulo inf-derecho
    }

    //  MÉTODOS AUXILIARES

    /**
     * Devuelve el punto medio entre P y Q.
     */
    private Point puntoMedio(Point p, Point q) {
        return new Point((p.x + q.x) / 2,
                (p.y + q.y) / 2);
    }

    /**
     * Dibuja el triángulo central "hueco" con el color del nivel.
     * El color cambia por nivel, lo que visualiza el recorrido
     * LIFO de la pila de llamadas recursivas.
     */
    private void dibujarHueco(Graphics2D g2,
                              Point m1, Point m2, Point m3,
                              int nivel) {
        int[] xs = { m1.x, m2.x, m3.x };
        int[] ys = { m1.y, m2.y, m3.y };

        // Rellenar con el fondo para crear el "hueco" visual
        g2.setColor(Tema.FONDO);
        g2.fillPolygon(xs, ys, 3);

        // Borde con el color del nivel actual
        g2.setColor(Tema.colorDeNivel(nivel).darker());
        g2.setStroke(new BasicStroke(0.6f));
        g2.drawPolygon(xs, ys, 3);
    }


    //  UTILIDADES ESTÁTICAS (sin estado, reutilizables)

    /** Calcula cuántos triángulos se dibujan para un nivel N → 3^N */
    public static long contarTriangulos(int nivel) {
        return (long) Math.pow(3, nivel);
    }

    /** Calcula el total de llamadas recursivas → Σ 3^i (i=0..nivel) */
    public static long contarLlamadas(int nivel) {
        long total = 0;
        for (int i = 0; i <= nivel; i++) {
            total += (long) Math.pow(3, i);
        }
        return total;
    }
}