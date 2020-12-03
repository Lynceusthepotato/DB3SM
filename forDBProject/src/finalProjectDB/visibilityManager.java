package finalProjectDB;

public class visibilityManager {

    mainUI mUI;

    public visibilityManager(mainUI mainUI){
        mUI = mainUI;
    }

    public void visibilityatStart() {
        // Show
        mUI.loginMenu.setVisible(true);

        // Hide
        mUI.menuPanel.setVisible(false);
        mUI.registerPanel.setVisible(false);
        mUI.productPanel.setVisible(false);

    }

    public void visibilitytoRegister(){
        // Show
        mUI.registerPanel.setVisible(true);

        // Hide
        mUI.loginMenu.setVisible(false);
        mUI.menuPanel.setVisible(false);
        mUI.productPanel.setVisible(false);
    }

    public void visibilityAtProduct() {
        // Show
        mUI.productPanel.setVisible(true);

        // Hide
        mUI.loginMenu.setVisible(false);
        mUI.menuPanel.setVisible(false);
        mUI.registerPanel.setVisible(false);
    }

    public void visibilityAtMenu() {
        // Show
        mUI.menuPanel.setVisible(true);

        // Hide
        mUI.loginMenu.setVisible(false);
        mUI.productPanel.setVisible(false);
        mUI.registerPanel.setVisible(false);
    }

}
