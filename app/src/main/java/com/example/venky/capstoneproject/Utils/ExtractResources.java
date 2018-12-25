package com.example.venky.capstoneproject.Utils;

import android.util.Log;

import com.example.venky.capstoneproject.Models.Ingreds;
import com.example.venky.capstoneproject.Models.MealInfo;

import java.util.ArrayList;

/**
 * Created by VENKY on 12/18/2018.
 */

public class ExtractResources {
    public static ArrayList<Ingreds> extractData(ArrayList<MealInfo> list){
        String im_url="https://www.themealdb.com/images/ingredients/";
        ArrayList<Ingreds> ingList=new ArrayList<>();

        if (list.get(0).getStrIngredient1().length()>1){
            String image=im_url+list.get(0).getStrIngredient1()+".png";
            Log.i("image",image);
            String name=list.get(0).getStrIngredient1();
            String mesure=list.get(0).getStrMeasure1();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient2().length()>1){
            String image=im_url+list.get(0).getStrIngredient2()+".png";
            String name=list.get(0).getStrIngredient2();
            String mesure=list.get(0).getStrMeasure2();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient3().length()>1){
            String image=im_url+list.get(0).getStrIngredient3()+".png";
            String name=list.get(0).getStrIngredient3();
            String mesure=list.get(0).getStrMeasure3();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient4().length()>1){
            String image=im_url+list.get(0).getStrIngredient4()+".png";
            String name=list.get(0).getStrIngredient4();
            String mesure=list.get(0).getStrMeasure4();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient5().length()>1){
            String image=im_url+list.get(0).getStrIngredient5()+".png";
            String name=list.get(0).getStrIngredient5();
            String mesure=list.get(0).getStrMeasure5();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        //=============================================
        if (list.get(0).getStrIngredient6().length()>1){
            String image=im_url+list.get(0).getStrIngredient6()+".png";
            Log.i("image",image);
            String name=list.get(0).getStrIngredient6();
            String mesure=list.get(0).getStrMeasure6();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient7().length()>1){
            String image=im_url+list.get(0).getStrIngredient7()+".png";
            String name=list.get(0).getStrIngredient7();
            String mesure=list.get(0).getStrMeasure7();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient8().length()>1){
            String image=im_url+list.get(0).getStrIngredient8()+".png";
            String name=list.get(0).getStrIngredient8();
            String mesure=list.get(0).getStrMeasure8();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient9().length()>1){
            String image=im_url+list.get(0).getStrIngredient9()+".png";
            String name=list.get(0).getStrIngredient9();
            String mesure=list.get(0).getStrMeasure9();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient10().length()>1){
            String image=im_url+list.get(0).getStrIngredient10()+".png";
            String name=list.get(0).getStrIngredient10();
            String mesure=list.get(0).getStrMeasure10();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient11().length()>1){
            String image=im_url+list.get(0).getStrIngredient11()+".png";
            Log.i("image",image);
            String name=list.get(0).getStrIngredient11();
            String mesure=list.get(0).getStrMeasure11();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient12().length()>1){
            String image=im_url+list.get(0).getStrIngredient12()+".png";
            String name=list.get(0).getStrIngredient12();
            String mesure=list.get(0).getStrMeasure12();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient13().length()>1){
            String image=im_url+list.get(0).getStrIngredient13()+".png";
            String name=list.get(0).getStrIngredient13();
            String mesure=list.get(0).getStrMeasure13();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient14().length()>1){
            String image=im_url+list.get(0).getStrIngredient14()+".png";
            String name=list.get(0).getStrIngredient14();
            String mesure=list.get(0).getStrMeasure14();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient15().length()>1){
            String image=im_url+list.get(0).getStrIngredient15()+".png";
            String name=list.get(0).getStrIngredient15();
            String mesure=list.get(0).getStrMeasure15();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        //=============================================
        if (list.get(0).getStrIngredient16().length()>1){
            String image=im_url+list.get(0).getStrIngredient16()+".png";
            Log.i("image",image);
            String name=list.get(0).getStrIngredient16();
            String mesure=list.get(0).getStrMeasure16();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient17().length()>1){
            String image=im_url+list.get(0).getStrIngredient17()+".png";
            String name=list.get(0).getStrIngredient17();
            String mesure=list.get(0).getStrMeasure17();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient18().length()>1){
            String image=im_url+list.get(0).getStrIngredient18()+".png";
            String name=list.get(0).getStrIngredient18();
            String mesure=list.get(0).getStrMeasure18();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient19().length()>1){
            String image=im_url+list.get(0).getStrIngredient19()+".png";
            String name=list.get(0).getStrIngredient19();
            String mesure=list.get(0).getStrMeasure19();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        if (list.get(0).getStrIngredient20().length()>1){
            String image=im_url+list.get(0).getStrIngredient20()+".png";
            String name=list.get(0).getStrIngredient20();
            String mesure=list.get(0).getStrMeasure20();
            Ingreds ingreds=new Ingreds(image,name,mesure);
            ingList.add(ingreds);
        }
        return ingList;
    }
}

