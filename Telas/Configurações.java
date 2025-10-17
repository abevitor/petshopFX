package Telas;

import DAO.UsuarioDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Configurações {

    private BorderPane layout;
    private ImageView fotoPerfilView;
    private Label lblEmail;
    private PasswordField txtNovaSenha;
    private Button btnAlterarSenha, btnPagamento, btnAlterarFoto;
    private byte[] fotoBytes;

    public Configurações(String emailUsuario) {
        layout = new BorderPane();
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-font-family: 'Segoe UI', 'Roboto', sans-serif;");

        // ======= FOTO DE PERFIL =======
        fotoPerfilView = new ImageView(new Image("https://cdn-icons-png.flaticon.com/512/149/149071.png")); // padrão
        fotoPerfilView.setFitHeight(120);
        fotoPerfilView.setFitWidth(120);

        // Deixa circular
        Circle clip = new Circle(60, 60, 60);
        fotoPerfilView.setClip(clip);
        fotoPerfilView.setEffect(new DropShadow(10, Color.GRAY));

        btnAlterarFoto = new Button("Alterar foto");
        btnAlterarFoto.setOnAction(e -> selecionarImagem(emailUsuario));

        VBox fotoBox = new VBox(10, fotoPerfilView, btnAlterarFoto);
        fotoBox.setAlignment(Pos.CENTER);

        // ======= EMAIL DO USUÁRIO =======
        lblEmail = new Label(emailUsuario);
        lblEmail.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        VBox emailBox = new VBox(lblEmail);
        emailBox.setAlignment(Pos.CENTER);
        emailBox.setPadding(new Insets(10, 0, 20, 0));

        // ======= TROCAR SENHA =======
        Label lblSenha = new Label("Nova senha:");
        txtNovaSenha = new PasswordField();
        txtNovaSenha.setPromptText("Digite sua nova senha");

        btnAlterarSenha = new Button("Alterar senha");
        btnAlterarSenha.setOnAction(e -> alterarSenha(emailUsuario));

        VBox senhaBox = new VBox(8, lblSenha, txtNovaSenha, btnAlterarSenha);
        senhaBox.setAlignment(Pos.CENTER);
        senhaBox.setPadding(new Insets(10));

        // ======= FORMA DE PAGAMENTO =======
        btnPagamento = new Button("Mudar forma de pagamento");
        btnPagamento.setOnAction(e -> {
            // Trocar depois pela tela de pagamento
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Em desenvolvimento");
            alert.setHeaderText(null);
            alert.setContentText("Tela de forma de pagamento ainda será implementada!");
            alert.showAndWait();
        });

        VBox mainBox = new VBox(20, fotoBox, emailBox, senhaBox, btnPagamento);
        mainBox.setAlignment(Pos.TOP_CENTER);

        layout.setCenter(mainBox);
    }

    public BorderPane getLayout() {
        return layout;
    }

    // ======= MÉTODO PARA ALTERAR FOTO =======
    private void selecionarImagem(String emailUsuario) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolher imagem de perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File arquivo = fileChooser.showOpenDialog(null);

        if (arquivo != null) {
            try {
                Image img = new Image(arquivo.toURI().toString());
                fotoPerfilView.setImage(img);

                FileInputStream fis = new FileInputStream(arquivo);
                fotoBytes = fis.readAllBytes();
                fis.close();

                UsuarioDAO.atualizarFoto(emailUsuario, fotoBytes);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Foto de perfil atualizada com sucesso!");
                alert.showAndWait();

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar imagem!");
                alert.showAndWait();
            }
        }
    }

    // ======= MÉTODO PARA ALTERAR SENHA =======
    private void alterarSenha(String emailUsuario) {
        String novaSenha = txtNovaSenha.getText();

        if (novaSenha.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, digite uma nova senha.");
            alert.showAndWait();
            return;
        }

        boolean sucesso = UsuarioDAO.alterarSenha(emailUsuario, novaSenha);

        Alert alert = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                sucesso ? "Senha alterada com sucesso!" : "Erro ao alterar senha!");
        alert.showAndWait();

        if (sucesso) {
            txtNovaSenha.clear();
        }
    }
}
