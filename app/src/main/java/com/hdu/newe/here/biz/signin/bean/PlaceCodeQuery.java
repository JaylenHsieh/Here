package com.hdu.newe.here.biz.signin.bean;

import com.hdu.newe.here.page.main.signin.LocationBean;

/**
 * 保存所有教学楼的所有教室的经纬度坐标
 * @author pope
 * @date 2018/8/23
 */

public class PlaceCodeQuery {

    public LocationBean PlaceCodeQuery(String placeCode) {

        LocationBean location = new LocationBean();

        switch (placeCode.substring(0, 2)) {
            //不同教学楼的不同情况
            case "01":
                break;
            case "02":
                break;
            case "03":
                break;
            case "06":
                break;
            case "07":
                if (placeCode.substring(2, 3).equals("4")) {
                    //由于4楼的教室号相较于其他楼层的教室号有2的差值 故矫正一下
                    placeCode = String.valueOf(Integer.valueOf(placeCode) + 2);
                    if (placeCode.length() == 4) {
                        placeCode = "0" + placeCode;
                    }
                    if (placeCode.length() == 6) {
                        switch (placeCode) {
                            case "071011":
                                location.setLatitude(30.3205218740);
                                location.setLongitude(120.3502606017);
                                break;
                            case "071021":
                                location.setLatitude(30.3207548851);
                                location.setLongitude(120.3502611437);
                                break;
                            default:
                                break;
                        }
                    }
                }
                switch (placeCode.substring(3, 5)) {
                    case "06":
                        location.setLatitude(30.3208730515);
                        location.setLongitude(120.3504830123);
                        break;
                    case "08":
                        location.setLatitude(30.3208776825);
                        location.setLongitude(120.3506063939);
                        break;
                    case "10":
                        location.setLatitude(30.3208718938);
                        location.setLongitude(120.3507364810);
                        break;
                    case "22":
                        location.setLatitude(30.3209980864);
                        location.setLongitude(120.3511414946);
                        break;
                    case "20":
                        location.setLatitude(30.3209957709);
                        location.setLongitude(120.3510301830);
                        break;
                    case "18":
                        location.setLatitude(30.3207676979);
                        location.setLongitude(120.3511535646);
                        break;
                    case "16":
                        location.setLatitude(30.3207676979);
                        location.setLongitude(120.3510449351);
                        break;
                    case "01":
                        location.setLatitude(30.3205218740);
                        location.setLongitude(120.3502606017);
                        break;
                    case "02":
                        location.setLatitude(30.3207548851);
                        location.setLongitude(120.3502611437);
                        break;
                    case "05":
                        location.setLatitude(30.3203999128);
                        location.setLongitude(120.3504674517);
                        break;
                    case "07":
                        location.setLatitude(30.3203779158);
                        location.setLongitude(120.3505747400);
                        break;
                    case "09":
                        location.setLatitude(30.3203767580);
                        location.setLongitude(120.3506766640);
                        break;
                    case "11":
                        location.setLatitude(30.3203802312);
                        location.setLongitude(120.3507685297);
                        break;
                    case "15":
                        location.setLatitude(30.3204844275);
                        location.setLongitude(120.3504701339);
                        break;
                    case "17":
                        location.setLatitude(30.3204809542);
                        location.setLongitude(120.3505640112);
                        break;
                    case "19":
                        location.setLatitude(30.3204809542);
                        location.setLongitude(120.3506511830);
                        break;
                    case "21":
                        location.setLatitude(30.3204844274);
                        location.setLongitude(120.3507718824);
                        break;
                    case "27":
                        location.setLatitude(30.3204705346);
                        location.setLongitude(120.3511433684);
                        break;
                    case "29":
                        location.setLatitude(30.3204705346);
                        location.setLongitude(120.3511433684);
                        break;
                    default:
                        break;
                }
                break;
            case "08":
                break;
            case "09":
                break;
            case "10":
                break;
            case "11":
                break;
            case "12":
                break;
            default:
                return null;
        }
        return location;
    }
}
