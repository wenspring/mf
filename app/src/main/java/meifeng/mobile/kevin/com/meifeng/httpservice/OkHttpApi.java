package meifeng.mobile.kevin.com.meifeng.httpservice;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import meifeng.mobile.kevin.com.meifeng.base.BaseModelStatusNet;
import meifeng.mobile.kevin.com.meifeng.base.SelfApplication;
import meifeng.mobile.kevin.com.meifeng.ext.Fields;
import meifeng.mobile.kevin.com.meifeng.httpservice.callback.CallBackForOk;
import meifeng.mobile.kevin.com.meifeng.httpservice.requesthttp.OkhttpUtil;
import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserInfoModel;
import meifeng.mobile.kevin.com.meifeng.mvp.login.model.UserModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.city.model.CityModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.CancelCollectionModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.favorite.model.FavoriteModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.ControlDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.DecorateOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.model.NewOderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.home.view.CancelDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.CancelProductOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.CompleteDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyCDecorateOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.myorder.model.MyOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.PayForDecorateModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.SendOrderModelNewNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model.AddShopFavoriteModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.OrderSuccessModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.SubmitProOrderResponseModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.AddFavoriteModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.productDetail.comment.adapter.CommentModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.sendNewOrder.model.SendOrderModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.model.ShopModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shop.ShopProductDetailNet;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingCart.sub_create_order.model.ProductOrderModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModel;
import meifeng.mobile.kevin.com.meifeng.mvp.main.shoppingMall.model.MallModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.register.model.CheckMobileModelNet;
import meifeng.mobile.kevin.com.meifeng.mvp.register.model.RegisterModelNet;
import okhttp3.Response;

public class OkHttpApi {

    /**
     * api key set
     */
    public static final boolean DEBUG = true;
    public static final String APP_VERSION = "1";

    // 7niu domain
    public static final String sevenNiuDomain11 = "http://p4530f2i3.bkt.clouddn.com/";//old
    public static final String sevenNiuDomain22 = "http://pdvblcafu.bkt.clouddn.com/";//old
    public static final String sevenNiuDomain = "http://qiniu.zsptserver.top/";

    /********* ************  ***********  *********** *********/

    // API HTTP
    public static final String develop = "http://120.78.140.194:60000/";//dev
    public static final String test = "http://122.114.78.106/";//test
    public static final String product = "";//prd

    //  public static final String logServerUrl = "http://sjoffice.wiltechs.com:30082/BarCode/public/api.php/api/upload";

    // 环境（开发develop，测试test，生产product）
    public static final String SERVER_URL = develop;
    public static final String head = SERVER_URL + "api/";

    // api interface
    private static final String loginUrl = head + "Token"; // 登录
    private static final String homeListUrl = head + "Home"; // 商城主页信息
    private static final String registerUrl = head + "user/regist";// 注册
    private static final String getCityUrl = head + "Common/place"; // 城市
    private static final String getUserInfoUrl = head + "user/my"; // 用户信息
    private static final String submitNewOrderUrl = head + "order/decoration/add/"; // 发布装修订单
    private static final String getProductListByBrandIdUrl = head + "product/classification";// 品牌下的所有商品
    private static final String getProductDetailByShopIdProductIdUrl = head + "product/detail";// 获取商品详情
    private static final String addFavoriteForProductUrl = head + "collection/product/add"; // 加入收藏-产品
    private static final String addFavoriteForShopUrl = head + "collection/shop/add"; // 加入收藏 - 店铺
    private static final String getShopDetailInfoUrl = head + "shop/detail"; // 店铺详情
    private static final String getShopBrandListUrl = head + "classification/byshop"; // 店铺所有品牌
    private static final String getShopProductListUrl = head + "product/list"; // 店铺所有商品
    private static final String getProductCommentListUrl = head + "evaluate/product/"; // (根据 product id)商品的品论
    private static final String getMyCollectUrl = head + "collection/myCollection"; // 获取收藏(商铺，店铺)
    private static final String addOrderForProductUrl = head + "order/shopping/add"; // 添加购物订单x
    private static final String getNewOrderUrl = head + "order/decoration/status/payed"; // 获取最新的装修订单
    private static final String checkMobileIsRegUrl = head + "user/checkmobile/"; // 验证手机号码是否注册
    private static final String getMyDecorateListUrl = head + "order/decoration/my"; // 获取我的装修订单
    private static final String getMyProductOrderListUrl = head + "order/shopping/my"; // 获取我的购物订单
    private static final String cancelMyDecorateOrderUrl = head + "order/decoration/delete"; // 取消我发布的装修订单
    private static final String cancelMyProductOrderUrl = head + "order/shopping/delete"; // 取消我未付款的商品订单
    private static final String payForProductOrderUrl = head + "order/shopping/payed"; // 付款(商品)
    private static final String payForDecorateOrderUrl = head + "order/decoration/payed"; // 付款（装修)
    private static final String getNotUseProductListUrl = head + "product/notuse"; // 获取没有消耗的材料
    private static final String cancelCollectionUrl = head + "collection/delete"; // 取消收藏
    private static final String controlDecorateOrderUrl = head + "order/decoration/receipt"; // 承接订单
    private static final String getMyControlDecorateOrderUrl = head + "order/decoration/my/receipt"; // 我的承接的订单list
    private static final String completeDecorateOrderUrl = head + "order/decoration/completed"; // 完成装修订单
    private static final String sureProductReceiptUrl = head + "order/shopping/completed"; // 商品订单收货

    // ******  API METHOD  ******
    public static void login(String userName, String pwd, CallBackForOk<UserModelNet> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("useraccount", userName);
        params.put("userpassword", pwd);
        OkhttpUtil.okHttpPost(loginUrl, params, getHeaders_Client(), listener);
    }

    // ******  API METHOD  ******

    // 商城首页数据
    public static void homeList(String productStart, String productPageSize, String brandStart, String brandPageSize, CallBackForOk<MallModelNet> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("productindex", productStart);
        params.put("productpage", productPageSize);
        params.put("classindex", brandStart);
        params.put("classpage", brandPageSize);
        OkhttpUtil.okHttpPost(homeListUrl, params, getHeaders_Token(), listener);
    }

    // 注册用户
    public static void registerUser(String userName,
                                    String mobile,
                                    String pwd,
                                    String commpanyName,
                                    String cardFaceImg,
                                    String cardBackImg,
                                    String buinessImg,
                                    String carId,
                                    String head,
                                    String companyMobile,
                                    String Location,
                                    boolean isBusiness,
                                    CallBackForOk<RegisterModelNet> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("Name", userName);
        params.put("Mobile", mobile);
        params.put("Password", pwd);
        params.put("CompanyName", commpanyName);

        params.put("FaceOfIDCard", cardFaceImg);
        params.put("BackOfIDCard", cardBackImg);
        params.put("BusinessLicense", buinessImg);
        //params.put("CompanyName", carId);
        params.put("Head", head);
        params.put("TelePhoneNumber", companyMobile);
        params.put("Location", Location);

        params.put("IsBusiness", isBusiness == true ? "true" : "false");
        OkhttpUtil.okHttpPost(registerUrl, params, null, listener);
    }

    // 获取城市列表
    public static void getAllCitys(CallBackForOk<CityModelNet> listener) {
        OkhttpUtil.okHttpGet(getCityUrl, null, getHeaders_Token(), listener);
    }

    // 获取登录用户信息
    public static void getMyInfo(CallBackForOk<UserInfoModel> listener) {
        OkhttpUtil.okHttpGet(getUserInfoUrl, null, getHeaders_Token(), listener);
    }

    // 发布订单
    public static void submitNewOrder(String title,
                                      String desc,
                                      float amount,
                                      boolean isOtherAmount,
                                      float otherAmount,
                                      ArrayList<String> imgPlaces,
                                      boolean isChooseMateriel,
                                      ArrayList<String> materielIds,
                                      String endDate,
                                      String provice,
                                      String city,
                                      String street,
                                      CallBackForOk<SendOrderModelNewNet> listener) {

        SendOrderModelNet.OrderModel model = new SendOrderModelNet().getOrderModel();
        model.setTitle(title);
        model.setComment(desc);
        model.setWorkPrice(amount);
        model.setHasOtherPrice(isOtherAmount);
        model.setOtherPrice(otherAmount);
        model.setHasMaterial(isChooseMateriel);

        if (imgPlaces != null && imgPlaces.size() > 0) {
            model.setImages(imgPlaces);
        }
        if (materielIds != null && materielIds.size() > 0) {
            model.setMaterialIds(materielIds);
            model.setMaterialTotal(materielIds.size());
        }

        model.setDoneDate(endDate);
        model.setProvince(provice);
        model.setCity(city);
        model.setStreet(street);

        OkhttpUtil.okHttpPost(submitNewOrderUrl, getJsonString(model), getHeaders_Token(), listener);
    }

    // 根据品牌ID 获取品牌下的商品信息
    public static void getProdcutListByBrandId(String brandId, String pageIndex, String pageSize, CallBackForOk<ArrayList<MallModel.productModel>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("classificationId", brandId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        OkhttpUtil.okHttpGet(getProductListByBrandIdUrl, params, getHeaders_Token(), listener);
    }

    // 获取商品详情，根据商品id, 店铺id
    public static void getProductDetailByShopIdProductId(String shopId, String productId, CallBackForOk<MallModel.productModel> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("shopId", shopId);
        params.put("productId", productId);
        OkhttpUtil.okHttpPost(getProductDetailByShopIdProductIdUrl, params, getHeaders_Token(), listener);
    }

    // 收藏商品
    public static void addFavoriteForProduct(String productId, CallBackForOk<BaseModelStatusNet> listener) {
        //Map<String, String> params = new HashMap<>();
        //params.put("ProductID", productId);
        AddFavoriteModelNet modelNet = new AddFavoriteModelNet();
        modelNet.setProductID(productId);
        OkhttpUtil.okHttpPost(addFavoriteForProductUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 收藏店铺
    public static void addFavoriteForStop(String shopId, CallBackForOk<AddShopFavoriteModelNet> listener) {
        //Map<String, String> params = new HashMap<>();
        //params.put("shopId", shopId);
        AddShopFavoriteModelNet modelNet = new AddShopFavoriteModelNet();
        modelNet.setShopID(shopId);
        OkhttpUtil.okHttpPost(addFavoriteForShopUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 获取店铺详情
    public static void getShopInfoByShopId(String shopId, CallBackForOk<ShopModel> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("shopId", shopId);
        OkhttpUtil.okHttpGet(getShopDetailInfoUrl, params, getHeaders_Token(), listener);
    }

    // 获取店铺下的所有品牌
    public static void getShopBrandListByShopId(String shopId, CallBackForOk<ArrayList<MallModel.brandModel>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("shopId", shopId);
        OkhttpUtil.okHttpGet(getShopBrandListUrl, params, getHeaders_Token(), listener);
    }

    // 获取店铺下的所有产品
    public static void getShopProductListByShopId(String shopId, String pageIndex, String pageSize, CallBackForOk<ArrayList<MallModel.productModel>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("shopId", shopId);
        //params.put("sortId", "");
        //params.put("keyword", "");
        //params.put("typeId", "");
        params.put("page", pageIndex);
        params.put("pageSize", pageSize);
        OkhttpUtil.okHttpGet(getShopProductListUrl, params, getHeaders_Token(), listener);
    }

    // 根据产品ID，获取评论列表信息
    public static void getProductCommentList(String productId, CallBackForOk<ArrayList<CommentModel>> listener) {
        //Map<String, String> params = new HashMap<>();
        //params.put("productId", productId);
        OkhttpUtil.okHttpGet(getProductCommentListUrl + "/" + productId, null, getHeaders_Token(), listener);
    }

    // 提交商品订单
    public static void submitProductOrder(ProductOrderModel model, CallBackForOk<SubmitProOrderResponseModelNet> listener) {
        OkhttpUtil.okHttpPost(addOrderForProductUrl, getJsonString(model), getHeaders_Token(), listener);
    }

    // 我的收藏数据 ( collectionType 查询类型 0为查询商品收藏, 1为查询店铺收藏)
    public static void getMyCollectionList(String keyword, String collectionType, String pageSize, String pageIndex, CallBackForOk<FavoriteModelNet> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("collectionType", collectionType);
        params.put("pageSize", pageSize);
        params.put("pageIndex", pageIndex);
        if (!TextUtils.isEmpty(keyword)) {
            params.put("keyword", keyword);
        }
        OkhttpUtil.okHttpGet(getMyCollectUrl, params, getHeaders_Token(), listener);
    }

    // 验证手机号码是否注册
    public static void checkMobileIsReg(String mobile, CallBackForOk<CheckMobileModelNet> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        OkhttpUtil.okHttpPost(checkMobileIsRegUrl, params, getHeaders_Token(), listener);
    }

    //获取最新的装修订单
    public static void getNewOrder(int pageIndex, int pageSize, CallBackForOk<ArrayList<DecorateOrderModel>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize + "");
        OkhttpUtil.okHttpGet(getNewOrderUrl, params, getHeaders_Token(), listener);
    }

    // 获取我的装修订单
    public static void getMyDecorateList(int pageIndex, int pageSize, CallBackForOk<ArrayList<DecorateOrderModel>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize + "");
        OkhttpUtil.okHttpGet(getMyDecorateListUrl, params, getHeaders_Token(), listener);
    }

    // 获取我的商品订单
    public static void getMyProductOrderList(int pageIndex, int pageSize, CallBackForOk<ArrayList<MyOrderModelNet>> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize + "");
        //params.put("status",  "");
        OkhttpUtil.okHttpGet(getMyProductOrderListUrl, params, getHeaders_Token(), listener);
    }

    //取消已发布的装修订单
    public static void cancelMyNoPayDecorateOrder(String id, CallBackForOk<CancelDecorateOrderModelNet> listener) {
        CancelDecorateOrderModelNet modelNet = new CancelDecorateOrderModelNet();
        modelNet.setDecorationOrderID(id);
        OkhttpUtil.okHttpPost(cancelMyDecorateOrderUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    //取消未付款的商品订单
    public static void cancelMyNoPayProductOrder(String id, CallBackForOk<CancelProductOrderModelNet> listener) {
        CancelProductOrderModelNet modelNet = new CancelProductOrderModelNet();
        modelNet.setShoppingOrderID(id);
        OkhttpUtil.okHttpPost(cancelMyProductOrderUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 付款接口（商品）
    public static void payForProductByOrderId(String orderId, float total, String payType, CallBackForOk<OrderSuccessModelNet> listener) {
        CancelProductOrderModelNet modelNet = new CancelProductOrderModelNet();
        modelNet.setShoppingOrderID(orderId);
        OkhttpUtil.okHttpPost(payForProductOrderUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 付款接口（装修）
    public static void payForDecorateByOrderId(String orderId, float total, String payType, CallBackForOk<PayForDecorateModelNet> listener) {
        PayForDecorateModelNet modelNet = new PayForDecorateModelNet();
        modelNet.setDecorationOrderID(orderId);
        modelNet.setPayType(payType);
        modelNet.setMoney(total);
        OkhttpUtil.okHttpPost(payForDecorateOrderUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 获取未消耗的材料
    public static void getNotUseProduct(CallBackForOk<Response> listener) {
        OkhttpUtil.okHttpGet(getNotUseProductListUrl, null, getHeaders_Token(), listener);
    }

    //取消收藏
    public static void cancelCollection(String collectionId, CallBackForOk<CancelCollectionModelNet> listener) {
        CancelCollectionModelNet modelNet = new CancelCollectionModelNet();
        modelNet.setCollectionID(collectionId);
        OkhttpUtil.okHttpPost(cancelCollectionUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 抢装修订单
    public static void controlDecorateOrder(String orderId, CallBackForOk<ControlDecorateOrderModelNet> listener) {
        ControlDecorateOrderModelNet modelNet = new ControlDecorateOrderModelNet();
        modelNet.setDecorationOrderID(orderId);
        OkhttpUtil.okHttpPost(controlDecorateOrderUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 获取我承接的装修订单
    public static void getMyControlDecorateOrderList(int pageSize, int pageIndex, CallBackForOk<MyCDecorateOrderModelNet> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize + "");
        OkhttpUtil.okHttpGet(getMyControlDecorateOrderUrl, params, getHeaders_Token(), listener);
    }

    // 完成装修订单
    public static void completeDecorateOrder(String orderId, CallBackForOk<CompleteDecorateOrderModelNet> listener) {
        CompleteDecorateOrderModelNet modelNet = new CompleteDecorateOrderModelNet();
        modelNet.setDecorationOrderID(orderId);
        OkhttpUtil.okHttpPost(completeDecorateOrderUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    // 确认收货
    public static void sureReceipt(String orderId, CallBackForOk<CancelProductOrderModelNet> listener){
        CancelProductOrderModelNet modelNet = new CancelProductOrderModelNet();
        modelNet.setShoppingOrderID(orderId);
        OkhttpUtil.okHttpPost(sureProductReceiptUrl, getJsonString(modelNet), getHeaders_Token(), listener);
    }

    //  ********************** 分割线 *************************

    /**
     * 登陸的 CLIENT KEY & SECRET
     *
     * @return
     */
    private static Map<String, String> getHeaders_Token() {
        Map<String, String> headers = new HashMap<>();
        if (SelfApplication.isLoginNow()){
            headers.put(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + SelfApplication.user.getToken());
        }else {
            headers.put(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + "");
        }

        return headers;
    }

    /**
     * 登陸 Bearer
     *
     * @return
     */
    private static Map<String, String> getHeaders_Client() {
        Map<String, String> headers = new HashMap<>();
        headers.put(Fields.STRING_REQUEST_Authorization, Fields.STRING_REQUEST_TOKEN_prefix + "test_login");
        return headers;
    }

    /**
     * 将obj转化为json
     *
     * @param obj
     * @return
     */
    private static String getJsonString(Object obj) {
        Gson g = new Gson();
        return g.toJson(obj);
    }

}
