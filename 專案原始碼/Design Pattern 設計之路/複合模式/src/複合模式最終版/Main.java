package 複合模式最終版;

public class Main {
    public static void main(String[] args) {
        FileSystem distributedFileSystem = new FileSystem("dfs");
        FileSystem fs1 = new FileSystem("fs1");
        FileSystem fs2 = new FileSystem("fs2");

        fs1.addChild(new StandardFile("JavaIsGreat", "Very good."));
        fs1.addChild(new StandardFile("JavaIsBeautiful", "Very good."));
        fs1.addChild(new StandardFile("JavaIsGreat", "Very good."));

        Directory home = new StandardDirectory("home");
        File strategyTxt = new StandardFile("strategy.txt", "The strategy pattern is trivial.");
        fs2.addChild(home);
        Directory jenkins = new StandardDirectory("jenkins");
        jenkins.addChild(new StandardFile("pipeline", "The pipeline document \n version 1.5"));
        jenkins.addChild(strategyTxt);
        home.addChild(jenkins);

        distributedFileSystem.addChild(fs1);
        distributedFileSystem.addChild(fs2);
        distributedFileSystem.addChild(new StandardFile("ThankYouSubscriber", "2020/1/5, Please subscribe my channel\n    if you like the videos :)"));
        distributedFileSystem.addChild(new FileShortcut(strategyTxt));
        distributedFileSystem.addChild(new DirectoryShortcut(jenkins));
        CommandLineSystem cmd = new CommandLineSystem(distributedFileSystem, distributedFileSystem);
        cmd.start();
    }
}
