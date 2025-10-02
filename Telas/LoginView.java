package Telas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {
    private VBox layout;

    public LoginView() {
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label lblUser = new Label(" Usuário:");
        TextField textField = new TextField();

        Label lblSenha = new Label("Senha:");
         PasswordField LocalSenha = new PasswordField();

         Button btnLogin = new Button("Entrar");
         Button btnCadastro = new Button("Cadastrar");

         btnCadastro.setOnAction(e -> Main.trocarTela("cadastro"));

         layout.getChildren().addAll(lblUser, textField, lblSenha, LocalSenha, btnLogin, btnCadastro);

         
    }

    public VBox getView(){
        return layout;
    }
      
        
}
