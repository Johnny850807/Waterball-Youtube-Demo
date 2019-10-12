package 初版;

public class Main {
    public static void main(String[] args) {
        Dictionary toeic =
                new Dictionary("toeic",
                    new Topic("supply chain",
                            new Word("retailer"), new Word("wholesaler"), new Word("manufacturer")));


        Topic emotions = new Topic("emotions",
                                    new Word("sad"), new Word("happy"), new Word("excited"));

        User user = new User();
        user.addFavorite(toeic);
        user.addFavorite(emotions);
        user.addFavorite(new Word("waterball"));

        user.searchWord("e");
    }

}

