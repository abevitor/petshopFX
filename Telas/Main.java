package Telas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("PetShop Brainrot");

        // Tela inicial: login
        LoginView login = new LoginView();
        Scene scene = new Scene(login.getView(), 900, 650);
        scene.getStylesheets().add("css/light.css"); // tema padrão

        stage.setScene(scene);
        stage.show();
    }

    // Método para trocar telas
    public static void trocarTela(String tela) {
        switch (tela) {
            case "login" -> stage.setScene(new Scene(new LoginView().getView(), 900, 650));
            case "cadastro" -> stage.setScene(new Scene(new CadastroView().getView(), 900, 650));
            case "principal" -> stage.setScene(new Scene(new PrincipalView().getView(), 900, 650));
            case "configuracoes" -> stage.setScene(new Scene(new ConfiguracoesView().getView(), 900, 650));
        }
    }
}
