package Telas;

import DAO.UsuarioDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.*;

public class ConfiguracoesView {

    private BorderPane layout;
    private ImageView fotoPerfilView;
    private Label lblEmail;
    private PasswordField txtSenhaAtual;
    private PasswordField txtNovaSenha;
    private Button btnAlterarSenha, btnPagamento, btnAlterarFoto;
    private byte[] fotoBytes;

    // Construtor padrão
    public ConfiguracoesView() {
        layout = new BorderPane();
    }

    // Construtor com email do usuário
    public ConfiguracoesView(String emailUsuario) {
        layout = new BorderPane();
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-font-family: 'Segoe UI', 'Roboto', sans-serif;");

        // ======= BOTÃO DE TEMA =======
        Button btnTema = new Button("\uD83C\uDF19"); // 🌙
        btnTema.setStyle("-fx-background-color: transparent; -fx-font-size: 20px;");
        BorderPane.setAlignment(btnTema, Pos.TOP_RIGHT);
        BorderPane.setMargin(btnTema, new Insets(10));
        layout.setTop(btnTema);

        btnTema.setOnAction(e -> {
            if (layout.getScene() != null) {
                if (btnTema.getText().equals("\uD83C\uDF19")) {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/dark.css");
                    btnTema.setText("\u2600"); // ☀️
                } else {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/light.css");
                    btnTema.setText("\uD83C\uDF19"); // 🌙
                }
            }
        });

        // ======= FOTO DE PERFIL =======
        byte[] imagemSalva = UsuarioDAO.carregarFoto(emailUsuario);
        if (imagemSalva != null && imagemSalva.length > 0) {
            fotoPerfilView = new ImageView(new Image(new ByteArrayInputStream(imagemSalva)));
        } else {
            fotoPerfilView = new ImageView(new Image("https://cdn-icons-png.flaticon.com/512/149/149071.png"));
        }

        fotoPerfilView.setFitHeight(120);
        fotoPerfilView.setFitWidth(120);

        Circle clip = new Circle(60, 60, 60);
        fotoPerfilView.setClip(clip);
        fotoPerfilView.setEffect(new DropShadow(10, Color.GRAY));

        btnAlterarFoto = new Button("Alterar foto");
        btnAlterarFoto.setOnAction(e -> selecionarImagem(emailUsuario));

        VBox fotoBox = new VBox(10, fotoPerfilView, btnAlterarFoto);
        fotoBox.setAlignment(Pos.CENTER);

        // ======= EMAIL DO USUÁRIO =======
        String nomeUsuario = UsuarioDAO.obterNomePorEmail(emailUsuario);
        lblEmail = new Label(nomeUsuario != null ? nomeUsuario + " (" + emailUsuario + ")" : emailUsuario);
        lblEmail.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        VBox emailBox = new VBox(lblEmail);
        emailBox.setAlignment(Pos.CENTER);
        emailBox.setPadding(new Insets(10, 0, 20, 0));

        // ======= TROCAR SENHA =======
        Label lblSenhaAtual = new Label("Senha atual:");
        txtSenhaAtual = new PasswordField();
        txtSenhaAtual.setPromptText("Digite sua senha atual");
        txtSenhaAtual.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblNovaSenha = new Label("Nova senha:");
        txtNovaSenha = new PasswordField();
        txtNovaSenha.setPromptText("Digite sua nova senha");
        txtNovaSenha.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        btnAlterarSenha = new Button("Alterar senha");
        btnAlterarSenha.setStyle("-fx-background-color: #0a22ac; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAlterarSenha.setOnAction(e -> alterarSenha(emailUsuario));

        VBox senhaBox = new VBox(10, lblSenhaAtual, txtSenhaAtual, lblNovaSenha, txtNovaSenha, btnAlterarSenha);
        senhaBox.setAlignment(Pos.CENTER);
        senhaBox.setPadding(new Insets(20));
        senhaBox.setMaxWidth(300);

        // ======= FORMA DE PAGAMENTO =======
        btnPagamento = new Button("Mudar forma de pagamento");
        btnPagamento.setStyle("-fx-background-color: #6c63ff; -fx-text-fill: white; -fx-font-weight: bold;");
        btnPagamento.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Em desenvolvimento");
            alert.setHeaderText(null);
            alert.setContentText("Tela de forma de pagamento ainda será implementada!");
            alert.showAndWait();
        });

        VBox mainBox = new VBox(25, fotoBox, emailBox, senhaBox, btnPagamento);
        mainBox.setAlignment(Pos.TOP_CENTER);

        layout.setCenter(mainBox);
    }

    public StackPane getView() {
        return new StackPane(layout);
    }

    // ======= ALTERAR FOTO =======
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

                mostrarAlerta(Alert.AlertType.INFORMATION, "Foto de perfil atualizada com sucesso!");
            } catch (IOException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao carregar imagem!");
            }
        }
    }

    // ======= ALTERAR SENHA =======
    private void alterarSenha(String emailUsuario) {
        String senhaAtual = txtSenhaAtual.getText();
        String novaSenha = txtNovaSenha.getText();

        if (senhaAtual.isEmpty() || novaSenha.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Por favor, preencha todos os campos.");
            return;
        }

        boolean senhaCorreta = UsuarioDAO.validarLogin(emailUsuario, senhaAtual);
        if (!senhaCorreta) {
            mostrarAlerta(Alert.AlertType.ERROR, "Senha atual incorreta!");
            return;
        }

        boolean sucesso = UsuarioDAO.alterarSenha(emailUsuario, novaSenha);
        mostrarAlerta(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                sucesso ? "Senha alterada com sucesso!" : "Erro ao alterar senha!");

        if (sucesso) {
            txtSenhaAtual.clear();
            txtNovaSenha.clear();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo, mensagem);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
