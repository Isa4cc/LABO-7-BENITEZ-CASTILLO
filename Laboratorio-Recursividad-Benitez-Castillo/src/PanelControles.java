import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.IntConsumer;

/**
 * PanelControles.java
 * ─────────────────────────────────────────────────────────────
 * Panel inferior minimalista:
 *   - Campo de texto para ingresar el nivel
 *   - Botón Dibujar (también responde a Enter)
 *   - Mensaje de error inline si el input no es válido
 * ─────────────────────────────────────────────────────────────
 */
public class PanelControles extends JPanel {



    private final JTextField campoNivel;
    private final JLabel     lblError;

    public PanelControles(int nivelInicial, IntConsumer alCambiar) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 14, 12));
        setBackground(Tema.FONDO_PANEL);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Tema.BORDE));

        // Etiqueta
        JLabel lbl = new JLabel("Nivel de recursión:");
        lbl.setFont(new Font("Monospaced", Font.BOLD, 13));
        lbl.setForeground(new Color(0xA8C4DC));

        // Campo de texto
        campoNivel = new JTextField(String.valueOf(nivelInicial), 4);
        campoNivel.setFont(new Font("Monospaced", Font.BOLD, 20));
        campoNivel.setBackground(new Color(0x0F1923));
        campoNivel.setForeground(new Color(0x06B6D4));
        campoNivel.setCaretColor(new Color(0x06B6D4));
        campoNivel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDE, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        campoNivel.setHorizontalAlignment(JTextField.CENTER);

        // Botón
        JButton btnDibujar = new JButton("Dibujar ▶");
        btnDibujar.setFont(new Font("Monospaced", Font.BOLD, 13));
        btnDibujar.setBackground(new Color(0x1E3A5F));
        btnDibujar.setForeground(new Color(0x06B6D4));
        btnDibujar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x06B6D4), 1),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        btnDibujar.setFocusPainted(false);
        btnDibujar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Mensaje de error
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Monospaced", Font.ITALIC, 11));
        lblError.setForeground(new Color(0xFF4444));

        // Acción compartida (botón y Enter)
        Runnable accion = () -> {
            try {
                int nivel = Integer.parseInt(campoNivel.getText().trim());
                if (nivel < 0) {
                    lblError.setText("⚠ El nivel debe ser 0 o mayor.");
                    return;
                }
                lblError.setText(" ");
                alCambiar.accept(nivel);
            } catch (NumberFormatException ex) {
                lblError.setText("⚠ Ingresa un número entero válido.");
            }
        };

        btnDibujar.addActionListener((ActionEvent e) -> accion.run());
        campoNivel.addActionListener((ActionEvent e) -> accion.run());

        // Ensamblar
        add(lbl);
        add(campoNivel);
        add(btnDibujar);
        add(lblError);
    }
}