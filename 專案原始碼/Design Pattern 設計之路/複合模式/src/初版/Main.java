package 初版;

public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("root");
        Directory d1 = new Directory("d1");
        Directory d2 = new Directory("d2");

        d1.addChild(new File("JavaIsGreat", "Very good."));
        d1.addChild(new File("JavaIsBeautiful", "Very good."));
        d1.addChild(new File("JavaIsGreat", "Very good."));

        Directory home = new Directory("home");
        d2.addChild(home);
        Directory jenkins = new Directory("jenkins");
        jenkins.addChild(new File("pipeline", "The pipeline document \n version 1.5"));
        jenkins.addChild(new File("strategy.txt", "The strategy pattern is trivial."));
        home.addChild(jenkins);

        root.addChild(d1);
        root.addChild(d2);
        root.addChild(new File("ThankYouSubscriber", "2020/1/5, Please subscribe my channel\n    if you like the videos :)"));

        CommandLineSystem cmd = new CommandLineSystem(root, root);
        cmd.start();
    }
}
