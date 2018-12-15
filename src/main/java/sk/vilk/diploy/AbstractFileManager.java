package sk.vilk.diploy;

public class AbstractFileManager {

    private String extension;

    public AbstractFileManager(String extension) {
        this.extension = extension;
    }

    public String createFileNameFromObject(Object object) {
        return object.getClass().getSimpleName().toLowerCase() + getExtension();
    }

    public String createFileNameFromClass(Class clazz) {
        return clazz.getSimpleName().toLowerCase() + getExtension();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
