package com.coolcollege.aar.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;

import com.coolcollege.aar.callback.ILocationCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LocationManager {
    private ILocationCallback locationCallback; // 声明一个接口属性 用于定位数据回调
    private static LocationManager locationManager = new LocationManager(); // 单例类的对象
    private static Activity activity; // 接收外界activity

    public android.location.LocationManager systemLocationManager; // 系统定位 管理器
    public SystemLocationListener systemLocationListener; // 系统定位 监听器

    // 类似单例类
    public static LocationManager shareLocationManager(Activity act) {
        // 先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口
        activity = act;
        return locationManager;
    }

    // 定义一个公开方法 由外界设置LocationManager的回调
    public void setLocationCallback(ILocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    // 外界获取定位信息
    public void getLocationInfo() {
        getLocationBySystem();
    }

    // 获取定位数据（通过安卓系统api）
    private void getLocationBySystem() {
        systemLocationManager = (android.location.LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        systemLocationManager.getProviders(true);
        systemLocationManager.getProvider(android.location.LocationManager.GPS_PROVIDER);
        systemLocationManager.getProvider(android.location.LocationManager.NETWORK_PROVIDER);
        systemLocationManager.getProvider(android.location.LocationManager.PASSIVE_PROVIDER);

        Criteria criteria = new Criteria();
        // Criteria是一组筛选条件
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置定位精准度
        criteria.setAltitudeRequired(false);
        //是否要求海拔
        criteria.setBearingRequired(true);
        //是否要求方向
        criteria.setCostAllowed(true);
        //是否要求收费
        criteria.setSpeedRequired(true);
        //是否要求速度
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        //设置电池耗电要求
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        //设置方向精确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        //设置速度精确度
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        //设置水平方向精确度
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        //设置垂直方向精确度

        //返回满足条件的当前设备可用的provider，第二个参数为false时返回当前设备所有provider中最符合条件的那个provider，但是不一定可用
        systemLocationManager.getBestProvider(criteria, true);

        // 获取位置信息，调用监听方法
        systemLocationListener = new SystemLocationListener();
        if (systemLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            systemLocationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 5, 10, systemLocationListener);
        }
    }

    // 注册一个位置监听器来接受结果
    private final class SystemLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            // 系统定位回调经纬度: 纬度：34.19817548417356 | 经度：108.88292359880214
//            Log.d("getLocationBySystem - ", "系统定位回调经纬度: 纬度：" + latitude + " | 经度：" + longitude);

            // 反地理编码
            Geocoder geocoder = new Geocoder(activity);
            boolean flag = Geocoder.isPresent();
            if (flag) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String sAddress;//地址
                        if (!TextUtils.isEmpty(address.getLocality())) {
                            if (!TextUtils.isEmpty(address.getFeatureName())) {
//                                Locale locale = address.getLocale(); // zh_CN
                                String featureName = address.getFeatureName(); // 老潼关肉夹馍(都市之门店)都市之门C座
                                String adminArea = address.getAdminArea(); // 陕西省
//                                String subAdminArea = address.getSubAdminArea(); // 丈八沟街道
                                String locality = address.getLocality(); // 西安市
                                String subLocality = address.getSubLocality(); // 雁塔区
                                String thoroughfare = address.getThoroughfare(); // 锦业路
                                String subThoroughfare = address.getSubThoroughfare(); // 1号
                                String countryCode = address.getCountryCode(); // CN
                                String countryName = address.getCountryName(); // 中国
                                double latitude2 = address.getLatitude(); // 34.197178
                                double longitude2 = address.getLongitude(); // 108.883653
                                sAddress = adminArea + locality + subLocality + thoroughfare + subThoroughfare + featureName;
                                // 定位信息数据字典设置
                                HashMap locationInfo = new HashMap();
                                locationInfo.put("latitude", latitude2);
                                locationInfo.put("longitude", longitude2);
                                locationInfo.put("countryCode", countryCode);
                                locationInfo.put("address", sAddress);
                                locationInfo.put("country", countryName);
                                locationInfo.put("province", adminArea);
                                locationInfo.put("city", locality);
                                locationInfo.put("district", subLocality);
                                locationInfo.put("street", thoroughfare+subThoroughfare);
                                locationInfo.put("place", featureName);
                                // 回调定位信息
                                locationCallback.onSuccessCallback(locationInfo);
                            }
                        } else {
                            sAddress = "定位失败";
                            HashMap locationInfo = new HashMap();
                            locationInfo.put("error", sAddress);
                            locationCallback.onErrorCallback(locationInfo);
                        }
                    }
                } catch (IOException e) {

                }
            }
        }
    }
}
