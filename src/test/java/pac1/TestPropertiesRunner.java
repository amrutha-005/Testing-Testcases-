package pac1;

import java.io.IOException;

public class TestPropertiesRunner {

    public static void main(String[] args) {
        try {
            // 1. Load the properties file
            Repository_readfromproperties.loadproperties();

            // 2. Fetch locators using keys
            String usernameLocator = Repository_readfromproperties.getlocator("username_id");
            String passwordLocator = Repository_readfromproperties.getlocator("password_id");
            String loginBtnLocator = Repository_readfromproperties.getlocator("login_btn_xpath");

            // 3. Print values to console
            System.out.println("Username Locator: " + usernameLocator);
            System.out.println("Password Locator: " + passwordLocator);
            System.out.println("Login Button Locator: " + loginBtnLocator);

        } catch (IOException e) {
            System.out.println("Error reading properties file: " + e.getMessage());
        }
    }
}