package Telas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;
    private static Scene login;
    private static Scene cadastro;

    public Main() {}

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        LoginView loginView = new LoginView();
        CadastroView cadastroView = new CadastroView();

        login = new Scene(loginView.getView(), 900, 800);
        cadastro = new Scene(cadastroView.getView(), 900, 800);

       
        login.getStylesheets().add("css/light.css");
        cadastro.getStylesheets().add("css/light.css");

        primaryStage.setTitle("PetShop Brainrot");
        primaryStage.setScene(login);
        primaryStage.show();
    }

    public static void trocarTela(String tela) {
        if ("cadastro".equals(tela)) {
            stage.setScene(cadastro);
        } else if ("login".equals(tela)) {
            stage.setScene(login);
        }
    }

    public static Scene getSceneAtual() {
        return stage.getScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
