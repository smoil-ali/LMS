package com.example.lms.Model;

import com.example.lms.ui.addcourses.BasicFragment;
import com.example.lms.ui.addcourses.MediaFragment;

import java.util.ArrayList;
import java.util.List;

public class Container {

    public static  BasicFragmentModel model = new BasicFragmentModel();
    public static String listOfRequirements="[]";
    public static String listOfOutcomes="[]";
    public static PriceFragmentModel priceFragmentModel = new PriceFragmentModel();
    public static MediaFragmentModel mediaFragmentModel = new MediaFragmentModel();
    public static SeoModelClass seoModelClass = new SeoModelClass();

    public static SeoModelClass getSeoModelClass() {
        return seoModelClass;
    }

    public static void setSeoModelClass(SeoModelClass seoModelClass) {
        Container.seoModelClass = seoModelClass;
    }

    public static void setModel(BasicFragmentModel model) {
        Container.model = model;
    }

    public static BasicFragmentModel getModel() {
        return model;
    }

    public static String getListOfRequirements() {
        return listOfRequirements;
    }

    public static void setListOfRequirements(String listOfRequirements) {
        Container.listOfRequirements = listOfRequirements;
    }

    public static PriceFragmentModel getPriceFragmentModel() {
        return priceFragmentModel;
    }

    public static void setPriceFragmentModel(PriceFragmentModel priceFragmentModel) {
        Container.priceFragmentModel = priceFragmentModel;
    }

    public static MediaFragmentModel getMediaFragmentModel() {
        return mediaFragmentModel;
    }

    public static void setMediaFragmentModel(MediaFragmentModel mediaFragmentModel) {
        Container.mediaFragmentModel = mediaFragmentModel;
    }

    public static String getListOfOutcomes() {
        return listOfOutcomes;
    }

    public static void setListOfOutcomes(String listOfOutcomes) {
        Container.listOfOutcomes = listOfOutcomes;
    }
}
