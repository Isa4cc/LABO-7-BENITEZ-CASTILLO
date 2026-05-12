import java.awt.Color;

/**
 * Tema.java
 * ─────────────────────────────────────────────────────────────
 * Constantes visuales compartidas por toda la aplicación.
 *
 * Centralizar los colores aquí evita repetirlos en cada clase
 * y hace que cambiar el tema sea una sola edición.
 * ─────────────────────────────────────────────────────────────
 */
public class Tema {

    // ── Colores de fondo y bordes ─────────────────────────────
    public static final Color FONDO         = new Color(0x0F1923);
    public static final Color FONDO_PANEL   = new Color(0x1A2636);
    public static final Color BORDE         = new Color(0x1E3A5F);

    /**
     * Paleta por nivel de recursión.
     * Índice 0 = caso base (nivel más profundo del stack).
     * Cada índice superior corresponde a un nivel más cerca de la raíz.
     *
     *   NIVEL_COLORES[0]  → naranja    (caso base)
     *   NIVEL_COLORES[1]  → cian       (nivel 1)
     *   NIVEL_COLORES[2]  → azul marino(nivel 2)
     *   NIVEL_COLORES[3]  → violeta    (nivel 3)
     *   NIVEL_COLORES[4]  → esmeralda  (nivel 4)
     *   NIVEL_COLORES[5]  → ámbar      (nivel 5)
     */
    public static final Color[] NIVEL_COLORES = {
            new Color(0xFF6B35),   // naranja   – nivel 0
            new Color(0x06B6D4),   // cian      – nivel 1
            new Color(0x1E3A5F),   // azul      – nivel 2
            new Color(0x7C3AED),   // violeta   – nivel 3
            new Color(0x059669),   // esmeralda – nivel 4
            new Color(0xF59E0B),   // ámbar     – nivel 5
    };

    /** Devuelve el color del nivel dado (con protección de índice). */
    public static Color colorDeNivel(int nivel) {
        int idx = Math.min(Math.abs(nivel), NIVEL_COLORES.length - 1);
        return NIVEL_COLORES[idx];
    }

    // Constructor privado: esta clase solo tiene constantes estáticas.
    private Tema() {}
}