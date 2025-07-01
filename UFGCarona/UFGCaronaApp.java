package UFGCarona;

import UFGCarona.controller.LoginController;
import UFGCarona.model.DatabaseManager;
import UFGCarona.view.LoginView;
import javax.swing.*;

public class UFGCaronaApp {
    public static void main(String[] args) {
        // Configuração inicial
        configurarLookAndFeel();
        
        // Inicialização do banco de dados com tratamento de erro
        if (!inicializarBancoDados()) {
            JOptionPane.showMessageDialog(null, 
                "Falha crítica ao iniciar o banco de dados.\nVerifique os logs.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Inicia a interface gráfica
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }

    private static void configurarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar aparência: " + e.getMessage());
        }
    }

    private static boolean inicializarBancoDados() {
        try {
            System.out.println("Inicializando banco de dados...");
            DatabaseManager.createTables();
            System.out.println("Banco de dados pronto");
            return true;
        } catch (Exception e) {
            System.err.println("ERRO na inicialização do banco:");
            e.printStackTrace();
            return false;
        }
    }
}