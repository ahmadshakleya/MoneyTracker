package visualisation;

public class VisualMain {
    public static void main(String[] args) throws InterruptedException {
        MainFrame mainFrame = new MainFrame();
        while (mainFrame.isSwitchToOverView() == false) {
            mainFrame.GotoNext();
            Thread.sleep(3000);
        }
        System.out.println(mainFrame.isSwitchToOverView());
        ShowPossibilities possibilitiesFrame = new ShowPossibilities();
    }
}
