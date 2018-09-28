package qa.pages;

public enum Pages {

    //common pages
    TEMPLATE_PAGE("#");

    private final String postfix;

    Pages(String postfix) {
        this.postfix = postfix;
    }

    public String getPostfix() {
        return postfix;
    }

}
