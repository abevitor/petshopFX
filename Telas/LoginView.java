package Telas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginView {
    private BorderPane layout;

    public LoginView() {
        layout = new BorderPane();

        // ===== Botão Modo Escuro/Claro =====
        Button btnTema = new Button("\uD83C\uDF19");
        btnTema.setOnAction(e -> {
            if (layout.getScene() != null) {
                if (btnTema.getText().equals("\uD83C\uDF19")) {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/dark.css");
                    btnTema.setText("\u2600");
                } else {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/light.css");
                    btnTema.setText("\uD83C\uDF19");
                }
            }
        });

        
        layout.setTop(btnTema);
        BorderPane.setAlignment(btnTema, Pos.TOP_LEFT);

        // ===== Campos centrais =====
        VBox centro = new VBox(10);
        centro.setAlignment(Pos.CENTER);

        Label lblUser = new Label("Usuário:");
        TextField textField = new TextField();
        textField.setMaxWidth(500);
        textField.setStyle(
            "-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;"
        );

        Label lblSenha = new Label("Senha:");
        PasswordField localSenha = new PasswordField();
        localSenha.setMaxWidth(500);
        localSenha.setStyle(
            "-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;"
        );

        Button btnLogin = new Button("Entrar");
        Button btnCadastro = new Button("Cadastrar");
        btnCadastro.setOnAction(e -> Main.trocarTela("cadastro"));

        centro.getChildren().addAll(lblUser, textField, lblSenha, localSenha, btnLogin, btnCadastro);

        // Coloca o VBox central no centro do BorderPane
        layout.setCenter(centro);
    }

    public BorderPane getView() {
        return layout;
    }
}
