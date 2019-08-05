package top.blesslp.com.sample.presenter;

public class Banner {
    private String desc;//         :"一起来做个App吧",
    private String id;//         :10,
    private String imagePath;//         :"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
    private String isVisible;//         :1,
    private String order;//         :0,
    private String title;//         :"一起来做个App吧",
    private String type;//         :0,
    private String url;//         :"http://www.wanandroid.com/blog/show/2"

    @Override
    public String toString() {
        return "Banner{" +
                "desc='" + desc + '\'' +
                ", id='" + id + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", isVisible='" + isVisible + '\'' +
                ", order='" + order + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
