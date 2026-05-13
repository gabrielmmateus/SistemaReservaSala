package estrutura;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

// Classe principal responsável por iniciar a aplicação
public class Main {

    public static void main(String[] args) {

        // Tenta aplicar o tema Nimbus para deixar a interface mais bonita
        configurarTema();

        // Inicia a interface na thread gráfica do Swing
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }

    // Método que tenta definir um tema visual melhor para o Swing
    private static void configurarTema() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }

            // Caso o Nimbus não exista, usa o tema padrão do sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            // Se ocorrer erro, o sistema continua com o tema padrão
        }
    }
}