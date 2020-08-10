package com.example.lms.Model;

import com.example.lms.ui.addcourses.BasicFragment;
import com.example.lms.ui.addcourses.MediaFragment;

import java.util.ArrayList;
import java.util.List;

public class Container {

    public static  BasicFragmentModel model = new BasicFragmentModel();
    public static List<String> listOfRequirements=new ArrayList<>();
    public static List<String> listOfOutcomes=new ArrayList<>();
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

    public static List<String> getListOfRequirements() {
        return listOfRequirements;
    }

    public static void setListOfRequirements(List<String> listOfRequirements) {
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

    public static List<String> getListOfOutcomes() {
        return listOfOutcomes;
    }

    public static void setListOfOutcomes(List<String> listOfOutcomes) {
        Container.listOfOutcomes = listOfOutcomes;
    }
}
