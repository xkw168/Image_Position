package com.example.xkw.imagepos;

import java.io.File;

public class LocalImage{
    // Table Name
    public static final String TABLE_NAME= "local_images";

    private Long ID;

    /**
     * The flag indicate whether a image is selected.
     */
    private boolean isSelected;
    /**
     * The timestamp of when the Image item was generated.
     */
    private Long DATE_ADDED;

    /**
     * The abstraction of image data.
     * The value is an `ImageData` instance.
     */
    private ImageData IMAGE_DATA;

    /**
     * The id of the bucket (folder) that the image belongs to.
     * This field is only available with `getFromStorage` provider.
     */
    private Integer BUCKET_ID;

    /**
     * The name of the bucket (folder) that the image belongs to.
     * This field is only available with `getFromStorage` provider.
     */
    private String BUCKET_NAME;

    /**
     * The id of the image in Android media database.
     * This field is only available with `getFromStorage` provider.
     */
    private Integer IMAGE_ID;

    /**
     * The name of the image.
     * This field is only available with `getFromStorage` provider.
     */
    private String IMAGE_NAME;

    /**
     * The file path of the image.
     * This field is only available with `getFromStorage` provider.
     */
    private String IMAGE_PATH;

    /**
     * The longitude of the image taken
     */
    private double LONGITUDE;

    /**
     * The latitude of the image taken
     */
    private double LATITUDE;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getDATE_ADDED() {
        return DATE_ADDED;
    }

    public void setDATE_ADDED(Long DATE_ADDED) {
        this.DATE_ADDED = DATE_ADDED;
    }

    public ImageData getIMAGE_DATA() {
        return IMAGE_DATA;
    }

    public void setIMAGE_DATA(ImageData IMAGE_DATA) {
        this.IMAGE_DATA = IMAGE_DATA;
    }

    public Integer getBUCKET_ID() {
        return BUCKET_ID;
    }

    public void setBUCKET_ID(Integer BUCKET_ID) {
        this.BUCKET_ID = BUCKET_ID;
    }

    public String getBUCKET_NAME() {
        return BUCKET_NAME;
    }

    public void setBUCKET_NAME(String BUCKET_NAME) {
        this.BUCKET_NAME = BUCKET_NAME;
    }

    public Integer getIMAGE_ID() {
        return IMAGE_ID;
    }

    public void setIMAGE_ID(Integer IMAGE_ID) {
        this.IMAGE_ID = IMAGE_ID;
    }

    public String getIMAGE_NAME() {
        return IMAGE_NAME;
    }

    public void setIMAGE_NAME(String IMAGE_NAME) {
        this.IMAGE_NAME = IMAGE_NAME;
    }

    public String getIMAGE_PATH() {
        return IMAGE_PATH;
    }

    public void setIMAGE_PATH(String IMAGE_PATH) {
        this.IMAGE_PATH = IMAGE_PATH;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public LocalImage(Long dateAdded, ImageData imageData) {
        this.setDATE_ADDED(dateAdded);
        this.setIMAGE_DATA(imageData);
        isSelected = false;
    }

    /*public File toFile(){
        ImageData imageData = getIMAGE_DATA();
        return new File(imageData.getFilepath());
    }*/
}
