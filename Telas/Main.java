package Telas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;
    private static Scene login;
    private static Scene cadastro;
    private static Scene principal;

    public Main() {}

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        LoginView loginView = new LoginView();
        CadastroView cadastroView = new CadastroView();
        PrincipalView principalView = new PrincipalView();

        login = new Scene(loginView.getView(), 900, 800);
        cadastro = new Scene(cadastroView.getView(), 900, 800);
        principal = new Scene(principalView.getView(), 900, 650);

       
        login.getStylesheets().add("css/light.css");
        cadastro.getStylesheets().add("css/light.css");
        principal.getStylesheets().addAll("css./light.css");

        primaryStage.setTitle("PetShop Brainrot");
        primaryStage.setScene(login);
        primaryStage.show();
    }

        public static void trocarTela(String nomeTela) {
        switch (nomeTela) {
            case "login":
                stage.setScene(login);
                break;
            case "cadastro":
                stage.setScene(cadastro);
                break;
            case "principal":
                stage.setScene(principal);
                break;
            default:
                System.out.println("Tela desconhecida: " + nomeTela);
        }
    }

    public static Scene getSceneAtual() {
        return stage.getScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
