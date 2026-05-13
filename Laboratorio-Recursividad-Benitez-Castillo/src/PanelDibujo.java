import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * PanelDibujo.java
 * ─────────────────────────────────────────────────────────────
 * JPanel personalizado que actúa como "lienzo" del fractal.
 *
 * Responsabilidades:
 *   1. Calcular los vértices del triángulo principal
 *      según el tamaño actual del panel.
 *   2. Delegar el dibujo a AlgoritmoSierpinski.
 *   3. Mostrar la anotación de nivel en pantalla.
 *
 * No contiene lógica de recursión ni de controles UI.
 * ─────────────────────────────────────────────────────────────
 */
public class PanelDibujo extends JPanel {

    private int nivel;
    private final AlgoritmoSierpinski algoritmo;

    /**
     * @param nivelInicial Nivel de recursión al abrir la ventana
     */
    public PanelDibujo(int nivelInicial) {
        this.nivel     = nivelInicial;
        this.algoritmo = new AlgoritmoSierpinski();
        setBackground(Tema.FONDO);
    }

    /** Cambia el nivel de recursión y vuelve a dibujar. */
    public void setNivel(int nivel) {
        this.nivel = nivel;
        repaint();
    }

    //  PINTURA
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Calcular los 3 vértices del triángulo principal
        // centrado y con margen proporcional al tamaño del panel
        Point[] vertices = calcularVertices();
        Point a = vertices[0];   // cima
        Point b = vertices[1];   // inferior-izquierdo
        Point c = vertices[2];   // inferior-derecho

        // Delegar el dibujo recursivo al algoritmo
        algoritmo.dibujar(g2, nivel, a, b, c);
    }

    /**
     * Calcula los vértices A (cima), B (inf-izq), C (inf-der)
     * adaptados al tamaño actual del panel.
     */
    private Point[] calcularVertices() {
        int w = getWidth();
        int h = getHeight();

        // El triángulo ocupa ~88 % del panel con margen uniforme
        int margen   = (int)(Math.min(w, h) * 0.06);
        int lado     = (int)(Math.min(w - margen * 2,
                (h - margen * 2) * 2.0 / Math.sqrt(3)));
        int alturaT  = (int)(lado * Math.sqrt(3) / 2.0);

        int cx    = w / 2;
        int baseY = (h + alturaT) / 2 - margen / 2;

        return new Point[]{
                new Point(cx,            baseY - alturaT),   // A
                new Point(cx - lado / 2, baseY),             // B
                new Point(cx + lado / 2, baseY)              // C
        };
    }

}