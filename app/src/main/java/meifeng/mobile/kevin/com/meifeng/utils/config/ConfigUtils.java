package meifeng.mobile.kevin.com.meifeng.utils.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.mvp.main.leftmenu.view.SelfActivity;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.model.ShoppingCartModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;

public class ConfigUtils {

    // Account
    public static void saveUserInfo(String userName, String pwd) {
        SharedPreferences sp = getLocalSharedPreerences("K_USER_INFO", SelfApplication.getContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("K_USER_NAME", userName);
        editor.putString("K_USER_PWD", pwd);
        editor.commit();
    }

    public static String[] getUserNameAndPwd() {
        SharedPreferences sp = getLocalSharedPreerences("K_USER_INFO", SelfApplication.getContext());
        String username = sp.getString("K_USER_NAME", "");
        String pwd = sp.getString("K_USER_PWD", "");
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            return new String[]{username, pwd};
        } else {
            return new String[]{};
        }
    }

    // choose city
    public static void saveChooseCity(String cityName) {
        SharedPreferences sp = getLocalSharedPreerences("K_USER_CHOOSE_CITY", SelfApplication.getContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("K_USER_CITY", cityName);
        editor.commit();
    }

    // 获取选择的城市
    public static String getChooseCity() {
        SharedPreferences sp = getLocalSharedPreerences("K_USER_CHOOSE_CITY", SelfApplication.getContext());
        String cityName = sp.getString("K_USER_CITY", "");
        return cityName;
    }

    private static final String K_LOCAL_CART_SP = "k_LOCAL_CART";
    private static final String K_LOCAL_CART_LIST_STRING = "K_LOCAL_CART_LIST_STR";

    // 缓存购物车数据
    // 1. 获取本地已存在的购物车
    // 2.新的商品加入到购物车
    // 3. 缓存新的数据到购物车
    // 4 区别用户
    public static boolean addProductToCart(MallModel.productModel newProductModel, int count) {
        if (newProductModel != null) {
            newProductModel.setCountForCart(count);
            // 用户区别用户
            String FINAL_KEY = K_LOCAL_CART_LIST_STRING + SelfApplication.user.getMobile();

            Gson gson = new Gson();
            SharedPreferences sp = getLocalSharedPreerences(K_LOCAL_CART_SP, SelfApplication.getContext());
            List<MallModel.productModel> listCart = new ArrayList<>();

            if (sp != null) {
                String localCartListJsonStr = sp.getString(FINAL_KEY, "");
                if (!TextUtils.isEmpty(localCartListJsonStr)) {
                    listCart = gson.fromJson(localCartListJsonStr, new TypeToken<List<MallModel.productModel>>() {
                    }.getType());//把JSON格式的字符串转为List

                    // 若是product id 相同，那么数量= 原本的count + count
                    boolean isSameWithBefore = false;
                    for (MallModel.productModel p : listCart) {
                        if (newProductModel.getId().equals(p.getId())) {
                            isSameWithBefore = true;
                            p.setCountForCart(p.getCountForCart() + newProductModel.getCountForCart()); // 数量+count
                        }
                    }

                    if (!isSameWithBefore) {
                        listCart.add(newProductModel);
                    }
                } else {
                    listCart.add(newProductModel);
                }
            }
            String listStr = gson.toJson(listCart);
            return sp.edit().putString(FINAL_KEY, listStr + "").commit();
        }
        return false;
    }

    //获取本地购物车
    // 1.区别用户
    // 2.获取即可
    public static ArrayList<MallModel.productModel> getLocalCartList() {
        // 用户区别用户
        String FINAL_KEY = K_LOCAL_CART_LIST_STRING + SelfApplication.user.getMobile();
        SharedPreferences sp = getLocalSharedPreerences(K_LOCAL_CART_SP, SelfApplication.getContext());

        ArrayList<MallModel.productModel> listCart = new ArrayList<>();

        String localCartListJsonStr = sp.getString(FINAL_KEY, "");
        if (!TextUtils.isEmpty(localCartListJsonStr)) {
            Gson gson = new Gson();
            listCart = gson.fromJson(localCartListJsonStr, new TypeToken<List<MallModel.productModel>>() {
            }.getType());//把JSON格式的字符串转为List
        }

        return listCart;
    }

    // 更新cart list
    public static boolean upateCartProduct(ArrayList<ShoppingCartModel> cartList) {

        if (cartList != null) {
            // 用户区别用户
            String FINAL_KEY = K_LOCAL_CART_LIST_STRING + SelfApplication.user.getMobile();
            SharedPreferences sp = getLocalSharedPreerences(K_LOCAL_CART_SP, SelfApplication.getContext());

            Gson gson = new Gson();
            List<MallModel.productModel> listCart = new ArrayList<>();

            if (cartList.size() == 0) { // 全部清空
                return sp.edit().putString(FINAL_KEY, "").commit();
            } else { // 过滤商品model, 保存
                for (int i = 0; i < cartList.size(); i++) {
                    ShoppingCartModel cartModel = cartList.get(i);
                    listCart.add(cartModel.getProductModel());
                }
                String listStr = gson.toJson(listCart);
                return sp.edit().putString(FINAL_KEY, listStr + "").commit();
            }
        }
        return false;
    }

    //清空购物车
    public static boolean clearCart() {
        // 用户区别用户
        String FINAL_KEY = K_LOCAL_CART_LIST_STRING + SelfApplication.user.getMobile();
        SharedPreferences sp = getLocalSharedPreerences(K_LOCAL_CART_SP, SelfApplication.getContext());
        return sp.edit().putString(FINAL_KEY, "").commit();
    }

    // Get SharedPreferences
    public static SharedPreferences getLocalSharedPreerences(String sharedPre, Context c) {
        return c.getSharedPreferences(sharedPre, Context.MODE_PRIVATE);
    }

}
