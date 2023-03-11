package Localization;

import java.time.LocalDateTime;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * this is the FrenchResourceBundle class. it is used to translate language from english to french based on the systems current language settings
 */
public class FrenchResourceBundle extends ListResourceBundle {
@Override

/**
 * this is the getContents method. it is used to get all contents from this bundle and translate language on the login form.
 */

protected Object[][] getContents(){
    return contents;
    }
    public static void main(String[] args){

    }

    private Object[][] contents = {
            {"Login", "Écran"},
            {"Screen, De Connexion"},
            {"UserName", "Nom d'utilisateur"},
            {"Password", "le mot de passe"},
            {"Exit", "sortir"},
            {"Location", "localisation actuelle"},
    };
}
