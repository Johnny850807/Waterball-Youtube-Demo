package 複合模式版;

public class Main {
    public static void main(String[] args) {
        FileSystem distributedFileSystem = new FileSystem("dfs");
        FileSystem fs1 = new FileSystem("fs1");
        FileSystem fs2 = new FileSystem("fs2");

        fs1.addChild(new File("JavaIsGreat", "Very good."));
        fs1.addChild(new File("JavaIsBeautiful", "Very good."));
        fs1.addChild(new File("JavaIsGreat", "Very good."));

        Directory home = new StandardDirectory("home");
        File strategyTxt = new File("strategy.txt", "The strategy pattern is trivial.");
        fs2.addChild(home);
        Directory jenkins = new StandardDirectory("jenkins");
        jenkins.addChild(new File("pipeline", "The pipeline document \n version 1.5"));
        jenkins.addChild(strategyTxt);
        home.addChild(jenkins);

        distributedFileSystem.addChild(fs1);
        distributedFileSystem.addChild(fs2);
        distributedFileSystem.addChild(new File("ThankYouSubscriber", "2020/1/5, Please subscribe my channel\n    if you like the videos :)"));
        distributedFileSystem.addChild(new Link(strategyTxt));
        distributedFileSystem.addChild(new Link(jenkins));
        CommandLineSystem cmd = new CommandLineSystem(distributedFileSystem, distributedFileSystem);
        cmd.start();
    }
}
