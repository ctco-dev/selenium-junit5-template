package qa.pages;

public enum Pages {

    //common pages
    LOGIN_PAGE("index.php?route=account/login");

    private final String postfix;

    Pages(String postfix) {
        this.postfix = postfix;
    }

    public String getPostfix() {
        return postfix;
    }

}
