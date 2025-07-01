package UFGCarona.view;

import javax.swing.*;
import java.awt.*;

public class AvaliacaoView extends JDialog {
    private JSlider slNota;
    private JTextArea taComentario;
    private JButton btnEnviar;
    private JButton btnCancelar;

    public AvaliacaoView(JFrame parent) {
        super(parent, "Avaliar Carona", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        // Componentes
        JPanel notaPanel = new JPanel();
        notaPanel.add(new JLabel("Nota:"));
        slNota = new JSlider(1, 5, 3);
        slNota.setMajorTickSpacing(1);
        slNota.setPaintTicks(true);
        slNota.setPaintLabels(true);
        notaPanel.add(slNota);
        
        panel.add(notaPanel, BorderLayout.NORTH);
        
        taComentario = new JTextArea(5, 20);
        taComentario.setLineWrap(true);
        panel.add(new JScrollPane(taComentario), BorderLayout.CENTER);
        
        btnEnviar = new JButton("Enviar Avaliação");
        btnCancelar = new JButton("Cancelar");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnEnviar);
        buttonPanel.add(btnCancelar);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }

    // Getters
    public JSlider getSlNota() { return slNota; }
    public JTextArea getTaComentario() { return taComentario; }
    public JButton getBtnEnviar() { return btnEnviar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}