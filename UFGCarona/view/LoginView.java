package UFGCarona.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCadastrar;

    public LoginView() {
        setTitle("UFGCarona - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel lblEmail = new JLabel("E-mail @discente.ufg.br:");
        txtEmail = new JTextField(20);
        
        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(20);
        
        btnLogin = new JButton("Entrar");
        btnCadastrar = new JButton("Cadastrar");

        // Posicionamento
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblEmail, gbc);
        
        gbc.gridy = 1;
        panel.add(txtEmail, gbc);
        
        gbc.gridy = 2;
        panel.add(lblSenha, gbc);
        
        gbc.gridy = 3;
        panel.add(txtSenha, gbc);
        
        gbc.gridy = 4;
        panel.add(btnLogin, gbc);
        
        gbc.gridy = 5;
        panel.add(btnCadastrar, gbc);

        add(panel);
    }

    // Getters
    public JTextField getTxtEmail() { return txtEmail; }
    public JPasswordField getTxtSenha() { return txtSenha; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnCadastrar() { return btnCadastrar; }
}