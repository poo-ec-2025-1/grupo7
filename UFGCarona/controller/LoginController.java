package UFGCarona.controller;

import UFGCarona.model.Usuario; // Importar a classe Usuario
import UFGCarona.model.UsuarioDAO;
import UFGCarona.view.LoginView;
import UFGCarona.view.CadastroView;
import UFGCarona.view.PrincipalView; // Importar a PrincipalView

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class LoginController {
    private final LoginView view;
    private final UsuarioDAO usuarioDAO;

    public LoginController(LoginView view) {
        this.view = view;
        this.usuarioDAO = new UsuarioDAO();
        configurarAcoes();
    }

    private void configurarAcoes() {
        view.getBtnLogin().addActionListener(this::autenticarUsuario);
        view.getBtnCadastrar().addActionListener(this::abrirTelaCadastro);
    }

    private void autenticarUsuario(ActionEvent e) {
        String email = view.getTxtEmail().getText().trim();
        String senha = new String(view.getTxtSenha().getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Preencha todos os campos!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Primeiro valida o login (senha e ativo)
            if (usuarioDAO.validarLogin(email, senha)) {
                // Se o login for válido, busca o objeto Usuario completo
                // (Isso é importante para passar o objeto Usuario correto para a tela principal)
                Usuario usuarioLogado = usuarioDAO.findByEmail(email).orElse(null);

                if (usuarioLogado != null) {
                    view.dispose(); // Fecha a tela de login
                    PrincipalView principalView = new PrincipalView();
                    // **Modificado: Passa o usuário logado para o PrincipalController**
                    new PrincipalController(principalView, usuarioLogado);
                    principalView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(view,
                        "Erro interno: Usuário não encontrado após login bem-sucedido. Verifique o console para detalhes.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                    System.err.println("Usuário com email " + email + " não encontrado no banco de dados, mesmo após validar o login.");
                }
            } else {
                JOptionPane.showMessageDialog(view,
                    "Credenciais inválidas ou usuário inativo",
                    "Erro de Login",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                "Erro inesperado durante o login: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Para ver o stack trace completo durante o desenvolvimento
        }
    }

    private void abrirTelaCadastro(ActionEvent e) {
        CadastroView cadastroView = new CadastroView(view); // Passa a LoginView como parent
        new CadastroController(cadastroView); // Cria um novo controller para a tela de cadastro
        cadastroView.setLocationRelativeTo(view); // Centraliza a tela de cadastro em relação à de login
        cadastroView.setVisible(true);
    }
}