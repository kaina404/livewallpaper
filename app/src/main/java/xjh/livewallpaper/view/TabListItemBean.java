package xjh.livewallpaper.view;


import android.graphics.Bitmap;

public class TabListItemBean {

    private String text;
    private int selectedColor;
    private int unSelectColor;
    private Bitmap selectedBitmap;
    private Bitmap unSelectBitmap;
    private Object tag;
    private String selectedUrl;
    private String unSelectUrl;

    public String getSelectedUrl() {
        return selectedUrl;
    }

    public void setSelectedUrl(String selectedUrl) {
        this.selectedUrl = selectedUrl;
    }

    public String getUnSelectUrl() {
        return unSelectUrl;
    }

    public void setUnSelectUrl(String unSelectUrl) {
        this.unSelectUrl = unSelectUrl;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public TabListItemBean() {

    }

    public Bitmap getSelectedBitmap() {
        return selectedBitmap;
    }

    public void setSelectedBitmap(Bitmap selectedBitmap) {
        this.selectedBitmap = selectedBitmap;
    }

    public Bitmap getUnSelectBitmap() {
        return unSelectBitmap;
    }

    public void setUnSelectBitmap(Bitmap unSelectBitmap) {
        this.unSelectBitmap = unSelectBitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getUnSelectColor() {
        return unSelectColor;
    }

    public void setUnSelectColor(int unSelectColor) {
        this.unSelectColor = unSelectColor;
    }
}
