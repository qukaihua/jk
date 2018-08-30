package com.qu.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.qu.bean.Order;
import com.qu.bean.OrderItem;
import com.qu.bean.Pay;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.dao.OrderItemMapper;
import com.qu.dao.OrderMapper;
import com.qu.dao.PayMapper;
import com.qu.service.IOrderService;
import com.qu.util.BigdecimalUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/28 0028.
 */
@Service
public class OrderServiceImpl implements IOrderService {
    public Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
      @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    PayMapper payMapper;
    @Override
    public String pay(int userid, int orderno, String path) {
        System.out.print(userid);
        Configs.init("zfbinfo.properties");
         AlipayTradeService tradeService;
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        Order order = orderMapper.selectbyuseridorderno(userid,orderno);
        if(null==order){
            return JSONObject.toJSONString(Result.getfailed("no this order"));
        }
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(orderno);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "凯华的店";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "18888";

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";
        Double totalprice=0.0;

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        List<OrderItem> orderItemList = orderItemMapper.selectbyuseridorderno(userid,orderno);
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        for(OrderItem orderItem:orderItemList){
            GoodsDetail itemgoods = GoodsDetail.newInstance(orderItem.getProductId().toString(),orderItem.getProductName(),orderItem.getCprice().longValue(),orderItem.getQuantity());
            goodsDetailList.add(itemgoods);
            totalprice += BigdecimalUtil.mut(orderItem.getCprice().doubleValue(),orderItem.getQuantity().doubleValue());
        }
        totalAmount = totalprice.toString();
        System.out.println("总价"+totalAmount);


        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://v8ik55.natappfree.cc/shopp/order/callback")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format(path+"\\"+"qr-%s.png",
                        response.getOutTradeNo());
                log.info("filePath:" + filePath);
                System.out.println(filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return  "ok";

    }

    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    public Result CheckParams(Map<String,String> map){
        String out_trade_no = map.get("out_trade_no");
        String trade_no = map.get("trade_no");
        String trade_status = map.get("trade_status");
        Order order = orderMapper.selectbyorderno(Integer.parseInt(out_trade_no));
        if(null==order){
            return Result.getfailed("no this order");
        }
        if(order.getStatus()>= Integer.parseInt(Const.pay_status.have_pay.getCode())){
            return Result.getsuccessful("no repeat callback");
        }
       if(trade_status.equals(Const.Success_Pay)){
           order.setPaymentTime(new Date());
           order.setPaymentType(1);
           order.setStatus(Integer.parseInt(Const.pay_status.have_pay.getCode()));
           orderMapper.updateByPrimaryKeySelective(order);
       }
        Pay payinfo = new Pay();
        payinfo.setOrderNo(Long.valueOf(out_trade_no));
        payinfo.setUserId(order.getUserId());
        payinfo.setPayPlatform(1);
        payinfo.setPlatformStatus(trade_status);
        payMapper.insertSelective(payinfo);
        return Result.getsuccessful();




    }
}