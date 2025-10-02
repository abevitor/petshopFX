package Telas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
   private static Stage stage;
   private static Scene login;
   private static Scene cadastro;

   public Main() {
   }

   public void start(Stage var1) {
      stage = var1;
      LoginView var2 = new LoginView();
      CadastroView var3 = new CadastroView();
      login = new Scene(var2.getView(), 400.0, 250.0);
      cadastro = new Scene(var3.getView(), 400.0, 300.0);
      var1.setTitle("PetShop Brainrot");
      var1.setScene(login);
      var1.show();
   }

   public static void trocarTela(String var0) {
      if (var0.equals("cadastro")) {
         stage.setScene(cadastro);
      } else if (var0.equals("login")) {
         stage.setScene(login);
      }

   }

   public static void main(String[] var0) {
      launch(var0);
   }
}


